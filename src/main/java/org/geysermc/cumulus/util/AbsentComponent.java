/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.util;

import org.jspecify.annotations.NullMarked;

/**
 * A class intended to be used as a marker that a given optional component is not present. This
 * should only be used internally.
 *
 * @since 1.1
 */
@NullMarked
public final class AbsentComponent {
  @SuppressWarnings("InstantiationOfUtilityClass")
  private static final AbsentComponent INSTANCE = new AbsentComponent();

  /**
   * Returns the instance of the singleton AbsentComponent.
   *
   * @since 1.1
   */
  public static AbsentComponent instance() {
    return INSTANCE;
  }
}
