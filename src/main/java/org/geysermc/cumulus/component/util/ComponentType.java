/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component.util;

import com.google.gson.annotations.SerializedName;
import java.util.Locale;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * An enum containing the valid component types. Valid component types are:
 *
 * <ul>
 *   <li>{@link org.geysermc.cumulus.component.DropdownComponent Dropdown Component}
 *   <li>{@link org.geysermc.cumulus.component.InputComponent Input Component}
 *   <li>{@link org.geysermc.cumulus.component.LabelComponent Label Component}
 *   <li>{@link org.geysermc.cumulus.component.SliderComponent Slider Component}
 *   <li>{@link org.geysermc.cumulus.component.StepSliderComponent Step Slider Component}
 *   <li>{@link org.geysermc.cumulus.component.ToggleComponent Toggle Component}
 * </ul>
 *
 * For more information and for code examples look at <a
 * href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
 *
 * @since 1.1
 */
@NullMarked
public enum ComponentType {
  @SerializedName("dropdown")
  DROPDOWN,
  @SerializedName("input")
  INPUT,
  @SerializedName("label")
  LABEL,
  @SerializedName("slider")
  SLIDER,
  @SerializedName("step_slider")
  STEP_SLIDER,
  @SerializedName("toggle")
  TOGGLE;

  private static final ComponentType[] VALUES = values();

  private final String name = name().toLowerCase(Locale.ROOT);

  /**
   * Returns the component type from the name that is used in Bedrock.
   *
   * @param name the name that is used in Bedrock
   */
  public static @Nullable ComponentType fromName(@Nullable String name) {
    for (ComponentType type : VALUES) {
      if (type.name.equals(name)) {
        return type;
      }
    }
    return null;
  }

  /** Returns the component name as used in Bedrock. */
  public String componentName() {
    return name;
  }
}
