/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response.impl;

import java.util.Objects;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class SimpleFormResponseImpl implements SimpleFormResponse {
  private final int clickedButtonId;
  private final ButtonComponent clickedButton;

  private SimpleFormResponseImpl(int clickedButtonId, ButtonComponent clickedButton) {
    if (clickedButtonId < 0) {
      throw new IllegalArgumentException("clickedButtonId cannot be negative");
    }
    this.clickedButtonId = clickedButtonId;
    this.clickedButton = Objects.requireNonNull(clickedButton, "clickedButton");
  }

  public static SimpleFormResponseImpl of(int clickedButtonId, ButtonComponent clickedButton) {
    return new SimpleFormResponseImpl(clickedButtonId, clickedButton);
  }

  @Override
  public int clickedButtonId() {
    return clickedButtonId;
  }

  @Override
  public ButtonComponent clickedButton() {
    return clickedButton;
  }
}
