/*
 * Copyright (c) 2020-2023 GeyserMC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
import org.checkerframework.common.returnsreceiver.qual.This;
import org.geysermc.cumulus.component.impl.StepSliderComponentImpl;

/**
 * Step slider component is a component that can only be used in CustomForm. This component is a
 * combination of the dropdown component and the slider component. A step slider has a defined set
 * of items you can slide through.
 */
public interface StepSliderComponent extends Component {
  static @NonNull StepSliderComponent of(
      @NonNull String text, @NonNull List<String> steps, @NonNegative int defaultStep) {
    return new StepSliderComponentImpl(text, steps, defaultStep);
  }

  static @NonNull StepSliderComponent of(
      @NonNull String text, @NonNegative int defaultStep, @NonNull String... steps) {
    return of(text, Arrays.asList(steps), defaultStep);
  }

  static @NonNull StepSliderComponent of(@NonNull String text, @NonNull String... steps) {
    return of(text, 0, steps);
  }

  static @NonNull Builder builder() {
    return new StepSliderComponentImpl.Builder();
  }

  static @NonNull Builder builder(@NonNull String text) {
    return builder().text(text);
  }

  /**
   * Returns the list of steps that will be shown in the step slider.
   *
   * @since 1.1
   */
  @NonNull List<String> steps();

  /**
   * Returns the index of the step that is selected by default.
   *
   * @since 1.1
   */
  @NonNegative int defaultStep();

  interface Builder {
    /**
     * Sets the text that will be shown before the steps.
     *
     * @param text the text to show
     */
    @This Builder text(@NonNull String text);

    /**
     * Adds a step to the list of steps.
     *
     * @param step the text to show in the step slider entry
     * @param isDefault if this should become the default option
     */
    @This Builder step(@NonNull String step, boolean isDefault);

    /**
     * Adds a step to the list of steps. This step won't become the default step, unless {@link
     * #defaultStep(int)} is called after this.
     *
     * @param step the text to show in the step slider entry
     */
    @This Builder step(@NonNull String step);

    /**
     * Sets the default step of this step slider.
     *
     * @param defaultStep the index of the option that should become the default option.
     * @throws IllegalArgumentException when the index of the default option is out of bounds
     */
    @This Builder defaultStep(@NonNegative int defaultStep) throws IllegalArgumentException;

    /** Returns the created step slider from the given options. */
    @NonNull StepSliderComponent build();

    /**
     * Translated everything given to this builder using the provided translation function, and
     * return the created step slider after that.
     *
     * @param translator the translation function
     */
    @NonNull StepSliderComponent translateAndBuild(@NonNull Function<String, String> translator);
  }
}
