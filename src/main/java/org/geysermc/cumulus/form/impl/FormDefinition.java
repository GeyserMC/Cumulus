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

package org.geysermc.cumulus.form.impl;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.util.FormCodec;
import org.geysermc.cumulus.util.FormType;

public abstract class FormDefinition<F extends Form, I extends FormImpl<R>, R extends FormResponse> {
  private final FormCodec<F, R> codec;
  private final FormType formType;
  private final Class<F> formClass;
  private final Class<I> formImplClass;

  protected FormDefinition(
      FormCodec<F, R> codec, FormType formType, Class<F> formClass, Class<I> formImplClass) {

    this.codec = codec;
    this.formType = formType;
    this.formClass = formClass;
    this.formImplClass = formImplClass;
  }

  public final FormCodec<F, R> codec() {
    return codec;
  }

  public void handleFormResponse(F form, @Nullable String responseData) throws Exception {
    if (!callRawResponseConsumer(form, responseData)) {
      FormResponseResult<R> result = codec().deserializeFormResponse(form, responseData);
      callResponseHandler(form, result);
    }
  }

  @SuppressWarnings("unchecked")
  protected boolean callRawResponseConsumer(F form, @Nullable String responseData)
      throws Exception {
    return ((FormImpl<R>) form).callRawResponseConsumer(responseData);
  }

  @SuppressWarnings("unchecked")
  protected void callResponseHandler(F form, FormResponseResult<R> result) throws Exception {
    ((FormImpl<R>) form).callResultHandler(result);
  }

  public final FormType formType() {
    return formType;
  }

  public final Class<F> formClass() {
    return formClass;
  }

  public final Class<I> formImplClass() {
    return formImplClass;
  }
}
