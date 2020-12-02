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
 * @link https://github.com/GeyserMC/Geyser
 */

package org.geysermc.cumulus.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.geysermc.cumulus.Form;
import org.geysermc.cumulus.Forms;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.impl.component.*;
import org.geysermc.cumulus.impl.util.FormAdaptor;
import org.geysermc.cumulus.impl.util.FormImpl;
import org.geysermc.cumulus.impl.util.TypeInitializerImpl;
import org.geysermc.cumulus.util.ComponentType;
import org.geysermc.cumulus.util.FormType;

@Getter
public final class FormsImpl extends Forms {
    private final Gson formsGson =
            new GsonBuilder()
                    .registerTypeAdapter(FormImpl.class, new FormAdaptor(this))
                    .create();
    private final TypeInitializerImpl typeInitializer = new TypeInitializerImpl();

    @Override
    public Class<? extends Form> getFormImplFrom(FormType formType) {
        switch (formType) {
            case CUSTOM_FORM:
                return CustomFormImpl.class;
            case MODAL_FORM:
                return ModalFormImpl.class;
            case SIMPLE_FORM:
                return SimpleFormImpl.class;
            default:
                throw new RuntimeException("Cannot find implementation form FormType" + formType);
        }
    }

    public Class<? extends Component> getComponentImplFrom(ComponentType type) {
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
