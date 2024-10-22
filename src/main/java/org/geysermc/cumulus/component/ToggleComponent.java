/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component;

import org.geysermc.cumulus.component.impl.ToggleComponentImpl;
import org.jspecify.annotations.NullMarked;

/**
 * Toggle component is a component that can only be used in CustomForm. With this component you can
 * let the client toggle an option.
 *
 * @since 1.0
 */
@NullMarked
public interface ToggleComponent extends Component {
  /**
   * Create a direct instance of ToggleComponent.
   *
   * @param text the text that is shown in the component
   * @param defaultValue the default value for the toggle
   * @return the created ToggleComponent
   * @since 1.0
   */
  static ToggleComponent of(String text, boolean defaultValue) {
    return new ToggleComponentImpl(text, defaultValue);
  }

  /**
   * Create a direct instance of ToggleComponent with a default value of false.
   *
   * @param text the text that is shown in the component
   * @return the created ToggleComponent
   * @since 1.0
   */
  static ToggleComponent of(String text) {
    return of(text, false);
  }

  /**
   * Returns whether the default value of this toggle should be true or false.
   *
   * @since 1.1
   */
  boolean defaultValue();
}
