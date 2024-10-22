/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response.result;

import java.util.Objects;
import org.geysermc.cumulus.response.FormResponse;
import org.jspecify.annotations.NullMarked;

/**
 * A class holding information about a valid form response.
 *
 * @param <R> the form type that this valid result is for
 * @since 1.1
 */
@NullMarked
public final class ValidFormResponseResult<R extends FormResponse>
    implements FormResponseResult<R> {
  private final R response;

  private ValidFormResponseResult(R response) {
    this.response = Objects.requireNonNull(response);
  }

  /**
   * Returns a response result for a valid form response.
   *
   * @param response the form-type specific response data
   * @param <F> the form type that this response result is for
   * @since 1.1
   */
  public static <F extends FormResponse> ValidFormResponseResult<F> of(F response) {
    return new ValidFormResponseResult<>(response);
  }

  /**
   * Returns the form response for the given form type.
   *
   * @since 1.1
   */
  public R response() {
    return response;
  }

  @Override
  public ResultType responseType() {
    return ResultType.VALID;
  }
}
