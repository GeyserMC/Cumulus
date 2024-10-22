/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.util;

import com.google.gson.annotations.SerializedName;
import java.util.Locale;
import java.util.Objects;
import org.geysermc.cumulus.util.impl.FormImageImpl;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Represents a form image which is used in buttons and as image for client settings. This class
 * holds an image type and data for the image type. For more information and for code examples look
 * at <a href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
 *
 * @since 1.0
 */
@NullMarked
public interface FormImage {
  /**
   * Create a FormImage with the following information.
   *
   * @param type the form image type
   * @param data the data form the form image type
   * @return a FormImage holding the given data
   * @since 1.0
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
   * @since 1.0
   */
  static FormImage of(String type, String data) {
    Type imageType = Type.fromName(type);
    if (imageType == null) {
      throw new IllegalArgumentException("Received an unknown type '" + type + "'");
    }
    return of(imageType, data);
  }

  /**
   * Returns the type of FormImage.
   *
   * @since 1.1
   */
  Type type();

  /**
   * Returns the data needed for the FormImage.
   *
   * @since 1.1
   */
  String data();

  /**
   * An enum which has the available FormImage Types. For more information and for code examples
   * look at <a href="https://github.com/GeyserMC/Cumulus/wiki">the wiki</a>.
   *
   * @since 1.0
   */
  enum Type {
    /**
     * Path is the path to an image of a resource pack. Example path: {@code
     * textures/i/glyph_world_template.png}
     *
     * @since 1.0
     */
    @SerializedName("path")
    PATH,
    /**
     * Url is an url to an image on a website. Example url: {@code
     * https://github.com/GeyserMC.png?size=200}
     *
     * @since 1.0
     */
    @SerializedName("url")
    URL;

    private static final Type[] VALUES = values();

    private final String name = name().toLowerCase(Locale.ROOT);

    /**
     * Gets the image type from the name as used in Bedrock.
     *
     * @param name the name of the image type
     * @return the image type or null if the image type doesn't exist
     * @since 1.1
     */
    public static @Nullable Type fromName(String name) {
      Objects.requireNonNull(name, "name");
      for (Type value : VALUES) {
        if (value.typeName().equals(name)) {
          return value;
        }
      }
      return null;
    }

    /**
     * Returns the image type name as used in Bedrock.
     *
     * @since 1.1
     */
    public String typeName() {
      return name;
    }
  }
}
