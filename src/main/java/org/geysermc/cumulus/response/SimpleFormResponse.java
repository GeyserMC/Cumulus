/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response;

import org.checkerframework.checker.index.qual.NonNegative;
import org.geysermc.cumulus.component.ButtonComponent;
import org.jspecify.annotations.NullMarked;

/**
 * Information about a valid response to a SimpleForm.
 *
 * @since 1.0
 */
@NullMarked
public interface SimpleFormResponse extends FormResponse {
  /**
   * Returns the id (index) of the button that has been clicked.
   *
   * @since 1.1
   */
  @NonNegative int clickedButtonId();

  /**
   * Returns the button component that has been clicked.
   *
   * @since 1.1
   */
  ButtonComponent clickedButton();
}
