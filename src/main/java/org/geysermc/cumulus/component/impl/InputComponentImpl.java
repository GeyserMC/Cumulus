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

package org.geysermc.cumulus.component.impl;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.cumulus.component.InputComponent;
import org.geysermc.cumulus.component.util.ComponentType;

public final class InputComponentImpl extends Component implements InputComponent {
  private final String placeholder;
  @SerializedName("default")
  private final String defaultText;

  public InputComponentImpl(
      @NonNull String text,
      @NonNull String placeholder,
      @NonNull String defaultText) {
    super(ComponentType.INPUT, text);
    this.placeholder = Objects.requireNonNull(placeholder, "placeholder");
    this.defaultText = Objects.requireNonNull(defaultText, "defaultText");
  }

  @Override
  public @NonNull String placeholder() {
    return placeholder;
  }

  @Override
  public @NonNull String defaultText() {
    return defaultText;
  }
}
