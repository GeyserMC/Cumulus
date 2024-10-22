/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl.modal;

import org.geysermc.cumulus.form.ModalForm;
import org.geysermc.cumulus.form.impl.FormDefinition;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.response.ModalFormResponse;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ModalFormDefinition
    extends FormDefinition<ModalForm, ModalFormImpl, ModalFormResponse> {

  private static final ModalFormDefinition INSTANCE = new ModalFormDefinition();

  private ModalFormDefinition() {
    super(new ModalFormCodec(), FormType.MODAL_FORM, ModalForm.class, ModalFormImpl.class);
  }

  public static ModalFormDefinition instance() {
    return INSTANCE;
  }
}
