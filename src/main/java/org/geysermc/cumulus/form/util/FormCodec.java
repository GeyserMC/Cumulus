/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.util;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import java.util.function.BiConsumer;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.jspecify.annotations.Nullable;

public interface FormCodec<F extends Form, R extends FormResponse>
    extends JsonDeserializer<F>, JsonSerializer<F> {

  /**
   * @param json
   * @param rawResponseConsumer
   * @return
   */
  F fromJson(String json, @Nullable BiConsumer<F, @Nullable String> rawResponseConsumer);

  /**
   * Serializes the form to data that can be used by the Bedrock client to display the form.
   *
   * @param form the form to serialize
   * @return the serialized form
   */
  String jsonData(F form);

  /**
   * Deserializes the response of the client to a form that has been sent.
   *
   * @param form the form instance that was sent to the client
   * @param responseData the response of the client
   * @return the responseResult from deserializing the response
   */
  FormResponseResult<R> deserializeFormResponse(F form, @Nullable String responseData);
}
