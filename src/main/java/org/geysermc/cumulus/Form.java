/*
 * Copyright (c) 2020-2023 GeyserMC
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
package org.geysermc.cumulus;

import java.util.function.Consumer;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.util.FormType;

/**
 * @deprecated since 1.1 and will be removed in 2.0. This class will be replaced by {@link
 *     org.geysermc.cumulus.form.Form}.
 */
@Deprecated
public interface Form<T extends org.geysermc.cumulus.form.Form> {

  /**
   * @see org.geysermc.cumulus.form.impl.FormDefinitions#typeFromImplClass(Class)
   *     FormDefinitions.typeFromImplClass(Class)
   * @deprecated since 1.1 and will be removed in 2.0. While this method does not have a replacement
   *     in the API, an internal method can be used.
   */
  @Deprecated
  FormType getType();

  /**
   * @see org.geysermc.cumulus.form.util.FormCodec#jsonData(org.geysermc.cumulus.form.Form)
   *     FormCodec.jsonData(Form)
   * @deprecated since 1.1 and will be removed in 2.0. While this method does not have a replacement
   *     in the API, an internal method can be used.
   */
  @Deprecated
  String getJsonData();

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This system has been replaced in favor of
   *     resultHandlers. More info can be found <a
   *     href="https://github.com/GeyserMC/Cumulus/wiki/Updating-from-1.0-to-1.1-(and-2.0)#response-handling-changes">here</a>.
   */
  @Deprecated
  Consumer<String> getResponseHandler();

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This system has been replaced in favor of
   *     resultHandlers. More info can be found <a
   *     href="https://github.com/GeyserMC/Cumulus/wiki/Updating-from-1.0-to-1.1-(and-2.0)#response-handling-changes">here</a>.
   */
  @Deprecated
  void setResponseHandler(Consumer<String> responseHandler);

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This system has been replaced in favor of
   *     resultHandlers. More info can be found <a
   *     href="https://github.com/GeyserMC/Cumulus/wiki/Updating-from-1.0-to-1.1-(and-2.0)#response-handling-changes">here</a>.
   */
  @Deprecated
  FormResponse parseResponse(String response);

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This system has been replaced in favor of
   *     resultHandlers. More info can be found <a
   *     href="https://github.com/GeyserMC/Cumulus/wiki/Updating-from-1.0-to-1.1-(and-2.0)#response-handling-changes">here</a>.
   */
  @Deprecated
  boolean isClosed(String response);

  T newForm();
}
