/*
 * Copyright (c) 2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface NpcFormResponse extends FormResponse {
  /** Returns the id (index) of the button that has been clicked. */
  @NonNegative int clickedButtonId();

  /** Returns the text of the button that has been clicked. */
  @NonNull String clickedButtonText();
}
