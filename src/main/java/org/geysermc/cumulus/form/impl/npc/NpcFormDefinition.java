/*
 * Copyright (c) 2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl.npc;

import org.geysermc.cumulus.form.NpcForm;
import org.geysermc.cumulus.form.impl.FormDefinition;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.response.NpcFormResponse;

public class NpcFormDefinition extends FormDefinition<NpcForm, NpcFormImpl, NpcFormResponse> {
  private static final NpcFormDefinition INSTANCE = new NpcFormDefinition();

  private NpcFormDefinition() {
    super(new NpcFormCodec(), FormType.NPC_FORM, NpcForm.class, NpcFormImpl.class);
  }

  public static NpcFormDefinition instance() {
    return INSTANCE;
  }
}
