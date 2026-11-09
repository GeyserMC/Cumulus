/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.util;

import com.google.gson.annotations.SerializedName;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.form.ModalForm;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.form.NpcForm;

/**
 * An enum containing the valid form types. Valid form types are:
 *
 * <ul>
 *   <li>{@link SimpleForm Simple Form}
 *   <li>{@link ModalForm Modal Form}
 *   <li>{@link CustomForm Custom Form}
 *   <li>{@link NpcForm NPC Form}
 * </ul>
 *
 * For more information and for code examples look at <a
 * href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
 *
 * @since 1.1
 */
public enum FormType {
  @SerializedName("form")
  SIMPLE_FORM,
  @SerializedName("modal")
  MODAL_FORM,
  @SerializedName("custom_form")
  CUSTOM_FORM,
  @SerializedName("npc_form")
  NPC_FORM;

  private static final FormType[] VALUES = values();

  public static @Nullable FormType fromOrdinal(int ordinal) {
    return ordinal < VALUES.length ? VALUES[ordinal] : null;
  }
}
