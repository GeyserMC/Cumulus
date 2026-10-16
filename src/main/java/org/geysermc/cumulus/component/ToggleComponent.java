/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component;

import org.geysermc.cumulus.component.impl.ToggleComponentImpl;

/**
 * Toggle component is a component that can only be used in CustomForm. With this component you can
 * let the client toggle an option.
 */
public interface ToggleComponent extends Component {
  static ToggleComponent of(String text, boolean defaultValue) {
    return new ToggleComponentImpl(text, defaultValue);
  }

  static ToggleComponent of(String text) {
    return of(text, false);
  }

  /** Returns whether the default value should be true or false. */
  boolean defaultValue();
}
