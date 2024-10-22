/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl.custom;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import java.util.ArrayList;
import java.util.List;
import org.geysermc.cumulus.Forms;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.util.ComponentType;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.form.util.impl.FormCodecImpl;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.impl.CustomFormResponseImpl;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.util.AbsentComponent;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.cumulus.util.JsonUtils;
import org.geysermc.cumulus.util.impl.FormImageAdaptor;
import org.geysermc.cumulus.util.impl.FormImageImpl;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class CustomFormCodec extends FormCodecImpl<CustomForm, CustomFormResponse> {
  CustomFormCodec() {
    super(CustomForm.class, FormType.CUSTOM_FORM);
  }

  @Override
  protected CustomForm deserializeForm(JsonObject source, JsonDeserializationContext context) {
    String title = JsonUtils.assumeMember(source, "title").getAsString();
    FormImage icon = context.deserialize(source.get("icon"), FormImageImpl.class);

    List<Component> content = new ArrayList<>();

    JsonArray contentArray = JsonUtils.assumeMember(source, "content").getAsJsonArray();
    for (JsonElement contentElement : contentArray) {
      String typeName =
          JsonUtils.assumeMember(contentElement.getAsJsonObject(), "type").getAsString();

      ComponentType type = ComponentType.fromName(typeName);
      if (type == null) {
        throw new JsonParseException("Failed to find Component type " + typeName);
      }

      content.add(context.deserialize(contentElement, Forms.getComponentTypeImpl(type)));
    }
    return new CustomFormImpl(title, icon, content);
  }

  @Override
  protected void serializeForm(
      CustomForm form, JsonSerializationContext context, JsonObject result) {
    result.addProperty("title", form.title());
    result.add("icon", context.serialize(form.icon()));

    // remove optional components from the content
    JsonArray content = new JsonArray();
    for (Component component : form.content()) {
      if (component != null) {
        content.add(context.serialize(component));
      }
    }
    result.add("content", content);
  }

  @Override
  protected FormResponseResult<CustomFormResponse> deserializeResponse(
      CustomForm form, String data) {
    JsonArray responses = gson.fromJson(data, JsonArray.class);
    int responsesSize = responses.size();

    List<@Nullable Object> mappedResponse = new ArrayList<>();

    int responseIndex = 0;
    List<@Nullable Component> content = form.content();
    for (int i = 0; i < content.size(); i++) {
      Component component = content.get(i);
      if (component == null) {
        mappedResponse.add(AbsentComponent.instance());
        continue;
      }

      if (responseIndex >= responsesSize) {
        return FormResponseResult.invalid(-1, "Response doesn't contain enough components");
      }

      try {
        JsonElement response = responses.get(responseIndex++); // note the index increment
        mappedResponse.add(validateComponent(component, response));
      } catch (Exception exception) {
        // looks like it didn't pass the validation.
        return FormResponseResult.invalid(i, exception.getMessage());
      }
    }

    if (responseIndex < responsesSize) {
      return FormResponseResult.invalid(-1, "Response contains too many elements");
    }

    return FormResponseResult.valid(CustomFormResponseImpl.of(mappedResponse));
  }

  private @Nullable Object validateComponent(Component component, JsonElement element) {
    ComponentType type = component.type();
    if (type == ComponentType.LABEL) {
      if (element.isJsonNull()) {
        return null;
      }
      throw new IllegalStateException("Return value of label should be null");
    }

    if (!element.isJsonPrimitive()) {
      // throw our own exception
      throw new IllegalStateException(
          String.format("Return value of %s should be a json primitive", type.componentName()));
    }

    JsonPrimitive value = element.getAsJsonPrimitive();

    // todo (for a future version) make a separate validator class for each component
    switch (type) {
      case INPUT:
        if (value.isString()) {
          return value.getAsString();
        }
        throw new IllegalStateException("Return value of input should be a string");
      case SLIDER:
        if (value.isNumber()) {
          return value.getAsFloat();
        }
        throw new IllegalStateException("Return value of slider should be a float");
      case STEP_SLIDER:
        if (value.isNumber()) {
          return value.getAsInt();
        }
        throw new IllegalStateException("Return value of step slider should be an integer");
      case TOGGLE:
        if (value.isBoolean()) {
          return value.getAsBoolean();
        }
        throw new IllegalStateException("Return value of toggle should be a boolean");
      case DROPDOWN:
        if (value.isNumber()) {
          return value.getAsInt();
        }
        throw new IllegalStateException("Return value of dropdown should be an integer");
      default:
        throw new IllegalStateException("Type " + type + " does not have validation implemented");
    }
  }

  @Override
  protected void initializeGson(GsonBuilder builder) {
    super.initializeGson(builder);
    builder.registerTypeAdapter(FormImage.class, new FormImageAdaptor());
  }
}
