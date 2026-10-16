/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response;

public interface ModalFormResponse extends FormResponse {
  /** Returns the id of the button that has been clicked. */
  int clickedButtonId();

  /** Returns the text of the button that has been clicked. */
  String clickedButtonText();

  /** Returns true if the player clicked the first button, returns false otherwise. */
  boolean clickedFirst();
}
