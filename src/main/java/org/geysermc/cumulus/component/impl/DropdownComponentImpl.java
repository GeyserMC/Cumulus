/*
 * Copyright (c) 2020-2023 GeyserMC
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
package org.geysermc.cumulus.component.impl;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.cumulus.component.DropdownComponent;
import org.geysermc.cumulus.component.util.ComponentType;

public final class DropdownComponentImpl extends ComponentImpl implements DropdownComponent {
  private final List<String> options;

  @SerializedName("default")
  private final int defaultOption;

  public DropdownComponentImpl(
      @NonNull String text, @NonNull List<String> options, int defaultOption) {
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
  public @NonNull List<String> options() {
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
    public Builder text(@NonNull String text) {
      this.text = Objects.requireNonNull(text, "test");
      return this;
    }

    @Override
    public Builder option(@NonNull String option, boolean isDefault) {
      options.add(Objects.requireNonNull(option, "option"));
      if (isDefault) {
        defaultOption = options.size() - 1;
      }
      return this;
    }

    @Override
    public Builder option(@NonNull String option) {
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
    public @NonNull DropdownComponentImpl build() {
      return new DropdownComponentImpl(text, options, defaultOption);
    }

    @Override
    public @NonNull DropdownComponentImpl translateAndBuild(
        @NonNull Function<String, String> translator) {
      Objects.requireNonNull(translator, "translator cannot be null");
      options.replaceAll(translator::apply);
      return new DropdownComponentImpl(translator.apply(text), options, defaultOption);
    }
  }
}
