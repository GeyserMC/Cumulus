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
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.util.FormBuilder;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.response.result.InvalidFormResponseResult;
import org.geysermc.cumulus.response.result.ResultType;
import org.geysermc.cumulus.response.result.ValidFormResponseResult;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public abstract class FormImpl<R extends FormResponse> implements Form {
  protected @Nullable Consumer<FormResponseResult<R>> resultHandler;
  protected @Nullable Consumer<@Nullable String> rawResponseConsumer;

  private final String title;

  public FormImpl(String title) {
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

  public void callResultHandler(FormResponseResult<R> response) throws Exception {
    if (resultHandler != null) {
      resultHandler.accept(response);
    }
  }

  public void resultHandler(Consumer<FormResponseResult<R>> responseHandler) {
    this.resultHandler = Objects.requireNonNull(responseHandler);
  }

  @Override
  public String title() {
    return title;
  }

  public abstract static class Builder<
          B extends FormBuilder<B, F, R>, F extends Form, R extends FormResponse>
      implements FormBuilder<B, F, R> {

    protected String title = "";

    protected @Nullable BiFunction<String, String, @Nullable String> translationHandler;
    protected @Nullable String locale;

    protected @Nullable BiConsumer<F, FormResponseResult<R>> selectedResultHandler;

    protected @Nullable Consumer<F> closedResultHandlerConsumer;
    protected @Nullable BiConsumer<F, InvalidFormResponseResult<R>> invalidResultHandler;
    protected @Nullable BiConsumer<F, FormResponseResult<R>> closedOrInvalidResultHandler;
    protected @Nullable BiConsumer<F, R> validResultHandler;

    @Override
    public B title(String title) {
      this.title = translate(Objects.requireNonNull(title, "title"));
      return self();
    }

    @Override
    public B translator(BiFunction<String, String, @Nullable String> translator, String locale) {
      this.translationHandler = Objects.requireNonNull(translator, "translator");
      this.locale = Objects.requireNonNull(locale, "locale");
      return title(title);
    }

    @Override
    public B translator(BiFunction<String, String, @Nullable String> translator) {
      String locale = this.locale;
      if (locale == null) {
        throw new IllegalStateException("locale cannot be null");
      }
      return translator(translator, locale);
    }

    @Override
    public B closedResultHandler(Consumer<F> resultHandler) {
      this.closedResultHandlerConsumer = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    public B closedResultHandler(Runnable resultHandler) {
      Objects.requireNonNull(resultHandler, "resultHandler");
      return closedResultHandler($ -> resultHandler.run());
    }

    @Override
    public B invalidResultHandler(Runnable resultHandler) {
      Objects.requireNonNull(resultHandler, "resultHandler");
      return invalidResultHandler(($, $$) -> resultHandler.run());
    }

    @Override
    public B invalidResultHandler(Consumer<InvalidFormResponseResult<R>> resultHandler) {
      Objects.requireNonNull(resultHandler, "resultHandler");
      return invalidResultHandler(($, result) -> resultHandler.accept(result));
    }

    @Override
    public B invalidResultHandler(BiConsumer<F, InvalidFormResponseResult<R>> resultHandler) {
      this.invalidResultHandler = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    public B closedOrInvalidResultHandler(Runnable resultHandler) {
      Objects.requireNonNull(resultHandler, "resultHandler");
      return closedOrInvalidResultHandler(($, $$) -> resultHandler.run());
    }

    @Override
    public B closedOrInvalidResultHandler(Consumer<FormResponseResult<R>> resultHandler) {
      Objects.requireNonNull(resultHandler, "resultHandler");
      return closedOrInvalidResultHandler(($, result) -> resultHandler.accept(result));
    }

    @Override
    public B closedOrInvalidResultHandler(BiConsumer<F, FormResponseResult<R>> resultHandler) {
      this.closedOrInvalidResultHandler = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    public B validResultHandler(Consumer<R> resultHandler) {
      Objects.requireNonNull(resultHandler, "resultHandler");
      return validResultHandler(($, result) -> resultHandler.accept(result));
    }

    @Override
    public B validResultHandler(BiConsumer<F, R> resultHandler) {
      this.validResultHandler = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    public B resultHandler(BiConsumer<F, FormResponseResult<R>> resultHandler) {
      this.selectedResultHandler = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    public B resultHandler(
        BiConsumer<F, FormResponseResult<R>> resultHandler, ResultType[] selectedTypes) {
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
    public abstract F build();

    protected void setResponseHandler(FormImpl<R> impl, F form) {
      setResponseHandler(impl, form, null);
    }

    protected void setResponseHandler(
        FormImpl<R> impl, F form, @Nullable Consumer<R> validHandler) {
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

    protected String translate(String text) {
      Objects.requireNonNull(text, "text");

      if (translationHandler != null && !text.isEmpty()) {
        // locale cannot be null when translationHandler isn't null. See the #translator methods
        //noinspection DataFlowIssue
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
