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

package org.geysermc.cumulus.component;

import org.checkerframework.checker.index.qual.Positive;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.cumulus.component.impl.SliderComponentImpl;

/**
 * Slider component is a component that can only be used in CustomForm. With this component you can
 * slide between the min (left) and max (right) using the provided step size. The Bedrock client
 * does not work well with a negative step value. Because of that, Cumulus does not allow the min
 * higher than the max and also doesn't allow a negative step value.<br> Cumulus will generate a
 * default value if none is provided. It will try to find a valid step in the middle of your range,
 * but if your step size is too low it will use the min value instead.
 */
public interface SliderComponent extends Component {
  @NonNull
  static SliderComponent of(
      @NonNull String text,
      float min,
      float max,
      @Positive float step,
      float defaultValue) {
    return new SliderComponentImpl(text, min, max, step, defaultValue);
  }

  @NonNull
  static SliderComponent of(@NonNull String text, float min, float max, @Positive float step) {
    return new SliderComponentImpl(text, min, max, step);
  }

  @NonNull
  static SliderComponent of(@NonNull String text, float min, float max) {
    return of(text, min, max, 1);
  }

  /**
   * Returns the minimum value of the slider.
   *
   * @since 1.1
   */
  float minValue();

  /**
   * Returns the maximum value of the slider.
   *
   * @since 1.1
   */
  float maxValue();

  /**
   * Returns the amount that each step should add to the value.
   *
   * @since 1.1
   */
  @Positive
  float step();

  /**
   * Returns the default value of the slider.
   *
   * @since 1.1
   */
  float defaultValue();

  //todo control_locked ??

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method has been replaced by
   * {@link #minValue()}.
   */
  @Deprecated
  default float getMin() {
    return minValue();
  }

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method has been replaced by
   * {@link #maxValue()}.
   */
  @Deprecated
  default float getMax() {
    return maxValue();
  }

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method has been replaced by
   * {@link #step()}.
   */
  @Deprecated
  @Positive
  default int getStep() {
    return (int) step();
  }

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method has been replaced by
   * {@link #defaultValue()}.
   */
  default float getDefaultValue() {
    return defaultValue();
  }
}
