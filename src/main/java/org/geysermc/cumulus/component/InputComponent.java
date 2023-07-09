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

package org.geysermc.cumulus.component;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.cumulus.component.impl.InputComponentImpl;

/**
 * Input component is a component that can only be used in CustomForm. With this component you can
 * show an input field where the client can enter something in.
 */
public interface InputComponent extends Component {
  static @NonNull InputComponent of(
      @NonNull String text,
      @NonNull String placeholder,
      @NonNull String defaultText) {
    return new InputComponentImpl(text, placeholder, defaultText);
  }

  static @NonNull InputComponent of(@NonNull String text, @NonNull String placeholder) {
    return of(text, placeholder, "");
  }

  static @NonNull InputComponent of(@NonNull String text) {
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
  @NonNull String placeholder();

  /**
   * Returns the text that will be placed in the input component by default when the component is
   * being shown. For text that is only shown when there is nothing in the input component, use
   * {@link #placeholder()} instead.
   *
   * @see #placeholder()
   * @since 1.1
   */
  @NonNull String defaultText();

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method will be replaced by
   * {@link #placeholder()}.
   */
  @Deprecated
  @NonNull String getPlaceholder();

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method will be replaced by
   * {@link #defaultText()}.
   */
  @Deprecated
  @NonNull String getDefaultText();
}
