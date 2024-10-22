/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl.modal;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.geysermc.cumulus.form.ModalForm;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.form.util.impl.FormCodecImpl;
import org.geysermc.cumulus.response.ModalFormResponse;
import org.geysermc.cumulus.response.impl.ModalFormResponseImpl;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.util.JsonUtils;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ModalFormCodec extends FormCodecImpl<ModalForm, ModalFormResponse> {
  ModalFormCodec() {
    super(ModalForm.class, FormType.MODAL_FORM);
  }

  @Override
  public ModalForm deserializeForm(JsonObject source, JsonDeserializationContext context) {
    String title = JsonUtils.assumeMember(source, "title").getAsString();
    String content = JsonUtils.assumeMember(source, "content").getAsString();
    String button1 = JsonUtils.assumeMember(source, "button1").getAsString();
    String button2 = JsonUtils.assumeMember(source, "button2").getAsString();
    return new ModalFormImpl(title, content, button1, button2);
  }

  @Override
  public void serializeForm(ModalForm form, JsonSerializationContext context, JsonObject result) {
    result.addProperty("title", form.title());
    result.addProperty("content", form.content());
    result.addProperty("button1", form.button1());
    result.addProperty("button2", form.button2());
  }

  @Override
  public FormResponseResult<ModalFormResponse> deserializeResponse(ModalForm form, String data) {
    data = data.trim();

    if ("true".equals(data)) {
      return FormResponseResult.valid(ModalFormResponseImpl.of(0, form.button1()));
    } else if ("false".equals(data)) {
      return FormResponseResult.valid(ModalFormResponseImpl.of(1, form.button2()));
    }
    return FormResponseResult.invalid(-1, "Response wasn't a boolean");
  }
}
