/*
 * Copyright (c) 2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.util.FormBuilder;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.response.result.InvalidFormResponseResult;
import org.geysermc.cumulus.response.result.ResultType;
import org.geysermc.cumulus.response.result.ValidFormResponseResult;

public abstract class FormImpl<R extends FormResponse> implements Form {
  protected Consumer<FormResponseResult<R>> responseHandler;
  protected Consumer<@Nullable String> rawResponseConsumer;

  private final String title;

  public FormImpl(@NonNull String title) {
    this.title = Objects.requireNonNull(title, "title");
  }

  public boolean callRawResponseConsumer(@Nullable String responseData) throws Exception {
    if (rawResponseConsumer != null) {
      rawResponseConsumer.accept(responseData);
      return true;
    }
    return false;
  }

  public void rawResponseConsumer(Consumer<@Nullable String> rawResponseConsumer) {
    this.rawResponseConsumer = rawResponseConsumer;
  }

  public void callResultHandler(@Nullable FormResponseResult<R> response) throws Exception {
    if (responseHandler != null) {
      responseHandler.accept(response);
    }
  }

  public void resultHandler(@NonNull Consumer<FormResponseResult<R>> responseHandler) {
    this.responseHandler = Objects.requireNonNull(responseHandler);
  }

  @Override
  public @NonNull String title() {
    return title;
  }

  public abstract static class Builder<
          B extends FormBuilder<B, F, R>, F extends Form, R extends FormResponse>
      implements FormBuilder<B, F, R> {

    protected String title = "";

    protected BiFunction<String, String, String> translationHandler = null;
    protected String locale;

    protected BiConsumer<F, FormResponseResult<R>> selectedResultHandler;

    protected Consumer<F> closedResultHandlerConsumer;
    protected BiConsumer<F, InvalidFormResponseResult<R>> invalidResultHandler;
    protected BiConsumer<F, FormResponseResult<R>> closedOrInvalidResultHandler;
    protected BiConsumer<F, R> validResultHandler;

    @Override
    public B title(@NonNull String title) {
      this.title = translate(Objects.requireNonNull(title, "title"));
      return self();
    }

    @Override
    public B translator(
        @NonNull BiFunction<String, String, String> translator, @NonNull String locale) {
      this.translationHandler = Objects.requireNonNull(translator, "translator");
      this.locale = Objects.requireNonNull(locale, "locale");
      return title(title);
    }

    @Override
    public B translator(@NonNull BiFunction<String, String, String> translator) {
      return translator(translator, locale);
    }

    @Override
    public B closedResultHandler(@NonNull Consumer<F> resultHandler) {
      this.closedResultHandlerConsumer = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    public B closedResultHandler(@NonNull Runnable resultHandler) {
      Objects.requireNonNull(resultHandler, "resultHandler");
      return closedResultHandler($ -> resultHandler.run());
    }

    @Override
    public B invalidResultHandler(@NonNull Runnable resultHandler) {
      Objects.requireNonNull(resultHandler, "resultHandler");
      return invalidResultHandler(($, $$) -> resultHandler.run());
    }

    @Override
    public B invalidResultHandler(@NonNull Consumer<InvalidFormResponseResult<R>> resultHandler) {
      Objects.requireNonNull(resultHandler, "resultHandler");
      return invalidResultHandler(($, result) -> resultHandler.accept(result));
    }

    @Override
    public B invalidResultHandler(
        @NonNull BiConsumer<F, InvalidFormResponseResult<R>> resultHandler) {
      this.invalidResultHandler = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    public B closedOrInvalidResultHandler(@NonNull Runnable resultHandler) {
      Objects.requireNonNull(resultHandler, "resultHandler");
      return closedOrInvalidResultHandler(($, $$) -> resultHandler.run());
    }

    @Override
    public B closedOrInvalidResultHandler(@NonNull Consumer<FormResponseResult<R>> resultHandler) {
      Objects.requireNonNull(resultHandler, "resultHandler");
      return closedOrInvalidResultHandler(($, result) -> resultHandler.accept(result));
    }

    @Override
    public B closedOrInvalidResultHandler(
        @NonNull BiConsumer<F, FormResponseResult<R>> resultHandler) {
      this.closedOrInvalidResultHandler = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    public B validResultHandler(@NonNull Consumer<R> resultHandler) {
      Objects.requireNonNull(resultHandler, "resultHandler");
      return validResultHandler(($, result) -> resultHandler.accept(result));
    }

    @Override
    public B validResultHandler(@NonNull BiConsumer<F, R> resultHandler) {
      this.validResultHandler = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    public B resultHandler(@NonNull BiConsumer<F, FormResponseResult<R>> resultHandler) {
      this.selectedResultHandler = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    public B resultHandler(
        @NonNull BiConsumer<F, FormResponseResult<R>> resultHandler,
        @NonNull ResultType[] selectedTypes) {
      Objects.requireNonNull(resultHandler, "resultHandler");
      Objects.requireNonNull(selectedTypes, "selectedTypes");

      if (selectedTypes.length == 0) {
        return self();
      }

      EnumSet<ResultType> selected = EnumSet.noneOf(ResultType.class);
      selected.addAll(Arrays.asList(selectedTypes));

      this.selectedResultHandler =
          (form, response) -> {
            if (selected.contains(response.responseType())) {
              resultHandler.accept(form, response);
            }
          };
      return self();
    }

    @Override
    public abstract @NonNull F build();

    protected void setResponseHandler(@NonNull FormImpl<R> impl, @NonNull F form) {
      setResponseHandler(impl, form, null);
    }

    protected void setResponseHandler(
        @NonNull FormImpl<R> impl, @NonNull F form, @Nullable Consumer<R> validHandler) {
      impl.resultHandler(
          result -> {
            if (selectedResultHandler != null) {
              selectedResultHandler.accept(form, result);
            }

            if (result.isClosed()) {
              if (closedResultHandlerConsumer != null) {
                closedResultHandlerConsumer.accept(form);
              }
              if (closedOrInvalidResultHandler != null) {
                closedOrInvalidResultHandler.accept(form, result);
              }
            }

            if (result.isInvalid()) {
              if (invalidResultHandler != null) {
                invalidResultHandler.accept(form, (InvalidFormResponseResult<R>) result);
              }
              if (closedOrInvalidResultHandler != null) {
                closedOrInvalidResultHandler.accept(form, result);
              }
            }

            if (result.isValid()) {
              R response = ((ValidFormResponseResult<R>) result).response();
              if (validResultHandler != null) {
                validResultHandler.accept(form, response);
              }
              if (validHandler != null) {
                validHandler.accept(response);
              }
            }
          });
    }

    protected @NonNull String translate(@NonNull String text) {
      Objects.requireNonNull(text, "text");

      if (translationHandler != null && !text.isEmpty()) {
        String result = translationHandler.apply(text, locale);
        return result != null ? result : text;
      }

      return text;
    }

    @SuppressWarnings("unchecked")
    protected B self() {
      return (B) this;
    }
  }
}
