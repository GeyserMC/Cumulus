/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component.impl;

import java.util.Objects;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.util.FormImage;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public final class ButtonComponentImpl implements ButtonComponent {
  private final String text;
  private final @Nullable FormImage image;

  public ButtonComponentImpl(String text, @Nullable FormImage image) {
    this.text = Objects.requireNonNull(text, "text");
    this.image = image;
  }

  @Override
  public String text() {
    return text;
  }

  @Override
  public @Nullable FormImage image() {
    return image;
  }
}
