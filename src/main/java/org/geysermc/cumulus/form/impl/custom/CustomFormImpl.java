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
package org.geysermc.cumulus.form.impl.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.index.qual.Positive;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.DropdownComponent;
import org.geysermc.cumulus.component.InputComponent;
import org.geysermc.cumulus.component.LabelComponent;
import org.geysermc.cumulus.component.SliderComponent;
import org.geysermc.cumulus.component.StepSliderComponent;
import org.geysermc.cumulus.component.ToggleComponent;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.form.impl.FormImpl;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.util.FormImage;

public final class CustomFormImpl extends FormImpl<CustomFormResponse> implements CustomForm {

  private final FormImage icon;
  private final List<Component> content;

  public CustomFormImpl(
      @NonNull String title, @Nullable FormImage icon, @NonNull List<Component> content) {
    super(title);
    this.icon = icon;
    this.content = Collections.unmodifiableList(content);
  }

  @Override
  public @NonNull FormImage icon() {
    return icon;
  }

  @Override
  public @NonNull List<Component> content() {
    return content;
  }

  public static final class Builder
      extends FormImpl.Builder<CustomForm.Builder, CustomForm, CustomFormResponse>
      implements CustomForm.Builder {

    private final List<Component> components = new ArrayList<>();
    private FormImage icon;

    @Override
    public Builder icon(@NonNull FormImage image) {
      icon = Objects.requireNonNull(image, "image");
      return this;
    }

    @Override
    public Builder icon(FormImage.@NonNull Type type, @NonNull String data) {
      icon = FormImage.of(type, data);
      return this;
    }

    @Override
    public Builder iconPath(@NonNull String path) {
      return icon(FormImage.Type.PATH, path);
    }

    @Override
    public Builder iconUrl(@NonNull String url) {
      return icon(FormImage.Type.URL, url);
    }

    @Override
    public Builder component(@NonNull Component component) {
      components.add(Objects.requireNonNull(component, "component"));
      return this;
    }

    @Override
    public Builder optionalComponent(@NonNull Component component, boolean shouldAdd) {
      if (shouldAdd) {
        return component(component);
      }
      return addNullComponent();
    }

    @Override
    public Builder dropdown(DropdownComponent.@NonNull Builder dropdownBuilder) {
      Objects.requireNonNull(dropdownBuilder, "dropdownBuilder");
      return component(dropdownBuilder.translateAndBuild(this::translate));
    }

    @Override
    public Builder dropdown(
        @NonNull String text, @NonNull List<String> options, @NonNegative int defaultOption) {
      Objects.requireNonNull(text, "text");
      //noinspection ConstantValue
      if (defaultOption < 0) throw new IllegalArgumentException("defaultOption cannot be negative");

      List<String> optionsList = new ArrayList<>();
      for (String option : options) {
        optionsList.add(translate(option));
      }
      return component(DropdownComponent.of(translate(text), optionsList, defaultOption));
    }

    @Override
    public Builder dropdown(@NonNull String text, int defaultOption, @NonNull String... options) {
      return dropdown(text, Arrays.asList(options), defaultOption);
    }

    @Override
    public Builder dropdown(@NonNull String text, @NonNull List<String> options) {
      return dropdown(text, options, 0);
    }

    @Override
    public Builder dropdown(@NonNull String text, @NonNull String... options) {
      return dropdown(text, Arrays.asList(options), 0);
    }

    @Override
    public Builder optionalDropdown(
        @NonNull String text,
        @NonNull List<String> options,
        @NonNegative int defaultOption,
        boolean shouldAdd) {
      if (shouldAdd) {
        return dropdown(text, options, defaultOption);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalDropdown(
        boolean shouldAdd,
        @NonNull String text,
        @NonNegative int defaultOption,
        @NonNull String... options) {
      return optionalDropdown(text, Arrays.asList(options), defaultOption, shouldAdd);
    }

    @Override
    public Builder optionalDropdown(
        @NonNull String text, @NonNull List<String> options, boolean shouldAdd) {
      if (shouldAdd) {
        return dropdown(text, options);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalDropdown(
        boolean shouldAdd, @NonNull String text, @NonNull String... options) {
      return optionalDropdown(text, Arrays.asList(options), shouldAdd);
    }

    @Override
    public Builder input(
        @NonNull String text, @NonNull String placeholder, @NonNull String defaultText) {
      return component(
          InputComponent.of(translate(text), translate(placeholder), translate(defaultText)));
    }

    @Override
    public Builder input(@NonNull String text, @NonNull String placeholder) {
      return component(InputComponent.of(translate(text), translate(placeholder)));
    }

    @Override
    public Builder input(@NonNull String text) {
      return component(InputComponent.of(translate(text)));
    }

    @Override
    public Builder optionalInput(
        @NonNull String text,
        @NonNull String placeholder,
        @NonNull String defaultText,
        boolean shouldAdd) {
      if (shouldAdd) {
        return input(text, placeholder, defaultText);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalInput(
        @NonNull String text, @NonNull String placeholder, boolean shouldAdd) {
      if (shouldAdd) {
        return input(text, placeholder);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalInput(@NonNull String text, boolean shouldAdd) {
      if (shouldAdd) {
        return input(text);
      }
      return addNullComponent();
    }

    @Override
    public Builder label(@NonNull String text) {
      return component(LabelComponent.of(translate(text)));
    }

    @Override
    public Builder optionalLabel(@NonNull String text, boolean shouldAdd) {
      if (shouldAdd) {
        return label(text);
      }
      return addNullComponent();
    }

    @Override
    public Builder slider(
        @NonNull String text, float min, float max, @Positive float step, float defaultValue) {
      return component(SliderComponent.of(text, min, max, step, defaultValue));
    }

    @Override
    public Builder slider(@NonNull String text, float min, float max, @Positive float step) {
      return component(SliderComponent.of(text, min, max, step));
    }

    @Override
    public Builder slider(@NonNull String text, float min, float max) {
      return slider(text, min, max, 1);
    }

    @Override
    public Builder optionalSlider(
        @NonNull String text,
        float min,
        float max,
        @Positive float step,
        float defaultValue,
        boolean shouldAdd) {
      if (shouldAdd) {
        return slider(text, min, max, step, defaultValue);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalSlider(
        @NonNull String text, float min, float max, @Positive float step, boolean shouldAdd) {
      if (shouldAdd) {
        return slider(text, min, max, step);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalSlider(@NonNull String text, float min, float max, boolean shouldAdd) {
      if (shouldAdd) {
        return slider(text, min, max);
      }
      return addNullComponent();
    }

    @Override
    public Builder stepSlider(StepSliderComponent.@NonNull Builder stepSliderBuilder) {
      Objects.requireNonNull(stepSliderBuilder, "stepSliderBuilder");
      return component(stepSliderBuilder.translateAndBuild(this::translate));
    }

    @Override
    public Builder stepSlider(
        @NonNull String text, @NonNull List<String> steps, @NonNegative int defaultStep) {
      Objects.requireNonNull(text, "text");
      //noinspection ConstantValue
      if (defaultStep < 0) throw new IllegalArgumentException("defaultStep cannot be negative");

      List<String> stepsList = new ArrayList<>();
      for (String option : steps) {
        stepsList.add(translate(option));
      }
      return component(StepSliderComponent.of(translate(text), stepsList, defaultStep));
    }

    @Override
    public Builder stepSlider(@NonNull String text, int defaultStep, String... steps) {
      return stepSlider(text, Arrays.asList(steps), defaultStep);
    }

    @Override
    public Builder stepSlider(@NonNull String text, @NonNull List<String> steps) {
      return stepSlider(text, steps, 0);
    }

    @Override
    public Builder stepSlider(@NonNull String text, String... steps) {
      return stepSlider(text, Arrays.asList(steps));
    }

    @Override
    public Builder optionalStepSlider(
        @NonNull String text,
        @NonNull List<String> steps,
        @NonNegative int defaultStep,
        boolean shouldAdd) {
      if (shouldAdd) {
        return stepSlider(text, steps, defaultStep);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalStepSlider(
        boolean shouldAdd,
        @NonNull String text,
        @NonNegative int defaultStep,
        @NonNull String... steps) {
      return optionalStepSlider(text, Arrays.asList(steps), defaultStep, shouldAdd);
    }

    @Override
    public Builder optionalStepSlider(
        @NonNull String text, @NonNull List<String> steps, boolean shouldAdd) {
      if (shouldAdd) {
        return stepSlider(text, steps);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalStepSlider(
        boolean shouldAdd, @NonNull String text, @NonNull String... steps) {
      return optionalStepSlider(text, Arrays.asList(steps), shouldAdd);
    }

    @Override
    public Builder toggle(@NonNull String text, boolean defaultValue) {
      return component(ToggleComponent.of(translate(text), defaultValue));
    }

    @Override
    public Builder toggle(@NonNull String text) {
      return component(ToggleComponent.of(translate(text)));
    }

    @Override
    public Builder optionalToggle(@NonNull String text, boolean defaultValue, boolean shouldAdd) {
      if (shouldAdd) {
        return toggle(text, defaultValue);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalToggle(@NonNull String text, boolean shouldAdd) {
      if (shouldAdd) {
        return toggle(text);
      }
      return addNullComponent();
    }

    @Override
    public @NonNull CustomForm build() {
      CustomFormImpl form = new CustomFormImpl(title, icon, components);
      setResponseHandler(form, form);
      return form;
    }

    private Builder addNullComponent() {
      components.add(null);
      return this;
    }
  }
}
