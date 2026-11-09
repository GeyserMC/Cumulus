/*
 * Copyright (c) 2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl.npc;

import java.util.*;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.cloudburstmc.math.vector.Vector3f;
import org.geysermc.cumulus.form.NpcForm;
import org.geysermc.cumulus.form.impl.FormImpl;
import org.geysermc.cumulus.response.NpcFormResponse;

public class NpcFormImpl extends FormImpl<NpcFormResponse> implements NpcForm {

  private final String content;
  private UUID entityUUID;
  private List<String> buttons;
  private Vector3f entityOffset;
  private Vector3f entityScale;
  public String actionJson; //The data that gets sent in the npc dialogue packet
  public String npc_data; //the data for entities' npc data

  public NpcFormImpl(
      @NonNull String title, @NonNull String content, UUID entityUUID,
      @NonNull List<String> buttons, @NonNull Vector3f entityOffset, @NonNull Vector3f entityScale
  ) {
    super(title);
    this.content = Objects.requireNonNull(content, "content");
    this.entityUUID = entityUUID;
    this.buttons = Collections.unmodifiableList(buttons);
    this.entityOffset = entityOffset;
    this.entityScale = entityScale;
    this.actionJson = actionJsonFromButtons(buttons);

    //Sets up npcData
    JsonObject json = new JsonObject();
    JsonObject portrait_offsets = new JsonObject();
    portrait_offsets.add("scale", jsonArrayOf(entityScale));
    portrait_offsets.add("translate", jsonArrayOf(entityOffset));
    json.add("portrait_offsets", portrait_offsets);

    JsonArray skin_list = new JsonArray();
    JsonObject skin_variant = new JsonObject();
    skin_variant.addProperty("variant", 0);
    skin_list.add(skin_variant);
    json.add("skin_list", skin_list);

    this.npc_data = json.toString();
  }

  private String actionJsonFromButtons(List<String> buttons) {
    JsonArray array = new JsonArray();
    for (String s : buttons) {
      JsonObject json = new JsonObject();
      json.addProperty("button_name", s);
      json.add("data", new JsonArray());
      json.addProperty("mode", 0);
      json.addProperty("text", ""); //text is in button_name
      json.addProperty("type", 1);
      array.add(json);
    }
    return array.toString();
  }

  @Override
  public @NonNull String content() {
    return content;
  }

  @Override
  public @NonNull UUID entityUUID() {
    return entityUUID;
  }

  @Override
  public @NonNull Vector3f entityOffset() {
    return entityOffset;
  }

  @Override
  public @NonNull Vector3f entityScale() {
    return entityScale;
  }

  @Override
  public @NonNull List<String> buttons() {
    return buttons;
  }

  public static final class Builder
      extends FormImpl.Builder<NpcForm.Builder, NpcForm, NpcFormResponse>
      implements NpcForm.Builder {

    private List<String> buttons = new ArrayList<>();
    private final Map<Integer, Consumer<NpcFormResponse>> callbacks = new HashMap<>();
    private String content = "";
    private UUID entityUUID;
    public Vector3f entityOffset = Vector3f.from(0, 0, 0);
    public Vector3f entityScale = Vector3f.from(1, 1, 1);

    // setDialogue
    @Override
    public NpcFormImpl.Builder content(@NonNull String content) {
      this.content = translate(Objects.requireNonNull(content, "content"));
      return this;
    }

    // setNpcID
    @Override
    public NpcForm.Builder entity(@NonNull UUID entityUUID) {
      this.entityUUID = entityUUID;
      return this;
    }

    @Override
    public NpcForm.Builder entityOffset(double x, double y, double z) {
      this.entityOffset = Vector3f.from(x, y, z);
      return this;
    }

    @Override
    public NpcForm.Builder entityOffset(@NonNull Vector3f vector3f) {
      this.entityOffset = vector3f;
      return this;
    }

    @Override
    public NpcForm.Builder entityScale(double x, double y, double z) {
      this.entityScale = Vector3f.from(x, y, z);
      return this;
    }

    @Override
    public NpcForm.Builder entityScale(@NonNull Vector3f vector3f) {
      this.entityScale = vector3f;
      return this;
    }

    @Override
    public NpcFormImpl.Builder button(@NonNull String text) {
      //There is a limit of 6 buttons in npc dialogs
      if (buttons.size() <= 6) {
        this.buttons.add(translate(Objects.requireNonNull(text, "text")));
      }
      return this;
    }

    @Override
    public NpcForm.Builder optionalButton(@NonNull String text, boolean shouldAdd) {
      if (shouldAdd) {
        return button(text);
      }
      return this;
    }

    @Override
    public NpcForm.Builder button(@NonNull String text, @NonNull Consumer<NpcFormResponse> callback) {
      if (buttons.size() <= 6) {
        callbacks.put(buttons.size(), Objects.requireNonNull(callback));
      }
      return button(text);
    }

    @Override
    public NpcForm.Builder button(@NonNull String text,  @NonNull Consumer<NpcFormResponse> callback, boolean shouldAdd) {
      if (shouldAdd) {
        return button(text, callback);
      }
      return this;
    }

    @Override
    public @NonNull NpcForm build() {
      NpcFormImpl form = new NpcFormImpl(title, content, entityUUID, buttons, entityOffset, entityScale);
      setResponseHandler(
          form,
          form,
          valid -> {
            Consumer<NpcFormResponse> callback = callbacks.get(valid.clickedButtonId());
            if (callback != null) {
              callback.accept(valid);
            }
          });
      return form;
    }
  }

  private static JsonArray jsonArrayOf(Vector3f vector) {
    JsonArray array = new JsonArray();
    array.add(new JsonPrimitive(vector.getX()));
    array.add(new JsonPrimitive(vector.getY()));
    array.add(new JsonPrimitive(vector.getZ()));
    return array;
  }
}
