/*
 * Copyright (c) 2020-2021 GeyserMC. http://geysermc.org
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

public interface SliderComponent extends Component {
  @NonNull
  static SliderComponent of(
      @NonNull String text,
      float min,
      float max,
      @Positive int step,
      float defaultValue) {
    return new SliderComponentImpl(text, min, max, step, defaultValue);
  }

  @NonNull
  static SliderComponent of(@NonNull String text, float min, float max, @Positive int step) {
    return of(text, min, max, step, 0);
  }

  @NonNull
  static SliderComponent of(@NonNull String text, float min, float max, float defaultValue) {
    return of(text, min, max, 1, defaultValue);
  }

  @NonNull
  static SliderComponent of(@NonNull String text, float min, float max) {
    return of(text, min, max, 1, 0);
  }

  float getMin();

  float getMax();

  @Positive
  int getStep();

  float getDefaultValue();
}
