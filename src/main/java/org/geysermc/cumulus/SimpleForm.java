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

package org.geysermc.cumulus;

import java.util.List;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.util.FormBuilder;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.cumulus.util.glue.SimpleFormGlue;

/**
 * @deprecated since 1.1 and will be removed in 2.0. This class will be replaced by
 * {@link org.geysermc.cumulus.form.SimpleForm}.
 */
@Deprecated
public interface SimpleForm extends Form<org.geysermc.cumulus.form.SimpleForm> {

  static Builder builder() {
    return new SimpleFormGlue.Builder();
  }

  static SimpleForm of(String title, String content, List<ButtonComponent> buttons) {
    Builder builder = SimpleForm.builder()
        .title(title)
        .content(content);

    for (ButtonComponent button : buttons) {
      builder.button(button.text(), button.image());
    }

    return builder.build();
  }

  String getTitle();

  String getContent();

  List<ButtonComponent> getButtons();

  @Override
  SimpleFormResponse parseResponse(String response);

  interface Builder extends FormBuilder<Builder, SimpleForm> {
    Builder content(String content);

    Builder button(String text, FormImage.Type type, String data);

    Builder button(String text, FormImage image);

    Builder button(String text);

    // default methods have to stay default for the JVM (:

    default Builder optionalButton(
        String text,
        FormImage.Type type,
        String data,
        boolean shouldAdd) {
      throw new IllegalStateException();
    }

    default Builder optionalButton(String text, FormImage image, boolean shouldAdd) {
      throw new IllegalStateException();
    }

    default Builder optionalButton(String text, boolean shouldAdd) {
      throw new IllegalStateException();
    }
  }
}
