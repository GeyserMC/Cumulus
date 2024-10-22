/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component.impl;

import java.util.Objects;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.util.ComponentType;
import org.jspecify.annotations.NullMarked;

@NullMarked
public abstract class ComponentImpl implements Component {
  private final ComponentType type;
  private final String text;

  ComponentImpl(ComponentType type, String text) {
    this.type = Objects.requireNonNull(type, "type");
    this.text = Objects.requireNonNull(text, "text");
  }

  @Override
  public ComponentType type() {
    return type;
  }

  @Override
  public String text() {
    return text;
  }
}
