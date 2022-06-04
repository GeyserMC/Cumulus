/*
 * Copyright (c) 2020-2022 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Cumulus
 */

package org.geysermc.cumulus.component.impl;

import com.google.common.base.Preconditions;
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
      @NonNull String text,
      @NonNull List<String> options,
      int defaultOption) {
    super(ComponentType.DROPDOWN, text);
    Preconditions.checkNotNull(options, "options");
    Preconditions.checkArgument(defaultOption >= 0, "defaultOption");

    this.options = Collections.unmodifiableList(options);
    //todo should we allow this?
    if (defaultOption >= options.size()) {
      defaultOption = 0;
    }
    this.defaultOption = defaultOption;
  }

  @Override
  @NonNull
  public List<String> options() {
    return options;
  }

  @Override
  @NonNegative
  public int defaultOption() {
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
      Preconditions.checkArgument(defaultOption >= 0, "defaultOption");
      this.defaultOption = defaultOption;
      return this;
    }

    @Override
    @NonNull
    public DropdownComponentImpl build() {
      return new DropdownComponentImpl(text, options, defaultOption);
    }

    @Override
    @NonNull
    public DropdownComponentImpl translateAndBuild(@NonNull Function<String, String> translator) {
      Preconditions.checkNotNull(translator, "traslator");
      options.replaceAll(translator::apply);
      return new DropdownComponentImpl(translator.apply(text), options, defaultOption);
    }
  }
}
