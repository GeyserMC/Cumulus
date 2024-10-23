/*
 * Copyright (c) 2020-2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl;

import java.util.Objects;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.util.FormCodec;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public abstract class FormDefinition<
    F extends Form, I extends FormImpl<R>, R extends FormResponse> {
  private final FormCodec<F, R> codec;
  private final FormType formType;
  private final Class<F> formClass;
  private final Class<I> formImplClass;

  protected FormDefinition(
      FormCodec<F, R> codec, FormType formType, Class<F> formClass, Class<I> formImplClass) {
    this.codec = Objects.requireNonNull(codec);
    this.formType = Objects.requireNonNull(formType);
    this.formClass = Objects.requireNonNull(formClass);
    this.formImplClass = Objects.requireNonNull(formImplClass);
  }

  public final FormCodec<F, R> codec() {
    return codec;
  }

  public void handleFormResponse(F form, @Nullable String responseData) throws Exception {
    if (!callRawResponseConsumer(form, responseData)) {
      FormResponseResult<R> result = codec().deserializeFormResponse(form, responseData);
      callResponseHandler(form, result);
    }
  }

  @SuppressWarnings("unchecked")
  protected boolean callRawResponseConsumer(F form, @Nullable String responseData)
      throws Exception {
    return ((FormImpl<R>) form).callRawResponseConsumer(responseData);
  }

  @SuppressWarnings("unchecked")
  protected void callResponseHandler(F form, FormResponseResult<R> result) throws Exception {
    ((FormImpl<R>) form).callResultHandler(result);
  }

  @SuppressWarnings("unchecked")
  protected void addCloseListener(F form, int formId, Runnable runnable) {
    ((FormImpl<R>) form).addCloseListener(formId, runnable);
  }

  public final FormType formType() {
    return formType;
  }

  public final Class<F> formClass() {
    return formClass;
  }

  public final Class<I> formImplClass() {
    return formImplClass;
  }
}
