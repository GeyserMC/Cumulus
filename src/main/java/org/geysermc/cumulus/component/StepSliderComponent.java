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

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.geysermc.cumulus.component.impl.StepSliderComponentImpl;

public interface StepSliderComponent extends Component {
  static StepSliderComponent of(String text, List<String> steps, int defaultStep) {
    return new StepSliderComponentImpl(text, steps, defaultStep);
  }

  static StepSliderComponent of(String text, int defaultStep, String... steps) {
    return of(text, Arrays.asList(steps), defaultStep);
  }

  static StepSliderComponent of(String text, String... steps) {
    return of(text, -1, steps);
  }

  static Builder builder() {
    return new StepSliderComponentImpl.Builder();
  }

  static Builder builder(String text) {
    return builder().text(text);
  }

  List<String> getSteps();

  int getDefaultStep();

  interface Builder {
    Builder text(String text);

    Builder step(String step, boolean defaultStep);

    Builder step(String step);

    Builder defaultStep(int defaultStep);

    StepSliderComponent build();

    StepSliderComponent translateAndBuild(Function<String, String> translator);
  }
}
