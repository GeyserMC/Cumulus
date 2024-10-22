/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form;

import java.util.List;
import java.util.function.Consumer;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.form.impl.simple.SimpleFormImpl;
import org.geysermc.cumulus.form.util.FormBuilder;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.util.FormImage;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Represents a SimpleForm which can be shown to the client. A SimpleForm is a simple but handy Form
 * type. It is a list of buttons which can have images. For more information and for code examples
 * look at <a href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
 *
 * @since 1.1
 */
@NullMarked
public interface SimpleForm extends Form {
  /**
   * Returns a new SimpleForm builder. A more friendly way of creating a Form.
   *
   * @since 1.1
   */
  static Builder builder() {
    return new SimpleFormImpl.Builder();
  }

  /**
   * Create a SimpleForm with predefined information.
   *
   * @param title the title of the form
   * @param content the description of the form (under title, above the buttons)
   * @param buttons the list of buttons to place in the form
   * @return the created SimpleForm instance
   * @since 1.1
   */
  static SimpleForm of(String title, String content, List<ButtonComponent> buttons) {
    return new SimpleFormImpl(title, content, buttons);
  }

  /**
   * Returns the description of the Form.
   *
   * @since 1.1
   */
  String content();

  /**
   * Returns all the components of the form. This includes optional components, which will be null
   * when they are not present.
   *
   * @since 1.1
   */
  List<@Nullable ButtonComponent> buttons();

  /**
   * An easy way to create a SimpleForm. For more information and code examples look at <a
   * href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
   *
   * @since 1.1
   */
  interface Builder extends FormBuilder<Builder, SimpleForm, SimpleFormResponse> {
    /**
     * Set the description of the Form.
     *
     * @param content the description of the Form
     * @return the form builder
     * @since 1.1
     */
    @This Builder content(String content);

    /**
     * Adds a button directly to the form.
     *
     * @param button the button to add
     * @return the form builder
     * @since 1.1.1
     */
    @This Builder button(ButtonComponent button);

    /**
     * Adds a button with callback directly to the form.
     *
     * @param button the button to add
     * @param callback the handler when the button is clicked
     * @return the form builder
     * @since 2.0
     */
    @This Builder button(ButtonComponent button, Consumer<SimpleFormResponse> callback);

    /**
     * Adds a button with image to the Form.
     *
     * @param text text of the button
     * @param type type of image
     * @param data the data for the image type
     * @return the form builder
     * @since 1.1
     */
    @This Builder button(String text, FormImage.Type type, String data);

    /**
     * Adds a button with image and callback to the Form.
     *
     * @param text text of the button
     * @param type type of image
     * @param data the data for the image type
     * @param callback the handler when the button is clicked
     * @return the form builder
     * @since 2.0
     */
    @This Builder button(
        String text, FormImage.Type type, String data, Consumer<SimpleFormResponse> callback);

    /**
     * Adds a button with image to the Form.
     *
     * @param text the text of the button
     * @param image the image
     * @return the form builder
     * @since 1.1
     */
    @This Builder button(String text, @Nullable FormImage image);

    /**
     * Adds a button with image and callback to the Form.
     *
     * @param text the text of the button
     * @param image the image
     * @param callback the handler when the button is clicked
     * @return the form builder
     * @since 2.0
     */
    @This Builder button(String text, @Nullable FormImage image, Consumer<SimpleFormResponse> callback);

    /**
     * Adds a button to the Form.
     *
     * @param text the text of the button
     * @return the form builder
     * @since 1.1
     */
    @This Builder button(String text);

    /**
     * Adds a button with callback to the Form.
     *
     * @param text the text of the button
     * @param callback the handler when the button is clicked
     * @return the form builder
     * @since 2.0
     */
    @This Builder button(String text, Consumer<SimpleFormResponse> callback);

    /**
     * Adds a button with image to the Form, but only when shouldAdd is true.
     *
     * @param text text of the button
     * @param type type of image
     * @param data the data for the image type
     * @param shouldAdd if the button should be added
     * @return the form builder
     * @since 1.1
     */
    @This Builder optionalButton(String text, FormImage.Type type, String data, boolean shouldAdd);

    /**
     * Adds a button with image to the Form, but only when shouldAdd is true.
     *
     * @param text the text of the button
     * @param image the image
     * @param shouldAdd if the button should be added
     * @return the form builder
     * @since 1.1
     */
    @This Builder optionalButton(String text, @Nullable FormImage image, boolean shouldAdd);

    /**
     * Adds a button to the Form, but only when shouldAdd is true.
     *
     * @param text the text of the button
     * @param shouldAdd if the button should be added
     * @return the form builder
     * @since 1.1
     */
    @This Builder optionalButton(String text, boolean shouldAdd);
  }
}
