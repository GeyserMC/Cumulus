/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.util.AbsentComponent;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class CustomFormResponseImpl implements CustomFormResponse {
  /**
   * Contains null for LabelComponent and {@link AbsentComponent} for components that were optional
   * and not added.
   */
  private final List<@Nullable Object> responses;

  private int index = -1;
  private boolean includeLabels = false;

  private CustomFormResponseImpl(List<@Nullable Object> responses) {
    this.responses = Collections.unmodifiableList(responses);
  }

  public static CustomFormResponse of(List<@Nullable Object> responses) {
    Objects.requireNonNull(responses, "responses");
    return new CustomFormResponseImpl(responses);
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
  public void skip(int amount) {
    if (amount < 1) throw new IllegalArgumentException("skip amount has to be positive");
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
    if (index < -1) throw new IllegalArgumentException("index has to be -1 or greater");
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
  private <T> @Nullable T nextOrAbsent() {
    return nextOrAbsent(includeLabels);
  }

  @Override
  public <T> @Nullable T next() {
    T next = nextOrAbsent(includeLabels);
    if (next instanceof AbsentComponent) {
      return null;
    }
    return next;
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

  /**
   * @return null for label components and {@link AbsentComponent} for optional components that were
   *     not added
   */
  @SuppressWarnings("unchecked")
  private <T> @Nullable T valueOrAbsent(int index)
      throws IllegalArgumentException, ClassCastException {
    if (index < 0) throw new IllegalArgumentException("index cannot be negative");
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
