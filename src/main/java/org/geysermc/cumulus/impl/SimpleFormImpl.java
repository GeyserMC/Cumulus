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

package org.geysermc.cumulus.impl;

import com.google.gson.annotations.JsonAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.impl.SimpleFormResponseImpl;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.cumulus.util.FormType;
import org.geysermc.cumulus.util.impl.FormAdaptor;
import org.geysermc.cumulus.util.impl.FormImpl;

@Getter
@JsonAdapter(FormAdaptor.class)
public final class SimpleFormImpl extends FormImpl implements SimpleForm {
    private final String title;
    private final String content;
    private final List<ButtonComponent> buttons;

    public SimpleFormImpl(
            @NonNull String title,
            @NonNull String content,
            @NonNull List<ButtonComponent> buttons
    ) {
        super(FormType.SIMPLE_FORM);

        this.title = title;
        this.content = content;
        this.buttons = Collections.unmodifiableList(buttons);
    }

    public @NonNull SimpleFormResponse parseResponse(@Nullable String data) {
        if (isClosed(data)) {
            return SimpleFormResponseImpl.closed();
        }
        //noinspection ConstantConditions
        data = data.trim();

        int buttonId;
        try {
            buttonId = Integer.parseInt(data);
        } catch (Exception exception) {
            return SimpleFormResponseImpl.invalid();
        }

        if (buttonId >= buttons.size()) {
            return SimpleFormResponseImpl.invalid();
        }

        return SimpleFormResponseImpl.of(buttonId, buttons.get(buttonId));
    }

    public static final class Builder extends FormImpl.Builder<SimpleForm.Builder, SimpleForm>
            implements SimpleForm.Builder {

        private final List<ButtonComponent> buttons = new ArrayList<>();
        private String content = "";

        public @NonNull Builder content(@NonNull String content) {
            this.content = translate(content);
            return this;
        }

        public @NonNull Builder button(
                @NonNull String text,
                FormImage.@NonNull Type type,
                @NonNull String data
        ) {
            buttons.add(ButtonComponent.of(translate(text), type, data));
            return this;
        }

        public @NonNull Builder button(@NonNull String text, @NonNull FormImage image) {
            buttons.add(ButtonComponent.of(translate(text), image));
            return this;
        }

        public @NonNull Builder button(@NonNull String text) {
            buttons.add(ButtonComponent.of(translate(text)));
            return this;
        }

        @Override
        public @NonNull SimpleForm build() {
            SimpleFormImpl form = new SimpleFormImpl(title, content, buttons);
            if (biResponseHandler != null) {
                form.setResponseHandler(response -> biResponseHandler.accept(form, response));
                return form;
            }

            form.setResponseHandler(responseHandler);
            return form;
        }
    }
}
