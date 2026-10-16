/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.geysermc.cumulus.component.impl.StepSliderComponentImpl;

/**
 * Step slider component is a component that can only be used in CustomForm. This component is a
 * combination of the dropdown component and the slider component. A step slider has a defined set
 * of items you can slide through.
 */
public interface StepSliderComponent extends Component {
  static StepSliderComponent of(String text, List<String> steps, @NonNegative int defaultStep) {
    return new StepSliderComponentImpl(text, steps, defaultStep);
  }

  static StepSliderComponent of(String text, @NonNegative int defaultStep, String... steps) {
    return of(text, Arrays.asList(steps), defaultStep);
  }

  static StepSliderComponent of(String text, String... steps) {
    return of(text, 0, steps);
  }

  static Builder builder() {
    return new StepSliderComponentImpl.Builder();
  }

  static Builder builder(String text) {
    return builder().text(text);
  }

  /**
   * Returns the list of steps that will be shown in the step slider.
   *
   * @since 1.1
   */
  List<String> steps();

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
    @This Builder text(String text);

    /**
     * Adds a step to the list of steps.
     *
     * @param step the text to show in the step slider entry
     * @param isDefault if this should become the default option
     */
    @This Builder step(String step, boolean isDefault);

    /**
     * Adds a step to the list of steps. This step won't become the default step, unless {@link
     * #defaultStep(int)} is called after this.
     *
     * @param step the text to show in the step slider entry
     */
    @This Builder step(String step);

    /**
     * Sets the default step of this step slider.
     *
     * @param defaultStep the index of the option that should become the default option.
     * @throws IllegalArgumentException when the index of the default option is out of bounds
     */
    @This Builder defaultStep(@NonNegative int defaultStep) throws IllegalArgumentException;

    /** Returns the created step slider from the given options. */
    StepSliderComponent build();

    /**
     * Translated everything given to this builder using the provided translation function, and
     * return the created step slider after that.
     *
     * @param translator the translation function
     */
    StepSliderComponent translateAndBuild(Function<String, String> translator);
  }
}
