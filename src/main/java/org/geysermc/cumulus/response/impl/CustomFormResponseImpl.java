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
import org.geysermc.cumulus.response.result.FormResponseResult;

public final class CustomFormResponseImpl implements CustomFormResponse {
  private final JsonArray responses;
  private final List<ComponentType> componentTypes;

  private int index = -1;
  private boolean includeLabels = false;

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
      return FormResponseResult.invalid(-1, "Response size doesn't match what has been sent");
    }

    //todo move validation and such to here

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

  @Nullable
  @SuppressWarnings("unchecked")
  private <T> T nextComponent(boolean includeLabels) {
    if (!hasNext()) {
      return null;
    }

    while (++index < responses.size()) {
      ComponentType type = componentTypes.get(index);
      if (type == ComponentType.LABEL && !includeLabels) {
        continue;
      }
      return (T) dataFromType(type, index);
    }
    return null; // we don't have anything to check anymore
  }

  @Override
  @Nullable
  public <T> T next(boolean includeLabels) {
    return nextComponent(includeLabels);
  }

  @Override
  @Nullable
  public <T> T next() {
    return next(includeLabels);
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
    // noinspection ConstantConditions
    return responses.size() > index && !componentAt(index).isJsonNull();
  }

  @Override
  public boolean isNextPresent() {
    // noinspection ConstantConditions
    return hasNext() && !componentAt(index + 1).isJsonNull();
  }

  //todo this doesn't work properly. Optional components are supposed to return the default value.
  // Currently they throw an exception

  @Override
  public int asDropdown() {
    skip();
    return asDropdown(index);
  }

  @Override
  @Nullable
  public String asInput() {
    skip();
    return asInput(index);
  }

  @Override
  public float asSlider() {
    skip();
    return asSlider(index);
  }

  @Override
  public int asStepSlider() {
    skip();
    return asStepSlider(index);
  }

  @Override
  public boolean asToggle() {
    skip();
    return asToggle(index);
  }

  @Override
  @Nullable
  public JsonPrimitive componentAt(int index) {
    Preconditions.checkArgument(index >= 0, "index");
    try {
      return responses.get(index).getAsJsonPrimitive();
    } catch (IllegalStateException exception) {
      wrongType(index, "a primitive");
      return null;
    }
  }

  @Override
  public int asDropdown(int index) {
    JsonPrimitive primitive = componentAt(index);
    if (primitive == null || !primitive.isNumber()) {
      wrongType(index, "dropdown");
    }
    return primitive.getAsInt();
  }

  @Override
  public String asInput(int index) {
    JsonPrimitive primitive = componentAt(index);
    if (primitive == null || !primitive.isString()) {
      wrongType(index, "input");
    }
    return primitive.getAsString();
  }

  @Override
  public float asSlider(int index) {
    JsonPrimitive primitive = componentAt(index);
    if (primitive == null || !primitive.isNumber()) {
      wrongType(index, "slider");
    }
    return primitive.getAsFloat();
  }

  @Override
  public int asStepSlider(int index) {
    JsonPrimitive primitive = componentAt(index);
    if (primitive == null || !primitive.isNumber()) {
      wrongType(index, "step slider");
    }
    return primitive.getAsInt();
  }

  @Override
  public boolean asToggle(int index) {
    JsonPrimitive primitive = componentAt(index);
    if (primitive == null || !primitive.isBoolean()) {
      wrongType(index, "toggle");
    }
    return primitive.getAsBoolean();
  }

  private Object dataFromType(ComponentType type, int index) {
    switch (type) {
      case DROPDOWN:
        return asDropdown(index);
      case INPUT:
        return asInput(index);
      case SLIDER:
        return asSlider(index);
      case STEP_SLIDER:
        return asStepSlider(index);
      case TOGGLE:
        return asToggle(index);
      default:
        return null; // label e.g. is always null
    }
  }

  private void wrongType(int index, String expected) {
    throw new IllegalStateException(String.format(
        "Expected %s on %s, got %s",
        expected, index, responses.get(index).toString()
    ));
  }
}
