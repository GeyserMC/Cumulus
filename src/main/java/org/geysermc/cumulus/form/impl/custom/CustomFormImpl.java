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

package org.geysermc.cumulus.form.impl.custom;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
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
import org.geysermc.cumulus.util.impl.FormImageImpl;

public final class CustomFormImpl extends FormImpl<CustomFormResponse>
    implements CustomForm {

  private final FormImage icon;
  private final List<Component> content;

  public CustomFormImpl(
      @NonNull String title,
      @Nullable FormImage icon,
      @NonNull List<Component> content) {
    super(title);
    this.icon = icon;
    this.content = Collections.unmodifiableList(content);
  }

  @Override
  public @Nullable FormImage icon() {
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

    @NonNull
    public Builder icon(FormImage.@NonNull Type type, @NonNull String data) {
      icon = new FormImageImpl(type, data);
      return this;
    }

    @NonNull
    public Builder iconPath(@NonNull String path) {
      return icon(FormImage.Type.PATH, path);
    }

    @NonNull
    public Builder iconUrl(@NonNull String url) {
      return icon(FormImage.Type.URL, url);
    }

    @NonNull
    public Builder component(@NonNull Component component) {
      components.add(Objects.requireNonNull(component, "component"));
      return this;
    }

    @Override
    @NonNull
    public Builder optionalComponent(@NonNull Component component, boolean shouldAdd) {
      return this;
    }

    @NonNull
    public Builder dropdown(DropdownComponent.@NonNull Builder dropdownBuilder) {
      Objects.requireNonNull(dropdownBuilder, "dropdownBuilder");
      return component(dropdownBuilder.translateAndBuild(this::translate));
    }

    @NonNull
    public Builder dropdown(
        @NonNull String text,
        int defaultOption,
        @NonNull String... options) {
      Objects.requireNonNull(text, "text");
      Preconditions.checkArgument(defaultOption >= 0, "defaultOption");

      List<String> optionsList = new ArrayList<>();
      for (String option : options) {
        optionsList.add(translate(option));
      }
      return component(DropdownComponent.of(translate(text), optionsList, defaultOption));
    }

    @NonNull
    public Builder dropdown(@NonNull String text, @NonNull String... options) {
      return dropdown(text, 0, options);
    }

    @Override
    @NonNull
    public Builder optionalDropdown(
        boolean shouldAdd,
        @NonNull String text,
        @NonNegative int defaultOption,
        @NonNull String... options) {
      return null;
    }

    @Override
    @NonNull
    public Builder optionalDropdown(
        boolean shouldAdd,
        @NonNull String text,
        @NonNull String... options) {
      return null;
    }

    @NonNull
    public Builder input(
        @NonNull String text,
        @NonNull String placeholder,
        @NonNull String defaultText) {
      return component(InputComponent.of(
          translate(text), translate(placeholder), translate(defaultText)
      ));
    }

    @NonNull
    public Builder input(@NonNull String text, @NonNull String placeholder) {
      return component(InputComponent.of(translate(text), translate(placeholder)));
    }

    @NonNull
    public Builder input(@NonNull String text) {
      return component(InputComponent.of(translate(text)));
    }

    @Override
    @NonNull
    public Builder optionalInput(
        @NonNull String text,
        @NonNull String placeholder,
        @NonNull String defaultText,
        boolean shouldAdd) {
      return null;
    }

    @Override
    @NonNull
    public Builder optionalInput(
        @NonNull String text,
        @NonNull String placeholder,
        boolean shouldAdd) {
      return null;
    }

    @Override
    @NonNull
    public Builder optionalInput(@NonNull String text, boolean shouldAdd) {
      return null;
    }

    @NonNull
    public Builder label(@NonNull String text) {
      return component(LabelComponent.of(translate(text)));
    }

    @Override
    @NonNull
    public Builder optionalLabel(@NonNull String text, boolean shouldAdd) {
      return null;
    }

    @NonNull
    public Builder slider(
        @NonNull String text,
        float min,
        float max,
        @Positive int step,
        float defaultValue) {
      return component(SliderComponent.of(text, min, max, step, defaultValue));
    }

    @NonNull
    public Builder slider(@NonNull String text, float min, float max, @Positive int step) {
      return slider(text, min, max, step, 0);
    }

    @NonNull
    public Builder slider(
        @NonNull String text,
        float min,
        float max,
        @Positive float defaultValue) {
      return slider(text, min, max, 1, defaultValue);
    }

    @NonNull
    public Builder slider(@NonNull String text, float min, float max) {
      return slider(text, min, max, 1, 0);
    }

    @Override
    @NonNull
    public Builder optionalSlider(
        @NonNull String text,
        float min,
        float max,
        @Positive int step,
        float defaultValue,
        boolean shouldAdd) {
      return null;
    }

    @Override
    @NonNull
    public Builder optionalSlider(
        @NonNull String text,
        float min,
        float max,
        @Positive int step,
        boolean shouldAdd) {
      return null;
    }

    @Override
    @NonNull
    public Builder optionalSlider(
        @NonNull String text,
        float min,
        float max,
        float defaultValue,
        boolean shouldAdd) {
      return null;
    }

    @Override
    @NonNull
    public Builder optionalSlider(
        @NonNull String text,
        float min,
        float max,
        boolean shouldAdd) {
      return null;
    }

    @NonNull
    public Builder stepSlider(StepSliderComponent.@NonNull Builder stepSliderBuilder) {
      Objects.requireNonNull(stepSliderBuilder, "stepSliderBuilder");
      return component(stepSliderBuilder.translateAndBuild(this::translate));
    }

    @NonNull
    public Builder stepSlider(@NonNull String text, int defaultStep, String... steps) {
      Objects.requireNonNull(text, "text");
      Preconditions.checkArgument(defaultStep >= 0, "defaultStep");

      List<String> stepsList = new ArrayList<>();
      for (String option : steps) {
        stepsList.add(translate(option));
      }
      return component(StepSliderComponent.of(translate(text), stepsList, defaultStep));
    }

    @NonNull
    public Builder stepSlider(@NonNull String text, String... steps) {
      return stepSlider(text, 0, steps);
    }

    @Override
    @NonNull
    public Builder optionalStepSlider(
        boolean shouldAdd,
        @NonNull String text,
        @NonNegative int defaultStep,
        @NonNull String... steps) {
      return null;
    }

    @Override
    @NonNull
    public Builder optionalStepSlider(
        boolean shouldAdd,
        @NonNull String text,
        @NonNull String... steps) {
      return null;
    }

    @NonNull
    public Builder toggle(@NonNull String text, boolean defaultValue) {
      return component(ToggleComponent.of(translate(text), defaultValue));
    }

    @NonNull
    public Builder toggle(@NonNull String text) {
      return component(ToggleComponent.of(translate(text)));
    }

    @Override
    @NonNull
    public Builder optionalToggle(@NonNull String text, boolean defaultValue, boolean shouldAdd) {
      return null;
    }

    @Override
    @NonNull
    public Builder optionalToggle(@NonNull String text, boolean shouldAdd) {
      return null;
    }

    @Override
    @NonNull
    public CustomForm build() {
      CustomFormImpl form = new CustomFormImpl(title, icon, components);
      setResponseHandler(form, form);
      return form;
    }
  }
}
