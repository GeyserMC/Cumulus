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

package org.geysermc.cumulus.response.impl;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.component.util.ComponentType;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.ResultType;
import org.geysermc.cumulus.util.AbsentComponent;

public final class CustomFormResponseImpl extends ResponseToResultGlue
    implements CustomFormResponse {

  private final List<Object> responses;
  private final JsonArray rawResponse;
  private final List<ComponentType> componentTypes;

  private int index = -1;
  private boolean includeLabels = false;

  private CustomFormResponseImpl(
      List<Object> responses,
      JsonArray rawResponse,
      List<ComponentType> componentTypes) {
    this.responses = Collections.unmodifiableList(responses);
    this.rawResponse = rawResponse;
    this.componentTypes = Collections.unmodifiableList(componentTypes);
  }

  @Deprecated
  public CustomFormResponseImpl(ResultType resultType) {
    //todo remove in 2.0
    super(resultType);
    this.responses = null;
    this.rawResponse = null;
    this.componentTypes = null;
  }

  @NonNull
  public static CustomFormResponse of(
      @NonNull List<Object> responses,
      @NonNull JsonArray rawResponse,
      @NonNull List<ComponentType> componentTypes) {
    Objects.requireNonNull(responses, "responses");
    Objects.requireNonNull(componentTypes, "componentTypes");
    return new CustomFormResponseImpl(responses, rawResponse, componentTypes);
  }

  @Override
  @NonNull
  public JsonArray getResponses() {
    return rawResponse;
  }

  @Override
  @NonNull
  public List<ComponentType> getComponentTypes() {
    return componentTypes;
  }

  @Nullable
  @SuppressWarnings("unchecked")
  private <T> T nextComponent(boolean includeLabels) {
    if (!hasNext()) {
      return null;
    }

    while (++index < responses.size()) {
      Object response = responses.get(index);
      if (response == null && !includeLabels) {
        continue;
      }
      if (response instanceof AbsentComponent) {
        return null;
      }
      return (T) response;
    }
    return null; // we don't have anything to check anymore
  }

  @Override
  @Nullable
  public <T> T next(boolean includeLabels) {
    return nextComponent(includeLabels);
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
  public void index(int index) {
    Preconditions.checkArgument(index >= 0, "index");
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

  @Override
  @Nullable
  public <T> T next() {
    return next(includeLabels);
  }

  @Override
  public int asDropdown() {
    Object next = next();
    if (next == null) {
      return 0;
    }
    if (next instanceof Integer) {
      return (int) next;
    }
    throw wrongType(index, "dropdown");
  }

  @Override
  @Nullable
  public String asInput() {
    Object next = next();
    if (next == null) {
      return null;
    }
    if (next instanceof String) {
      return (String) next;
    }
    throw wrongType(index, "input");
  }

  @Override
  public float asSlider() {
    Object next = next();
    if (next == null) {
      return 0.0f;
    }
    if (next instanceof Float) {
      return (float) next;
    }
    throw wrongType(index, "slider");
  }

  @Override
  public int asStepSlider() {
    Object next = next();
    if (next == null) {
      return 0;
    }
    if (next instanceof Integer) {
      return (int) next;
    }
    throw wrongType(index, "step slider");
  }

  @Override
  public boolean asToggle() {
    Object next = next();
    if (next == null) {
      return false;
    }
    if (next instanceof Boolean) {
      return (boolean) next;
    }
    throw wrongType(index, "toggle");
  }

  @Override
  @NonNull
  public JsonPrimitive get(int index) {
    Preconditions.checkArgument(index >= 0, "index");
    try {
      return rawResponse.get(index).getAsJsonPrimitive();
    } catch (IllegalStateException exception) {
      throw wrongType(index, "a primitive");
    }
  }

  @Override
  @Nullable
  @SuppressWarnings("unchecked")
  public <T> T valueAt(int index) throws IllegalArgumentException, ClassCastException {
    Preconditions.checkArgument(index >= 0, "index");
    if (index >= responses.size()) {
      throw new IllegalArgumentException("Requested an higher index than there are components");
    }
    return (T) responses.get(index);
  }

  @Override
  public int asDropdown(int index) {
    Object component = valueAt(index);
    if (component == null) {
      return 0;
    }
    if (component instanceof Integer) {
      return (int) component;
    }
    throw wrongType(index, "dropdown");
  }

  @Override
  @Nullable
  public String asInput(int index) {
    Object next = valueAt(index);
    if (next == null) {
      return null;
    }
    if (next instanceof String) {
      return (String) next;
    }
    throw wrongType(index, "input");
  }

  @Override
  public float asSlider(int index) {
    Object next = valueAt(index);
    if (next == null) {
      return 0.0f;
    }
    if (next instanceof Float) {
      return (float) next;
    }
    throw wrongType(index, "slider");
  }

  @Override
  public int asStepSlider(int index) {
    Object next = valueAt(index);
    if (next == null) {
      return 0;
    }
    if (next instanceof Integer) {
      return (int) next;
    }
    throw wrongType(index, "step slider");
  }

  @Override
  public boolean asToggle(int index) {
    Object next = valueAt(index);
    if (next == null) {
      return false;
    }
    if (next instanceof Boolean) {
      return (boolean) next;
    }
    throw wrongType(index, "toggle");
  }

  private IllegalStateException wrongType(int index, String expected) {
    return new IllegalStateException(String.format(
        "Expected %s on %s, got %s",
        expected, index, responses.get(index).toString()
    ));
  }
}
