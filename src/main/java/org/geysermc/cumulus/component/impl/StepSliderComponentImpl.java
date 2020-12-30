/*
 * Copyright (c) 2019-2020 GeyserMC. http://geysermc.org
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

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;
import org.geysermc.cumulus.component.StepSliderComponent;
import org.geysermc.cumulus.util.ComponentType;

@Getter
public final class StepSliderComponentImpl extends Component implements StepSliderComponent {
  private final List<String> steps;
  @SerializedName("default")
  private final int defaultStep;

  public StepSliderComponentImpl(String text, List<String> steps, int defaultStep) {
    super(ComponentType.STEP_SLIDER, text != null ? text : "");
    this.steps = Collections.unmodifiableList(steps);

    if (defaultStep >= steps.size() || defaultStep == -1) {
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

  public static final class Builder implements StepSliderComponent.Builder {
    private final List<String> steps = new ArrayList<>();
    private String text;
    private int defaultStep;

    public Builder text(String text) {
      this.text = text;
      return this;
    }

    public Builder step(String step, boolean defaultStep) {
      steps.add(step);
      if (defaultStep) {
        this.defaultStep = steps.size() - 1;
      }
      return this;
    }

    public Builder step(String step) {
      return step(step, false);
    }

    public Builder defaultStep(int defaultStep) {
      this.defaultStep = defaultStep;
      return this;
    }

    public StepSliderComponentImpl build() {
      return new StepSliderComponentImpl(text, steps, defaultStep);
    }

    public StepSliderComponentImpl translateAndBuild(Function<String, String> translator) {
      for (int i = 0; i < steps.size(); i++) {
        steps.set(i, translator.apply(steps.get(i)));
      }

      return new StepSliderComponentImpl(translator.apply(text), steps, defaultStep);
    }
  }
}
