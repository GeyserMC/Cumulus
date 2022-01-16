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
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.util.ComponentType;

public final class CustomFormResponseImpl implements CustomFormResponse {
  private final JsonArray responses;
  private final List<ComponentType> componentTypes;

  private int index = -1;

  private CustomFormResponseImpl(JsonArray responses, List<ComponentType> componentTypes) {
    this.responses = responses;
    this.componentTypes = componentTypes;
  }

  @NonNull
  public static FormResponseResult<CustomFormResponse> of(
      @NonNull List<ComponentType> componentTypes,
      @NonNull JsonArray responses) {
    Objects.requireNonNull(componentTypes, "componentTypes");
    Objects.requireNonNull(responses, "responses");

    if (componentTypes.size() != responses.size()) {
      return FormResponseResult.invalid();
    }

    return FormResponseResult.valid(
        new CustomFormResponseImpl(responses, Collections.unmodifiableList(componentTypes))
    );
  }

  @Override
  public @NonNull JsonArray responses() {
    return responses;
  }

  @Override
  public @NonNull List<ComponentType> componentTypes() {
    return componentTypes;
  }

  @Override
  @Nullable
  @SuppressWarnings("unchecked")
  public <T> T next(boolean includeLabels) {
    if (!hasNext()) {
      return null;
    }

    while (++index < responses.size()) {
      ComponentType type = componentTypes.get(index);
      if (type == ComponentType.LABEL && !includeLabels) {
        continue;
      }
      return (T) getDataFromType(type, index);
    }
    return null; // we don't have anything to check anymore
  }

  @Override
  @Nullable
  public <T> T next() {
    return next(false);
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
  public boolean hasNext() {
    return responses.size() > index + 1;
  }

  @Override
  @Nullable
  public JsonPrimitive get(int index) {
    Preconditions.checkArgument(index >= 0, "index");
    try {
      return responses.get(index).getAsJsonPrimitive();
    } catch (IllegalStateException exception) {
      wrongType(index, "a primitive");
      return null;
    }
  }

  @Override
  public int getDropdown(int index) {
    JsonPrimitive primitive = get(index);
    if (primitive == null || !primitive.isNumber()) {
      wrongType(index, "dropdown");
    }
    return primitive.getAsInt();
  }

  @Override
  public String getInput(int index) {
    JsonPrimitive primitive = get(index);
    if (primitive == null || !primitive.isString()) {
      wrongType(index, "input");
    }
    return primitive.getAsString();
  }

  @Override
  public float getSlider(int index) {
    JsonPrimitive primitive = get(index);
    if (primitive == null || !primitive.isNumber()) {
      wrongType(index, "slider");
    }
    return primitive.getAsFloat();
  }

  @Override
  public int getStepSlide(int index) {
    JsonPrimitive primitive = get(index);
    if (primitive == null || !primitive.isNumber()) {
      wrongType(index, "step slider");
    }
    return primitive.getAsInt();
  }

  @Override
  public boolean getToggle(int index) {
    JsonPrimitive primitive = get(index);
    if (primitive == null || !primitive.isBoolean()) {
      wrongType(index, "toggle");
    }
    return primitive.getAsBoolean();
  }

  private Object getDataFromType(ComponentType type, int index) {
    switch (type) {
      case DROPDOWN:
        return getDropdown(index);
      case INPUT:
        return getInput(index);
      case SLIDER:
        return getSlider(index);
      case STEP_SLIDER:
        return getStepSlide(index);
      case TOGGLE:
        return getToggle(index);
      default:
        return null; // label e.g. is always null
    }
  }

  private void wrongType(int index, String expected) {
    throw new IllegalStateException(String.format(
        "Expected %s on %s, got %s",
        expected, index, responses.get(index).toString()));
  }
}
