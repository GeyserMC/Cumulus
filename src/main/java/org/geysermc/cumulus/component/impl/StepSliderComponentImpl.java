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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.cumulus.component.StepSliderComponent;
import org.geysermc.cumulus.component.util.ComponentType;

public final class StepSliderComponentImpl extends ComponentImpl implements StepSliderComponent {
  private final List<String> steps;
  @SerializedName("default")
  private final int defaultStep;

  public StepSliderComponentImpl(
      @NonNull String text,
      @NonNull List<String> steps,
      int defaultStep) {
    super(ComponentType.STEP_SLIDER, text);
    Preconditions.checkNotNull(steps, "steps");
    Preconditions.checkArgument(defaultStep >= 0, "defaultStep");

    this.steps = Collections.unmodifiableList(steps);
    //todo should we allow this?
    if (defaultStep >= steps.size()) {
      defaultStep = 0;
    }
    this.defaultStep = defaultStep;
  }

  public static @NonNull Builder builder() {
    return new Builder();
  }

  public static @NonNull Builder builder(@NonNull String text) {
    return builder().text(text);
  }

  @Override
  public @NonNull List<String> steps() {
    return steps;
  }

  @Override
  public @NonNegative int defaultStep() {
    return defaultStep;
  }

  // the JVM doesn't allow interface methods to become default methods

  public @NonNull List<String> getSteps() {
    return steps();
  }

  public int getDefaultStep() {
    return defaultStep();
  }

  public static final class Builder implements StepSliderComponent.Builder {
    private final List<String> steps = new ArrayList<>();
    private String text = "";
    private int defaultStep;

    public Builder text(@NonNull String text) {
      this.text = Objects.requireNonNull(text, "text");
      return this;
    }

    public Builder step(@NonNull String step, boolean isDefault) {
      steps.add(Objects.requireNonNull(step, "step"));
      if (isDefault) {
        this.defaultStep = steps.size() - 1;
      }
      return this;
    }

    public Builder step(@NonNull String step) {
      return step(step, false);
    }

    public Builder defaultStep(int defaultStep) {
      Preconditions.checkArgument(defaultStep >= 0, "defaultStep");
      Preconditions.checkArgument(steps.size() > defaultStep, "defaultStep is out of bounds");
      this.defaultStep = defaultStep;
      return this;
    }

    public @NonNull StepSliderComponentImpl build() {
      return new StepSliderComponentImpl(text, steps, defaultStep);
    }

    public @NonNull StepSliderComponentImpl translateAndBuild(@NonNull Function<String, String> translator) {
      Objects.requireNonNull(translator, "translator");
      steps.replaceAll(translator::apply);
      return new StepSliderComponentImpl(translator.apply(text), steps, defaultStep);
    }
  }
}
