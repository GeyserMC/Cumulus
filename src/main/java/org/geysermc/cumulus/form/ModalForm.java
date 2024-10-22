/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form;

import org.checkerframework.common.returnsreceiver.qual.This;
import org.geysermc.cumulus.form.impl.modal.ModalFormImpl;
import org.geysermc.cumulus.form.util.FormBuilder;
import org.geysermc.cumulus.response.ModalFormResponse;
import org.jspecify.annotations.NullMarked;

/**
 * Represents a ModalForm which can be shown to the client. A ModalForm is the most basic form type.
 * It has a title, description and two buttons. For more information and for code examples look at
 * <a href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
 *
 * @since 1.1
 */
@NullMarked
public interface ModalForm extends Form {
  /**
   * Returns a new ModalForm builder. A more friendly way of creating a form.
   *
   * @since 1.1
   */
  static Builder builder() {
    return new ModalFormImpl.Builder();
  }

  /**
   * Create a SimpleForm with predefined information.
   *
   * @param title the title of the form
   * @param content the description of the form
   * @param button1 the first button of the form
   * @param button2 the second button of the form
   * @return the created ModalForm instance
   * @since 1.1
   */
  static ModalForm of(String title, String content, String button1, String button2) {
    return new ModalFormImpl(title, content, button1, button2);
  }

  /**
   * Returns the description of the Form.
   *
   * @since 1.1
   */
  String content();

  /**
   * Returns the content of the first button.
   *
   * @return the content
   * @since 1.1
   */
  String button1();

  /**
   * Returns the content of the second (last) button.
   *
   * @return the content
   * @since 1.1
   */
  String button2();

  /**
   * An easy way to create a ModalForm. For more information and code examples look at <a
   * href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
   *
   * @since 1.1
   */
  interface Builder extends FormBuilder<Builder, ModalForm, ModalFormResponse> {
    /**
     * Set the description of the form.
     *
     * @param content the description of the form
     * @return the form builder
     * @since 1.1
     */
    @This Builder content(String content);

    /**
     * Set the text of the first button.
     *
     * @param button1 the text of the first button
     * @return the form builder
     * @since 1.1
     */
    @This Builder button1(String button1);

    /**
     * Set the text of the second button.
     *
     * @param button2 the text of the second button
     * @return the form builder
     * @since 1.1
     */
    @This Builder button2(String button2);
  }
}
