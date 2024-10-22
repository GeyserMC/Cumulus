/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.util.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import org.geysermc.cumulus.component.impl.ButtonComponentImpl;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.impl.FormImpl;
import org.geysermc.cumulus.form.util.FormCodec;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public abstract class FormCodecImpl<F extends Form, R extends FormResponse>
    implements JsonDeserializer<F>, JsonSerializer<F>, FormCodec<F, R> {

  protected static final Type LIST_BUTTON_TYPE =
      new TypeToken<List<ButtonComponentImpl>>() {}.getType();

  protected final Class<F> typeClass;
  protected final FormType formType;
  protected final Gson gson;

  protected FormCodecImpl(Class<F> typeClass, FormType formType) {
    this.typeClass = typeClass;
    this.formType = formType;

    GsonBuilder builder = new GsonBuilder();
    initializeGson(builder);
    this.gson = builder.create();
  }

  @Override
  public final F fromJson(String json, BiConsumer<F, @Nullable String> rawResponseConsumer) {
    F form = gson.fromJson(json, typeClass);
    setRawResponseConsumer(form, rawResponseConsumer);
    return form;
  }

  @SuppressWarnings("unchecked")
  protected void setRawResponseConsumer(
      F form, BiConsumer<F, @Nullable String> rawResponseConsumer) {
    ((FormImpl<R>) form)
        .rawResponseConsumer(response -> rawResponseConsumer.accept(form, response));
  }

  @Override
  public final String jsonData(F form) {
    return gson.toJson(form, typeClass);
  }

  @Override
  public final F deserialize(JsonElement element, Type typeOfF, JsonDeserializationContext context)
      throws JsonParseException {

    if (!element.isJsonObject()) {
      throw new JsonParseException("Form has to be a JsonObject");
    }

    return deserializeForm(element.getAsJsonObject(), context);
  }

  @Override
  public final JsonElement serialize(F src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject result = new JsonObject();
    serializeForm(src, context, result);

    result.add("type", context.serialize(formType));
    return result;
  }

  @Override
  public final FormResponseResult<R> deserializeFormResponse(F form, @Nullable String response) {
    Objects.requireNonNull(form);

    // if the form has been closed by the client
    if (response == null || response.isEmpty() || "null".equals(response.trim())) {
      return FormResponseResult.closed();
    }

    return deserializeResponse(form, response);
  }

  protected void initializeGson(GsonBuilder builder) {
    builder.registerTypeAdapter(typeClass, this);
  }

  protected abstract F deserializeForm(JsonObject source, JsonDeserializationContext context);

  protected abstract void serializeForm(
      F form, JsonSerializationContext context, JsonObject result);

  protected abstract FormResponseResult<R> deserializeResponse(F form, String data);
}
