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
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.form.impl.FormImpl;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.util.FormImage;

public final class SimpleFormImpl extends FormImpl<SimpleFormResponse> implements SimpleForm {
  private final String content;
  private final List<ButtonComponent> buttons;

  public SimpleFormImpl(
      @NonNull String title, @NonNull String content, @NonNull List<ButtonComponent> buttons) {
    super(title);
    this.content = Objects.requireNonNull(content, "content");
    this.buttons = Collections.unmodifiableList(buttons);
  }

  @Override
  public @NonNull String content() {
    return content;
  }

  @Override
  public @NonNull List<ButtonComponent> buttons() {
    return buttons;
  }

  public static final class Builder
      extends FormImpl.Builder<SimpleForm.Builder, SimpleForm, SimpleFormResponse>
      implements SimpleForm.Builder {

    private final List<ButtonComponent> buttons = new ArrayList<>();
    private final Map<Integer, Consumer<SimpleFormResponse>> callbacks = new HashMap<>();
    private String content = "";

    @Override
    public Builder content(@NonNull String content) {
      this.content = translate(Objects.requireNonNull(content, "content"));
      return this;
    }

    @Override
    public Builder button(@NonNull ButtonComponent button) {
      buttons.add(Objects.requireNonNull(button, "button"));
      return this;
    }

    @Override
    public SimpleForm.@This Builder button(
        @NonNull ButtonComponent button, @NonNull Consumer<SimpleFormResponse> callback) {
      callbacks.put(buttons.size(), Objects.requireNonNull(callback));
      return button(button);
    }

    @Override
    public Builder button(
        @NonNull String text, FormImage.@NonNull Type type, @NonNull String data) {
      buttons.add(ButtonComponent.of(translate(text), type, data));
      return this;
    }

    @Override
    public SimpleForm.@This Builder button(
        @NonNull String text,
        FormImage.@NonNull Type type,
        @NonNull String data,
        @NonNull Consumer<SimpleFormResponse> callback) {
      callbacks.put(buttons.size(), Objects.requireNonNull(callback));
      return button(text, type, data);
    }

    @Override
    public Builder button(@NonNull String text, @Nullable FormImage image) {
      buttons.add(ButtonComponent.of(translate(text), image));
      return this;
    }

    @Override
    public SimpleForm.@This Builder button(
        @NonNull String text,
        @Nullable FormImage image,
        @NonNull Consumer<SimpleFormResponse> callback) {
      callbacks.put(buttons.size(), Objects.requireNonNull(callback));
      return button(text, image);
    }

    @Override
    public Builder button(@NonNull String text) {
      buttons.add(ButtonComponent.of(translate(text)));
      return this;
    }

    @Override
    public SimpleForm.@This Builder button(
        @NonNull String text, @NonNull Consumer<SimpleFormResponse> callback) {
      callbacks.put(buttons.size(), Objects.requireNonNull(callback));
      return button(text);
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
        @NonNull String text, @Nullable FormImage image, boolean shouldAdd) {
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
    public @NonNull SimpleForm build() {
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
