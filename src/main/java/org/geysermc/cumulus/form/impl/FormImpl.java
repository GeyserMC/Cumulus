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

import com.google.gson.Gson;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.Forms;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.result.ClosedFormResponseResult;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.response.result.InvalidFormResponseResult;
import org.geysermc.cumulus.response.result.ValidFormResponseResult;
import org.geysermc.cumulus.util.FormBuilder;
import org.geysermc.cumulus.util.FormCodec;
import org.geysermc.cumulus.util.FormType;

@Getter
public abstract class FormImpl<F extends Form, R extends FormResponse> implements Form {
  protected static final Gson GSON = Forms.GSON;

  private final FormType type;
  protected String hardcodedJsonData = null;
  @Setter protected Consumer<String> responseHandler;

  public FormImpl(@NonNull FormType type) {
    this.type = Objects.requireNonNull(type, "type");
  }

  @Override
  @NonNull
  public String getJsonData() {
    if (hardcodedJsonData != null) {
      return hardcodedJsonData;
    }
    return GSON.toJson(this);
  }

  @NonNull
  protected abstract R resultToResponse(FormResponseResult<R> result);

  @Override
  @NonNull
  public final R parseResponse(@Nullable String response) {
    FormCodec<F, R> codec = FormDefinitions.instance().codecFor(type);
    return resultToResponse(codec.deserializeFormResponse((F) this, response));
  }

  @Override
  public void callResponseHandler(@Nullable String response) throws Exception {
    if (responseHandler != null) {
      responseHandler.accept(response);
    }
  }

  @Override
  public boolean isClosed(@Nullable String response) {
    return response == null || response.isEmpty() || "null".equalsIgnoreCase(response.trim());
  }

  public abstract static class Builder<B extends FormBuilder<B, F, R>, F extends Form, R extends FormResponse>
      implements FormBuilder<B, F, R> {

    protected String title = "";

    protected BiFunction<String, String, String> translationHandler = null;
    protected String locale;

    protected BiConsumer<F, String> biResponseHandler = null;
    protected Consumer<String> responseHandler;

    protected BiConsumer<F, FormResponseResult<R>> allBiResponseHandler;
    protected Consumer<FormResponseResult<R>> allResponseHandler;
    protected BiConsumer<F, ClosedFormResponseResult<R>> closedResponseHandler;
    protected BiConsumer<F, InvalidFormResponseResult<R>> invalidResponseHandler;
    protected BiConsumer<F, ValidFormResponseResult<R>> validResponseHandler;

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
    public B responseHandler(@NonNull BiConsumer<F, String> responseHandler) {
      biResponseHandler = Objects.requireNonNull(responseHandler, "responseHandler");
      return self();
    }

    @Override
    @NonNull
    public B responseHandler(@NonNull Consumer<String> responseHandler) {
      this.responseHandler = Objects.requireNonNull(responseHandler, "responseHandler");
      return self();
    }

    @Override
    public @NonNull B handleAllResponses(
        @NonNull BiConsumer<F, FormResponseResult<R>> responseHandler) {
      this.allBiResponseHandler = Objects.requireNonNull(responseHandler, "responseHandler");
      return self();
    }

    @Override
    public @NonNull B handleAllResponses(@NonNull Consumer<FormResponseResult<R>> responseHandler) {
      this.allResponseHandler = Objects.requireNonNull(responseHandler, "responseHandler");
      return self();
    }

    @Override
    @NonNull
    public abstract F build();

    protected <T extends FormImpl<F, R>> void setResponseHandler(T impl) {
      F form = (F) impl;

//      if (closedResponseHandler != null || invalidResponseHandler != null ||
//          validResponseHandler != null) {
//
//        form.setResponseHandler(data -> {
//          FormResponseResult<R> result = impl.getCodec().deserializeFormResponse(form, data);
//
//          if (allBiResponseHandler != null) {
//            allBiResponseHandler.accept(form, result);
//          }
//          if (allResponseHandler != null) {
//            allResponseHandler.accept(result);
//          }
//
//          if (result.isClosed() && closedResponseHandler != null) {
//            closedResponseHandler.accept(form, (ClosedFormResponseResult<R>) result);
//          }
//          if (result.isInvalid() && invalidResponseHandler != null) {
//            invalidResponseHandler.accept(form, (InvalidFormResponseResult<R>) result);
//          }
//          if (result.isValid() && validResponseHandler != null) {
//            validResponseHandler.accept(form, (ValidFormResponseResult<R>) result);
//          }
//        });
//        return;
//      }
//
//      if (allBiResponseHandler != null) {
//        form.setResponseHandler(data -> {
//          FormResponseResult<R> result = impl.getCodec().deserializeFormResponse(form, data);
//          allBiResponseHandler.accept(form, result);
//        });
//        return;
//      }
//
//      // todo remove the if once biResponseHandler and responseHandler have been removed
//      if (allResponseHandler != null) {
//        form.setResponseHandler(
//            data -> allResponseHandler.accept(impl.getCodec().deserializeFormResponse(form, data))
//        );
//        return;
//      }
//
//      if (biResponseHandler != null) {
//        form.setResponseHandler(response -> biResponseHandler.accept(form, response));
//      }

      form.setResponseHandler(responseHandler);
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
