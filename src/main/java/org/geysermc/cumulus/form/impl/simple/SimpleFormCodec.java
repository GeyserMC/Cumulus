/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl.simple;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.form.util.impl.FormCodecImpl;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.impl.SimpleFormResponseImpl;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.cumulus.util.JsonUtils;
import org.geysermc.cumulus.util.impl.FormImageAdaptor;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class SimpleFormCodec extends FormCodecImpl<SimpleForm, SimpleFormResponse> {
  SimpleFormCodec() {
    super(SimpleForm.class, FormType.SIMPLE_FORM);
  }

  @Override
  protected SimpleForm deserializeForm(JsonObject source, JsonDeserializationContext context) {
    String title = JsonUtils.assumeMember(source, "title").getAsString();
    String content = JsonUtils.assumeMember(source, "content").getAsString();

    JsonElement buttonsElement = JsonUtils.assumeMember(source, "buttons");
    // optionals shouldn't be serialized, meaning every item is not null
    List<ButtonComponent> buttons = context.deserialize(buttonsElement, LIST_BUTTON_TYPE);

    return new SimpleFormImpl(title, content, buttons);
  }

  @Override
  protected void serializeForm(
      SimpleForm form, JsonSerializationContext context, JsonObject result) {
    result.addProperty("title", form.title());
    result.addProperty("content", form.content());

    // remove optional buttons from the button list
    JsonArray buttons = new JsonArray();
    for (ButtonComponent button : form.buttons()) {
      if (button != null) {
        buttons.add(context.serialize(button));
      }
    }
    result.add("buttons", buttons);
  }

  @Override
  protected FormResponseResult<SimpleFormResponse> deserializeResponse(
      SimpleForm form, String data) {
    data = data.trim();

    int buttonId;
    try {
      buttonId = Integer.parseInt(data);
    } catch (Exception exception) {
      return FormResponseResult.invalid(
          -1, "Received invalid integer representing the clicked button");
    }

    if (buttonId < 0) {
      return FormResponseResult.invalid(-1, "Received a clicked button id that's smaller than 0");
    }

    // we could have optional buttons.
    // let's make sure that the buttonId we received is mapped correctly
    ButtonComponent button = null;
    int correctButtonId = -1;

    for (int i = 0; i < form.buttons().size(); i++) {
      ButtonComponent current = form.buttons().get(i);
      if (current != null && buttonId-- == 0) {
        // only decrement buttonId when we pass over a button that is present
        // once buttonId is zero, i is the correct ID (since i is incremented over null components)
        button = current;
        correctButtonId = i;
        break;
      }
    }

    if (button == null) {
      return FormResponseResult.invalid(
          -1, "Receiver a button id larger than the amount of buttons in the form");
    }

    return FormResponseResult.valid(SimpleFormResponseImpl.of(correctButtonId, button));
  }

  @Override
  protected void initializeGson(GsonBuilder builder) {
    super.initializeGson(builder);
    builder.registerTypeAdapter(FormImage.class, new FormImageAdaptor());
  }
}
