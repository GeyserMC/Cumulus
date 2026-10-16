/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form;

import java.util.List;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.index.qual.Positive;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.DropdownComponent;
import org.geysermc.cumulus.component.StepSliderComponent;
import org.geysermc.cumulus.form.impl.custom.CustomFormImpl;
import org.geysermc.cumulus.form.util.FormBuilder;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.util.FormImage;
import org.jspecify.annotations.Nullable;

/**
 * Represents a CustomForm which can be shown to the client. A CustomForm is the most customisable
 * form type, you can add all component types except for buttons. For more information and for code
 * examples look at <a href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
 *
 * @since 1.1
 */
public interface CustomForm extends Form {
  /** Returns a new CustomForm builder. A more friendly way of creating a Form. */
  static Builder builder() {
    return new CustomFormImpl.Builder();
  }

  /**
   * Create a CustomForm with predefined information.
   *
   * @param title the title of the form
   * @param icon the icon of the form (optional)
   * @param content the list of components in this form
   * @return the created CustomForm instance
   */
  static CustomForm of(String title, @Nullable FormImage icon, List<Component> content) {
    return new CustomFormImpl(title, icon, content);
  }

  /** Returns the optional icon of the form. The icon can only be seen in the servers settings. */
  @Nullable FormImage icon();

  /**
   * Returns all the components of the form. This includes optional components, which will be null
   * when they are not present.
   */
  List<@Nullable Component> content();

  /**
   * An easy way to create a CustomForm. For more information and code examples look at <a
   * href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
   */
  interface Builder extends FormBuilder<Builder, CustomForm, CustomFormResponse> {
    @This Builder icon(FormImage image);

    @This Builder icon(FormImage.Type type, String data);

    @This Builder iconPath(String path);

    @This Builder iconUrl(String url);

    @This Builder component(Component component);

    /**
     * @param component
     * @param shouldAdd
     * @return
     */
    @This Builder optionalComponent(Component component, boolean shouldAdd);

    @This Builder dropdown(DropdownComponent.Builder dropdownBuilder);

    @This Builder dropdown(String text, List<String> options, @NonNegative int defaultOption);

    @This Builder dropdown(String text, @NonNegative int defaultOption, String... options);

    @This Builder dropdown(String text, List<String> options);

    @This Builder dropdown(String text, String... options);

    /**
     * @param text
     * @param options
     * @param defaultOption
     * @param shouldAdd
     * @return
     */
    @This Builder optionalDropdown(
        String text, List<String> options, @NonNegative int defaultOption, boolean shouldAdd);

    /**
     * @param shouldAdd
     * @param text
     * @param defaultOption
     * @param options
     * @return
     */
    @This Builder optionalDropdown(
        boolean shouldAdd, String text, @NonNegative int defaultOption, String... options);

    /**
     * @param text
     * @param options
     * @param shouldAdd
     * @return
     */
    @This Builder optionalDropdown(String text, List<String> options, boolean shouldAdd);

    /**
     * @param shouldAdd
     * @param text
     * @param options
     * @return
     */
    @This Builder optionalDropdown(boolean shouldAdd, String text, String... options);

    @This Builder input(String text, String placeholder, String defaultText);

    @This Builder input(String text, String placeholder);

    @This Builder input(String text);

    /**
     * @param text
     * @param placeholder
     * @param defaultText
     * @param shouldAdd
     * @return
     */
    @This Builder optionalInput(String text, String placeholder, String defaultText, boolean shouldAdd);

    /**
     * @param text
     * @param placeholder
     * @param shouldAdd
     * @return
     */
    @This Builder optionalInput(String text, String placeholder, boolean shouldAdd);

    /**
     * @param text
     * @param shouldAdd
     * @return
     */
    @This Builder optionalInput(String text, boolean shouldAdd);

    @This Builder label(String text);

    /**
     * @param text
     * @param shouldAdd
     * @return
     */
    @This Builder optionalLabel(String text, boolean shouldAdd);

    @This Builder slider(String text, float min, float max, @Positive float step, float defaultValue);

    @This Builder slider(String text, float min, float max, @Positive float step);

    @This Builder slider(String text, float min, float max);

    /**
     * @param text
     * @param min
     * @param max
     * @param step
     * @param defaultValue
     * @param shouldAdd
     * @return
     */
    @This Builder optionalSlider(
        String text,
        float min,
        float max,
        @Positive float step,
        float defaultValue,
        boolean shouldAdd);

    /**
     * @param text
     * @param min
     * @param max
     * @param step
     * @param shouldAdd
     * @return
     */
    @This Builder optionalSlider(
        String text, float min, float max, @Positive float step, boolean shouldAdd);

    /**
     * @param text
     * @param min
     * @param max
     * @param shouldAdd
     * @return
     */
    @This Builder optionalSlider(String text, float min, float max, boolean shouldAdd);

    @This Builder stepSlider(StepSliderComponent.Builder stepSliderBuilder);

    @This Builder stepSlider(String text, List<String> steps, @NonNegative int defaultStep);

    @This Builder stepSlider(String text, @NonNegative int defaultStep, String... steps);

    @This Builder stepSlider(String text, List<String> steps);

    @This Builder stepSlider(String text, String... steps);

    /**
     * @param text
     * @param steps
     * @param defaultStep
     * @param shouldAdd
     * @return
     */
    @This Builder optionalStepSlider(
        String text, List<String> steps, @NonNegative int defaultStep, boolean shouldAdd);

    /**
     * @param shouldAdd
     * @param text
     * @param defaultStep
     * @param steps
     * @return
     */
    @This Builder optionalStepSlider(
        boolean shouldAdd, String text, @NonNegative int defaultStep, String... steps);

    /**
     * @param text
     * @param steps
     * @param shouldAdd
     * @return
     */
    @This Builder optionalStepSlider(String text, List<String> steps, boolean shouldAdd);

    /**
     * @param shouldAdd
     * @param text
     * @param steps
     * @return
     */
    @This Builder optionalStepSlider(boolean shouldAdd, String text, String... steps);

    @This Builder toggle(String text, boolean defaultValue);

    @This Builder toggle(String text);

    /**
     * @param text
     * @param defaultValue
     * @param shouldAdd
     * @return
     */
    @This Builder optionalToggle(String text, boolean defaultValue, boolean shouldAdd);

    /**
     * @param text
     * @param shouldAdd
     * @return
     */
    @This Builder optionalToggle(String text, boolean shouldAdd);
  }
}
