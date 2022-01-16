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
import org.geysermc.cumulus.util.ComponentType;

public final class SliderComponentImpl extends Component implements SliderComponent {
  private final float min;
  private final float max;
  private final int step;
  @SerializedName("default")
  private final float defaultValue;

  public SliderComponentImpl(
      @NonNull String text,
      float min,
      float max,
      int step,
      float defaultValue) {
    super(ComponentType.SLIDER, text);
    Preconditions.checkArgument(step >= 1, "step");

    min = Math.max(min, 0f);
    max = Math.max(max, min);

    if (defaultValue == -1f) {
      defaultValue = (int) Math.floor(min + max / 2D);
    }

    this.min = min;
    this.max = max;
    this.step = step;
    this.defaultValue = defaultValue;
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
  public @Positive int step() {
    return step;
  }

  @Override
  public float defaultValue() {
    return defaultValue;
  }
}
