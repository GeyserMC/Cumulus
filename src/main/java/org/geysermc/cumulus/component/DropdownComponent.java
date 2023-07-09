/*
 * Copyright (c) 2020-2022 GeyserMC
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
package org.geysermc.cumulus.component;

import java.util.List;
import java.util.function.Function;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.geysermc.cumulus.component.impl.DropdownComponentImpl;

/**
 * Dropdown component is a component that can only be used in CustomForm. With this component you
 * can choose one item from the given options in a dropdown.
 */
public interface DropdownComponent extends Component {
  static @NonNull DropdownComponent of(
      @NonNull String text, @NonNull List<String> options, @NonNegative int defaultOption) {
    return new DropdownComponentImpl(text, options, defaultOption);
  }

  // todo should these 'of' methods be removed in favor of the builders?

  static @NonNull Builder builder() {
    return new DropdownComponentImpl.Builder();
  }

  static @NonNull Builder builder(@NonNull String text) {
    return builder().text(text);
  }

  /**
   * Returns the list of options that will be shown in the dropdown.
   *
   * @since 1.1
   */
  @NonNull List<String> options();

  /**
   * Returns the index of the option that is selected by default.
   *
   * @since 1.1
   */
  @NonNegative int defaultOption();

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method will be replaced by {@link
   *     #options()}.
   */
  @Deprecated
  @NonNull List<String> getOptions();

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method will be replaced by {@link
   *     #defaultOption()}.
   */
  @Deprecated
  @NonNegative int getDefaultOption();

  interface Builder {
    /**
     * Sets the text that will be shown in the component.
     *
     * @param text the text to show
     */
    @This Builder text(@NonNull String text);

    /**
     * Adds an option to the list of options.
     *
     * @param option the text to show in the dropdown entry
     * @param isDefault if this should become the default option
     */
    @This Builder option(@NonNull String option, boolean isDefault);

    /**
     * Adds an option to the list of options. This option won't become the default option, unless
     * {@link #defaultOption(int)} is called after this.
     *
     * @param option the text to show in the dropdown entry
     */
    @This Builder option(@NonNull String option);

    /**
     * Sets the default option of this dropdown.
     *
     * @param defaultOption the index of the option that should become the default option.
     * @throws IllegalArgumentException when the index of the default option is out of bounds
     */
    @This Builder defaultOption(@NonNegative int defaultOption) throws IllegalArgumentException;

    /** Returns the created dropdown from the given options. */
    @NonNull DropdownComponent build();

    /**
     * Translates everything given to this builder using the provided translation function, and
     * returns the created dropdown after that.
     *
     * @param translator the translation function
     */
    @NonNull DropdownComponent translateAndBuild(@NonNull Function<String, String> translator);
  }
}
