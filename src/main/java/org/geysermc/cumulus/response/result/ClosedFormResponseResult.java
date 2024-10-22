/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response.result;

import org.geysermc.cumulus.response.FormResponse;
import org.jspecify.annotations.NullMarked;

/**
 * A class holding information about a closed form response.
 *
 * @param <R> the form-type that this closed result is for
 * @since 1.1
 */
@NullMarked
public final class ClosedFormResponseResult<R extends FormResponse>
    implements FormResponseResult<R> {
  private static final ClosedFormResponseResult<?> result = new ClosedFormResponseResult<>();

  private ClosedFormResponseResult() {}

  /**
   * Returns the singleton instance for a closed form.
   *
   * @param <T> the form-type that this closed result is for
   * @since 1.1
   */
  @SuppressWarnings("unchecked")
  public static <T extends FormResponse> ClosedFormResponseResult<T> instance() {
    return (ClosedFormResponseResult<T>) result;
  }

  @Override
  public ResultType responseType() {
    return ResultType.CLOSED;
  }
}
