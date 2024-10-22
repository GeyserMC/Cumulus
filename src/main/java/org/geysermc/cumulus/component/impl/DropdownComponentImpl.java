/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component.impl;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.checkerframework.checker.index.qual.NonNegative;
import org.geysermc.cumulus.component.DropdownComponent;
import org.geysermc.cumulus.component.util.ComponentType;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DropdownComponentImpl extends ComponentImpl implements DropdownComponent {
  private final List<String> options;

  @SerializedName("default")
  private final int defaultOption;

  public DropdownComponentImpl(String text, List<String> options, int defaultOption) {
    super(ComponentType.DROPDOWN, text);
    Objects.requireNonNull(options);
    if (defaultOption < 0) throw new IllegalArgumentException("defaultOption cannot be negative");

    this.options = Collections.unmodifiableList(options);
    // todo should we allow this?
    if (defaultOption >= options.size()) {
      defaultOption = 0;
    }
    this.defaultOption = defaultOption;
  }

  @Override
  public List<String> options() {
    return options;
  }

  @Override
  public @NonNegative int defaultOption() {
    return defaultOption;
  }

  public static class Builder implements DropdownComponent.Builder {
    private final List<String> options = new ArrayList<>();
    private String text = "";
    private int defaultOption = 0;

    @Override
    public Builder text(String text) {
      this.text = Objects.requireNonNull(text, "test");
      return this;
    }

    @Override
    public Builder option(String option, boolean isDefault) {
      options.add(Objects.requireNonNull(option, "option"));
      if (isDefault) {
        defaultOption = options.size() - 1;
      }
      return this;
    }

    @Override
    public Builder option(String option) {
      return option(option, false);
    }

    @Override
    public Builder defaultOption(int defaultOption) {
      if (defaultOption < 0) throw new IllegalArgumentException("defaultOption cannot be negative");
      if (defaultOption >= options.size()) {
        throw new IllegalArgumentException("defaultOption is out of bounds");
      }
      this.defaultOption = defaultOption;
      return this;
    }

    @Override
    public DropdownComponentImpl build() {
      return new DropdownComponentImpl(text, options, defaultOption);
    }

    @Override
    public DropdownComponentImpl translateAndBuild(Function<String, String> translator) {
      Objects.requireNonNull(translator, "translator cannot be null");
      options.replaceAll(translator::apply);
      return new DropdownComponentImpl(translator.apply(text), options, defaultOption);
    }
  }
}
