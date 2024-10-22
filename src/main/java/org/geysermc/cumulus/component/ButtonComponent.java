/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component;

import org.geysermc.cumulus.component.impl.ButtonComponentImpl;
import org.geysermc.cumulus.util.FormImage;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Button component is a component that can only be used in SimpleForm. With this component you can
 * show a button with an optional image attached to it.
 *
 * @since 1.0
 */
@NullMarked
public interface ButtonComponent {
  /**
   * Create an instance of the button component.
   *
   * @param text the text that is shown in the component
   * @param image the image that will be shown next to the button
   * @return the created instance
   * @since 1.0
   */
  static ButtonComponent of(String text, @Nullable FormImage image) {
    return new ButtonComponentImpl(text, image);
  }

  /**
   * Create an instance of the button component.
   *
   * @param text the text that is shown in the component
   * @param type the form image type
   * @param data the form image data
   * @return the created instance
   * @since 1.0
   */
  static ButtonComponent of(String text, FormImage.Type type, String data) {
    return new ButtonComponentImpl(text, FormImage.of(type, data));
  }

  /**
   * Create an instance of a button component that doesn't have an image.
   *
   * @param text the text that is shown in the component
   * @since 1.0
   */
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
