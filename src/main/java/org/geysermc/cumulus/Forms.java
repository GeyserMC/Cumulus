/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus;

import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.impl.DropdownComponentImpl;
import org.geysermc.cumulus.component.impl.InputComponentImpl;
import org.geysermc.cumulus.component.impl.LabelComponentImpl;
import org.geysermc.cumulus.component.impl.SliderComponentImpl;
import org.geysermc.cumulus.component.impl.StepSliderComponentImpl;
import org.geysermc.cumulus.component.impl.ToggleComponentImpl;
import org.geysermc.cumulus.component.util.ComponentType;
import org.jspecify.annotations.NullMarked;

/**
 * Some common utility methods related to forms. These methods aren't meant to be public API and may
 * disappear without notice.
 *
 * @since 1.0
 */
@NullMarked
public final class Forms {
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
