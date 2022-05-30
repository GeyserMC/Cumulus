/*
 * Copyright (c) 2020-2022 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Cumulus
 */

package org.geysermc.cumulus.response.result;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.cumulus.response.FormResponse;

public final class InvalidFormResponseResult<R extends FormResponse>
    implements FormResponseResult<R> {
  private final int componentIndex;
  private final String errorMessage;

  private InvalidFormResponseResult(int componentIndex, String errorMessage) {
    this.componentIndex = componentIndex;
    this.errorMessage = errorMessage;
  }

  public static <R extends FormResponse> InvalidFormResponseResult<R> of(
      int componentIndex,
      String errorMessage
  ) {
    return new InvalidFormResponseResult<>(componentIndex, errorMessage);
  }

  @Override
  @NonNull
  public ResultType responseType() {
    return ResultType.INVALID;
  }

  /**
   * Returns the index of the component that is invalid. Can be -1 when it's not component specific
   * (e.g. when more or less components got returned than got sent).
   * As of writing Cumulus 1.1, only the custom form type can cause this to return a different value
   * than -1.
   */
  public int componentIndex() {
    return componentIndex;
  }

  /**
   * Returns an additional message that should describe what went wrong.
   */
  public String errorMessage() {
    return errorMessage;
  }
}
