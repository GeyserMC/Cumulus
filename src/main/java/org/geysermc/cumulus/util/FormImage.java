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

import com.google.gson.annotations.SerializedName;
import java.util.Locale;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.util.impl.FormImageImpl;

/**
 * Represents a form image which is used in buttons and as image for client settings. This class
 * holds a image type and data for the image type. For more information and for code examples look
 * at <a href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
 */
public interface FormImage {
  /**
   * Create a FormImage with the following information.
   *
   * @param type the form image type
   * @param data the data form the form image type
   * @return a FormImage holding the given data
   */
  @NonNull
  static FormImage of(@NonNull Type type, @NonNull String data) {
    return new FormImageImpl(type, data);
  }

  /**
   * Create a FormImage with the following information.
   *
   * @param type the form image type
   * @param data the data form the form image type
   * @return a FormImage holding the given data
   */
  @NonNull
  static FormImage of(@NonNull String type, @NonNull String data) {
    Type imageType = Type.fromName(type);
    if (imageType == null) {
      throw new IllegalArgumentException("Received an unknown type '" + type + "'");
    }
    return of(imageType, data);
  }

  /**
   * Returns the type of FormImage.
   */
  @NonNull
  Type type();

  /**
   * Returns the data needed for the FormImage.
   */
  @NonNull
  String data();

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method has been replaced by
   * {@link #type()}.
   */
  @Deprecated
  Type getType();

  /**
   * @deprecated since 1.1 and will be removed in 2.0. This method has been replaced by
   * {@link #data()}.
   */
  @Deprecated
  String getData();

  /**
   * An enum which has the available FormImage Types. For more information and for code examples
   * look at <a href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
   */
  enum Type {
    @SerializedName("path") PATH,
    @SerializedName("url") URL;

    private static final Type[] VALUES = values();

    private final String name = name().toLowerCase(Locale.ROOT);

    @Nullable
    public static Type fromName(@NonNull String name) {
      Objects.requireNonNull(name, "name");
      for (Type value : VALUES) {
        if (value.typeName().equals(name)) {
          return value;
        }
      }
      return null;
    }

    @NonNull
    public String typeName() {
      return name;
    }

    /**
     * @deprecated since 1.1 and will be removed in 2.0. This method has been replaced by
     * {@link #fromName(String)}.
     */
    @Deprecated
    public static Type getByName(@NonNull String name) {
      return fromName(name);
    }

    /**
     * @deprecated since 1.1 and will be removed in 2.0. This method has been replaced by
     * {@link #typeName()}.
     */
    @Deprecated
    public String getName() {
      return name();
    }
  }
}
