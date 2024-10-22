/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response.result;

import org.geysermc.cumulus.response.FormResponse;
import org.jspecify.annotations.NullMarked;

/**
 * A class holding information about an invalid form response.
 *
 * @param <R> the form-type that this invalid result is for
 * @since 1.1
 */
@NullMarked
public final class InvalidFormResponseResult<R extends FormResponse>
    implements FormResponseResult<R> {
  private final int componentIndex;
  private final String errorMessage;

  private InvalidFormResponseResult(int componentIndex, String errorMessage) {
    this.componentIndex = componentIndex;
    this.errorMessage = errorMessage;
  }

  /**
   * Returns a response result that says that the response was invalid.
   *
   * @param componentIndex the specific component that was invalid
   * @param errorMessage a message specifying what was wrong
   * @param <R> a form-type specific class that holds data about the response
   * @see #componentIndex()
   * @see #errorMessage()
   * @since 1.1
   */
  public static <R extends FormResponse> InvalidFormResponseResult<R> of(
      int componentIndex, String errorMessage) {
    return new InvalidFormResponseResult<>(componentIndex, errorMessage);
  }

  @Override
  public ResultType responseType() {
    return ResultType.INVALID;
  }

  /**
   * Returns the index of the component that is invalid. Can be -1 when it's not component specific
   * (e.g. when more or less components got returned than got sent). As of writing Cumulus 1.1, only
   * the custom form type can cause this to return a different value than -1.
   *
   * @since 1.1
   */
  public int componentIndex() {
    return componentIndex;
  }

  /**
   * Returns an additional message that should describe what went wrong.
   *
   * @since 1.1
   */
  public String errorMessage() {
    return errorMessage;
  }
}
