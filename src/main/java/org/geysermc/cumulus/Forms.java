/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus;

import java.util.function.BiConsumer;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.impl.DropdownComponentImpl;
import org.geysermc.cumulus.component.impl.InputComponentImpl;
import org.geysermc.cumulus.component.impl.LabelComponentImpl;
import org.geysermc.cumulus.component.impl.SliderComponentImpl;
import org.geysermc.cumulus.component.impl.StepSliderComponentImpl;
import org.geysermc.cumulus.component.impl.ToggleComponentImpl;
import org.geysermc.cumulus.component.util.ComponentType;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.impl.FormDefinitions;
import org.geysermc.cumulus.form.util.FormCodec;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.response.FormResponse;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Some common utility methods related to forms.
 *
 * @since 1.0
 */
@NullMarked
public final class Forms {
  /**
   * Translate the data that is readable by the Bedrock client into a form instance.
   *
   * @param json the json data that is readable by the client
   * @param type the form data type
   * @param <T> the result will be cast to T
   * @return the form instance holding the translated data
   * @since 1.1
   */
  public static <T extends Form> T fromJson(
      String json, FormType type, BiConsumer<T, @Nullable String> responseHandler) {
    return FormDefinitions.instance()
        .<FormCodec<T, FormResponse>>codecFor(type)
        .fromJson(json, responseHandler);
  }

  /**
   * Get the class implementing the component by the component type.
   *
   * @param type the component type
   * @return the class implementing the component
   * @since 1.0
   */
  public static Class<? extends Component> getComponentTypeImpl(ComponentType type) {
    // todo do we want a component definition as well?
    switch (type) {
      case DROPDOWN:
        return DropdownComponentImpl.class;
      case INPUT:
        return InputComponentImpl.class;
      case LABEL:
        return LabelComponentImpl.class;
      case SLIDER:
        return SliderComponentImpl.class;
      case STEP_SLIDER:
        return StepSliderComponentImpl.class;
      case TOGGLE:
        return ToggleComponentImpl.class;
      default:
        throw new RuntimeException("Cannot find implementation for ComponentType " + type);
    }
  }
}
