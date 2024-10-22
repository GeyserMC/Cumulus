/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.util.impl;

import java.util.Objects;
import org.geysermc.cumulus.util.FormImage;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class FormImageImpl implements FormImage {
  private final Type type;
  private final String data;

  public FormImageImpl(Type type, String data) {
    this.type = Objects.requireNonNull(type, "type");
    this.data = Objects.requireNonNull(data, "data");
  }

  @Override
  public Type type() {
    return type;
  }

  @Override
  public String data() {
    return data;
  }
}
