/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component;

import org.geysermc.cumulus.component.impl.InputComponentImpl;

/**
 * Input component is a component that can only be used in CustomForm. With this component you can
 * show an input field where the client can enter something in.
 */
public interface InputComponent extends Component {
  static InputComponent of(String text, String placeholder, String defaultText) {
    return new InputComponentImpl(text, placeholder, defaultText);
  }

  static InputComponent of(String text, String placeholder) {
    return of(text, placeholder, "");
  }

  static InputComponent of(String text) {
    return of(text, "", "");
  }

  /**
   * Returns the text that will be shown as a placeholder in the input component. The text isn't
   * actually placed in the component, but is shown to the client as a suggestion. To actually set
   * text, use {@link #defaultText()} instead.
   *
   * @see #defaultText()
   * @since 1.1
   */
  String placeholder();

  /**
   * Returns the text that will be placed in the input component by default when the component is
   * being shown. For text that is only shown when there is nothing in the input component, use
   * {@link #placeholder()} instead.
   *
   * @see #placeholder()
   * @since 1.1
   */
  String defaultText();
}
