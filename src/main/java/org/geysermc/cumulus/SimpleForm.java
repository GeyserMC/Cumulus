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
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.impl.SimpleFormImpl;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.util.FormBuilder;
import org.geysermc.cumulus.util.FormImage;

/**
 * Represents a SimpleForm which can be shown to the client. A SimpleForm is a simple but handy Form
 * type. It is a list of buttons which can have images. For more information and for code examples
 * look at <a href='https://github.com/GeyserMC/Cumulus/wiki'>the wiki</a>.
 *
 * @since 1.0
 */
public interface SimpleForm extends Form {
  /**
   * Returns a new SimpleForm builder. A more friendly way of creating a Form.
   */
  @NonNull
  static Builder builder() {
    return new SimpleFormImpl.Builder();
  }

  /**
   * Create a SimpleForm with predefined information.
   *
   * @param title   the title of the form
   * @param content the description of the form (under title, above the buttons)
   * @param buttons the list of buttons to place in the form
   * @return the created SimpleForm instance
   */
  @NonNull
  static SimpleForm of(
      @NonNull String title,
      @NonNull String content,
      @NonNull List<ButtonComponent> buttons) {
    return new SimpleFormImpl(title, content, buttons);
  }

  /**
   * Returns the title of the Form.
   */
  @NonNull
  String getTitle();

  /**
   * Returns the description of the Form.
   */
  @NonNull
  String getContent();

  /**
   * Returns the list of button components.
   */
  @NonNull
  List<ButtonComponent> getButtons();

  @Override
  @NonNull
  SimpleFormResponse parseResponse(@Nullable String response);

  /**
   * An easy way to create a CustomForm. For more information and code examples look at <a
   * href='https://github.com/GeyserMC/Cumulus/wiki'>the wiki</a>.
   */
  interface Builder extends FormBuilder<Builder, SimpleForm> {
    /**
     * Set the description of the Form.
     *
     * @param content the description of the Form
     * @return the form builder
     */
    @NonNull
    Builder content(@NonNull String content);

    /**
     * Adds a button with image to the Form.
     *
     * @param text text of the button
     * @param type type of image
     * @param data the data for the image type
     * @return the form builder
     */
    @NonNull
    Builder button(@NonNull String text, FormImage.@NonNull Type type, @NonNull String data);

    /**
     * Adds a button with image to the Form.
     *
     * @param text  the text of the button
     * @param image the image
     * @return the form builder
     */
    @NonNull
    Builder button(@NonNull String text, @Nullable FormImage image);

    /**
     * Adds a button to the Form.
     *
     * @param text the text of the button
     * @return the form builder
     */
    @NonNull
    Builder button(@NonNull String text);

    /**
     * Adds a button with image to the Form, but only when shouldAdd is true.
     *
     * @param text      text of the button
     * @param type      type of image
     * @param data      the data for the image type
     * @param shouldAdd if the button should be added
     * @return the form builder
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalButton(
        @NonNull String text,
        FormImage.@NonNull Type type,
        @NonNull String data,
        boolean shouldAdd) {
      if (shouldAdd) {
        return button(text, type, data);
      }
      return this;
    }

    /**
     * Adds a button with image to the Form, but only when shouldAdd is true.
     *
     * @param text      the text of the button
     * @param image     the image
     * @param shouldAdd if the button should be added
     * @return the form builder
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response. 
     */
    @Deprecated
    @NonNull
    default Builder optionalButton(
        @NonNull String text,
        @Nullable FormImage image,
        boolean shouldAdd) {
      if (shouldAdd) {
        return button(text, image);
      }
      return this;
    }

    /**
     * Adds a button to the Form, but only when shouldAdd is true.
     *
     * @param text      the text of the button
     * @param shouldAdd if the button should be added
     * @return the form builder
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalButton(@NonNull String text, boolean shouldAdd) {
      if (shouldAdd) {
        return button(text);
      }
      return this;
    }
  }
}
