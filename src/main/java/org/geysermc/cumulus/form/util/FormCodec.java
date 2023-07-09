/*
 * Copyright (c) 2020-2022 GeyserMC
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
package org.geysermc.cumulus.form.util;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import java.util.function.BiConsumer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public interface FormCodec<F extends Form, R extends FormResponse>
    extends JsonDeserializer<F>, JsonSerializer<F> {

  /**
   * @param json
   * @param rawResponseConsumer
   * @return
   */
  F fromJson(@NonNull String json, @Nullable BiConsumer<F, @Nullable String> rawResponseConsumer);

  /**
   * Serializes the form to data that can be used by the Bedrock client to display the form.
   *
   * @param form the form to serialize
   * @return the serialized form
   */
  String jsonData(@NonNull F form);

  /**
   * Deserializes the response of the client to a form that has been sent.
   *
   * @param form the form instance that was sent to the client
   * @param responseData the response of the client
   * @return the responseResult from deserializing the response
   */
  FormResponseResult<R> deserializeFormResponse(@NonNull F form, @Nullable String responseData);
}
