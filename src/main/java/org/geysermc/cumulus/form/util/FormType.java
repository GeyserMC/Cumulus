/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.util;

import com.google.gson.annotations.SerializedName;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.form.ModalForm;
import org.geysermc.cumulus.form.SimpleForm;
import org.jspecify.annotations.Nullable;

/**
 * An enum containing the valid form types. Valid form types are:
 *
 * <ul>
 *   <li>{@link SimpleForm Simple Form}
 *   <li>{@link ModalForm Modal Form}
 *   <li>{@link CustomForm Custom Form}
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
  CUSTOM_FORM;

  private static final FormType[] VALUES = values();

  /**
   * Returns the form type from the ordinal.
   *
   * @param ordinal the ordinal of the FormType
   */
  public static @Nullable FormType fromOrdinal(int ordinal) {
    return ordinal < VALUES.length ? VALUES[ordinal] : null;
  }
}
