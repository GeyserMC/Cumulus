/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component;

import org.geysermc.cumulus.component.util.ComponentType;
import org.jspecify.annotations.NullMarked;

/**
 * The base class of all components.
 *
 * @since 1.0
 */
@NullMarked
public interface Component {
  /**
   * Returns the type of component this component is.
   *
   * @since 1.1
   */
  ComponentType type();

  /**
   * Returns the text that is shown in the component.
   *
   * @since 1.1
   */
  String text();
}
