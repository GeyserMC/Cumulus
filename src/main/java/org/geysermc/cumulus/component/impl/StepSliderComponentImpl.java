/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component.impl;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.checkerframework.checker.index.qual.NonNegative;
import org.geysermc.cumulus.component.StepSliderComponent;
import org.geysermc.cumulus.component.util.ComponentType;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class StepSliderComponentImpl extends ComponentImpl implements StepSliderComponent {
  private final List<String> steps;

  @SerializedName("default")
  private final int defaultStep;

  public StepSliderComponentImpl(String text, List<String> steps, int defaultStep) {
    super(ComponentType.STEP_SLIDER, text);
    Objects.requireNonNull(steps, "steps cannot be null");
    if (defaultStep < 0) throw new IllegalArgumentException("defaultStep cannot be negative");

    this.steps = Collections.unmodifiableList(steps);
    // todo should we allow this?
    if (defaultStep >= steps.size()) {
      defaultStep = 0;
    }
    this.defaultStep = defaultStep;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static Builder builder(String text) {
    return builder().text(text);
  }

  @Override
  public List<String> steps() {
    return steps;
  }

  @Override
  public @NonNegative int defaultStep() {
    return defaultStep;
  }

  public static final class Builder implements StepSliderComponent.Builder {
    private final List<String> steps = new ArrayList<>();
    private String text = "";
    private int defaultStep;

    public Builder text(String text) {
      this.text = Objects.requireNonNull(text, "text");
      return this;
    }

    public Builder step(String step, boolean isDefault) {
      steps.add(Objects.requireNonNull(step, "step"));
      if (isDefault) {
        this.defaultStep = steps.size() - 1;
      }
      return this;
    }

    public Builder step(String step) {
      return step(step, false);
    }

    public Builder defaultStep(int defaultStep) {
      if (defaultStep < 0) throw new IllegalArgumentException("defaultStep cannot be negative");
      if (defaultStep >= steps.size()) {
        throw new IllegalArgumentException("defaultStep is out of bound");
      }
      this.defaultStep = defaultStep;
      return this;
    }

    public StepSliderComponentImpl build() {
      return new StepSliderComponentImpl(text, steps, defaultStep);
    }

    public StepSliderComponentImpl translateAndBuild(Function<String, String> translator) {
      Objects.requireNonNull(translator, "translator cannot be null");
      steps.replaceAll(translator::apply);
      return new StepSliderComponentImpl(translator.apply(text), steps, defaultStep);
    }
  }
}
