/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.util;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.result.ClosedFormResponseResult;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.response.result.InvalidFormResponseResult;
import org.geysermc.cumulus.response.result.ResultType;
import org.geysermc.cumulus.response.result.ValidFormResponseResult;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * A FormBuilder for the specified Form type in an easy to handle class.
 *
 * @param <B> the Builder extending this class
 * @param <F> the Form type this builder is made for
 * @param <R> the Form response type of the given form
 * @since 1.1
 */
@NullMarked
public interface FormBuilder<
    B extends FormBuilder<B, F, R>, F extends Form, R extends FormResponse> {
  /**
   * Set the title of the form.
   *
   * @param title the title of the form
   * @return the form builder
   * @since 1.1
   */
  @This B title(String title);

  /**
   * Set the translator of the form. The translator is called every time a component is added, and
   * it can be called more than once for one component (it depends on the amount of string fields a
   * component has).<br>
   * <br>
   * Note that the translation is executed when building the form. This info will not be present in
   * the final form instance.
   *
   * @param translator the translator that will translate every string. First argument is the text
   *     to translate, the second argument is the player's locale
   * @param locale the locale to translate the messages to
   * @since 1.1
   */
  @This B translator(BiFunction<String, String, @Nullable String> translator, String locale);

  /**
   * Set the translator of the form. The translator is called every time a component is added, and
   * it can be called more than once for one component (it depends on the amount of string fields a
   * component has).<br>
   * <br>
   * Note that the translation is executed when building the form. This info will not be present in
   * the final form instance.<br>
   * <br>
   * This specific method assumes that {@link #translator(BiFunction, String)} has been executed
   * before, because this method will reuse the same locale.
   *
   * @param translator the translator that will translate every string. First argument is the text
   *     to translate, the second argument is the player's locale
   * @since 1.1
   */
  @This B translator(BiFunction<String, String, @Nullable String> translator);

  /**
   * Registers a result handler for the 'closed' result type. Calling this specific method more than
   * once will override the previously defined closed result handler.
   *
   * @param resultHandler the result handler to define
   * @see ClosedFormResponseResult
   * @since 1.1
   */
  @This B closedResultHandler(Runnable resultHandler);

  /**
   * Registers a result handler for the 'closed' result type. Calling this specific method more than
   * once will override the previously defined closed result handler.
   *
   * @param resultHandler the result handler to define
   * @see ClosedFormResponseResult
   * @since 1.1
   */
  @This B closedResultHandler(Consumer<F> resultHandler);

  /**
   * Registers a result handler for the 'invalid' result type. Calling this specific method more
   * than once will override the previously defined invalid result handler.
   *
   * @param resultHandler the result handler to define
   * @see InvalidFormResponseResult
   * @since 1.1
   */
  @This B invalidResultHandler(Runnable resultHandler);

  /**
   * Registers a result handler for the 'invalid' result type. Calling this specific method more
   * than once will override the previously defined invalid result handler.
   *
   * @param resultHandler the result handler to define
   * @see InvalidFormResponseResult
   * @since 1.1
   */
  @This B invalidResultHandler(Consumer<InvalidFormResponseResult<R>> resultHandler);

  /**
   * Registers a result handler for the 'invalid' result type. Calling this specific method more
   * than once will override the previously defined invalid result handler.
   *
   * @param resultHandler the result handler to define
   * @see InvalidFormResponseResult
   * @since 1.1
   */
  @This B invalidResultHandler(BiConsumer<F, InvalidFormResponseResult<R>> resultHandler);

  /**
   * Registers a result handler for both the 'closed' and the 'invalid' result type. Calling this
   * specific method more than once will override the previously defined closedOrInvalid result
   * handler.
   *
   * @param resultHandler the result handler to define
   * @see ClosedFormResponseResult
   * @see InvalidFormResponseResult
   * @since 1.1
   */
  @This B closedOrInvalidResultHandler(Runnable resultHandler);

  /**
   * Registers a result handler for both the 'closed' and the 'invalid' result type. Calling this
   * specific method more than once will override the previously defined closedOrInvalid result
   * handler.
   *
   * @param resultHandler the result handler to define
   * @see ClosedFormResponseResult
   * @see InvalidFormResponseResult
   * @since 1.1
   */
  @This B closedOrInvalidResultHandler(Consumer<FormResponseResult<R>> resultHandler);

  /**
   * Registers a result handler for both the 'closed' and the 'invalid' result type. Calling this
   * specific method more than once will override the previously defined closedOrInvalid result
   * handler.
   *
   * @param resultHandler the result handler to define
   * @see ClosedFormResponseResult
   * @see InvalidFormResponseResult
   * @since 1.1
   */
  @This B closedOrInvalidResultHandler(BiConsumer<F, FormResponseResult<R>> resultHandler);

  /**
   * Registers a result handler for the 'valid' result type. Calling this specific method more than
   * once will override the previously defined valid result handler.
   *
   * @param resultHandler the result handler to define
   * @see ValidFormResponseResult
   * @since 1.1
   */
  @This B validResultHandler(Consumer<R> resultHandler);

  /**
   * Registers a result handler for the 'valid' result type. Calling this specific method more than
   * once will override the previously defined valid result handler.
   *
   * @param resultHandler the result handler to define
   * @see ValidFormResponseResult
   * @since 1.1
   */
  @This B validResultHandler(BiConsumer<F, R> resultHandler);

  /**
   * Registers a result handler for every result type. Note that there can only be <b>one</b>
   * 'resultHandler'. Calling this specific method or {@link #resultHandler(BiConsumer,
   * ResultType...)} multiple times will override the previously defined result handler.
   *
   * @param resultHandler the result handler to define
   * @see FormResponseResult
   * @since 1.1
   */
  @This B resultHandler(BiConsumer<F, FormResponseResult<R>> resultHandler);

  /**
   * Registers a result handler for the selected result types. Note that there can only be
   * <b>one</b> 'resultHandler'. Calling this specific method or its overloads more than once will
   * override the previously defined result handler.
   *
   * @param resultHandler the result handler to define
   * @see ResultType
   * @see FormResponseResult
   * @since 1.1
   */
  @This B resultHandler(BiConsumer<F, FormResponseResult<R>> resultHandler, ResultType... selectedTypes);

  /**
   * Build the form and returns the created form.
   *
   * @since 1.1
   */
  F build();
}
