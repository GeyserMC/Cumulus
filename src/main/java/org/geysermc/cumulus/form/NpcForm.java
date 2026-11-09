/*
 * Copyright (c) 2020-2026 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.cloudburstmc.math.vector.Vector3f;
import org.geysermc.cumulus.form.impl.npc.NpcFormImpl;
import org.geysermc.cumulus.form.util.FormBuilder;
import org.geysermc.cumulus.response.NpcFormResponse;

public interface NpcForm extends Form {
  /** Returns a new NpcForm builder. A more friendly way of creating a Form. */
  static @NonNull Builder builder() {
    return new NpcFormImpl.Builder();
  }

  /**
   * Create a NpcForm with predefined information.
   *
   * @param title the title of the form
   * @param content the description of the form
   * @param buttons the list of buttons
   * @param entityUUID the entity's UUID
   * @param entityOffset the entity's offset
   * @param entityScale the entity's scale
   * @return the created NpcForm instance
   */
  static @NonNull NpcForm of(
      @NonNull String title, @NonNull String content,
      @NonNull List<String> buttons, UUID entityUUID,
      @NonNull Vector3f entityOffset, @NonNull Vector3f entityScale
  ) {
    return new NpcFormImpl(title, content, entityUUID, buttons, entityOffset, entityScale);
  }

  /** Returns the description of the Form. */
  @NonNull String content();

  /** Returns the entity's ID that is associated with the Form. */
  @NonNull UUID entityUUID();

  /** Returns the entity's offset. */
  @NonNull Vector3f entityOffset();

  /** Returns the entity's scale. */
  @NonNull Vector3f entityScale();

  /** Returns a list of all buttons' strings. */
  @NonNull List<String> buttons();

  interface Builder extends FormBuilder<Builder, NpcForm, NpcFormResponse> {

    /**
     * Sets the description of the Form
     *
     * @param content Sets the description of the Form
     * @return the form builder
     *
     * @since 2.0.0
     */
    NpcForm.Builder content(@NonNull String content);

    /**
     * Sets the entity associated with this NpcForm
     *
     * @param entityUUID the UUID of the entity associated.
     * @return the form builder
     *
     * @since 2.0.0
     */
    NpcForm.Builder entity(@NonNull UUID entityUUID);

    /**
     * Sets the entity's portrait offset.
     *
     * @param x X position
     * @param y Y position
     * @param z Z position
     * @return the form builder
     *
     * @since 2.0.0
     */
    NpcForm.Builder entityOffset(double x, double y, double z);

    /**
     * Sets the entity's portrait offset.
     *
     * @param vector3f the offset position
     * @return the form builder
     *
     * @since 2.0.0
     */
    NpcForm.Builder entityOffset(@NonNull Vector3f vector3f);

    /**
     * Sets the entity's portrait scale. The default scale is "1, 1, 1"
     *
     * @param x X scale
     * @param y Y scale
     * @param z Z scale
     * @return the form builder
     *
     * @since 2.0.0
     */
    NpcForm.Builder entityScale(double x, double y, double z);

    /**
     * Sets the entity's portrait scale. The default scale is "1, 1, 1"
     *
     * @param vector3f the size
     * @return the form builder
     *
     * @since 2.0.0
     */
    NpcForm.Builder entityScale(@NonNull Vector3f vector3f);

    /**
     * Adds a button to the Form. There is a limit of 6 buttons in Npc Forms
     *
     * @param text the text of the added button
     * @return the form builder
     *
     * @since 2.0.0
     */
    NpcForm.Builder button(@NonNull String text);

    /**
     * Adds a button to the Form, but only when shouldAdd is true. There is a limit of 6 buttons in Npc Forms
     *
     * @param text the text of the added button
     * @param shouldAdd if the button should be added
     * @return the form builder
     *
     * @since 2.0.0
     */
    NpcForm.Builder optionalButton(@NonNull String text, boolean shouldAdd);

    /**
     * Adds a button with callback directly to the form. There is a limit of 6 buttons in Npc Forms
     *
     * @param text the text of the added button
     * @param callback the handler when the button is clicked
     * @return the form builder
     */
    NpcForm.Builder button(@NonNull String text, @NonNull Consumer<NpcFormResponse> callback);

    /**
     * Adds a button with callback directly to the form, but only when shouldAdd is true. There is a limit of 6 buttons in Npc Forms
     *
     * @param text the text of the added button
     * @param callback the handler when the button is clicked
     * @param shouldAdd if the button should be added
     * @return the form builder
     */
    NpcForm.Builder button(@NonNull String text, @NonNull Consumer<NpcFormResponse> callback, boolean shouldAdd);
  }
}
