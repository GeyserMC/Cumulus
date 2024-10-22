/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
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
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class CustomFormImpl extends FormImpl<CustomFormResponse> implements CustomForm {
  private final @Nullable FormImage icon;
  private final List<@Nullable Component> content;

  public CustomFormImpl(String title, @Nullable FormImage icon, List<@Nullable Component> content) {
    super(title);
    this.icon = icon;
    this.content = Collections.unmodifiableList(content);
  }

  @Override
  public @Nullable FormImage icon() {
    return icon;
  }

  @Override
  public List<@Nullable Component> content() {
    return content;
  }

  public static final class Builder
      extends FormImpl.Builder<CustomForm.Builder, CustomForm, CustomFormResponse>
      implements CustomForm.Builder {

    private final List<@Nullable Component> components = new ArrayList<>();
    private @Nullable FormImage icon;

    @Override
    public Builder icon(FormImage image) {
      icon = Objects.requireNonNull(image, "image");
      return this;
    }

    @Override
    public Builder icon(FormImage.Type type, String data) {
      icon = FormImage.of(type, data);
      return this;
    }

    @Override
    public Builder iconPath(String path) {
      return icon(FormImage.Type.PATH, path);
    }

    @Override
    public Builder iconUrl(String url) {
      return icon(FormImage.Type.URL, url);
    }

    @Override
    public Builder component(Component component) {
      components.add(Objects.requireNonNull(component, "component"));
      return this;
    }

    @Override
    public Builder optionalComponent(Component component, boolean shouldAdd) {
      if (shouldAdd) {
        return component(component);
      }
      return addNullComponent();
    }

    @Override
    public Builder dropdown(DropdownComponent.Builder dropdownBuilder) {
      Objects.requireNonNull(dropdownBuilder, "dropdownBuilder");
      return component(dropdownBuilder.translateAndBuild(this::translate));
    }

    @Override
    public Builder dropdown(String text, List<String> options, @NonNegative int defaultOption) {
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
    public Builder dropdown(String text, int defaultOption, String... options) {
      return dropdown(text, Arrays.asList(options), defaultOption);
    }

    @Override
    public Builder dropdown(String text, List<String> options) {
      return dropdown(text, options, 0);
    }

    @Override
    public Builder dropdown(String text, String... options) {
      return dropdown(text, Arrays.asList(options), 0);
    }

    @Override
    public Builder optionalDropdown(
        String text, List<String> options, @NonNegative int defaultOption, boolean shouldAdd) {
      if (shouldAdd) {
        return dropdown(text, options, defaultOption);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalDropdown(
        boolean shouldAdd, String text, @NonNegative int defaultOption, String... options) {
      return optionalDropdown(text, Arrays.asList(options), defaultOption, shouldAdd);
    }

    @Override
    public Builder optionalDropdown(String text, List<String> options, boolean shouldAdd) {
      if (shouldAdd) {
        return dropdown(text, options);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalDropdown(boolean shouldAdd, String text, String... options) {
      return optionalDropdown(text, Arrays.asList(options), shouldAdd);
    }

    @Override
    public Builder input(String text, String placeholder, String defaultText) {
      return component(
          InputComponent.of(translate(text), translate(placeholder), translate(defaultText)));
    }

    @Override
    public Builder input(String text, String placeholder) {
      return component(InputComponent.of(translate(text), translate(placeholder)));
    }

    @Override
    public Builder input(String text) {
      return component(InputComponent.of(translate(text)));
    }

    @Override
    public Builder optionalInput(
        String text, String placeholder, String defaultText, boolean shouldAdd) {
      if (shouldAdd) {
        return input(text, placeholder, defaultText);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalInput(String text, String placeholder, boolean shouldAdd) {
      if (shouldAdd) {
        return input(text, placeholder);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalInput(String text, boolean shouldAdd) {
      if (shouldAdd) {
        return input(text);
      }
      return addNullComponent();
    }

    @Override
    public Builder label(String text) {
      return component(LabelComponent.of(translate(text)));
    }

    @Override
    public Builder optionalLabel(String text, boolean shouldAdd) {
      if (shouldAdd) {
        return label(text);
      }
      return addNullComponent();
    }

    @Override
    public Builder slider(
        String text, float min, float max, @Positive float step, float defaultValue) {
      return component(SliderComponent.of(text, min, max, step, defaultValue));
    }

    @Override
    public Builder slider(String text, float min, float max, @Positive float step) {
      return component(SliderComponent.of(text, min, max, step));
    }

    @Override
    public Builder slider(String text, float min, float max) {
      return slider(text, min, max, 1);
    }

    @Override
    public Builder optionalSlider(
        String text,
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
        String text, float min, float max, @Positive float step, boolean shouldAdd) {
      if (shouldAdd) {
        return slider(text, min, max, step);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalSlider(String text, float min, float max, boolean shouldAdd) {
      if (shouldAdd) {
        return slider(text, min, max);
      }
      return addNullComponent();
    }

    @Override
    public Builder stepSlider(StepSliderComponent.Builder stepSliderBuilder) {
      Objects.requireNonNull(stepSliderBuilder, "stepSliderBuilder");
      return component(stepSliderBuilder.translateAndBuild(this::translate));
    }

    @Override
    public Builder stepSlider(String text, List<String> steps, @NonNegative int defaultStep) {
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
    public Builder stepSlider(String text, int defaultStep, String... steps) {
      return stepSlider(text, Arrays.asList(steps), defaultStep);
    }

    @Override
    public Builder stepSlider(String text, List<String> steps) {
      return stepSlider(text, steps, 0);
    }

    @Override
    public Builder stepSlider(String text, String... steps) {
      return stepSlider(text, Arrays.asList(steps));
    }

    @Override
    public Builder optionalStepSlider(
        String text, List<String> steps, @NonNegative int defaultStep, boolean shouldAdd) {
      if (shouldAdd) {
        return stepSlider(text, steps, defaultStep);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalStepSlider(
        boolean shouldAdd, String text, @NonNegative int defaultStep, String... steps) {
      return optionalStepSlider(text, Arrays.asList(steps), defaultStep, shouldAdd);
    }

    @Override
    public Builder optionalStepSlider(String text, List<String> steps, boolean shouldAdd) {
      if (shouldAdd) {
        return stepSlider(text, steps);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalStepSlider(boolean shouldAdd, String text, String... steps) {
      return optionalStepSlider(text, Arrays.asList(steps), shouldAdd);
    }

    @Override
    public Builder toggle(String text, boolean defaultValue) {
      return component(ToggleComponent.of(translate(text), defaultValue));
    }

    @Override
    public Builder toggle(String text) {
      return component(ToggleComponent.of(translate(text)));
    }

    @Override
    public Builder optionalToggle(String text, boolean defaultValue, boolean shouldAdd) {
      if (shouldAdd) {
        return toggle(text, defaultValue);
      }
      return addNullComponent();
    }

    @Override
    public Builder optionalToggle(String text, boolean shouldAdd) {
      if (shouldAdd) {
        return toggle(text);
      }
      return addNullComponent();
    }

    @Override
    public CustomForm build() {
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
