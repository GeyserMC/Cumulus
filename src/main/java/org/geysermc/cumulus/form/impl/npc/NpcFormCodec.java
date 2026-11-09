/*
 * Copyright (c) 2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl.npc;

import com.google.gson.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.cloudburstmc.math.vector.Vector3f;
import org.geysermc.cumulus.form.NpcForm;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.form.util.impl.FormCodecImpl;
import org.geysermc.cumulus.response.NpcFormResponse;
import org.geysermc.cumulus.response.impl.NpcFormResponseImpl;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.cumulus.util.JsonUtils;
import org.geysermc.cumulus.util.impl.FormImageAdaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class NpcFormCodec extends FormCodecImpl<NpcForm, NpcFormResponse> {
  NpcFormCodec() {
    super(NpcForm.class, FormType.NPC_FORM);
  }

  @Override
  protected NpcForm deserializeForm(JsonObject source, JsonDeserializationContext context) {
    String title = JsonUtils.assumeMember(source, "title").getAsString();
    String content = JsonUtils.assumeMember(source, "content").getAsString();
    UUID entityUUID = UUID.fromString(JsonUtils.assumeMember(source, "entityUUID").getAsString());
    JsonArray array = JsonUtils.assumeMember(source, "buttons").getAsJsonArray();
    List<String> buttons = new ArrayList<>();
    for (JsonElement e : array) {
      buttons.add(e.getAsString());
    }
    Vector3f entityOffset = jsonArrayToVector3f(JsonUtils.assumeMember(source, "entityOffset").getAsJsonArray());
    Vector3f entityScale = jsonArrayToVector3f(JsonUtils.assumeMember(source, "entityScale").getAsJsonArray());

    return new NpcFormImpl(title, content, entityUUID, buttons, entityOffset, entityScale);
  }

  @Override
  protected void serializeForm(NpcForm form, JsonSerializationContext context, JsonObject result) {
    result.addProperty("title", form.title());
    result.addProperty("content", form.content());
    result.addProperty("entityUUID", form.entityUUID().toString());
    result.add("entityOffset", vector3fToJsonArray(form.entityOffset()));
    result.add("entityScale", vector3fToJsonArray(form.entityScale()));

    // remove optional buttons from the button list
    JsonArray buttons = new JsonArray();
    for (String button : form.buttons()) {
      if (button != null) {
        buttons.add(new JsonPrimitive(button));
      }
    }
    result.add("buttons", buttons);
  }

  private JsonArray vector3fToJsonArray(Vector3f v) {
    JsonArray array = new JsonArray();
    array.add(new JsonPrimitive(v.getX()));
    array.add(new JsonPrimitive(v.getY()));
    array.add(new JsonPrimitive(v.getZ()));
    return array;
  }

  private Vector3f jsonArrayToVector3f(JsonArray a) {
    return Vector3f.from(a.get(0).getAsDouble(), a.get(1).getAsDouble(), a.get(1).getAsDouble());
  }

  @Override
  protected FormResponseResult<NpcFormResponse> deserializeResponse(
      @NonNull NpcForm form, @NonNull String data) {
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
    String button = null;
    int correctButtonId = -1;

    for (int i = 0; i < form.buttons().size(); i++) {
      String current = form.buttons().get(i);
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

    return FormResponseResult.valid(NpcFormResponseImpl.of(correctButtonId, button));
  }

  @Override
  protected void initializeGson(GsonBuilder builder) {
    super.initializeGson(builder);
    builder.registerTypeAdapter(FormImage.class, new FormImageAdaptor());
  }
}
