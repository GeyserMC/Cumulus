/*
 * Copyright (c) 2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response.impl;

import java.util.Objects;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.cumulus.response.NpcFormResponse;

public class NpcFormResponseImpl implements NpcFormResponse {

  private final int clickedButtonId;
  private final String clickedButtonText;

  private NpcFormResponseImpl(int clickedButtonId, String clickedButtonText) {
    if (clickedButtonId < 0) {
      throw new IllegalArgumentException("clickedButtonId cannot be negative");
    }
    this.clickedButtonId = clickedButtonId;
    this.clickedButtonText =
        Objects.requireNonNull(clickedButtonText, "clickedButtonText cannot be null!");
  }

  public static NpcFormResponseImpl of(int clickedButtonId, String clickedButtonText) {
    return new NpcFormResponseImpl(clickedButtonId, clickedButtonText);
  }

  @Override
  public @NonNegative int clickedButtonId() {
    return clickedButtonId;
  }

  @Override
  public @NonNull String clickedButtonText() {
    return clickedButtonText;
  }
}
