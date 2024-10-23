/*
 * Copyright (c) 2024 GeyserMC
 * Licensed under the MIT license
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.form.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.util.FormCodec;
import org.geysermc.cumulus.form.util.FormType;
import org.geysermc.cumulus.response.FormResponse;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * The only class that the average platform implementing Cumulus needs to implement. This
 * consolidates and simplifies need of using multiple internal classes.
 *
 * @since 2.0
 */
@NullMarked
public abstract class FormPlatform {
  private final FormDefinitions formDefinitions = new FormDefinitions();

  private final Map<Integer, Form> formsAwaitingResponse = new HashMap<>();
  private final AtomicInteger nextId = new AtomicInteger(0);

  /**
   * The platform's send form implementation. This is responsible for actually sending the packets
   * to the player. This method is called after the form has started being tracking.
   *
   * @param id the identifier of the form to send
   * @param encodedForm the encoded form request
   * @since 2.0
   */
  protected abstract void sendForm(int id, String encodedForm);

  /**
   * The platform's close form implementation. This is responsible for actually sending the packets
   * to the player, after the form was verified to still awaiting a response. The behaviour of this
   * method should be documented on {@link #closeForm(int)}.
   *
   * @param id the form to close
   * @since 2.0
   */
  protected abstract void closeForm0(int id);

  /**
   * Translate the data that is readable by the Bedrock client into a form instance.
   *
   * @param json the json data that is readable by the client
   * @param type the form data type
   * @param responseHandler the response handle for the created form
   * @param <T> the result will be cast to T
   * @return the form instance holding the translated data
   * @since 2.0
   */
  public final <T extends Form> T formFromRawData(
      String json, FormType type, BiConsumer<T, @Nullable String> responseHandler) {
    return formDefinitions
        .<FormCodec<T, FormResponse>>codecFor(type)
        .fromJson(json, responseHandler);
  }

  /**
   * Sends a specific form and listen for a response.
   *
   * @param form the form to send
   * @return the form identifier
   * @since 2.0
   */
  public final int sendForm(Form form) {
    EncodedFormRequestData request = createFormRequest(form);
    sendForm(request.id, request.encodedData);
    return request.id;
  }

  /**
   * Encode the data in the form in a way that's readable by Bedrock and start tracking it.
   *
   * @param form the form to encode and track
   * @return the identifier for the form and the encoded form
   * @since 2.0
   */
  public EncodedFormRequestData createFormRequest(Form form) {
    FormDefinition<Form, ?, ?> formDefinition = formDefinitions.definitionFor(form);

    int id = nextId();
    String data = formDefinition.codec().jsonData(form);

    formsAwaitingResponse.put(id, form);
    formDefinition.addCloseListener(form, id, () -> closeForm(id));
    return new EncodedFormRequestData(id, data);
  }

  /**
   * Stops tracking the given form and requests the form to be closed. The actual behaviour of
   * closing is implementation specific and the implementation should override this documentation.
   *
   * @param formId the form to close
   * @since 2.0
   */
  public final void closeForm(int formId) {
    Form form = formsAwaitingResponse.remove(formId);
    if (form == null) {
      return;
    }
    closeForm0(formId);
  }

  /**
   * Handle the raw filled in form response.
   *
   * @param id the identifier of the form
   * @param response the form response
   * @throws Exception if an exception occurred in the response handler(s)
   * @since 2.0
   */
  public void handleFormResponse(int id, @Nullable String response) throws Exception {
    Form form = formsAwaitingResponse.remove(id);
    if (form == null) {
      return;
    }
    formDefinitions.definitionFor(form).handleFormResponse(form, response);
  }

  /**
   * Returns the next (atomic) form ID based on the constraint specified in {@link #maxId()}
   *
   * @since 2.0
   */
  protected int nextId() {
    return nextId.getAndUpdate(id -> id > maxId() ? 0 : id + 1);
  }

  /**
   * Returns the max value that the form id can be (inclusive) before it resets back to 0. By
   * default, the value is Short.MAX_VALUE. This should be plenty for normal use.
   *
   * <p>Be careful with specifying a value. If the same ID is used for multiple forms then the last
   * form that was sent with the same id will override the others, which may lead to unexpected
   * behaviour (like an invalid response result) or data loss if the response handler relied on
   * getting a response.
   *
   * @since 2.0
   */
  protected int maxId() {
    return Short.MAX_VALUE;
  }

  public static class EncodedFormRequestData {
    private final int id;
    private final String encodedData;

    public EncodedFormRequestData(int id, String encodedData) {
      this.id = id;
      this.encodedData = encodedData;
    }

    public int id() {
      return id;
    }

    public String encodedData() {
      return encodedData;
    }
  }
}
