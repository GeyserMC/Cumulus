/*
 * Copyright (c) 2020-2024 GeyserMC
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
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Represents a CustomForm which can be shown to the client. A CustomForm is the most customisable
 * form type, you can add all component types except for buttons. For more information and for code
 * examples look at <a href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
 *
 * @since 1.1
 */
@NullMarked
public interface CustomForm extends Form {
  /**
   * Returns a new CustomForm builder. A more friendly way of creating a Form.
   *
   * @since 1.1
   */
  static Builder builder() {
    return new CustomFormImpl.Builder();
  }

  /**
   * Create a CustomForm with predefined information.
   *
   * @param title the title of the form
   * @param icon the icon of the form
   * @param content the list of components in this form
   * @return the created CustomForm instance
   * @since 1.1
   */
  static CustomForm of(String title, @Nullable FormImage icon, List<Component> content) {
    return new CustomFormImpl(title, icon, content);
  }

  /**
   * Returns the optional icon of the form. The icon can only be seen in the servers settings.
   *
   * @since 1.1
   */
  @Nullable FormImage icon();

  /**
   * Returns all the components of the form. This includes optional components, which will be null
   * when they are not present.
   *
   * @since 1.1
   */
  List<@Nullable Component> content();

  /**
   * An easy way to create a CustomForm. For more information and code examples look at <a
   * href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
   *
   * @since 1.1
   */
  interface Builder extends FormBuilder<Builder, CustomForm, CustomFormResponse> {
    /**
     * Set the icon of the form.
     *
     * @param image the icon to set
     * @see #icon()
     * @since 1.1.1
     */
    @This Builder icon(FormImage image);

    /**
     * Set the icon of the form.
     *
     * @param type the type of the icon to set
     * @param data the data of the icon to set
     * @see #icon()
     * @since 1.1
     */
    @This Builder icon(FormImage.Type type, String data);

    /**
     * Set the icon of the form.
     *
     * @param path the path of the icon to set
     * @see #icon()
     * @see FormImage.Type#PATH
     * @since 1.1
     */
    @This Builder iconPath(String path);

    /**
     * Set the icon of the form.
     *
     * @param url the url of the icon to set
     * @see #icon()
     * @see FormImage.Type#URL
     * @since 1.1
     */
    @This Builder iconUrl(String url);

    /**
     * Manually add a component. This is not the recommended approach but could be useful when you
     * directly create component instances.
     *
     * @param component the component to add
     * @since 1.1
     */
    @This Builder component(Component component);

    /**
     * Manually add a component. This is not the recommended approach but could be useful when you
     * directly create component instances. The component is only added when shouldAdd is true.
     *
     * @param component the component to add
     * @param shouldAdd whether the component should actually be added
     * @since 1.1
     */
    @This Builder optionalComponent(Component component, boolean shouldAdd);

    /**
     * Add a dropdown component using the provided dropdown builder.
     *
     * @param dropdownBuilder the builder to add
     * @see DropdownComponent
     * @since 1.1
     */
    @This Builder dropdown(DropdownComponent.Builder dropdownBuilder);

    /**
     * Add a dropdown component using the provided data.
     *
     * @param text the text that is shown in the component
     * @param options the options to choose from
     * @param defaultOption the index of the default option
     * @see DropdownComponent
     * @since 1.1
     */
    @This Builder dropdown(String text, List<String> options, @NonNegative int defaultOption);

    /**
     * Add a dropdown component using the provided data.
     *
     * @param text the text that is shown in the component
     * @param defaultOption the index of the default option
     * @param options the options to choose from
     * @see DropdownComponent
     * @since 1.1
     */
    @This Builder dropdown(String text, @NonNegative int defaultOption, String... options);

    /**
     * Add a dropdown component using the provided data, with the default option being set to the
     * first option.
     *
     * @param text the text that is shown in the component
     * @param options the options to choose from
     * @see DropdownComponent
     * @since 1.1
     */
    @This Builder dropdown(String text, List<String> options);

    /**
     * Add a dropdown component using the provided data, with the default option being set to the
     * first option.
     *
     * @param text the text that is shown in the component
     * @param options the options to choose from
     * @see DropdownComponent
     * @since 1.1
     */
    @This Builder dropdown(String text, String... options);

    /**
     * Optionally add a dropdown component using the provided data.
     *
     * @param text the text that is shown in the component
     * @param options the options to choose from
     * @param defaultOption the index of the default option
     * @param shouldAdd whether the component should be added
     * @see DropdownComponent
     * @since 1.1
     */
    @This Builder optionalDropdown(
        String text, List<String> options, @NonNegative int defaultOption, boolean shouldAdd);

    /**
     * Optionally add a dropdown component using the provided data
     *
     * @param shouldAdd whether the component should be added
     * @param text the text that is shown in the component
     * @param defaultOption the index of the default option
     * @param options the options to choose from
     * @see DropdownComponent
     * @since 1.1
     */
    @This Builder optionalDropdown(
        boolean shouldAdd, String text, @NonNegative int defaultOption, String... options);

    /**
     * Optionally add a dropdown component using the provided data, with the default option being
     * the first option.
     *
     * @param text the text that is shown in the component
     * @param options the options to choose from
     * @param shouldAdd whether the component should be added
     * @see DropdownComponent
     * @since 1.1
     */
    @This Builder optionalDropdown(String text, List<String> options, boolean shouldAdd);

    /**
     * Optionally add a dropdown component using the provided data, with the default option being
     * the first option.
     *
     * @param shouldAdd whether the component should be added
     * @param text the text that is shown in the component
     * @param options the options to choose from
     * @see DropdownComponent
     * @since 1.1
     */
    @This Builder optionalDropdown(boolean shouldAdd, String text, String... options);

    /**
     * Add an input component using the provided data.
     *
     * @param text the text that is shown in the component
     * @param placeholder the text to be shown when no text is entered
     * @param defaultText the text that is entered by default
     * @see org.geysermc.cumulus.component.InputComponent
     * @since 1.1
     */
    @This Builder input(String text, String placeholder, String defaultText);

    /**
     * Add an input component using the provided data, with defaultText being empty.
     *
     * @param text the text that is shown in the component
     * @param placeholder the text to be shown when no text is entered
     * @see org.geysermc.cumulus.component.InputComponent
     * @since 1.1
     */
    @This Builder input(String text, String placeholder);

    /**
     * Add an input component using the provided data, with both placeholder and defaultText being
     * empty.
     *
     * @param text the text that is shown in the component
     * @see org.geysermc.cumulus.component.InputComponent
     * @since 1.1
     */
    @This Builder input(String text);

    /**
     * Optionally add an input component using the provided data.
     *
     * @param text the text that is shown in the component
     * @param placeholder the text to be shown when no text is entered
     * @param defaultText the text that is entered by default
     * @param shouldAdd whether the component should be added
     * @see org.geysermc.cumulus.component.InputComponent
     * @since 1.1
     */
    @This Builder optionalInput(String text, String placeholder, String defaultText, boolean shouldAdd);

    /**
     * Optionally add an input component using the provided data, with defaultText being empty.
     *
     * @param text the text that is shown in the component
     * @param placeholder the text to be shown when no text is entered
     * @param shouldAdd whether the component should be added
     * @see org.geysermc.cumulus.component.InputComponent
     * @since 1.1
     */
    @This Builder optionalInput(String text, String placeholder, boolean shouldAdd);

    /**
     * Optionally add an input component using the provided data, with both placeholder and
     * defaultText being empty.
     *
     * @param text the text that is shown in the component
     * @param shouldAdd whether the component should be added
     * @see org.geysermc.cumulus.component.InputComponent
     * @since 1.1
     */
    @This Builder optionalInput(String text, boolean shouldAdd);

    /**
     * Add a label component using the provided data.
     *
     * @param text the text that is shown in the component
     * @see org.geysermc.cumulus.component.InputComponent
     * @since 1.1
     */
    @This Builder label(String text);

    /**
     * Optionally add a label component using the provided data.
     *
     * @param text the text that is shown in the component
     * @param shouldAdd whether the component should be added
     * @see org.geysermc.cumulus.component.InputComponent
     * @since 1.1
     */
    @This Builder optionalLabel(String text, boolean shouldAdd);

    /**
     * Add a slider component using the provided data.
     *
     * @param text the text that is shown in the component
     * @param min the minimal value of the slider
     * @param max the maximum value of the slider
     * @param step the amount between each step
     * @param defaultValue the default value of the slider
     * @see org.geysermc.cumulus.component.SliderComponent
     * @since 1.1
     */
    @This Builder slider(String text, float min, float max, @Positive float step, float defaultValue);

    /**
     * Add a slider component using the provided data, with defaultValue being automatically
     * computed.
     *
     * @param text the text that is shown in the component
     * @param min the minimal value of the slider
     * @param max the maximum value of the slider
     * @param step the amount between each step
     * @see org.geysermc.cumulus.component.SliderComponent
     * @since 1.1
     */
    @This Builder slider(String text, float min, float max, @Positive float step);

    /**
     * Add a slider component using the provided data, with step being 1 and defaultValue being
     * automatically computed.
     *
     * @param text the text that is shown in the component
     * @param min the minimal value of the slider
     * @param max the maximum value of the slider
     * @see org.geysermc.cumulus.component.SliderComponent
     * @since 1.1
     */
    @This Builder slider(String text, float min, float max);

    /**
     * Optionally add a slider component using the provided data.
     *
     * @param text the text that is shown in the component
     * @param min the minimal value of the slider
     * @param max the maximum value of the slider
     * @param step the amount between each step
     * @param defaultValue the default value of the slider
     * @param shouldAdd whether the component should be added
     * @see org.geysermc.cumulus.component.SliderComponent
     * @since 1.1
     */
    @This Builder optionalSlider(
        String text,
        float min,
        float max,
        @Positive float step,
        float defaultValue,
        boolean shouldAdd);

    /**
     * Optionally add a slider component using the provided data, with defaultValue being computed
     * automatically.
     *
     * @param text the text that is shown in the component
     * @param min the minimal value of the slider
     * @param max the maximum value of the slider
     * @param step the amount between each step
     * @param shouldAdd whether the component should be added
     * @see org.geysermc.cumulus.component.SliderComponent
     * @since 1.1
     */
    @This Builder optionalSlider(
        String text, float min, float max, @Positive float step, boolean shouldAdd);

    /**
     * Optionally add a slider component using the provided data, with step being 1 and defaultValue
     * being computed automatically.
     *
     * @param text the text that is shown in the component
     * @param min the minimal value of the slider
     * @param max the maximum value of the slider
     * @param shouldAdd whether the component should be added
     * @see org.geysermc.cumulus.component.SliderComponent
     * @since 1.1
     */
    @This Builder optionalSlider(String text, float min, float max, boolean shouldAdd);

    /**
     * Add a step slider component using the provided step slider builder.
     *
     * @param stepSliderBuilder the builder to add
     * @see StepSliderComponent
     * @since 1.1
     */
    @This Builder stepSlider(StepSliderComponent.Builder stepSliderBuilder);

    /**
     * Add a step slider using the provided data.
     *
     * @param text the text that is shown in the component
     * @param steps all the steps
     * @param defaultStep the index of the step that is selected by default
     * @see StepSliderComponent
     * @since 1.1
     */
    @This Builder stepSlider(String text, List<String> steps, @NonNegative int defaultStep);

    /**
     * Add a step slider using the provided data.
     *
     * @param text the text that is shown in the component
     * @param defaultStep the index of the step that is selected by default
     * @param steps all the steps
     * @see StepSliderComponent
     * @since 1.1
     */
    @This Builder stepSlider(String text, @NonNegative int defaultStep, String... steps);

    /**
     * Add a step slider using the provided data, with the default step being the first.
     *
     * @param text the text that is shown in the component
     * @param steps all the steps
     * @see StepSliderComponent
     * @since 1.1
     */
    @This Builder stepSlider(String text, List<String> steps);

    /**
     * Add a step slider using the provided data, with the default step being the first.
     *
     * @param text the text that is shown in the component
     * @param steps all the steps
     * @see StepSliderComponent
     * @since 1.1
     */
    @This Builder stepSlider(String text, String... steps);

    /**
     * Optionally add a step slider using the provided data.
     *
     * @param text the text that is shown in the component
     * @param steps all the steps
     * @param defaultStep the index of the step that is selected by default
     * @param shouldAdd whether the component should be added
     * @see StepSliderComponent
     * @since 1.1
     */
    @This Builder optionalStepSlider(
        String text, List<String> steps, @NonNegative int defaultStep, boolean shouldAdd);

    /**
     * Optionally add a step slider using the provided data.
     *
     * @param shouldAdd whether the component should be added
     * @param text the text that is shown in the component
     * @param defaultStep the index of the step that is selected by default
     * @param steps all the steps
     * @see StepSliderComponent
     * @since 1.1
     */
    @This Builder optionalStepSlider(
        boolean shouldAdd, String text, @NonNegative int defaultStep, String... steps);

    /**
     * Optionally add a step slider using the provided data, with the default step being the first.
     *
     * @param text the text that is shown in the component
     * @param steps all the steps
     * @param shouldAdd whether the component should be added
     * @see StepSliderComponent
     * @since 1.1
     */
    @This Builder optionalStepSlider(String text, List<String> steps, boolean shouldAdd);

    /**
     * Optionally add a step slider using the provided data, with the default step being the first.
     *
     * @param shouldAdd whether the component should be added
     * @param text the text that is shown in the component
     * @param steps all the steps
     * @see StepSliderComponent
     * @since 1.1
     */
    @This Builder optionalStepSlider(boolean shouldAdd, String text, String... steps);

    /**
     * Add a toggle component using the provided data.
     *
     * @param text the text that is shown in the component
     * @param defaultValue the default value for the toggle
     * @see org.geysermc.cumulus.component.ToggleComponent
     * @since 1.1
     */
    @This Builder toggle(String text, boolean defaultValue);

    /**
     * Add a toggle component using the provided data, with the default value being false.
     *
     * @param text the text that is shown in the component
     * @see org.geysermc.cumulus.component.ToggleComponent
     * @since 1.1
     */
    @This Builder toggle(String text);

    /**
     * Add a toggle component using the provided data.
     *
     * @param text the text that is shown in the component
     * @param defaultValue the default value for the toggle
     * @param shouldAdd whether the component should be added
     * @see org.geysermc.cumulus.component.ToggleComponent
     * @since 1.1
     */
    @This Builder optionalToggle(String text, boolean defaultValue, boolean shouldAdd);

    /**
     * Optionally add a toggle component using the provided data.
     *
     * @param text the text that is shown in the component
     * @param shouldAdd whether the component should be added
     * @see org.geysermc.cumulus.component.ToggleComponent
     * @since 1.1
     */
    @This Builder optionalToggle(String text, boolean shouldAdd);
  }
}
