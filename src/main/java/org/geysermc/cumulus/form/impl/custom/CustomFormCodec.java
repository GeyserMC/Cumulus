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

package org.geysermc.cumulus.form.impl.custom;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.geysermc.cumulus.Forms;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.impl.CustomFormResponseImpl;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.util.ComponentType;
import org.geysermc.cumulus.util.FormCodec;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.cumulus.util.impl.FormCodecImpl;
import org.geysermc.cumulus.util.impl.FormImageAdaptor;
import org.geysermc.cumulus.util.impl.FormImageImpl;

public final class CustomFormCodec extends FormCodecImpl<CustomForm, CustomFormResponse>
    implements FormCodec<CustomForm, CustomFormResponse> {

  CustomFormCodec() {
    super(CustomForm.class);
  }

  @Override
  protected CustomForm deserializeForm(JsonObject source, JsonDeserializationContext context) {
    String title = Forms.getOrThrow(source, "title").getAsString();
    FormImage icon = context.deserialize(source.get("icon"), FormImageImpl.class);

    List<Component> content = new ArrayList<>();

    JsonArray contentArray = Forms.getOrThrow(source, "content").getAsJsonArray();
    for (JsonElement contentElement : contentArray) {
      String typeName = Forms.getOrThrow(contentElement.getAsJsonObject(), "type").getAsString();

      ComponentType type = ComponentType.getByName(typeName);
      if (type == null) {
        throw new JsonParseException("Failed to find Component type " + typeName);
      }

      content.add(context.deserialize(contentElement, Forms.getComponentTypeImpl(type)));
    }
    return new CustomFormImpl(title, icon, content);
  }

  @Override
  protected void serializeForm(CustomForm form, JsonSerializationContext context, JsonObject result) {
    result.addProperty("title", form.getTitle());
    result.add("icon", context.serialize(form.getIcon()));
    result.add("content", context.serialize(form.getContent()));
  }

  @Override
  protected FormResponseResult<CustomFormResponse> deserializeResponse(
      CustomForm form,
      @Nullable String responseData) {

    return FormResponseResult.valid(
        CustomFormResponseImpl.of(form, responseData)
    );
  }

  @Override
  protected void initializeGson(GsonBuilder builder) {
    super.initializeGson(builder);
    builder.registerTypeAdapter(FormImage.class, new FormImageAdaptor());
  }
}
