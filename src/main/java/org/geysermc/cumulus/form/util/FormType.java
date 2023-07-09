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

import com.google.gson.annotations.SerializedName;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.form.ModalForm;
import org.geysermc.cumulus.form.SimpleForm;

/**
 * An enum containing the valid form types. Valid form types are:
 * <ul>
 *     <li>{@link SimpleForm Simple Form}</li>
 *     <li>{@link ModalForm Modal Form}</li>
 *     <li>{@link CustomForm Custom Form}</li>
 * </ul>
 * For more information and for code examples look at
 * <a href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
 *
 * @since 1.1
 */
public enum FormType {
  @SerializedName("form")
  SIMPLE_FORM,
  @SerializedName("modal")
  MODAL_FORM,
  @SerializedName("custom_form")
  CUSTOM_FORM;

  private static final FormType[] VALUES = values();

  public static @Nullable FormType fromOrdinal(int ordinal) {
    return ordinal < VALUES.length ? VALUES[ordinal] : null;
  }
}
