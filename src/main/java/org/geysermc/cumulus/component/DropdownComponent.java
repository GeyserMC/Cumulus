/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component;

import java.util.List;
import java.util.function.Function;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.geysermc.cumulus.component.impl.DropdownComponentImpl;

/**
 * Dropdown component is a component that can only be used in CustomForm. With this component you
 * can choose one item from the given options in a dropdown.
 */
public interface DropdownComponent extends Component {
  static DropdownComponent of(String text, List<String> options, @NonNegative int defaultOption) {
    return new DropdownComponentImpl(text, options, defaultOption);
  }

  // todo should these 'of' methods be removed in favor of the builders?

  static Builder builder() {
    return new DropdownComponentImpl.Builder();
  }

  static Builder builder(String text) {
    return builder().text(text);
  }

  /**
   * Returns the list of options that will be shown in the dropdown.
   *
   * @since 1.1
   */
  List<String> options();

  /**
   * Returns the index of the option that is selected by default.
   *
   * @since 1.1
   */
  @NonNegative int defaultOption();

  interface Builder {
    /**
     * Sets the text that will be shown in the component.
     *
     * @param text the text to show
     */
    @This Builder text(String text);

    /**
     * Adds an option to the list of options.
     *
     * @param option the text to show in the dropdown entry
     * @param isDefault if this should become the default option
     */
    @This Builder option(String option, boolean isDefault);

    /**
     * Adds an option to the list of options. This option won't become the default option, unless
     * {@link #defaultOption(int)} is called after this.
     *
     * @param option the text to show in the dropdown entry
     */
    @This Builder option(String option);

    /**
     * Sets the default option of this dropdown.
     *
     * @param defaultOption the index of the option that should become the default option.
     * @throws IllegalArgumentException when the index of the default option is out of bounds
     */
    @This Builder defaultOption(@NonNegative int defaultOption) throws IllegalArgumentException;

    /** Returns the created dropdown from the given options. */
    DropdownComponent build();

    /**
     * Translates everything given to this builder using the provided translation function, and
     * returns the created dropdown after that.
     *
     * @param translator the translation function
     */
    DropdownComponent translateAndBuild(Function<String, String> translator);
  }
}
