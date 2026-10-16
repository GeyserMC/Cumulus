/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component;

import org.checkerframework.checker.index.qual.Positive;
import org.geysermc.cumulus.component.impl.SliderComponentImpl;

/**
 * Slider component is a component that can only be used in CustomForm. With this component you can
 * slide between the min (left) and max (right) using the provided step size. The Bedrock client
 * does not work well with a negative step value. Because of that, Cumulus does not allow the min
 * higher than the max and also doesn't allow a negative step value.<br>
 * Cumulus will generate a default value if none is provided. It will try to find a valid step in
 * the middle of your range, but if your step size is too low it will use the min value instead.
 */
public interface SliderComponent extends Component {
  static SliderComponent of(
      String text, float min, float max, @Positive float step, float defaultValue) {
    return new SliderComponentImpl(text, min, max, step, defaultValue);
  }

  static SliderComponent of(String text, float min, float max, @Positive float step) {
    return new SliderComponentImpl(text, min, max, step);
  }

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
