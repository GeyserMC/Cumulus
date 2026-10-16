/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component;

import org.geysermc.cumulus.component.impl.LabelComponentImpl;

/**
 * The label component is a component that can only be used in CustomForm. This component is just a
 * piece of text that is being shown to the client.
 */
public interface LabelComponent extends Component {
  static LabelComponent of(String text) {
    return new LabelComponentImpl(text);
  }
}
