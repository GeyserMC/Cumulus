/*
 * Copyright (c) 2020-2022 GeyserMC. http://geysermc.org
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

package org.geysermc.cumulus.form.impl.simple;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.Forms;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.impl.SimpleFormResponseImpl;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.util.FormCodec;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.cumulus.util.FormType;
import org.geysermc.cumulus.util.impl.FormCodecImpl;
import org.geysermc.cumulus.util.impl.FormImageAdaptor;

public final class SimpleFormCodec extends FormCodecImpl<SimpleForm, SimpleFormResponse>
    implements FormCodec<SimpleForm, SimpleFormResponse> {

  SimpleFormCodec() {
    super(SimpleForm.class, FormType.MODAL_FORM);
  }

  @Override
  protected SimpleForm deserializeForm(JsonObject source, JsonDeserializationContext context) {
    String title = Forms.getOrThrow(source, "title").getAsString();
    String content = Forms.getOrThrow(source, "content").getAsString();

    JsonElement buttonsElement = Forms.getOrThrow(source, "buttons");
    List<ButtonComponent> buttons = context.deserialize(buttonsElement, LIST_BUTTON_TYPE);

    return new SimpleFormImpl(title, content, buttons);
  }

  @Override
  protected void serializeForm(SimpleForm form, JsonSerializationContext context,
                            JsonObject result) {
    result.addProperty("title", form.title());
    result.addProperty("content", form.content());
    result.add("buttons", context.serialize(form.buttons(), LIST_BUTTON_TYPE));
  }

  @Override
  protected FormResponseResult<SimpleFormResponse> deserializeResponse(
      @NonNull SimpleForm form, @Nullable String data) {

    //noinspection ConstantConditions
    data = data.trim();

    int buttonId;
    try {
      buttonId = Integer.parseInt(data);
    } catch (Exception exception) {
      return FormResponseResult.invalid();
    }

    if (buttonId >= form.buttons().size()) {
      return FormResponseResult.invalid();
    }

    return FormResponseResult.valid(
        SimpleFormResponseImpl.of(buttonId, form.buttons().get(buttonId))
    );
  }

  @Override
  protected void initializeGson(GsonBuilder builder) {
    super.initializeGson(builder);
    builder.registerTypeAdapter(FormImage.class, new FormImageAdaptor());
  }
}
