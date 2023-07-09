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
package org.geysermc.cumulus.util;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.geysermc.cumulus.Form;

/**
 * @deprecated since 1.1 and will be removed in 2.0. This class will be replaced by {@link
 *     org.geysermc.cumulus.form.util.FormBuilder}.
 */
@Deprecated
public interface FormBuilder<T extends FormBuilder<T, F>, F extends Form<?>> {

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method will be replaced by {@link
   *     org.geysermc.cumulus.form.util.FormBuilder#title(String)}.
   */
  @Deprecated
  T title(String title);

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method will be replaced by {@link
   *     org.geysermc.cumulus.form.util.FormBuilder#translator(BiFunction, String)}.
   */
  @Deprecated
  T translator(BiFunction<String, String, String> translator, String locale);

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method will be replaced by {@link
   *     org.geysermc.cumulus.form.util.FormBuilder#translator(BiFunction)}.
   */
  @Deprecated
  T translator(BiFunction<String, String, String> translator);

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method does not have a direct
   *     replacement since we no longer send the raw response. Take a look <a
   *     href="https://github.com/GeyserMC/Cumulus/wiki/Updating-from-1.0-to-1.1-(and-2.0)#response-handling-changes">here</a>
   *     to learn more about the response handling changes.
   */
  @Deprecated
  T responseHandler(BiConsumer<F, String> responseHandler);

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method does not have a direct
   *     replacement since we no longer send the raw response. Take a look <a
   *     href="https://github.com/GeyserMC/Cumulus/wiki/Updating-from-1.0-to-1.1-(and-2.0)#response-handling-changes">here</a>
   *     to learn more about the response handling changes.
   */
  @Deprecated
  T responseHandler(Consumer<String> responseHandler);

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method will be replaced by {@link
   *     org.geysermc.cumulus.form.util.FormBuilder#build()}.
   */
  @Deprecated
  F build();
}
