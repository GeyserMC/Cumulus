/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component.impl;

import org.geysermc.cumulus.component.LabelComponent;
import org.geysermc.cumulus.component.util.ComponentType;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class LabelComponentImpl extends ComponentImpl implements LabelComponent {
  public LabelComponentImpl(String text) {
    super(ComponentType.LABEL, text);
  }
}
