/*
 * Copyright (c) 2020-2021 GeyserMC. http://geysermc.org
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

package org.geysermc.cumulus;

import java.util.List;
import org.checkerframework.checker.index.qual.Positive;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.DropdownComponent;
import org.geysermc.cumulus.component.StepSliderComponent;
import org.geysermc.cumulus.impl.CustomFormImpl;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.util.FormBuilder;
import org.geysermc.cumulus.util.FormImage;

/**
 * Represents a CustomForm which can be shown to the client. A CustomForm is the most customisable
 * form type, you can add all component types except for buttons. For more information and for code
 * examples look at <a href='https://github.com/GeyserMC/Cumulus/wiki'>the wiki</a>.
 *
 * @since 1.0
 */
public interface CustomForm extends Form {
  /**
   * Returns a new CustomForm builder. A more friendly way of creating a Form.
   */
  @NonNull
  static Builder builder() {
    return new CustomFormImpl.Builder();
  }

  /**
   * Create a CustomForm with predefined information.
   *
   * @param title   the title of the form
   * @param icon    the icon of the form (optional)
   * @param content the list of components in this form
   * @return the created CustomForm instance
   */
  @NonNull
  static CustomForm of(
      @NonNull String title,
      @Nullable FormImage icon,
      @NonNull List<Component> content
  ) {
    return new CustomFormImpl(title, icon, content);
  }

  /**
   * Returns the title of the Form.
   */
  @NonNull
  String getTitle();

  /**
   * Returns the optional icon of the form. The icon can only be seen in the servers settings.
   */
  @Nullable
  FormImage getIcon();

  /**
   * Returns the list of components of the form.
   */
  @NonNull
  List<Component> getContent();

  @Override
  @NonNull
  CustomFormResponse parseResponse(@Nullable String response);

  /**
   * An easy way to create a CustomForm. For more information and code examples look at <a
   * href='https://github.com/GeyserMC/Cumulus/wiki'>the wiki</a>.
   */
  interface Builder extends FormBuilder<Builder, CustomForm> {
    @NonNull
    Builder icon(FormImage.@NonNull Type type, @NonNull String data);

    @NonNull
    Builder iconPath(@NonNull String path);

    @NonNull
    Builder iconUrl(@NonNull String url);

    @NonNull
    Builder component(@NonNull Component component);

    @NonNull
    Builder dropdown(DropdownComponent.@NonNull Builder dropdownBuilder);

    @NonNull
    Builder dropdown(@NonNull String text, int defaultOption, String... options);

    @NonNull
    Builder dropdown(@NonNull String text, String... options);

    @NonNull
    Builder input(
        @NonNull String text,
        @NonNull String placeholder,
        @NonNull String defaultText
    );

    @NonNull
    Builder input(@NonNull String text, @NonNull String placeholder);

    @NonNull
    Builder input(@NonNull String text);

    @NonNull
    Builder label(@NonNull String text);

    @NonNull
    Builder slider(
        @NonNull String text,
        float min,
        float max,
        @Positive int step,
        float defaultValue
    );

    @NonNull
    Builder slider(@NonNull String text, float min, float max, @Positive int step);

    @NonNull
    Builder slider(@NonNull String text, float min, float max, float defaultValue);

    @NonNull
    Builder slider(@NonNull String text, float min, float max);

    @NonNull
    Builder stepSlider(StepSliderComponent.@NonNull Builder stepSliderBuilder);

    @NonNull
    Builder stepSlider(@NonNull String text, int defaultStep, String... steps);

    @NonNull
    Builder stepSlider(@NonNull String text, String... steps);

    @NonNull
    Builder toggle(@NonNull String text, boolean defaultValue);

    @NonNull
    Builder toggle(@NonNull String text);
  }
}
