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
package org.geysermc.cumulus.util.glue;

import java.util.List;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.form.impl.simple.SimpleFormImpl;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.impl.SimpleFormResponseImpl;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.response.result.ResultType;
import org.geysermc.cumulus.response.result.ValidFormResponseResult;
import org.geysermc.cumulus.util.FormImage;

public class SimpleFormGlue extends FormGlue<org.geysermc.cumulus.form.SimpleForm>
    implements SimpleForm {

  private SimpleFormGlue() {
    super(FormType.SIMPLE_FORM);
  }

  public String getTitle() {
    return form.title();
  }

  public String getContent() {
    return form.content();
  }

  public List<ButtonComponent> getButtons() {
    return form.buttons();
  }

  @Override
  public SimpleFormResponse parseResponse(String response) {
    FormResponseResult<SimpleFormResponse> result = deserializeResponse(response);
    if (result.isValid()) {
      return ((ValidFormResponseResult<SimpleFormResponse>) result).response();
    }
    return new SimpleFormResponseImpl(result.isInvalid() ? ResultType.INVALID : ResultType.CLOSED);
  }

  public static class Builder
      extends FormBuilderGlue<
          SimpleForm.Builder,
          SimpleForm,
          org.geysermc.cumulus.form.SimpleForm,
          org.geysermc.cumulus.form.SimpleForm.Builder>
      implements SimpleForm.Builder {

    public Builder() {
      super(org.geysermc.cumulus.form.SimpleForm.builder());
    }

    public SimpleForm.Builder content(String content) {
      builder.content(content);
      return this;
    }

    public SimpleForm.Builder button(String text, FormImage.Type type, String data) {
      builder.button(text, type, data);
      return this;
    }

    public SimpleForm.Builder button(String text, FormImage image) {
      builder.button(text, image);
      return this;
    }

    public SimpleForm.Builder button(String text) {
      builder.button(text);
      return this;
    }

    public SimpleForm.Builder optionalButton(
        String text, FormImage.Type type, String data, boolean shouldAdd) {
      builder.optionalButton(text, type, data, shouldAdd);
      return this;
    }

    public SimpleForm.Builder optionalButton(String text, FormImage image, boolean shouldAdd) {
      builder.optionalButton(text, image, shouldAdd);
      return this;
    }

    public SimpleForm.Builder optionalButton(String text, boolean shouldAdd) {
      builder.optionalButton(text, shouldAdd);
      return this;
    }

    @Override
    public SimpleForm build() {
      SimpleFormGlue oldForm = new SimpleFormGlue();
      oldForm.responseHandler =
          (response) -> {
            if (biResponseHandler != null) {
              biResponseHandler.accept(oldForm, response);
            }
            if (responseHandler != null) {
              responseHandler.accept(response);
            }
          };

      SimpleFormImpl newForm = (SimpleFormImpl) builder.build();
      newForm.rawResponseConsumer(oldForm.responseHandler);
      oldForm.form = newForm;

      return oldForm;
    }
  }
}
