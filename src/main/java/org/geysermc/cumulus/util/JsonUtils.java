/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jspecify.annotations.NullMarked;

/**
 * JSON related utilities that Cumulus internals use.
 *
 * @since 1.1
 */
@NullMarked
public class JsonUtils {
  public static JsonElement assumeMember(JsonObject object, String memberName) {
    JsonElement member = object.get(memberName);
    if (member == null) {
      throw new IllegalStateException(
          "Excepted to find a member named '" + memberName + "' in the JsonObject!");
    }
    return member;
  }
}
