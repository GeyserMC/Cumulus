/*
 * Copyright (c) 2020-2022 GeyserMC
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
package org.geysermc.cumulus.util.glue;

import java.util.List;
import org.geysermc.cumulus.CustomForm;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.DropdownComponent;
import org.geysermc.cumulus.component.StepSliderComponent;
import org.geysermc.cumulus.form.impl.custom.CustomFormImpl;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.impl.CustomFormResponseImpl;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.response.result.ResultType;
import org.geysermc.cumulus.response.result.ValidFormResponseResult;
import org.geysermc.cumulus.util.FormImage;

public class CustomFormGlue extends FormGlue<org.geysermc.cumulus.form.CustomForm>
    implements CustomForm {

  private CustomFormGlue() {
    super(FormType.CUSTOM_FORM);
  }

  public String getTitle() {
    return form.title();
  }

  public FormImage getIcon() {
    return form.icon();
  }

  public List<Component> getContent() {
    return form.content();
  }

  @Override
  public CustomFormResponse parseResponse(String response) {
    FormResponseResult<CustomFormResponse> result = deserializeResponse(response);
    if (result.isValid()) {
      return ((ValidFormResponseResult<CustomFormResponse>) result).response();
    }
    return new CustomFormResponseImpl(result.isInvalid() ? ResultType.INVALID : ResultType.CLOSED);
  }

  public static class Builder
      extends FormBuilderGlue<
          CustomForm.Builder,
          CustomForm,
          org.geysermc.cumulus.form.CustomForm,
          org.geysermc.cumulus.form.CustomForm.Builder>
      implements CustomForm.Builder {

    public Builder() {
      super(org.geysermc.cumulus.form.CustomForm.builder());
    }

    public Builder icon(FormImage.Type type, String data) {
      builder.icon(type, data);
      return this;
    }

    public Builder iconPath(String path) {
      builder.iconPath(path);
      return this;
    }

    public Builder iconUrl(String url) {
      builder.iconUrl(url);
      return this;
    }

    public Builder component(Component component) {
      builder.component(component);
      return this;
    }

    public Builder optionalComponent(Component component, boolean shouldAdd) {
      builder.optionalComponent(component, shouldAdd);
      return this;
    }

    public Builder dropdown(DropdownComponent.Builder dropdownBuilder) {
      builder.dropdown(dropdownBuilder);
      return this;
    }

    public Builder dropdown(String text, int defaultOption, String... options) {
      builder.dropdown(text, defaultOption, options);
      return this;
    }

    public Builder dropdown(String text, String... options) {
      builder.dropdown(text, options);
      return this;
    }

    public Builder optionalDropdown(
        boolean shouldAdd, String text, int defaultOption, String... options) {
      builder.optionalDropdown(shouldAdd, text, defaultOption, options);
      return this;
    }

    public Builder optionalDropdown(boolean shouldAdd, String text, String... options) {
      builder.optionalDropdown(shouldAdd, text, options);
      return this;
    }

    public Builder input(String text, String placeholder, String defaultText) {
      builder.input(text, placeholder, defaultText);
      return this;
    }

    public Builder input(String text, String placeholder) {
      builder.input(text, placeholder);
      return this;
    }

    public Builder input(String text) {
      builder.input(text);
      return this;
    }

    public Builder optionalInput(
        String text, String placeholder, String defaultText, boolean shouldAdd) {
      builder.optionalInput(text, placeholder, defaultText, shouldAdd);
      return this;
    }

    public Builder optionalInput(String text, String placeholder, boolean shouldAdd) {
      builder.optionalInput(text, placeholder, shouldAdd);
      return this;
    }

    public Builder optionalInput(String text, boolean shouldAdd) {
      builder.optionalInput(text, shouldAdd);
      return this;
    }

    public Builder label(String text) {
      builder.label(text);
      return this;
    }

    public Builder optionalLabel(String text, boolean shouldAdd) {
      builder.optionalLabel(text, shouldAdd);
      return this;
    }

    public Builder slider(String text, float min, float max, int step, float defaultValue) {
      builder.slider(text, min, max, step, defaultValue);
      return this;
    }

    public Builder slider(String text, float min, float max, int step) {
      builder.slider(text, min, max, step);
      return this;
    }

    public Builder slider(String text, float min, float max, float defaultValue) {
      builder.slider(text, min, max, defaultValue);
      return this;
    }

    public Builder slider(String text, float min, float max) {
      builder.slider(text, min, max);
      return this;
    }

    public Builder optionalSlider(
        String text, float min, float max, int step, float defaultValue, boolean shouldAdd) {
      builder.optionalSlider(text, min, max, step, defaultValue, shouldAdd);
      return this;
    }

    public Builder optionalSlider(String text, float min, float max, int step, boolean shouldAdd) {
      builder.optionalSlider(text, min, max, shouldAdd);
      return this;
    }

    public Builder optionalSlider(
        String text, float min, float max, float defaultValue, boolean shouldAdd) {
      builder.optionalSlider(text, min, max, defaultValue, shouldAdd);
      return this;
    }

    public Builder optionalSlider(String text, float min, float max, boolean shouldAdd) {
      builder.optionalSlider(text, min, max, shouldAdd);
      return this;
    }

    public Builder stepSlider(StepSliderComponent.Builder stepSliderBuilder) {
      builder.stepSlider(stepSliderBuilder);
      return this;
    }

    public Builder stepSlider(String text, int defaultStep, String... steps) {
      builder.stepSlider(text, defaultStep, steps);
      return this;
    }

    public Builder stepSlider(String text, String... steps) {
      builder.stepSlider(text, steps);
      return this;
    }

    public Builder optionalStepSlider(
        boolean shouldAdd, String text, int defaultStep, String... steps) {
      builder.optionalStepSlider(shouldAdd, text, defaultStep, steps);
      return this;
    }

    public Builder optionalStepSlider(boolean shouldAdd, String text, String... steps) {
      builder.optionalStepSlider(shouldAdd, text, steps);
      return this;
    }

    public Builder toggle(String text, boolean defaultValue) {
      builder.toggle(text, defaultValue);
      return this;
    }

    public Builder toggle(String text) {
      builder.toggle(text);
      return this;
    }

    public Builder optionalToggle(String text, boolean defaultValue, boolean shouldAdd) {
      builder.optionalToggle(text, defaultValue, shouldAdd);
      return this;
    }

    public Builder optionalToggle(String text, boolean shouldAdd) {
      builder.optionalToggle(text, shouldAdd);
      return this;
    }

    @Override
    public CustomForm build() {
      CustomFormGlue oldForm = new CustomFormGlue();
      oldForm.responseHandler =
          (response) -> {
            if (biResponseHandler != null) {
              biResponseHandler.accept(oldForm, response);
            }
            if (responseHandler != null) {
              responseHandler.accept(response);
            }
          };

      CustomFormImpl newForm = (CustomFormImpl) builder.build();
      newForm.rawResponseConsumer(oldForm.responseHandler);
      oldForm.form = newForm;

      return oldForm;
    }
  }
}
