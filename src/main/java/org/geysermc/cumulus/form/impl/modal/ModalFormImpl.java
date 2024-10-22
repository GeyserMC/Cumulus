/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl.modal;

import java.util.Objects;
import org.geysermc.cumulus.form.ModalForm;
import org.geysermc.cumulus.form.impl.FormImpl;
import org.geysermc.cumulus.response.ModalFormResponse;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ModalFormImpl extends FormImpl<ModalFormResponse> implements ModalForm {
  private final String content;
  private final String button1;
  private final String button2;

  public ModalFormImpl(String title, String content, String button1, String button2) {
    super(title);
    this.content = Objects.requireNonNull(content, "content");
    this.button1 = Objects.requireNonNull(button1, "button1");
    this.button2 = Objects.requireNonNull(button2, "button2");
  }

  @Override
  public String content() {
    return content;
  }

  @Override
  public String button1() {
    return button1;
  }

  @Override
  public String button2() {
    return button2;
  }

  public static final class Builder
      extends FormImpl.Builder<ModalForm.Builder, ModalForm, ModalFormResponse>
      implements ModalForm.Builder {
    private String content = "";
    private String button1 = "";
    private String button2 = "";

    public Builder content(String content) {
      this.content = translate(Objects.requireNonNull(content, "content"));
      return this;
    }

    public Builder button1(String button1) {
      this.button1 = translate(Objects.requireNonNull(button1, "button1"));
      return this;
    }

    public Builder button2(String button2) {
      this.button2 = translate(Objects.requireNonNull(button2, "button2"));
      return this;
    }

    @Override
    public ModalForm build() {
      ModalFormImpl form = new ModalFormImpl(title, content, button1, button2);
      setResponseHandler(form, form);
      return form;
    }
  }
}
