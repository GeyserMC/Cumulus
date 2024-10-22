/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.response.result;

/**
 * Cumulus divides the responses in three different types: {@link #CLOSED closed}, {@link #INVALID
 * invalid} and {@link #VALID valid}. And every response only has one ResultType.
 *
 * @since 1.1
 */
public enum ResultType {
  /**
   * The client has closed the form.
   *
   * @since 1.1
   */
  CLOSED,
  /**
   * The client did intend on sending a response, but the response contains one or more invalid
   * values (e.g. received a bool where the type should've been an int).
   *
   * @since 1.1
   */
  INVALID,
  /**
   * The client sent a valid response.
   *
   * @since 1.1
   */
  VALID
}
