/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response.impl;

import java.util.Objects;
import org.geysermc.cumulus.response.ModalFormResponse;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class ModalFormResponseImpl implements ModalFormResponse {
  private final int clickedButtonId;
  private final String clickedButtonText;

  private ModalFormResponseImpl(int clickedButtonId, String clickedButtonText) {
    if (clickedButtonId < 0) {
      throw new IllegalArgumentException("clickedButtonId cannot be negative");
    }
    this.clickedButtonId = clickedButtonId;
    this.clickedButtonText = Objects.requireNonNull(clickedButtonText, "clickedButtonText");
  }

  public static ModalFormResponseImpl of(int clickedButtonId, String clickedButtonText) {
    return new ModalFormResponseImpl(clickedButtonId, clickedButtonText);
  }

  @Override
  public int clickedButtonId() {
    return clickedButtonId;
  }

  @Override
  public String clickedButtonText() {
    return clickedButtonText;
  }

  @Override
  public boolean clickedFirst() {
    return clickedButtonId == 0;
  }
}
