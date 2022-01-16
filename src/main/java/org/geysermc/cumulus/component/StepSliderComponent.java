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

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.cumulus.component.impl.StepSliderComponentImpl;

public interface StepSliderComponent extends Component {
  @NonNull
  static StepSliderComponent of(
      @NonNull String text,
      @NonNull List<String> steps,
      @NonNegative int defaultStep) {
    return new StepSliderComponentImpl(text, steps, defaultStep);
  }

  @NonNull
  static StepSliderComponent of(
      @NonNull String text,
      @NonNegative int defaultStep,
      @NonNull String... steps) {
    return of(text, Arrays.asList(steps), defaultStep);
  }

  @NonNull
  static StepSliderComponent of(@NonNull String text, @NonNull String... steps) {
    return of(text, 1, steps);
  }

  @NonNull
  static Builder builder() {
    return new StepSliderComponentImpl.Builder();
  }

  @NonNull
  static Builder builder(@NonNull String text) {
    return builder().text(text);
  }

  @NonNull
  List<String> steps();

  @NonNegative
  int defaultStep();

  interface Builder {
    @NonNull
    Builder text(@NonNull String text);

    @NonNull
    Builder step(@NonNull String step, boolean defaultStep);

    @NonNull
    Builder step(@NonNull String step);

    @NonNull
    Builder defaultStep(@NonNegative int defaultStep);

    @NonNull
    StepSliderComponent build();

    @NonNull
    StepSliderComponent translateAndBuild(@NonNull Function<String, String> translator);
  }
}
