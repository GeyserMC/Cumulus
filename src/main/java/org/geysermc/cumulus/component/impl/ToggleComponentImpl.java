/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component.impl;

import com.google.gson.annotations.SerializedName;
import org.geysermc.cumulus.component.ToggleComponent;
import org.geysermc.cumulus.component.util.ComponentType;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ToggleComponentImpl extends ComponentImpl implements ToggleComponent {
  @SerializedName("default")
  private final boolean defaultValue;

  public ToggleComponentImpl(String text, boolean defaultValue) {
    super(ComponentType.TOGGLE, text);
    this.defaultValue = defaultValue;
  }

  @Override
  public boolean defaultValue() {
    return defaultValue;
  }
}
