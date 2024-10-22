/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response.result;

import org.geysermc.cumulus.response.FormResponse;
import org.jspecify.annotations.NullMarked;

/**
 * The base class for all the possible form response results. See the classes extending this
 * interface for info specific to that result type.
 *
 * @param <R> a form-type specific class that holds data about the response
 * @since 1.1
 */
@NullMarked
public interface FormResponseResult<R extends FormResponse> {
  /**
   * Returns a response result that represents the form being closed.
   *
   * @param <R> a form-type specific class that holds data about the response
   * @since 1.1
   */
  static <R extends FormResponse> ClosedFormResponseResult<R> closed() {
    return ClosedFormResponseResult.instance();
  }

  /**
   * Returns a response result that says that the response received was invalid. See the respective
   * methods of {@link InvalidFormResponseResult} for more information.
   *
   * @param componentIndex the specific component that was invalid
   * @param errorMessage a message specifying what was wrong
   * @param <R> a form-type specific class that holds data about the response
   * @see InvalidFormResponseResult#componentIndex()
   * @see InvalidFormResponseResult#errorMessage()
   * @since 1.1
   */
  static <R extends FormResponse> InvalidFormResponseResult<R> invalid(
      int componentIndex, String errorMessage) {
    return InvalidFormResponseResult.of(componentIndex, errorMessage);
  }

  /**
   * Returns a response result that represents a valid response.
   *
   * @param formResponse the form-type specific response data
   * @param <R> a form-type specific class that holds data about the response
   * @since 1.1
   */
  static <R extends FormResponse> ValidFormResponseResult<R> valid(R formResponse) {
    return ValidFormResponseResult.of(formResponse);
  }

  /**
   * Returns the response type that this class represents.
   *
   * @since 1.1
   */
  ResultType responseType();

  // todo these should probably use the response type instead

  /**
   * Returns whether the response result represents the form being closed.
   *
   * @since 1.1
   */
  default boolean isClosed() {
    return this instanceof ClosedFormResponseResult;
  }

  /**
   * Returns whether the response result represents an invalid response.
   *
   * @since 1.1
   */
  default boolean isInvalid() {
    return this instanceof InvalidFormResponseResult;
  }

  /**
   * Returns whether the response result represents a valid response.
   *
   * @since 1.1
   */
  default boolean isValid() {
    return this instanceof ValidFormResponseResult;
  }
}
