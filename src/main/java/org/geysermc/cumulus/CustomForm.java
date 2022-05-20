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

package org.geysermc.cumulus;

import java.util.List;
import org.checkerframework.checker.index.qual.NonNegative;
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
      @NonNull List<Component> content) {
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

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalComponent(@NonNull Component component, boolean shouldAdd) {
      if (shouldAdd) {
        return component(component);
      }
      return this;
    }

    @NonNull
    Builder dropdown(DropdownComponent.@NonNull Builder dropdownBuilder);

    @NonNull
    Builder dropdown(
        @NonNull String text,
        @NonNegative int defaultOption,
        @NonNull String... options);

    @NonNull
    Builder dropdown(@NonNull String text, @NonNull String... options);

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalDropdown(
        boolean shouldAdd,
        @NonNull String text,
        @NonNegative int defaultOption,
        @NonNull String... options) {
      if (shouldAdd) {
        return dropdown(text, defaultOption, options);
      }
      return this;
    }

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalDropdown(
        boolean shouldAdd,
        @NonNull String text,
        @NonNull String... options) {
      if (shouldAdd) {
        return dropdown(text, options);
      }
      return this;
    }

    @NonNull
    Builder input(
        @NonNull String text,
        @NonNull String placeholder,
        @NonNull String defaultText);

    @NonNull
    Builder input(@NonNull String text, @NonNull String placeholder);

    @NonNull
    Builder input(@NonNull String text);

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalInput(
        @NonNull String text,
        @NonNull String placeholder,
        @NonNull String defaultText,
        boolean shouldAdd) {
      if (shouldAdd) {
        return input(text, placeholder, defaultText);
      }
      return this;
    }

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalInput(
        @NonNull String text,
        @NonNull String placeholder,
        boolean shouldAdd) {
      if (shouldAdd) {
        return input(text, placeholder);
      }
      return this;
    }

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalInput(@NonNull String text, boolean shouldAdd) {
      if (shouldAdd) {
        return input(text);
      }
      return this;
    }

    @NonNull
    Builder label(@NonNull String text);

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalLabel(@NonNull String text, boolean shouldAdd) {
      if (shouldAdd) {
        return label(text);
      }
      return this;
    }

    @NonNull
    Builder slider(
        @NonNull String text,
        float min,
        float max,
        @Positive int step,
        float defaultValue);

    @NonNull
    Builder slider(@NonNull String text, float min, float max, @Positive int step);

    @NonNull
    Builder slider(@NonNull String text, float min, float max, float defaultValue);

    @NonNull
    Builder slider(@NonNull String text, float min, float max);

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalSlider(
        @NonNull String text,
        float min,
        float max,
        @Positive int step,
        float defaultValue,
        boolean shouldAdd) {
      if (shouldAdd) {
        return slider(text, min, max, step, defaultValue);
      }
      return this;
    }

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalSlider(
        @NonNull String text,
        float min,
        float max,
        @Positive int step,
        boolean shouldAdd) {
      if (shouldAdd) {
        return slider(text, min, max, step);
      }
      return this;
    }

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalSlider(
        @NonNull String text,
        float min,
        float max,
        float defaultValue,
        boolean shouldAdd) {
      if (shouldAdd) {
        return slider(text, min, max, defaultValue);
      }
      return this;
    }

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalSlider(@NonNull String text, float min, float max, boolean shouldAdd) {
      if (shouldAdd) {
        return slider(text, min, max);
      }
      return this;
    }

    @NonNull
    Builder stepSlider(StepSliderComponent.@NonNull Builder stepSliderBuilder);

    @NonNull
    Builder stepSlider(
        @NonNull String text,
        @NonNegative int defaultStep,
        @NonNull String... steps);

    @NonNull
    Builder stepSlider(@NonNull String text, @NonNull String... steps);

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalStepSlider(
        boolean shouldAdd,
        @NonNull String text,
        @NonNegative int defaultStep,
        @NonNull String... steps) {
      if (shouldAdd) {
        return stepSlider(text, defaultStep, steps);
      }
      return this;
    }

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalStepSlider(
        boolean shouldAdd,
        @NonNull String text,
        @NonNull String... steps) {
      if (shouldAdd) {
        return stepSlider(text, steps);
      }
      return this;
    }

    @NonNull
    Builder toggle(@NonNull String text, boolean defaultValue);

    @NonNull
    Builder toggle(@NonNull String text);

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalToggle(@NonNull String text, boolean defaultValue, boolean shouldAdd) {
      if (shouldAdd) {
        return toggle(text, defaultValue);
      }
      return this;
    }

    /**
     * @deprecated be aware, this method will behave differently in 1.1. The current behaviour is
     * that the method only does something when 'shouldAdd' is true, meaning that you have to keep
     * track of the value of 'shouldAdd' when you read the response. The new behaviour is that the
     * component will be added to the form, but only displayed to the client when 'shouldAdd' is
     * true. This makes it easier to handle the response.
     */
    @Deprecated
    @NonNull
    default Builder optionalToggle(@NonNull String text, boolean shouldAdd) {
      if (shouldAdd) {
        return toggle(text);
      }
      return this;
    }
  }
}
