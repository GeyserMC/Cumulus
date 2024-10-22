/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component.impl;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;
import org.geysermc.cumulus.component.InputComponent;
import org.geysermc.cumulus.component.util.ComponentType;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class InputComponentImpl extends ComponentImpl implements InputComponent {
  private final String placeholder;

  @SerializedName("default")
  private final String defaultText;

  public InputComponentImpl(String text, String placeholder, String defaultText) {
    super(ComponentType.INPUT, text);
    this.placeholder = Objects.requireNonNull(placeholder, "placeholder");
    this.defaultText = Objects.requireNonNull(defaultText, "defaultText");
  }

  @Override
  public String placeholder() {
    return placeholder;
  }

  @Override
  public String defaultText() {
    return defaultText;
  }
}
