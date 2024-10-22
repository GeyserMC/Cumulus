/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component.impl;

import com.google.gson.annotations.SerializedName;
import org.checkerframework.checker.index.qual.Positive;
import org.geysermc.cumulus.component.SliderComponent;
import org.geysermc.cumulus.component.util.ComponentType;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class SliderComponentImpl extends ComponentImpl implements SliderComponent {
  private final float min;
  private final float max;
  private final float step;

  @SerializedName("default")
  private final float defaultValue;

  public SliderComponentImpl(
      String text, float min, float max, @Positive float step, float defaultValue) {
    super(ComponentType.SLIDER, text);
    // Bedrock doesn't work well with a higher min than max and negative steps,
    // so let's check all that.
    if (step <= 0.0f) throw new IllegalArgumentException("step value has to be positive");
    if (min > max) throw new IllegalArgumentException("min value is higher than max value");

    this.min = min;
    this.max = max;
    this.step = step;
    this.defaultValue = defaultValue;
  }

  public SliderComponentImpl(String text, float min, float max, @Positive float step) {
    this(text, min, max, step, generateDefaultValue(min, max, step));
  }

  private static float generateDefaultValue(float min, float max, @Positive float step) {
    // because step is always positive, max has to be bigger than min.
    // this allows us to get the middle between min and max quite easily.
    float middle = min + ((max - min) / 2.0f);

    // if the middle is a valid step, return it
    if (((middle - min) / step) % 1 == 0) {
      return middle;
    }

    // if it has tons of steps, don't even bother finding a technically correct middle
    if (min + step * 50.0f < max) {
      return min;
    }

    // find the closest middle (with a bias to the left)

    float previousStep = min;
    while (previousStep < max) {
      float next = previousStep + step;
      if (next > middle) {
        return previousStep;
      }
      previousStep = next;
    }

    // not sure how this can happen, but sure.
    // fallback to the last step assuming that it's the closest to the middle
    return previousStep;
  }

  @Override
  public float minValue() {
    return min;
  }

  @Override
  public float maxValue() {
    return max;
  }

  @Override
  public @Positive float step() {
    return step;
  }

  @Override
  public float defaultValue() {
    return defaultValue;
  }
}
