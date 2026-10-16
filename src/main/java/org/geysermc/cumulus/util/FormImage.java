/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.util;

import com.google.gson.annotations.SerializedName;
import java.util.Locale;
import java.util.Objects;
import org.geysermc.cumulus.util.impl.FormImageImpl;
import org.jspecify.annotations.Nullable;

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
  static FormImage of(Type type, String data) {
    return new FormImageImpl(type, data);
  }

  /**
   * Create a FormImage with the following information.
   *
   * @param type the form image type
   * @param data the data form the form image type
   * @return a FormImage holding the given data
   */
  static FormImage of(String type, String data) {
    Type imageType = Type.fromName(type);
    if (imageType == null) {
      throw new IllegalArgumentException("Received an unknown type '" + type + "'");
    }
    return of(imageType, data);
  }

  /** Returns the type of FormImage. */
  Type type();

  /** Returns the data needed for the FormImage. */
  String data();

  /**
   * An enum which has the available FormImage Types. For more information and for code examples
   * look at <a href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
   */
  enum Type {
    @SerializedName("path")
    PATH,
    @SerializedName("url")
    URL;

    private static final Type[] VALUES = values();

    private final String name = name().toLowerCase(Locale.ROOT);

    public static @Nullable Type fromName(String name) {
      Objects.requireNonNull(name, "name");
      for (Type value : VALUES) {
        if (value.typeName().equals(name)) {
          return value;
        }
      }
      return null;
    }

    public String typeName() {
      return name;
    }
  }
}
