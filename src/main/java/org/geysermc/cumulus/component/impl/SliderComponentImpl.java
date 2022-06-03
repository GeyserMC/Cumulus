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

package org.geysermc.cumulus.component.impl;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.SerializedName;
import org.checkerframework.checker.index.qual.Positive;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.cumulus.component.SliderComponent;
import org.geysermc.cumulus.component.util.ComponentType;

public final class SliderComponentImpl extends ComponentImpl implements SliderComponent {
  private final float min;
  private final float max;
  private final float step;
  @SerializedName("default")
  private final float defaultValue;

  public SliderComponentImpl(
      @NonNull String text,
      float min,
      float max,
      @Positive float step,
      float defaultValue) {
    super(ComponentType.SLIDER, text);
    // Bedrock doesn't work well with a higher min than max and negative steps,
    // so let's check all that.
    Preconditions.checkArgument(step > 0.0f, "step value cannot be negative");
    Preconditions.checkArgument(min <= max, "min value is higher than max value");

    this.min = min;
    this.max = max;
    this.step = step;
    this.defaultValue = defaultValue;
  }

  public SliderComponentImpl(@NonNull String text, float min, float max, @Positive float step) {
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
  @Positive
  public float step() {
    return step;
  }

  @Override
  public float defaultValue() {
    return defaultValue;
  }
}
