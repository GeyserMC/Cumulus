/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response.result;

import java.util.Objects;
import org.geysermc.cumulus.response.FormResponse;

public final class ValidFormResponseResult<R extends FormResponse>
    implements FormResponseResult<R> {

  private final R response;

  private ValidFormResponseResult(R response) {
    this.response = Objects.requireNonNull(response);
  }

  public static <F extends FormResponse> ValidFormResponseResult<F> of(F response) {
    return new ValidFormResponseResult<>(response);
  }

  public R response() {
    return response;
  }

  @Override
  public ResultType responseType() {
    return ResultType.VALID;
  }
}
