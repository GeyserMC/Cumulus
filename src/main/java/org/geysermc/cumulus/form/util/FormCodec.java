/*
 * Copyright (c) 2020-2024 GeyserMC
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
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * The base form codec, responsible for encoding and decoding form requests and responses.
 *
 * @param <F> the form (request) type
 * @param <R> the form response type
 * @since 1.1
 */
@NullMarked
public interface FormCodec<F extends Form, R extends FormResponse>
    extends JsonDeserializer<F>, JsonSerializer<F> {
  /**
   * Parse the form request data into {@link F}
   *
   * @param json the json containing the form request data
   * @param rawResponseConsumer a consumer for when the response is received
   * @return the parsed form
   * @since 1.1
   */
  F fromJson(String json, BiConsumer<F, @Nullable String> rawResponseConsumer);

  /**
   * Serializes the form to data that can be used by the Bedrock client to display the form.
   *
   * @param form the form to serialize
   * @return the serialized form
   * @since 1.1
   */
  String jsonData(F form);

  /**
   * Deserializes the response of the client to a form that has been sent. A null responseData
   * indicates that the client has closed the form.
   *
   * @param form the form instance that was sent to the client
   * @param responseData the response of the client
   * @return the responseResult from deserializing the response
   * @since 1.1
   */
  FormResponseResult<R> deserializeFormResponse(F form, @Nullable String responseData);
}
