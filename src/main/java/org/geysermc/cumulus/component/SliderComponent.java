/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component;

import org.checkerframework.checker.index.qual.Positive;
import org.geysermc.cumulus.component.impl.SliderComponentImpl;
import org.jspecify.annotations.NullMarked;

/**
 * Slider component is a component that can only be used in CustomForm. With this component you can
 * slide between the min (left) and max (right) using the provided step size.
 *
 * <p>The Bedrock client does not work well with a negative step value. Because of that, Cumulus
 * does not allow the min higher than the max and also doesn't allow a negative step value.
 *
 * <p>Cumulus will generate a default value if none is provided. It will try to find a valid step in
 * the middle of your range, but if your slider contains a lot of steps it may use min value
 * instead.
 *
 * @since 1.0
 */
@NullMarked
public interface SliderComponent extends Component {
  /**
   * Create a direct instance of a slider.
   *
   * @param text the text that is shown in the component
   * @param min the minimal value of the slider
   * @param max the maximum value of the slider
   * @param step the amount between each step
   * @param defaultValue the default value of the slider
   * @return the created instance
   * @since 1.1
   */
  static SliderComponent of(
      String text, float min, float max, @Positive float step, float defaultValue) {
    return new SliderComponentImpl(text, min, max, step, defaultValue);
  }

  /**
   * Create a direct instance of a slider, with the default value being computed.
   *
   * @param text the text that is shown in the component
   * @param min the minimal value of the slider
   * @param max the maximum value of the slider
   * @param step the amount between each step
   * @return the created instance
   * @see SliderComponent
   * @since 1.1
   */
  static SliderComponent of(String text, float min, float max, @Positive float step) {
    return new SliderComponentImpl(text, min, max, step);
  }

  /**
   * Create a direct instance of a slider, with the step being 1 and the default value being
   * computed.
   *
   * @param text the text that is shown in the component
   * @param min the minimal value of the slider
   * @param max the maximum value of the slider
   * @return the created instance
   * @see SliderComponent
   * @since 1.0
   */
  static SliderComponent of(String text, float min, float max) {
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
  @Positive float step();

  /**
   * Returns the default value of the slider.
   *
   * @since 1.1
   */
  float defaultValue();

  // todo control_locked ??
}
