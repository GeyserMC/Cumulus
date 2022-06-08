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

package org.geysermc.cumulus.form.impl.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.form.impl.FormImpl;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.util.FormImage;

public final class SimpleFormImpl extends FormImpl<SimpleFormResponse>
    implements SimpleForm {
  private final String content;
  private final List<ButtonComponent> buttons;

  public SimpleFormImpl(
      @NonNull String title,
      @NonNull String content,
      @NonNull List<ButtonComponent> buttons) {
    super(title);
    this.content = Objects.requireNonNull(content, "content");
    this.buttons = Collections.unmodifiableList(buttons);
  }

  @Override
  @NonNull
  public String content() {
    return content;
  }

  @Override
  @NonNull
  public List<ButtonComponent> buttons() {
    return buttons;
  }

  public static final class Builder
      extends FormImpl.Builder<SimpleForm.Builder, SimpleForm, SimpleFormResponse>
      implements SimpleForm.Builder {

    private final List<ButtonComponent> buttons = new ArrayList<>();
    private String content = "";

    @Override
    public Builder content(@NonNull String content) {
      this.content = translate(Objects.requireNonNull(content, "content"));
      return this;
    }

    @Override
    public Builder button(@NonNull ButtonComponent button) {
      buttons.add(button);
      return this;
    }

    @Override
    public Builder button(
        @NonNull String text,
        FormImage.@NonNull Type type,
        @NonNull String data) {
      return button(ButtonComponent.of(translate(text), type, data));
    }

    @Override
    public Builder button(@NonNull String text, @Nullable FormImage image) {
      return button(ButtonComponent.of(translate(text), image));
    }

    @Override
    public Builder button(@NonNull String text) {
      return button(ButtonComponent.of(translate(text)));
    }

    @Override
    public Builder optionalButton(@NonNull ButtonComponent button, boolean shouldAdd) {
      if (shouldAdd) {
        return button(button);
      }
      return addNullButton();
    }

    @Override
    public Builder optionalButton(
        @NonNull String text,
        FormImage.@NonNull Type type,
        @NonNull String data,
        boolean shouldAdd) {
      if (shouldAdd) {
        return button(text, type, data);
      }
      return addNullButton();
    }

    @Override
    public Builder optionalButton(
        @NonNull String text,
        @Nullable FormImage image,
        boolean shouldAdd) {
      if (shouldAdd) {
        return button(text, image);
      }
      return addNullButton();
    }

    @Override
    public Builder optionalButton(@NonNull String text, boolean shouldAdd) {
      if (shouldAdd) {
        return button(text);
      }
      return addNullButton();
    }

    @Override
    @NonNull
    public SimpleForm build() {
      SimpleFormImpl form = new SimpleFormImpl(title, content, buttons);
      setResponseHandler(form, form);
      return form;
    }

    private Builder addNullButton() {
      buttons.add(null);
      return this;
    }
  }
}
