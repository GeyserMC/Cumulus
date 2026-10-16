/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component;

import org.geysermc.cumulus.component.impl.ButtonComponentImpl;
import org.geysermc.cumulus.util.FormImage;
import org.jspecify.annotations.Nullable;

/**
 * Button component is a component that can only be used in SimpleForm. With this component you can
 * show a button with an optional image attached to it.
 */
public interface ButtonComponent {
  static ButtonComponent of(String text, @Nullable FormImage image) {
    return new ButtonComponentImpl(text, image);
  }

  static ButtonComponent of(String text, FormImage.Type type, String data) {
    return new ButtonComponentImpl(text, FormImage.of(type, data));
  }

  static ButtonComponent of(String text) {
    return of(text, null);
  }

  /**
   * Returns the text that will be shown in the button.
   *
   * @since 1.1
   */
  String text();

  /**
   * Returns the image that will be shown next to the button.
   *
   * @since 1.1
   */
  @Nullable FormImage image();
}
