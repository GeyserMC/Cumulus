/*
 * Copyright (c) 2020-2023 GeyserMC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.util.impl;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.cumulus.util.JsonUtils;

public class FormImageAdaptor implements JsonDeserializer<FormImage>, JsonSerializer<FormImage> {
  @Override
  public FormImage deserialize(
      JsonElement element, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {

    if (!element.isJsonObject()) {
      throw new JsonParseException("Form has to be a JsonObject");
    }

    JsonObject json = element.getAsJsonObject();

    JsonElement type = JsonUtils.assumeMember(json, "type");
    JsonElement data = JsonUtils.assumeMember(json, "data");

    return FormImage.of(type.getAsString(), data.getAsString());
  }

  @Override
  public JsonElement serialize(FormImage src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject json = new JsonObject();
    json.addProperty("type", src.type().typeName());
    json.addProperty("data", src.data());
    return json;
  }
}
