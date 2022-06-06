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

package org.geysermc.cumulus.util.glue;

import org.geysermc.cumulus.ModalForm;
import org.geysermc.cumulus.form.impl.modal.ModalFormImpl;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.response.ModalFormResponse;
import org.geysermc.cumulus.response.impl.ModalFormResponseImpl;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.response.result.ResultType;
import org.geysermc.cumulus.response.result.ValidFormResponseResult;

public class ModalFormGlue extends FormGlue<org.geysermc.cumulus.form.ModalForm>
    implements ModalForm {

  private ModalFormGlue() {
    super(FormType.MODAL_FORM);
  }

  @Override
  public ModalFormResponse parseResponse(String response) {
    FormResponseResult<ModalFormResponse> result = deserializeResponse(response);
    if (result.isValid()) {
      return ((ValidFormResponseResult<ModalFormResponse>) result).response();
    }
    return new ModalFormResponseImpl(result.isInvalid() ? ResultType.INVALID : ResultType.CLOSED);
  }

  public static class Builder extends FormBuilderGlue<
      ModalForm.Builder,
      ModalForm,
      org.geysermc.cumulus.form.ModalForm,
      org.geysermc.cumulus.form.ModalForm.Builder> implements ModalForm.Builder {

    public Builder() {
      super(org.geysermc.cumulus.form.ModalForm.builder());
    }

    public Builder content(String content) {
      builder.content(content);
      return this;
    }

    public Builder button1(String button1) {
      builder.button1(button1);
      return this;
    }

    public Builder optionalButton1(String button1, boolean shouldAdd) {
      // doesn't matter since the button will be showed anyway, either empty or with the text.
      if (shouldAdd) builder.button1(button1);
      return this;
    }

    public Builder button2(String button2) {
      builder.button2(button2);
      return this;
    }

    public Builder optionalButton2(String button2, boolean shouldAdd) {
      // doesn't matter since the button will be showed anyway, either empty or with the text.
      if (shouldAdd) builder.button2(button2);
      return this;
    }

    @Override
    public ModalForm build() {
      ModalFormGlue oldForm = new ModalFormGlue();
      oldForm.responseHandler = (response) -> {
        if (biResponseHandler != null) {
          biResponseHandler.accept(oldForm, response);
        }
        if (responseHandler != null) {
          responseHandler.accept(response);
        }
      };

      ModalFormImpl newForm = (ModalFormImpl) builder.build();
      newForm.rawResponseConsumer(oldForm.responseHandler);
      oldForm.form = newForm;

      return oldForm;
    }
  }
}
