/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl.simple;

import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.form.impl.FormDefinition;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class SimpleFormDefinition
    extends FormDefinition<SimpleForm, SimpleFormImpl, SimpleFormResponse> {

  private static final SimpleFormDefinition INSTANCE = new SimpleFormDefinition();

  private SimpleFormDefinition() {
    super(new SimpleFormCodec(), FormType.SIMPLE_FORM, SimpleForm.class, SimpleFormImpl.class);
  }

  public static SimpleFormDefinition instance() {
    return INSTANCE;
  }
}
