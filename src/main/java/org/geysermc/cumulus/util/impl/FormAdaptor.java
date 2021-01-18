/*
 * Copyright (c) 2020-2021 GeyserMC. http://geysermc.org
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

package org.geysermc.cumulus.util.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.geysermc.cumulus.Forms;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.impl.ButtonComponentImpl;
import org.geysermc.cumulus.impl.CustomFormImpl;
import org.geysermc.cumulus.impl.ModalFormImpl;
import org.geysermc.cumulus.impl.SimpleFormImpl;
import org.geysermc.cumulus.util.ComponentType;
import org.geysermc.cumulus.util.FormImage;

@RequiredArgsConstructor
public final class FormAdaptor implements JsonDeserializer<FormImpl>, JsonSerializer<FormImpl> {
  private static final Type LIST_BUTTON_TYPE =
      new TypeToken<List<ButtonComponentImpl>>() {}.getType();

  @Override
  public FormImpl deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {

    if (!element.isJsonObject()) {
      throw new JsonParseException("Form has to be a JsonObject");
    }

    JsonObject json = element.getAsJsonObject();

    if (typeOfT == SimpleFormImpl.class) {
      String title = Forms.getOrThrow(json, "title").getAsString();
      String content = Forms.getOrThrow(json, "content").getAsString();

      JsonElement buttonsElement = Forms.getOrThrow(json, "buttons");
      List<ButtonComponent> buttons = context.deserialize(buttonsElement, LIST_BUTTON_TYPE);

      return new SimpleFormImpl(title, content, buttons);
    }

    if (typeOfT == ModalFormImpl.class) {
      String title = Forms.getOrThrow(json, "title").getAsString();
      String content = Forms.getOrThrow(json, "content").getAsString();
      String button1 = Forms.getOrThrow(json, "button1").getAsString();
      String button2 = Forms.getOrThrow(json, "button2").getAsString();
      return new ModalFormImpl(title, content, button1, button2);
    }

    if (typeOfT == CustomFormImpl.class) {
      String title = Forms.getOrThrow(json, "title").getAsString();
      FormImage icon = context.deserialize(json.get("icon"), FormImageImpl.class);

      List<Component> content = new ArrayList<>();

      JsonArray contentArray = Forms.getOrThrow(json, "content").getAsJsonArray();
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
    return null;
  }

  @Override
  public JsonElement serialize(FormImpl src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject result = new JsonObject();
    result.add("type", context.serialize(src.getType()));

    if (typeOfSrc == SimpleFormImpl.class) {
      SimpleFormImpl form = (SimpleFormImpl) src;

      result.addProperty("title", form.getTitle());
      result.addProperty("content", form.getContent());
      result.add("buttons", context.serialize(form.getButtons(), LIST_BUTTON_TYPE));
      return result;
    }

    if (typeOfSrc == ModalFormImpl.class) {
      ModalFormImpl form = (ModalFormImpl) src;

      result.addProperty("title", form.getTitle());
      result.addProperty("content", form.getContent());
      result.addProperty("button1", form.getButton1());
      result.addProperty("button2", form.getButton2());
      return result;
    }

    if (typeOfSrc == CustomFormImpl.class) {
      CustomFormImpl form = (CustomFormImpl) src;

      result.addProperty("title", form.getTitle());
      result.add("icon", context.serialize(form.getIcon()));
      result.add("content", context.serialize(form.getContent()));
      return result;
    }
    return null;
  }
}
