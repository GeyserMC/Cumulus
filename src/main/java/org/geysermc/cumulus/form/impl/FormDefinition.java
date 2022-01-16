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

import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.util.FormCodec;
import org.geysermc.cumulus.util.FormType;

public abstract class FormDefinition<F extends Form, I extends FormImpl<F, R>, R extends FormResponse> {
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

  protected FormCodec<F, R> codec() {
    return codec;
  }

  public FormType formType() {
    return formType;
  }

  public Class<F> formClass() {
    return formClass;
  }

  public Class<I> formImplClass() {
    return formImplClass;
  }
}
