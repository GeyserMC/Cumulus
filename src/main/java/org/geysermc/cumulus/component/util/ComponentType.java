/*
 * Copyright (c) 2020-2022 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Cumulus
 */

package org.geysermc.cumulus.component.util;

import com.google.gson.annotations.SerializedName;
import java.util.Locale;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * An enum containing the valid component types. Valid component types are:
 * <ul>
 *     <li>{@link org.geysermc.cumulus.component.DropdownComponent Dropdown Component}</li>
 *     <li>{@link org.geysermc.cumulus.component.InputComponent Input Component}</li>
 *     <li>{@link org.geysermc.cumulus.component.LabelComponent Label Component}</li>
 *     <li>{@link org.geysermc.cumulus.component.SliderComponent Slider Component}</li>
 *     <li>{@link org.geysermc.cumulus.component.StepSliderComponent Step Slider Component}</li>
 *     <li>{@link org.geysermc.cumulus.component.ToggleComponent Toggle Component}</li>
 * </ul>
 * For more information and for code examples look at
 * <a href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
 *
 * @since 1.1
 */
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

  @Nullable
  public static ComponentType fromName(@Nullable String name) {
    for (ComponentType type : VALUES) {
      if (type.name.equals(name)) {
        return type;
      }
    }
    return null;
  }

  @NonNull
  public String componentName() {
    return name;
  }
}
