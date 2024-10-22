/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form;

import org.jspecify.annotations.NullMarked;

/**
 * Base class of all Forms. While it can be used it doesn't contain every data you could get when
 * using the specific class of the form type.
 *
 * @since 1.1
 */
@NullMarked
public interface Form {
  /**
   * Returns the title of the Form.
   *
   * @since 1.1
   */
  String title();
}
