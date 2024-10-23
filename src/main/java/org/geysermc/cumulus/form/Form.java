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

  /**
   * Close the form if it was open.
   *
   * <p>Note that reusing the same form instance (instead of building a new form instance for each
   * player) will result in the form being closed for all players that this instance has been sent
   * to. We don't recommend reusing the same form since forms are cheap to create, but Cumulus does
   * support it. If the behaviour of this method isn't what you want, you can close a specific
   * instance using {@link #close(int)}.
   *
   * @see #close(int)
   * @since 2.0
   */
  void close();

  /**
   * Close the form if it was open.
   *
   * <p>Unlike {@link #close()}, this will only close this form for a specific instance. This method
   * does not allow you to close a different form instance with the given form id. People using the
   * recommended way of creating forms do never have to use this method.
   *
   * @param formId the id of the form to close
   * @see #close()
   * @since 2.0
   */
  void close(int formId);
}
