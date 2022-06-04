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
import org.geysermc.cumulus.component.impl.ToggleComponentImpl;

/**
 * Toggle component is a component that can only be used in CustomForm. With this component you can
 * let the client toggle an option.
 */
public interface ToggleComponent extends Component {
  @NonNull
  static ToggleComponent of(@NonNull String text, boolean defaultValue) {
    return new ToggleComponentImpl(text, defaultValue);
  }

  @NonNull
  static ToggleComponent of(@NonNull String text) {
    return of(text, false);
  }

  /**
   * Returns whether the default value should be true or false.
   */
  boolean defaultValue();

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method will be replaced by
   * {@link #defaultValue()}.
   */
  @Deprecated
  default boolean getDefaultValue() {
    return defaultValue();
  }
}
