/*
 * Copyright (c) 2019-2020 GeyserMC. http://geysermc.org
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

package org.geysermc.cumulus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.impl.DropdownComponentImpl;
import org.geysermc.cumulus.component.impl.InputComponentImpl;
import org.geysermc.cumulus.component.impl.LabelComponentImpl;
import org.geysermc.cumulus.component.impl.SliderComponentImpl;
import org.geysermc.cumulus.component.impl.StepSliderComponentImpl;
import org.geysermc.cumulus.component.impl.ToggleComponentImpl;
import org.geysermc.cumulus.impl.CustomFormImpl;
import org.geysermc.cumulus.impl.ModalFormImpl;
import org.geysermc.cumulus.impl.SimpleFormImpl;
import org.geysermc.cumulus.util.ComponentType;
import org.geysermc.cumulus.util.FormType;
import org.geysermc.cumulus.util.impl.FormAdaptor;
import org.geysermc.cumulus.util.impl.FormImpl;

public final class Forms {
  public static final Gson GSON =
      new GsonBuilder()
          .registerTypeAdapter(FormImpl.class, new FormAdaptor())
          .create();

  /**
   * Translate the data that is readable by the the Bedrock client into a form instance.
   *
   * @param json the json data that is readable by the client
   * @param type the form data type
   * @param <T>  the result will be cast to T
   * @return the form instance holding the translated data
   */
  @NonNull
  @SuppressWarnings("unchecked")
  public static <T extends Form> T fromJson(String json, FormType type) {
    return (T) GSON.fromJson(json, getFormTypeImpl(type));
  }

  /**
   * Get the class implementing the form by the form type.
   *
   * @param type the form type
   * @return the class implementing the form
   */
  @NonNull
  public static Class<? extends Form> getFormTypeImpl(FormType type) {
    switch (type) {
      case CUSTOM_FORM:
        return CustomFormImpl.class;
      case MODAL_FORM:
        return ModalFormImpl.class;
      case SIMPLE_FORM:
        return SimpleFormImpl.class;
      default:
        throw new RuntimeException("Cannot find implementation form FormType" + type);
    }
  }

  /**
   * Get the class implementing the component by the component type.
   *
   * @param type the component type
   * @return the class implementing the component
   */
  @NonNull
  public static Class<? extends Component> getComponentTypeImpl(@NonNull ComponentType type) {
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
