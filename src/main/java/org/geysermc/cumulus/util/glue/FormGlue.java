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
package org.geysermc.cumulus.util.glue;

import java.util.function.Consumer;
import org.geysermc.cumulus.Form;
import org.geysermc.cumulus.form.impl.FormDefinitions;
import org.geysermc.cumulus.form.impl.FormImpl;
import org.geysermc.cumulus.form.util.FormCodec;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.util.FormType;

public abstract class FormGlue<T extends org.geysermc.cumulus.form.Form> implements Form<T> {
  protected final org.geysermc.cumulus.form.util.FormType formType;
  protected T form;
  protected Consumer<String> responseHandler;

  protected FormGlue(org.geysermc.cumulus.form.util.FormType type) {
    this.formType = type;
  }

  public FormType getType() {
    return FormType.values()[formType.ordinal()];
  }

  public String getJsonData() {
    return FormDefinitions.instance()
        .<FormCodec<org.geysermc.cumulus.form.Form, FormResponse>>codecFor(formType)
        .jsonData(form);
  }

  public Consumer<String> getResponseHandler() {
    return responseHandler;
  }

  public void setResponseHandler(Consumer<String> responseHandler) {
    this.responseHandler = responseHandler;
    ((FormImpl<?>) form).rawResponseConsumer(responseHandler);
  }

  public abstract FormResponse parseResponse(String response);

  public boolean isClosed(String response) {
    return response == null || response.isEmpty() || "null".equals(response.trim());
  }

  protected <R extends FormResponse> FormResponseResult<R> deserializeResponse(String response) {
    return FormDefinitions.instance()
        .<FormCodec<org.geysermc.cumulus.form.Form, R>>codecFor(formType)
        .deserializeFormResponse(form, response);
  }

  public T newForm() {
    return form;
  }
}
