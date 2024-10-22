/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
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
import org.jspecify.annotations.NullMarked;

@NullMarked
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
