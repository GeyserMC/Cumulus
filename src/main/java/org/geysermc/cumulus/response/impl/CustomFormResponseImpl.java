/*
 * Copyright (c) 2020-2022 GeyserMC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response.impl;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.component.util.ComponentType;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.ResultType;
import org.geysermc.cumulus.util.AbsentComponent;

public final class CustomFormResponseImpl extends ResponseToResultGlue
    implements CustomFormResponse {

  /**
   * Contains null for LabelComponent and {@link AbsentComponent} for components that were optional
   * and not added.
   */
  private final List<@Nullable Object> responses;

  private final JsonArray rawResponse;
  private final List<ComponentType> componentTypes;

  private int index = -1;
  private boolean includeLabels = false;

  private CustomFormResponseImpl(
      List<Object> responses, JsonArray rawResponse, List<ComponentType> componentTypes) {
    this.responses = Collections.unmodifiableList(responses);
    this.rawResponse = rawResponse;
    this.componentTypes = Collections.unmodifiableList(componentTypes);
  }

  @Deprecated
  public CustomFormResponseImpl(ResultType resultType) {
    // todo remove in 2.0
    super(resultType);
    this.responses = null;
    this.rawResponse = null;
    this.componentTypes = null;
  }

  public static @NonNull CustomFormResponse of(
      @NonNull List<Object> responses,
      @NonNull JsonArray rawResponse,
      @NonNull List<ComponentType> componentTypes) {
    Objects.requireNonNull(responses, "responses");
    Objects.requireNonNull(rawResponse, "rawResponse");
    Objects.requireNonNull(componentTypes, "componentTypes");
    return new CustomFormResponseImpl(responses, rawResponse, componentTypes);
  }

  @Override
  public @NonNull JsonArray getResponses() {
    return rawResponse;
  }

  @Override
  public @NonNull List<ComponentType> getComponentTypes() {
    return componentTypes;
  }

  /**
   * This should be used internally so that {@link AbsentComponent} can be taken into consideration
   * separately from label components.
   *
   * @return null for label components or if there is no next component, and {@link AbsentComponent}
   *     for optional components that were not added
   */
  @SuppressWarnings("unchecked")
  private <T> @Nullable T nextOrAbsent(boolean includeLabels) {
    if (!hasNext()) {
      return null;
    }

    while (++index < responses.size()) {
      Object response = responses.get(index);
      if (response == null && !includeLabels) {
        // if response is null, it was a label
        continue;
      }
      return (T) response;
    }
    return null; // we don't have anything to check anymore
  }

  @Override
  public <T> @Nullable T next(boolean includeLabels) {
    T next = nextOrAbsent(includeLabels);
    if (next instanceof AbsentComponent) {
      return null;
    }
    return next;
  }

  @Override
  public void skip(int amount) {
    Preconditions.checkArgument(amount >= 1, "amount");
    index += amount;
  }

  @Override
  public void skip() {
    skip(1);
  }

  @Override
  public void reset() {
    this.index = -1;
  }

  @Override
  public void index(int index) {
    Preconditions.checkArgument(index >= -1, "index");
    this.index = index;
  }

  @Override
  public void includeLabels(boolean includeLabels) {
    this.includeLabels = includeLabels;
  }

  @Override
  public boolean hasNext() {
    return responses.size() > index + 1;
  }

  @Override
  public boolean isPresent() {
    return responses.size() > index && responses.get(index) != null;
  }

  @Override
  public boolean isNextPresent() {
    return hasNext() && responses.get(index + 1) != null;
  }

  /**
   * This should be used internally so that {@link AbsentComponent} can be taken into consideration
   * separately from label components.
   *
   * @return null for label components and {@link AbsentComponent} for optional components that were
   *     not added
   */
  private <T> T nextOrAbsent() {
    return nextOrAbsent(includeLabels);
  }

  @Override
  public <T> @Nullable T next() {
    return next(includeLabels);
  }

  @Override
  public int asDropdown() {
    Object next = nextOrAbsent();
    if (next instanceof AbsentComponent) {
      return 0;
    }
    if (next instanceof Integer) {
      return (int) next;
    }
    throw wrongType(index, "dropdown");
  }

  @Override
  public @Nullable String asInput() {
    Object next = nextOrAbsent();
    if (next instanceof AbsentComponent) {
      return null;
    }
    if (next instanceof String) {
      return (String) next;
    }
    throw wrongType(index, "input");
  }

  @Override
  public float asSlider() {
    Object next = nextOrAbsent();
    if (next instanceof AbsentComponent) {
      return 0.0f;
    }
    if (next instanceof Float) {
      return (float) next;
    }
    throw wrongType(index, "slider");
  }

  @Override
  public int asStepSlider() {
    Object next = nextOrAbsent();
    if (next instanceof AbsentComponent) {
      return 0;
    }
    if (next instanceof Integer) {
      return (int) next;
    }
    throw wrongType(index, "step slider");
  }

  @Override
  public boolean asToggle() {
    Object next = nextOrAbsent();
    if (next instanceof AbsentComponent) {
      return false;
    }
    if (next instanceof Boolean) {
      return (boolean) next;
    }
    throw wrongType(index, "toggle");
  }

  @Override
  public @NonNull JsonPrimitive get(int index) {
    Preconditions.checkArgument(index >= 0, "index");
    try {
      return rawResponse.get(index).getAsJsonPrimitive();
    } catch (IllegalStateException exception) {
      throw wrongType(index, "a primitive");
    }
  }

  /**
   * @return null for label components and {@link AbsentComponent} for optional components that were
   *     not added
   */
  @SuppressWarnings("unchecked")
  private <T> @Nullable T valueOrAbsent(int index)
      throws IllegalArgumentException, ClassCastException {
    Preconditions.checkArgument(index >= 0, "index");
    if (index >= responses.size()) {
      throw new IllegalArgumentException("Requested an higher index than there are components");
    }

    return (T) responses.get(index);
  }

  @Override
  public <T> @Nullable T valueAt(int index) throws IllegalArgumentException, ClassCastException {
    T response = valueOrAbsent(index);
    if (response instanceof AbsentComponent) {
      return null;
    }
    return response;
  }

  @Override
  public int asDropdown(int index) {
    Object next = valueOrAbsent(index);
    if (next instanceof AbsentComponent) {
      return 0;
    }
    if (next instanceof Integer) {
      return (int) next;
    }
    throw wrongType(index, "dropdown");
  }

  @Override
  public @Nullable String asInput(int index) {
    Object next = valueOrAbsent(index);
    if (next instanceof AbsentComponent) {
      return null;
    }
    if (next instanceof String) {
      return (String) next;
    }
    throw wrongType(index, "input");
  }

  @Override
  public float asSlider(int index) {
    Object next = valueOrAbsent(index);
    if (next instanceof AbsentComponent) {
      return 0.0f;
    }
    if (next instanceof Float) {
      return (float) next;
    }
    throw wrongType(index, "slider");
  }

  @Override
  public int asStepSlider(int index) {
    Object next = valueOrAbsent(index);
    if (next instanceof AbsentComponent) {
      return 0;
    }
    if (next instanceof Integer) {
      return (int) next;
    }
    throw wrongType(index, "step slider");
  }

  @Override
  public boolean asToggle(int index) {
    Object next = valueOrAbsent(index);
    if (next instanceof AbsentComponent) {
      return false;
    }
    if (next instanceof Boolean) {
      return (boolean) next;
    }
    throw wrongType(index, "toggle");
  }

  // the JVM doesn't allow interface methods to become default methods

  public int getDropdown(@NonNegative int index) {
    return asDropdown(index);
  }

  public @Nullable String getInput(@NonNegative int index) {
    return asInput(index);
  }

  public float getSlider(@NonNegative int index) {
    return asSlider(index);
  }

  public int getStepSlide(@NonNegative int index) {
    return asStepSlider(index);
  }

  public boolean getToggle(@NonNegative int index) {
    return asToggle(index);
  }

  private IllegalStateException wrongType(int index, String expected) {
    Object response = responses.get(index);
    String unexpected;
    if (response == null) {
      unexpected = "label";
    } else {
      unexpected = response.toString();
    }

    return new IllegalStateException(
        String.format("Expected %s on %s, got %s", expected, index, unexpected));
  }
}
