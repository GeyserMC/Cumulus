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
package org.geysermc.cumulus;

import java.util.List;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.DropdownComponent;
import org.geysermc.cumulus.component.StepSliderComponent;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.util.FormBuilder;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.cumulus.util.glue.CustomFormGlue;

/**
 * @deprecated since 1.1 and will be removed in 2.0. This class will be replaced by {@link
 *     org.geysermc.cumulus.form.CustomForm}.
 */
@Deprecated
public interface CustomForm extends Form<org.geysermc.cumulus.form.CustomForm> {

  static Builder builder() {
    return new CustomFormGlue.Builder();
  }

  static CustomForm of(String title, FormImage icon, List<Component> content) {
    Builder builder = CustomForm.builder().title(title);

    if (icon != null) {
      builder.icon(icon.type(), icon.data());
    }

    for (Component component : content) {
      builder.component(component);
    }

    return builder.build();
  }

  String getTitle();

  FormImage getIcon();

  List<Component> getContent();

  @Override
  CustomFormResponse parseResponse(String response);

  interface Builder extends FormBuilder<Builder, CustomForm> {
    Builder icon(FormImage.Type type, String data);

    Builder iconPath(String path);

    Builder iconUrl(String url);

    Builder component(Component component);

    // default methods have to stay default for the JVM (:

    default Builder optionalComponent(Component component, boolean shouldAdd) {
      throw new IllegalStateException();
    }

    Builder dropdown(DropdownComponent.Builder dropdownBuilder);

    Builder dropdown(String text, int defaultOption, String... options);

    Builder dropdown(String text, String... options);

    default Builder optionalDropdown(
        boolean shouldAdd, String text, int defaultOption, String... options) {
      throw new IllegalStateException();
    }

    default Builder optionalDropdown(boolean shouldAdd, String text, String... options) {
      throw new IllegalStateException();
    }

    Builder input(String text, String placeholder, String defaultText);

    Builder input(String text, String placeholder);

    Builder input(String text);

    default Builder optionalInput(
        String text, String placeholder, String defaultText, boolean shouldAdd) {
      throw new IllegalStateException();
    }

    default Builder optionalInput(String text, String placeholder, boolean shouldAdd) {
      throw new IllegalStateException();
    }

    default Builder optionalInput(String text, boolean shouldAdd) {
      throw new IllegalStateException();
    }

    Builder label(String text);

    default Builder optionalLabel(String text, boolean shouldAdd) {
      throw new IllegalStateException();
    }

    Builder slider(String text, float min, float max, int step, float defaultValue);

    Builder slider(String text, float min, float max, int step);

    Builder slider(String text, float min, float max, float defaultValue);

    Builder slider(String text, float min, float max);

    default Builder optionalSlider(
        String text, float min, float max, int step, float defaultValue, boolean shouldAdd) {
      throw new IllegalStateException();
    }

    default Builder optionalSlider(String text, float min, float max, int step, boolean shouldAdd) {
      throw new IllegalStateException();
    }

    default Builder optionalSlider(
        String text, float min, float max, float defaultValue, boolean shouldAdd) {
      throw new IllegalStateException();
    }

    default Builder optionalSlider(String text, float min, float max, boolean shouldAdd) {
      throw new IllegalStateException();
    }

    Builder stepSlider(StepSliderComponent.Builder stepSliderBuilder);

    Builder stepSlider(String text, int defaultStep, String... steps);

    Builder stepSlider(String text, String... steps);

    default Builder optionalStepSlider(
        boolean shouldAdd, String text, int defaultStep, String... steps) {
      throw new IllegalStateException();
    }

    default Builder optionalStepSlider(boolean shouldAdd, String text, String... steps) {
      throw new IllegalStateException();
    }

    Builder toggle(String text, boolean defaultValue);

    Builder toggle(String text);

    default Builder optionalToggle(String text, boolean defaultValue, boolean shouldAdd) {
      throw new IllegalStateException();
    }

    default Builder optionalToggle(String text, boolean shouldAdd) {
      throw new IllegalStateException();
    }
  }
}
