/*
 * Copyright (c) 2020-2022 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
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
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.response.result.InvalidFormResponseResult;
import org.geysermc.cumulus.response.result.ResultType;
import org.geysermc.cumulus.response.result.ValidFormResponseResult;
import org.geysermc.cumulus.util.FormBuilder;

public abstract class FormImpl<R extends FormResponse> implements Form {
  protected Consumer<FormResponseResult<R>> responseHandler;
  protected Consumer<String> rawResponseConsumer;

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

  public void rawResponseConsumer(Consumer<String> rawResponseConsumer) {
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

  public abstract static class Builder<B extends FormBuilder<B, F, R>, F extends Form, R extends FormResponse>
      implements FormBuilder<B, F, R> {

    protected String title = "";

    protected BiFunction<String, String, String> translationHandler = null;
    protected String locale;

    protected BiConsumer<F, FormResponseResult<R>> selectedResultHandler;
    protected Consumer<F> closedResponseHandler;
    protected BiConsumer<F, InvalidFormResponseResult<R>> invalidResponseHandler;
    protected BiConsumer<F, R> validResponseHandler;

    @Override
    @NonNull
    public B title(@NonNull String title) {
      this.title = translate(Objects.requireNonNull(title, "title"));
      return self();
    }

    @Override
    @NonNull
    public B translator(
        @NonNull BiFunction<String, String, String> translator,
        @NonNull String locale) {
      this.translationHandler = Objects.requireNonNull(translator, "translator");
      this.locale = Objects.requireNonNull(locale, "locale");
      return title(title);
    }

    @Override
    @NonNull
    public B translator(@NonNull BiFunction<String, String, String> translator) {
      return translator(translator, locale);
    }

    @Override
    @NonNull
    public B closedResultHandler(@NonNull Consumer<F> resultHandler) {
      this.closedResponseHandler = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    @NonNull
    public B invalidResultHandler(
        @NonNull BiConsumer<F, InvalidFormResponseResult<R>> resultHandler) {
      this.invalidResponseHandler = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    @NonNull
    public B validResultHandler(@NonNull BiConsumer<F, R> resultHandler) {
      this.validResponseHandler = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    @NonNull
    public B resultHandler(@NonNull BiConsumer<F, FormResponseResult<R>> resultHandler) {
      this.selectedResultHandler = Objects.requireNonNull(resultHandler, "resultHandler");
      return self();
    }

    @Override
    @NonNull
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

      this.selectedResultHandler = (form, response) -> {
        if (selected.contains(response.responseType()))  {
          resultHandler.accept(form, response);
        }
      };
      return self();
    }


    @Override
    @NonNull
    public abstract F build();

    protected void setResponseHandler(FormImpl<R> impl, F form) {
      if (closedResponseHandler != null || invalidResponseHandler != null ||
          validResponseHandler != null) {

        impl.resultHandler(result -> {
          if (selectedResultHandler != null) {
            selectedResultHandler.accept(form, result);
          }

          if (result.isClosed() && closedResponseHandler != null) {
            closedResponseHandler.accept(form);
          }
          if (result.isInvalid() && invalidResponseHandler != null) {
            invalidResponseHandler.accept(form, (InvalidFormResponseResult<R>) result);
          }
          if (result.isValid() && validResponseHandler != null) {
            validResponseHandler.accept(form, ((ValidFormResponseResult<R>) result).response());
          }
        });
        return;
      }

      if (selectedResultHandler != null) {
        impl.resultHandler(result -> selectedResultHandler.accept(form, result));
      }
    }

    @NonNull
    protected String translate(@NonNull String text) {
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
