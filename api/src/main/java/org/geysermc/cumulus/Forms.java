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

package org.geysermc.cumulus;

import com.google.gson.Gson;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.util.ComponentType;
import org.geysermc.cumulus.util.FormType;
import org.geysermc.cumulus.util.FormsInstanceHolder;
import org.geysermc.cumulus.util.TypeInitializer;

public abstract class Forms {
    public static Gson getGson() {
        return getInstance().getFormsGson();
    }

    public static TypeInitializer getInitializer() {
        return getInstance().getTypeInitializer();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Form> T fromJson(String json, FormType formType) {
        return (T) getGson().fromJson(json, getInstance().getFormImplFrom(formType));
    }

    private static Forms getInstance() {
        return FormsInstanceHolder.get();
    }

    public abstract Gson getFormsGson();

    public abstract Class<? extends Form> getFormImplFrom(FormType formType);

    public abstract Class<? extends Component> getComponentImplFrom(ComponentType componentType);

    public abstract TypeInitializer getTypeInitializer();
}
