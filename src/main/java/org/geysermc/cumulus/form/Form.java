/*
 * Copyright (c) 2020-2022 GeyserMC. http://geysermc.org
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

package org.geysermc.cumulus.form;

import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.util.FormType;

/**
 * Base class of all Forms. While it can be used it doesn't contain every data you could get when
 * using the specific class of the form type.
 *
 * @since 1.1
 */
public interface Form {
  //todo re-add classes to the org.geysermc.cumulus package, to keep backwards compatability

  /**
   * Returns the form type of this specific instance.
   *
   * @see FormType
   * @since 1.0
   */
  @NonNull FormType getType();

  /**
   * Returns the title of the Form.
   *
   * @since 1.1. In 1.0 it was located in the SimpleForm and CustomForm class
   */
  @NonNull
  String getTitle();

  /**
   * Returns the data that will be sent to the Bedrock client.
   *
   * @since 1.0
   */
  @NonNull String getJsonData();

  /**
   * Calls the handler that was defined to handle the response.
   *
   * @param response the raw response data
   * @throws Exception when something went wrong during the execution.
   * @since 1.1
   */
  void callResponseHandler(@Nullable String response) throws Exception;
  //todo look at respond handler. kinda don't want to call the definition class in the actual form impl class

  /**
   * Returns the handler that will be invoked once the form got a response from the Bedrock client.
   * 
   * @deprecated since 1.1, will be removed in 1.2.
   * Replaced with {@link #callResponseHandler(String)}
   */
  @Deprecated
  @Nullable
  Consumer<String> getResponseHandler();

  /**
   * Sets the handler that will be invoked once the form got a response from the Bedrock client.
   * This handler will get the raw data sent by the Bedrock client.
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
   * @deprecated since 1.1, will be removed in 1.2
   */
  @Deprecated
  @NonNull
  FormResponse parseResponse(@Nullable String response);

  /**
   * Checks if the given data by the Bedrock client is saying that the client closed the form.
   *
   * @param response the raw data given by the Bedrock client
   * @return true if the raw data implies that the Bedrock client closed the form
   * @deprecated since 1.1, will be removed in 1.2
   */
  @Deprecated
  boolean isClosed(@Nullable String response);
}
