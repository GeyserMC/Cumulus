/*
 * Copyright (c) 2020-2021 GeyserMC. http://geysermc.org
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

package org.geysermc.cumulus.util.impl;

import com.google.gson.Gson;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.Form;
import org.geysermc.cumulus.Forms;
import org.geysermc.cumulus.util.FormBuilder;
import org.geysermc.cumulus.util.FormType;

@Getter
public abstract class FormImpl implements Form {
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

  @Override
  public boolean isClosed(@Nullable String response) {
    return response == null || response.isEmpty() || response.equalsIgnoreCase("null");
  }

  public abstract static class Builder<T extends FormBuilder<T, F>, F extends Form>
      implements FormBuilder<T, F> {
    protected String title = "";

    protected BiFunction<String, String, String> translationHandler = null;
    protected BiConsumer<F, String> biResponseHandler;
    protected Consumer<String> responseHandler;
    protected String locale;

    @Override
    @NonNull
    public T title(@NonNull String title) {
      this.title = translate(Objects.requireNonNull(title, "title"));
      return self();
    }

    @Override
    @NonNull
    public T translator(
        @NonNull BiFunction<String, String, String> translator,
        @NonNull String locale) {
      this.translationHandler = Objects.requireNonNull(translator, "translator");
      this.locale = Objects.requireNonNull(locale, "locale");
      return title(title);
    }

    @Override
    @NonNull
    public T translator(@NonNull BiFunction<String, String, String> translator) {
      return translator(translator, locale);
    }

    @Override
    @NonNull
    public T responseHandler(@NonNull BiConsumer<F, String> responseHandler) {
      biResponseHandler = Objects.requireNonNull(responseHandler, "responseHandler");
      return self();
    }

    @Override
    @NonNull
    public T responseHandler(@NonNull Consumer<String> responseHandler) {
      this.responseHandler = Objects.requireNonNull(responseHandler, "responseHandler");
      return self();
    }

    @Override
    @NonNull
    public abstract F build();

    @Nullable
    protected String translate(@Nullable String text) {
      if (translationHandler != null && text != null && !text.isEmpty()) {
        return translationHandler.apply(text, locale);
      }
      return text;
    }

    @SuppressWarnings("unchecked")
    protected T self() {
      return (T) this;
    }
  }
}
