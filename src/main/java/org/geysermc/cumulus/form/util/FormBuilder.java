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

package org.geysermc.cumulus.form.util;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.result.ClosedFormResponseResult;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.response.result.InvalidFormResponseResult;
import org.geysermc.cumulus.response.result.ResultType;
import org.geysermc.cumulus.response.result.ValidFormResponseResult;

/**
 * A FormBuilder for the specified Form type in an easy to handle class.
 *
 * @param <B> the Builder extending this class
 * @param <F> the Form type this builder is made for
 * @param <R> the Form response type of the given form
 * @since 1.1
 */
public interface FormBuilder<B extends FormBuilder<B, F, R>, F extends Form, R extends FormResponse> {
  /**
   * Set the title of the form.
   *
   * @param title the title of the form
   * @return the form builder
   */
  @This B title(@NonNull String title);

  /**
   * Set the translator of the form. The translator is called every time a component is added, and
   * it can be called more than once for one component (it depends on the amount of string fields a
   * component has).<br><br> Note that the translation is executed when building the form. This info
   * will not be present in the final form instance.
   *
   * @param translator the translator that will translate every string. First argument is the text
   *                   to translate, the second argument is the player's locale
   * @param locale     the locale to translate the messages to
   * @return the form builder
   */
  @This
  B translator(
      @NonNull BiFunction<String, String, String> translator,
      @NonNull String locale
  );

  /**
   * Set the translator of the form. The translator is called every time a component is added, and
   * it can be called more than once for one component (it depends on the amount of string fields a
   * component has).<br><br> Note that the translation is executed when building the form. This info
   * will not be present in the final form instance.<br><br> This specific method assumes that
   * {@link #translator(BiFunction, String)} has been executed before, because this method will
   * reuse the same locale.
   *
   * @param translator the translator that will translate every string. First argument is the text
   *                   to translate, the second argument is the player's locale
   * @return the form builder
   */
  @This B translator(@NonNull BiFunction<String, String, String> translator);

  /**
   * Registers a result handler for the 'closed' result type. Calling this specific method more than
   * once will override the previously defined closed result handler.
   *
   * @param resultHandler the result handler to define
   * @return the form builder
   * @see ClosedFormResponseResult ClosedFormResponseResult
   */
  @This B closedResultHandler(@NonNull Runnable resultHandler);

  /**
   * Registers a result handler for the 'closed' result type. Calling this specific method more than
   * once will override the previously defined closed result handler.
   *
   * @param resultHandler the result handler to define
   * @return the form builder
   * @see ClosedFormResponseResult ClosedFormResponseResult
   */
  @This B closedResultHandler(@NonNull Consumer<F> resultHandler);

  /**
   * Registers a result handler for the 'invalid' result type. Calling this specific method more
   * than once will override the previously defined invalid result handler.
   *
   * @param resultHandler the result handler to define
   * @return the form builder
   * @see InvalidFormResponseResult
   */
  @This B invalidResultHandler(@NonNull Runnable resultHandler);

  /**
   * Registers a result handler for the 'invalid' result type. Calling this specific method more
   * than once will override the previously defined invalid result handler.
   *
   * @param resultHandler the result handler to define
   * @return the form builder
   * @see InvalidFormResponseResult
   */
  @This B invalidResultHandler(@NonNull Consumer<InvalidFormResponseResult<R>> resultHandler);

  /**
   * Registers a result handler for the 'invalid' result type. Calling this specific method more
   * than once will override the previously defined invalid result handler.
   *
   * @param resultHandler the result handler to define
   * @return the form builder
   * @see InvalidFormResponseResult
   */
  @This B invalidResultHandler(@NonNull BiConsumer<F, InvalidFormResponseResult<R>> resultHandler);

  /**
   * Registers a result handler for both the 'closed' and the 'invalid' result type. Calling this
   * specific method more than once will override the previously defined closedAndInvalid result
   * handler.
   *
   * @param resultHandler the result handler to define
   * @return the form builder
   * @see ClosedFormResponseResult
   * @see InvalidFormResponseResult
   */
  @This
  B closedOrInvalidResultHandler(@NonNull Runnable resultHandler);


  /**
   * Registers a result handler for both the 'closed' and the 'invalid' result type. Calling this
   * specific method more than once will override the previously defined closedAndInvalid result
   * handler.
   *
   * @param resultHandler the result handler to define
   * @return the form builder
   * @see ClosedFormResponseResult
   * @see InvalidFormResponseResult
   */
  @This
  B closedOrInvalidResultHandler(@NonNull Consumer<FormResponseResult<R>> resultHandler);

  /**
   * Registers a result handler for both the 'closed' and the 'invalid' result type. Calling this
   * specific method more than once will override the previously defined closedAndInvalid result
   * handler.
   *
   * @param resultHandler the result handler to define
   * @return the form builder
   * @see ClosedFormResponseResult
   * @see InvalidFormResponseResult
   */
  @This
  B closedOrInvalidResultHandler(@NonNull BiConsumer<F, FormResponseResult<R>> resultHandler);

  /**
   * Registers a result handler for the 'valid' result type. Calling this specific method more than
   * once will override the previously defined valid result handler.
   *
   * @param resultHandler the result handler to define
   * @return the form builder
   * @see ValidFormResponseResult ValidFormResponseResult
   */
  @This B validResultHandler(@NonNull Consumer<R> resultHandler);

  /**
   * Registers a result handler for the 'valid' result type. Calling this specific method more than
   * once will override the previously defined valid result handler.
   *
   * @param resultHandler the result handler to define
   * @return the form builder
   * @see ValidFormResponseResult ValidFormResponseResult
   */
  @This B validResultHandler(@NonNull BiConsumer<F, R> resultHandler);

  /**
   * Registers a result handler for every result type. Note that there can only be <b>one</b>
   * 'resultHandler'. Calling this specific method or
   * {@link #resultHandler(BiConsumer, ResultType...)} multiple times will override the previously
   * defined result handler.
   *
   * @param resultHandler the result handler to define
   * @return the form builder
   * @see FormResponseResult
   */
  @This B resultHandler(@NonNull BiConsumer<F, FormResponseResult<R>> resultHandler);

  /**
   * Registers a result handler for the selected result types. Note that there can only be
   * <b>one</b> 'resultHandler'. Calling this specific method or its overloads more than once will
   * override the previously defined result handler.
   *
   * @param resultHandler the result handler to define
   * @return the form builder
   * @see ResultType
   * @see FormResponseResult
   */
  @This
  B resultHandler(
      @NonNull BiConsumer<F, FormResponseResult<R>> resultHandler,
      @NonNull ResultType... selectedTypes
  );

  /**
   * Build the form.
   *
   * @return the form instance
   */
  @NonNull F build();
}
