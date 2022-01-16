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

package org.geysermc.cumulus.util;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

/**
 * A FormBuilder for the specified Form type in an easy to handle class.
 *
 * @param <B> the Builder extending this class
 * @param <F> the Form type this builder is made for
 * @param <R> the Form response type of the given form
 * @since 1.0
 */
public interface FormBuilder<B extends FormBuilder<B, F, R>, F extends Form, R extends FormResponse> {
  //todo update documentation
  /**
   * Set the title of the form.
   *
   * @param title the title of the form
   * @return the form builder
   */
  @NonNull B title(@NonNull String title);

  /**
   * Set the translator of the form. The translator is called every time a component is added and it
   * can be called multiple times for one component, depending on how many string fields a component
   * has.<br><br> Note that the translation is executing when building the form. This info will not
   * be present in the final form instance.
   *
   * @param translator the translator that will translate every string. First argument is the text
   *                   to translate, the second argument is the player's locale
   * @param locale     the locale to translate the messages to
   * @return the form builder
   */
  @NonNull
  B translator(
      @NonNull BiFunction<String, String, String> translator,
      @NonNull String locale);

  /**
   * Set the translator of the form. The translator is called every time a component is added and it
   * can be called multiple times for one component, depending on how many string fields a component
   * has. This specific method assumes that {@link #translator(BiFunction, String)} has been
   * executed before, so this method is more for overriding the previous translator since the locale
   * isn't asked.<br><br> Note that the translation is executing when building the form. This info
   * will not be present in the final form instance.
   *
   * @param translator the translator that will translate every string. First argument is the text
   *                   to translate, the second argument is the player's locale
   * @return the form builder
   */
  @NonNull B translator(@NonNull BiFunction<String, String, String> translator);

  /**
   * @since 1.1
   */
  @NonNull B handleAllResponses(@NonNull BiConsumer<F, FormResponseResult<R>> responseHandler);

  /**
   * @since 1.1
   */
  @NonNull B handleAllResponses(@NonNull Consumer<FormResponseResult<R>> responseHandler);

  /**
   * Build the form.
   *
   * @return the form instance
   */
  @NonNull F build();
}
