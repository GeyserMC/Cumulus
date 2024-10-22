/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.form.impl.FormImpl;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.util.FormImage;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class SimpleFormImpl extends FormImpl<SimpleFormResponse> implements SimpleForm {
  private final String content;
  private final List<@Nullable ButtonComponent> buttons;

  public SimpleFormImpl(String title, String content, List<@Nullable ButtonComponent> buttons) {
    super(title);
    this.content = Objects.requireNonNull(content, "content");
    this.buttons = Collections.unmodifiableList(buttons);
  }

  @Override
  public String content() {
    return content;
  }

  @Override
  public List<@Nullable ButtonComponent> buttons() {
    return buttons;
  }

  public static final class Builder
      extends FormImpl.Builder<SimpleForm.Builder, SimpleForm, SimpleFormResponse>
      implements SimpleForm.Builder {

    private final List<@Nullable ButtonComponent> buttons = new ArrayList<>();
    private final Map<Integer, Consumer<SimpleFormResponse>> callbacks = new HashMap<>();
    private String content = "";

    @Override
    public Builder content(String content) {
      this.content = translate(Objects.requireNonNull(content, "content"));
      return this;
    }

    @Override
    public Builder button(ButtonComponent button) {
      buttons.add(Objects.requireNonNull(button, "button"));
      return this;
    }

    @Override
    public Builder button(ButtonComponent button, Consumer<SimpleFormResponse> callback) {
      Objects.requireNonNull(callback, "callback");
      button(button);
      callbacks.put(buttons.size(), callback);
      return this;
    }

    @Override
    public Builder button(String text, FormImage.Type type, String data) {
      buttons.add(ButtonComponent.of(translate(text), type, data));
      return this;
    }

    @Override
    public Builder button(
        String text, FormImage.Type type, String data, Consumer<SimpleFormResponse> callback) {
      Objects.requireNonNull(callback, "callback");
      button(text, type, data);
      callbacks.put(buttons.size(), callback);
      return this;
    }

    @Override
    public Builder button(String text, @Nullable FormImage image) {
      buttons.add(ButtonComponent.of(translate(text), image));
      return this;
    }

    @Override
    public Builder button(
        String text, @Nullable FormImage image, Consumer<SimpleFormResponse> callback) {
      Objects.requireNonNull(callback, "callback");
      button(text, image);
      callbacks.put(buttons.size(), callback);
      return this;
    }

    @Override
    public Builder button(String text) {
      buttons.add(ButtonComponent.of(translate(text)));
      return this;
    }

    @Override
    public Builder button(String text, Consumer<SimpleFormResponse> callback) {
      Objects.requireNonNull(callback, "callback");
      button(text);
      callbacks.put(buttons.size(), callback);
      return this;
    }

    @Override
    public Builder optionalButton(
        String text, FormImage.Type type, String data, boolean shouldAdd) {
      if (shouldAdd) {
        return button(text, type, data);
      }
      return addNullButton();
    }

    @Override
    public Builder optionalButton(String text, @Nullable FormImage image, boolean shouldAdd) {
      if (shouldAdd) {
        return button(text, image);
      }
      return addNullButton();
    }

    @Override
    public Builder optionalButton(String text, boolean shouldAdd) {
      if (shouldAdd) {
        return button(text);
      }
      return addNullButton();
    }

    @Override
    public SimpleForm build() {
      SimpleFormImpl form = new SimpleFormImpl(title, content, buttons);
      setResponseHandler(
          form,
          form,
          valid -> {
            Consumer<SimpleFormResponse> callback = callbacks.get(valid.clickedButtonId());
            if (callback != null) {
              callback.accept(valid);
            }
          });
      return form;
    }

    private Builder addNullButton() {
      buttons.add(null);
      return this;
    }
  }
}
