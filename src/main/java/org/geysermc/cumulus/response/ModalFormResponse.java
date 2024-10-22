/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response;

import org.jspecify.annotations.NullMarked;

/**
 * Information about a valid response to a ModalForm.
 *
 * @since 1.0
 */
@NullMarked
public interface ModalFormResponse extends FormResponse {
  /**
   * Returns the id of the button that has been clicked.
   *
   * @since 1.1
   */
  int clickedButtonId();

  /**
   * Returns the text of the button that has been clicked.
   *
   * @since 1.1
   */
  String clickedButtonText();

  /**
   * Returns true if the player clicked the first button, returns false otherwise.
   *
   * @since 1.1
   */
  boolean clickedFirst();
}
