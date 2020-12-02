package org.geysermc.cumulus.impl.util;

import org.geysermc.cumulus.CustomForm;
import org.geysermc.cumulus.ModalForm;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.*;
import org.geysermc.cumulus.impl.CustomFormImpl;
import org.geysermc.cumulus.impl.ModalFormImpl;
import org.geysermc.cumulus.impl.SimpleFormImpl;
import org.geysermc.cumulus.impl.component.*;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.cumulus.util.TypeInitializer;

import java.util.List;

public final class TypeInitializerImpl implements TypeInitializer {
    @Override
    public CustomForm customForm(String title, FormImage icon, List<Component> content) {
        return CustomFormImpl.of(title, icon, content);
    }

    @Override
    public CustomForm.Builder customFormBuilder() {
        return new CustomFormImpl.Builder();
    }

    @Override
    public ModalForm modalForm(String title, String content, String button1, String button2) {
        return ModalFormImpl.of(title, content, button1, button2);
    }

    @Override
    public ModalForm.Builder modalFormBuilder() {
        return new ModalFormImpl.Builder();
    }

    @Override
    public SimpleForm simpleForm(String title, String content, List<ButtonComponent> buttons) {
        return SimpleFormImpl.of(title, content, buttons);
    }

    @Override
    public SimpleForm.Builder simpleFormBuilder() {
        return new SimpleFormImpl.Builder();
    }

    @Override
    public FormImage image(FormImage.Type type, String data) {
        return FormImageImpl.of(type, data);
    }

    @Override
    public FormImage image(String type, String data) {
        return FormImageImpl.of(type, data);
    }

    @Override
    public ButtonComponent button(String text, FormImage image) {
        return ButtonComponentImpl.of(text, image);
    }

    @Override
    public ButtonComponent button(String text, FormImage.Type image, String data) {
        return ButtonComponentImpl.of(text, image, data);
    }

    @Override
    public DropdownComponent dropdown(String text, List<String> options, int defaultOption) {
        return DropdownComponentImpl.of(text, options, defaultOption);
    }

    @Override
    public DropdownComponent.Builder dropdownBuilder() {
        return new DropdownComponentImpl.Builder();
    }

    @Override
    public InputComponent input(String text, String placeholder, String defaultText) {
        return InputComponentImpl.of(text, placeholder, defaultText);
    }

    @Override
    public InputComponent input(String text, String placeholder) {
        return InputComponentImpl.of(text, placeholder);
    }

    @Override
    public InputComponent input(String text) {
        return InputComponentImpl.of(text);
    }

    @Override
    public LabelComponent label(String text) {
        return LabelComponentImpl.of(text);
    }

    @Override
    public SliderComponent slider(String text, float min, float max, int step, float defaultValue) {
        return SliderComponentImpl.of(text, min, max, step, defaultValue);
    }

    @Override
    public SliderComponent slider(String text, float min, float max, int step) {
        return SliderComponentImpl.of(text, min, max, step);
    }

    @Override
    public SliderComponent slider(String text, float min, float max, float defaultValue) {
        return SliderComponentImpl.of(text, min, max, defaultValue);
    }

    @Override
    public SliderComponent slider(String text, float min, float max) {
        return SliderComponentImpl.of(text, min, max);
    }

    @Override
    public StepSliderComponent stepSlider(String text, List<String> steps, int defaultStep) {
        return StepSliderComponentImpl.of(text, steps, defaultStep);
    }

    @Override
    public StepSliderComponent stepSlider(String text, int defaultStep, String... steps) {
        return StepSliderComponentImpl.of(text, defaultStep, steps);
    }

    @Override
    public StepSliderComponent stepSlider(String text, String... steps) {
        return StepSliderComponentImpl.of(text, steps);
    }

    @Override
    public StepSliderComponent.Builder stepSliderBuilder() {
        return new StepSliderComponentImpl.Builder();
    }

    @Override
    public ToggleComponent toggle(String text, boolean defaultValue) {
        return ToggleComponentImpl.of(text, defaultValue);
    }

    @Override
    public ToggleComponent toggle(String text) {
        return ToggleComponentImpl.of(text);
    }
}
