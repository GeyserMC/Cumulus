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

package org.geysermc.cumulus.form;

import java.util.List;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.index.qual.Positive;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.DropdownComponent;
import org.geysermc.cumulus.component.StepSliderComponent;
import org.geysermc.cumulus.form.impl.custom.CustomFormImpl;
import org.geysermc.cumulus.form.util.FormBuilder;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.util.FormImage;

/**
 * Represents a CustomForm which can be shown to the client. A CustomForm is the most customisable
 * form type, you can add all component types except for buttons. For more information and for code
 * examples look at <a href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
 *
 * @since 1.1
 */
public interface CustomForm extends Form {
  /**
   * Returns a new CustomForm builder. A more friendly way of creating a Form.
   */
  @NonNull
  static Builder builder() {
    return new CustomFormImpl.Builder();
  }

  /**
   * Create a CustomForm with predefined information.
   *
   * @param title   the title of the form
   * @param icon    the icon of the form (optional)
   * @param content the list of components in this form
   * @return the created CustomForm instance
   */
  @NonNull
  static CustomForm of(
      @NonNull String title,
      @Nullable FormImage icon,
      @NonNull List<Component> content) {
    return new CustomFormImpl(title, icon, content);
  }

  /**
   * Returns the optional icon of the form. The icon can only be seen in the servers settings.
   */
  @Nullable
  FormImage icon();

  /**
   * Returns all the components of the form. This includes optional components, which will be null
   * when they are not present.
   */
  @NonNull
  List<@Nullable Component> content();

  /**
   * An easy way to create a CustomForm. For more information and code examples look at <a
   * href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
   */
  interface Builder extends FormBuilder<Builder, CustomForm, CustomFormResponse> {
    @This
    Builder icon(FormImage.@NonNull Type type, @NonNull String data);

    @This
    Builder iconPath(@NonNull String path);

    @This
    Builder iconUrl(@NonNull String url);

    @This
    Builder component(@NonNull Component component);

    /**
     * @param component
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalComponent(@NonNull Component component, boolean shouldAdd);

    @This
    Builder dropdown(DropdownComponent.@NonNull Builder dropdownBuilder);

    @This
    Builder dropdown(
        @NonNull String text,
        @NonNegative int defaultOption,
        @NonNull List<String> options
    );

    @This
    Builder dropdown(
        @NonNull String text,
        @NonNegative int defaultOption,
        @NonNull String... options
    );

    @This
    Builder dropdown(@NonNull String text, @NonNull List<String> options);

    @This
    Builder dropdown(@NonNull String text, @NonNull String... options);

    /**
     * @param text
     * @param defaultOption
     * @param options
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalDropdown(
        @NonNull String text,
        @NonNegative int defaultOption,
        @NonNull List<String> options,
        boolean shouldAdd
    );

    /**
     * @param shouldAdd
     * @param text
     * @param defaultOption
     * @param options
     * @return
     */
    @This
    Builder optionalDropdown(
        boolean shouldAdd,
        @NonNull String text,
        @NonNegative int defaultOption,
        @NonNull String... options
    );

    /**
     * @param text
     * @param options
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalDropdown(
        @NonNull String text,
        @NonNull List<String> options,
        boolean shouldAdd
    );

    /**
     * @param shouldAdd
     * @param text
     * @param options
     * @return
     */
    @This
    Builder optionalDropdown(boolean shouldAdd, @NonNull String text, @NonNull String... options);

    @This
    Builder input(@NonNull String text, @NonNull String placeholder, @NonNull String defaultText);

    @This
    Builder input(@NonNull String text, @NonNull String placeholder);

    @This
    Builder input(@NonNull String text);

    /**
     * @param text
     * @param placeholder
     * @param defaultText
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalInput(
        @NonNull String text,
        @NonNull String placeholder,
        @NonNull String defaultText,
        boolean shouldAdd
    );

    /**
     * @param text
     * @param placeholder
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalInput(@NonNull String text, @NonNull String placeholder, boolean shouldAdd);

    /**
     * @param text
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalInput(@NonNull String text, boolean shouldAdd);

    @This
    Builder label(@NonNull String text);

    /**
     * @param text
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalLabel(@NonNull String text, boolean shouldAdd);

    @This
    Builder slider(
        @NonNull String text,
        float min,
        float max,
        @Positive float step,
        float defaultValue
    );

    @This
    Builder slider(@NonNull String text, float min, float max, @Positive float step);

    @This
    Builder slider(@NonNull String text, float min, float max);

    /**
     * @param text
     * @param min
     * @param max
     * @param step
     * @param defaultValue
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalSlider(
        @NonNull String text,
        float min,
        float max,
        @Positive float step,
        float defaultValue,
        boolean shouldAdd
    );

    /**
     * @param text
     * @param min
     * @param max
     * @param step
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalSlider(
        @NonNull String text,
        float min,
        float max,
        @Positive float step,
        boolean shouldAdd
    );

    /**
     * @param text
     * @param min
     * @param max
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalSlider(@NonNull String text, float min, float max, boolean shouldAdd);

    @This
    Builder stepSlider(StepSliderComponent.@NonNull Builder stepSliderBuilder);

    @This
    Builder stepSlider(
        @NonNull String text,
        @NonNegative int defaultStep,
        @NonNull List<String> steps
    );

    @This
    Builder stepSlider(
        @NonNull String text,
        @NonNegative int defaultStep,
        @NonNull String... steps
    );

    @This
    Builder stepSlider(@NonNull String text, @NonNull List<String> steps);

    @This
    Builder stepSlider(@NonNull String text, @NonNull String... steps);

    /**
     * @param text
     * @param defaultStep
     * @param steps
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalStepSlider(
        @NonNull String text,
        @NonNegative int defaultStep,
        @NonNull List<String> steps,
        boolean shouldAdd
    );

    /**
     * @param shouldAdd
     * @param text
     * @param defaultStep
     * @param steps
     * @return
     */
    @This
    Builder optionalStepSlider(
        boolean shouldAdd,
        @NonNull String text,
        @NonNegative int defaultStep,
        @NonNull String... steps
    );

    /**
     * @param text
     * @param steps
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalStepSlider(
        @NonNull String text,
        @NonNull List<String> steps,
        boolean shouldAdd
    );

    /**
     * @param shouldAdd
     * @param text
     * @param steps
     * @return
     */
    @This
    Builder optionalStepSlider(
        boolean shouldAdd,
        @NonNull String text,
        @NonNull String... steps
    );

    @This
    Builder toggle(@NonNull String text, boolean defaultValue);

    @This
    Builder toggle(@NonNull String text);

    /**
     * @param text
     * @param defaultValue
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalToggle(@NonNull String text, boolean defaultValue, boolean shouldAdd);

    /**
     * @param text
     * @param shouldAdd
     * @return
     */
    @This
    Builder optionalToggle(@NonNull String text, boolean shouldAdd);
  }
}
