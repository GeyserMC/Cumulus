/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response.result;

import org.geysermc.cumulus.response.FormResponse;

@SuppressWarnings("unused")
public interface FormResponseResult<R extends FormResponse> {
  static <R extends FormResponse> ClosedFormResponseResult<R> closed() {
    return ClosedFormResponseResult.instance();
  }

  static <R extends FormResponse> InvalidFormResponseResult<R> invalid(
      int componentIndex, String errorMessage) {
    return InvalidFormResponseResult.of(componentIndex, errorMessage);
  }

  static <R extends FormResponse> ValidFormResponseResult<R> valid(R formResponse) {
    return ValidFormResponseResult.of(formResponse);
  }

  default boolean isClosed() {
    return this instanceof ClosedFormResponseResult;
  }

  default boolean isInvalid() {
    return this instanceof InvalidFormResponseResult;
  }

  default boolean isValid() {
    return this instanceof ValidFormResponseResult;
  }

  ResultType responseType();
}
