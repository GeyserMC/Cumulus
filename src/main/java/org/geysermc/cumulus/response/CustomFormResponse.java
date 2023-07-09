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
package org.geysermc.cumulus.response;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import java.util.List;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.index.qual.Positive;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.value.qual.IntRange;
import org.geysermc.cumulus.component.Component;
import org.geysermc.cumulus.component.LabelComponent;
import org.geysermc.cumulus.component.util.ComponentType;
import org.geysermc.cumulus.form.CustomForm;

/**
 * The response class of CustomForm. Labels are not included by default, but this can be changed by
 * calling the {@link #includeLabels(boolean)} method.
 *
 * @see #includeLabels(boolean)
 */
public interface CustomFormResponse extends FormResponse {
  /** Resets the index of the iterator. */
  void reset();

  /**
   * Sets the index of the iterator. Use {@link #skip()} or {@link #skip(int)} when you want to
   * increase the index (skip specific items) instead of setting the index. Use {@link #reset()} to
   * reset the index.
   *
   * @param index the position to set
   * @see #skip()
   * @see #skip(int)
   * @see #reset()
   */
  void index(@IntRange(from = -1) int index);

  /**
   * If labels should be skipped or not when moving to next items (which includes skip and next
   * methods). Default value is <b>false</b>
   *
   * @param includeLabels true if labels should be included, false otherwise
   * @since 1.1
   */
  void includeLabels(boolean includeLabels);

  /**
   * Returns true when there is another element to be read (e.g. using {@link #next()}). The value
   * of {@link #includeLabels(boolean)} influences the outcome of this method.
   *
   * @see #includeLabels(boolean)
   */
  boolean hasNext();

  /**
   * Returns true if the currently selected component is present. It returns false when either the
   * current component is out of bounds or when the current component is an optional component that
   * is not present. Most of the time {@link #isNextPresent()} would be used instead of this method.
   *
   * @see #isNextPresent()
   * @since 1.1
   */
  boolean isPresent();

  /**
   * Returns true if the next component is present. It returns false when there is either no next
   * component or when the next component is an optional component that is not present.
   *
   * @see #next()
   * @see #hasNext()
   * @since 1.1
   */
  boolean isNextPresent();

  /**
   * Returns the next component. When there is no next component the return value will be null. The
   * value of {@link #includeLabels(boolean)} influences the outcome of this method. If the next
   * component is an optional component that is not present, the value will be null.
   *
   * @param <T> the type to cast the component to
   * @throws ClassCastException when the value of the component cannot be cast to the provided
   *     return type
   * @see #includeLabels(boolean)
   */
  <T> @Nullable T next() throws ClassCastException;

  /**
   * Skips the specified amount of components. The value of {@link #includeLabels(boolean)}
   * influences the outcome of this method.
   *
   * @param amount the amount of components to skip
   * @see #includeLabels(boolean)
   */
  void skip(@Positive int amount);

  /**
   * Skips one component. The value of {@link #includeLabels(boolean)} influences the outcome of
   * this method.
   *
   * @see #includeLabels(boolean)
   */
  void skip();

  /**
   * Returns the value at a given position. If the component is an optional component that is not
   * present, or the component is a {@link LabelComponent}, the value will be null.
   *
   * @param index the index of the value you want to access
   * @param <T> the type to cast the component to
   * @throws IllegalArgumentException when there is no component at the given index
   * @throws ClassCastException when the value of the component cannot be cast to the provided
   *     return type
   * @since 1.1
   */
  <T> @Nullable T valueAt(int index) throws IllegalArgumentException, ClassCastException;

  /**
   * Returns the next component as a dropdown value. The value is the index of the dropdown that was
   * selected. The default value of an int (0) will be returned when the component is an optional
   * component that was not present.
   *
   * @throws IllegalStateException when the component is not a dropdown
   * @see #next()
   * @see #isNextPresent()
   * @since 1.1
   */
  int asDropdown() throws IllegalStateException;

  /**
   * Returns the next component as an input value. The value is the input the client gave. Null will
   * be returned when the component is an optional component that was not present.
   *
   * @throws IllegalStateException when the component is not an input
   * @see #next()
   * @see #isNextPresent()
   * @since 1.1
   */
  @Nullable String asInput() throws IllegalStateException;

  /**
   * Returns the next component as a slider value. The value is the slider value the client
   * selected. The default value of a float (0.0) will be returned when the component is an optional
   * component that was not present.
   *
   * @throws IllegalStateException when the component is not a slider
   * @see #next()
   * @see #isNextPresent()
   * @since 1.1
   */
  float asSlider() throws IllegalStateException;

  /**
   * Returns the next component as a step slider value. The value is the step slider value the
   * client selected. The default value of an int (0) will be returned when the component is an
   * optional component that was not present.
   *
   * @throws IllegalStateException when the component is not a step slider
   * @see #next()
   * @see #isNextPresent()
   * @since 1.1
   */
  int asStepSlider() throws IllegalStateException;

  /**
   * Returns the next component as a toggle value. The value is the toggle value the client
   * selected. The default value of a boolean (false) will be returned when the component is an
   * optional component that was not present.
   *
   * @throws IllegalStateException when the component is not a toggle
   * @see #next()
   * @see #isNextPresent()
   * @since 1.1
   */
  boolean asToggle() throws IllegalStateException;

  /**
   * Returns the value of the selected component as a dropdown component.
   *
   * @param index the index of the dropdown to return
   * @see #asDropdown()
   * @see #valueAt(int)
   * @since 1.1
   */
  int asDropdown(@NonNegative int index) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns the value of the selected component as an input component.
   *
   * @param index the index of the input to return
   * @see #asInput()
   * @see #valueAt(int)
   * @since 1.1
   */
  @Nullable String asInput(@NonNegative int index) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns the value of the selected component as a slider component.
   *
   * @param index the index of the slider to return
   * @see #asSlider()
   * @see #valueAt(int)
   * @since 1.1
   */
  float asSlider(@NonNegative int index) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns the value of the selected component as a step slider component.
   *
   * @param index the index of the step slider to return
   * @see #asStepSlider()
   * @see #valueAt(int)
   * @since 1.1
   */
  int asStepSlider(@NonNegative int index) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns the value of the selected component as a toggle component.
   *
   * @param index the index of the toggle to return
   * @see #asToggle()
   * @see #valueAt(int)
   * @since 1.1
   */
  boolean asToggle(@NonNegative int index) throws IllegalArgumentException, IllegalStateException;

  /**
   * @deprecated since 1.1 and will be removed in 2.0. Please use the more friendly methods instead.
   */
  @Deprecated
  @NonNull JsonArray getResponses();

  /**
   * @see CustomForm#content()
   * @see Component#type()
   * @deprecated since 1.1 and will be removed in 2.0. The component types aren't relevant since
   *     they're already defined in the form itself.
   */
  @Deprecated
  @NonNull List<ComponentType> getComponentTypes();

  /**
   * @see #includeLabels(boolean)
   * @see #next()
   * @deprecated since 1.1 and will be removed in 2.0. The alternative is calling both {@link
   *     #includeLabels(boolean)} and {@link #next()}
   */
  @Deprecated
  <T> @Nullable T next(boolean includeLabels) throws ClassCastException;

  /**
   * @deprecated since 1.1 and will be removed in 2.0. Response validation now happens before an
   *     instance of this class is made, so raw json types are no longer relevant. The closest
   *     replacement is {@link #valueAt(int)}.
   */
  @Deprecated
  @Nullable JsonPrimitive get(@NonNegative int index);

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method has been replaced by {@link
   *     #asDropdown(int)}.
   */
  @Deprecated
  int getDropdown(@NonNegative int index);

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method has been replaced by {@link
   *     #asInput(int)}.
   */
  @Deprecated
  @Nullable String getInput(@NonNegative int index);

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method has been replaced by {@link
   *     #asSlider(int)}.
   */
  @Deprecated
  float getSlider(@NonNegative int index);

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method has been replaced by {@link
   *     #asStepSlider(int)}.
   */
  @Deprecated
  int getStepSlide(@NonNegative int index);

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method has been replaced by {@link
   *     #asToggle(int)}.
   */
  @Deprecated
  boolean getToggle(@NonNegative int index);
}
