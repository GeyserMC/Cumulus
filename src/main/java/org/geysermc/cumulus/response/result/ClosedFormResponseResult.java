/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response.result;

import org.geysermc.cumulus.response.FormResponse;

public final class ClosedFormResponseResult<T extends FormResponse>
    implements FormResponseResult<T> {

  private static final ClosedFormResponseResult<?> result = new ClosedFormResponseResult<>();

  private ClosedFormResponseResult() {}

  @SuppressWarnings("unchecked")
  public static <T extends FormResponse> ClosedFormResponseResult<T> instance() {
    return (ClosedFormResponseResult<T>) result;
  }

  @Override
  public ResultType responseType() {
    return ResultType.CLOSED;
  }
}
