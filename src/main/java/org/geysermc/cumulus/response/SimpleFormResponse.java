/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response;

import org.checkerframework.checker.index.qual.NonNegative;
import org.geysermc.cumulus.component.ButtonComponent;

public interface SimpleFormResponse extends FormResponse {
  /** Returns the id (index) of the button that has been clicked. */
  @NonNegative int clickedButtonId();

  /** Returns the button component that has been clicked. */
  ButtonComponent clickedButton();
}
