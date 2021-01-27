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

package org.geysermc.cumulus.impl;

import com.google.common.base.Preconditions;
import com.google.gson.annotations.JsonAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.checkerframework.checker.index.qual.Positive;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.CustomForm;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.DropdownComponent;
import org.geysermc.cumulus.component.InputComponent;
import org.geysermc.cumulus.component.LabelComponent;
import org.geysermc.cumulus.component.SliderComponent;
import org.geysermc.cumulus.component.StepSliderComponent;
import org.geysermc.cumulus.component.ToggleComponent;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.impl.CustomFormResponseImpl;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.cumulus.util.FormType;
import org.geysermc.cumulus.util.impl.FormAdaptor;
import org.geysermc.cumulus.util.impl.FormImageImpl;
import org.geysermc.cumulus.util.impl.FormImpl;

@Getter
@JsonAdapter(FormAdaptor.class)
public final class CustomFormImpl extends FormImpl implements CustomForm {
  private final String title;
  private final FormImage icon;
  private final List<Component> content;

  public CustomFormImpl(
      @NonNull String title,
      @Nullable FormImage icon,
      @NonNull List<Component> content) {
    super(FormType.CUSTOM_FORM);

    this.title = Objects.requireNonNull(title, "title");
    this.icon = icon;
    this.content = Collections.unmodifiableList(content);
  }

  @NonNull
  public CustomFormResponse parseResponse(@Nullable String data) {
    if (isClosed(data)) {
      return CustomFormResponseImpl.closed();
    }
    return CustomFormResponseImpl.of(this, data);
  }

  public static final class Builder extends FormImpl.Builder<CustomForm.Builder, CustomForm>
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

    @NonNull
    public Builder label(@NonNull String text) {
      return component(LabelComponent.of(translate(text)));
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
    public CustomFormImpl build() {
      CustomFormImpl form = new CustomFormImpl(title, icon, components);
      if (biResponseHandler != null) {
        form.setResponseHandler(response -> biResponseHandler.accept(form, response));
        return form;
      }

      form.setResponseHandler(responseHandler);
      return form;
    }
  }
}
