package org.geysermc.cumulus.util;

import org.geysermc.cumulus.CustomForm;
import org.geysermc.cumulus.ModalForm;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.component.*;

import java.util.List;

public interface TypeInitializer {
    CustomForm customForm(String title, FormImage icon, List<Component> content);

    CustomForm.Builder customFormBuilder();

    ModalForm modalForm(String title, String content, String button1, String button2);

    ModalForm.Builder modalFormBuilder();

    SimpleForm simpleForm(String title, String content, List<ButtonComponent> buttons);

    SimpleForm.Builder simpleFormBuilder();

    FormImage image(FormImage.Type type, String data);

    FormImage image(String type, String data);

    ButtonComponent button(String text, FormImage image);

    ButtonComponent button(String text, FormImage.Type image, String data);

    DropdownComponent dropdown(String text, List<String> options, int defaultOption);

    DropdownComponent.Builder dropdownBuilder();

    InputComponent input(String text, String placeholder, String defaultText);

    InputComponent input(String text, String placeholder);

    InputComponent input(String text);

    LabelComponent label(String text);

    SliderComponent slider(String text, float min, float max, int step, float defaultValue);

    SliderComponent slider(String text, float min, float max, int step);

    SliderComponent slider(String text, float min, float max, float defaultValue);

    SliderComponent slider(String text, float min, float max);

    StepSliderComponent stepSlider(String text, List<String> steps, int defaultStep);

    StepSliderComponent stepSlider(String text, int defaultStep, String... steps);

    StepSliderComponent stepSlider(String text, String... steps);

    StepSliderComponent.Builder stepSliderBuilder();

    ToggleComponent toggle(String text, boolean defaultValue);

    ToggleComponent toggle(String text);
}
