/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component;

import org.geysermc.cumulus.component.impl.LabelComponentImpl;
import org.jspecify.annotations.NullMarked;

/**
 * The label component is a component that can only be used in CustomForm. This component is just a
 * piece of text that is being shown to the client.
 *
 * @since 1.0
 */
@NullMarked
public interface LabelComponent extends Component {
  /**
   * Create an instance of the label component.
   *
   * @param text the text to be shown
   * @return the created instance
   */
  static LabelComponent of(String text) {
    return new LabelComponentImpl(text);
  }
}
