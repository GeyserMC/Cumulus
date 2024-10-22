/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl.custom;

import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.form.impl.FormDefinition;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class CustomFormDefinition
    extends FormDefinition<CustomForm, CustomFormImpl, CustomFormResponse> {

  private static final CustomFormDefinition INSTANCE = new CustomFormDefinition();

  private CustomFormDefinition() {
    super(new CustomFormCodec(), FormType.CUSTOM_FORM, CustomForm.class, CustomFormImpl.class);
  }

  public static CustomFormDefinition instance() {
    return INSTANCE;
  }
}
