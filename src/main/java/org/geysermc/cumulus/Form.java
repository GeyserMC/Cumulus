/*
 * Copyright (c) 2020-2021 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Cumulus
 */

package org.geysermc.cumulus;

import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.util.FormType;

/**
 * Base class of all Forms. While it can be used it doesn't contain every data you could get when
 * using the specific class of the form type.
 *
 * @since 1.0
 */
public interface Form {
  /**
   * Returns the form type of this specific instance.
   *
   * @see FormType
   */
  @NonNull
  FormType getType();

  /**
   * Returns the data that will be sent to the Bedrock client.
   */
  @NonNull
  String getJsonData();

  /**
   * Returns the handler that will be invoked once the form got a response from the Bedrock client.
   */
  @Nullable
  Consumer<String> getResponseHandler();

  /**
   * Sets the handler that will be invoked once the form got a response from the Bedrock client.
   * This handler will get the raw data sent by the Bedrock client. Use {@link
   * #parseResponse(String)} after receiving a response for getting a more friendly class to handle
   * the response.
   *
   * @param responseHandler the response handler
   */
  void setResponseHandler(@NonNull Consumer<String> responseHandler);

  /**
   * Parses the method into something provided by the form implementation, which will make the data
   * given by the Bedrock client easier to handle.
   *
   * @param response the raw data given by the Bedrock client
   * @return the data in an easy-to-handle class
   */
  @NonNull
  FormResponse parseResponse(@Nullable String response);

  /**
   * Checks if the given data by the Bedrock client is saying that the client closed the form.
   *
   * @param response the raw data given by the Bedrock client
   * @return true if the raw data implies that the Bedrock client closed the form
   */
  boolean isClosed(@Nullable String response);
}
