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

package org.geysermc.cumulus.form.impl;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.impl.custom.CustomFormDefinition;
import org.geysermc.cumulus.form.impl.modal.ModalFormDefinition;
import org.geysermc.cumulus.form.impl.simple.SimpleFormDefinition;
import org.geysermc.cumulus.util.FormCodec;
import org.geysermc.cumulus.util.FormType;

public final class FormDefinitions {
  private static final FormDefinitions definitions = new FormDefinitions();

  private final Map<FormType, FormDefinition<?, ?, ?>> typeDefinitionMap = new HashMap<>();
  private final Map<Class<? extends FormImpl<?>>, FormType> implClassTypeMap = new HashMap<>();

  private FormDefinitions() {
  }

  public Class<? extends FormImpl<?>> formImplClass(FormType formType) {
    return findDefinition(formType).formImplClass();
  }

  @SuppressWarnings("unchecked")
  public <C extends FormCodec<?, ?>> C codecFor(FormType formType) {
    return (C) findDefinition(formType).codec();
  }

  public FormType typeFromImplClass(Class<? extends Form> formClass) {
    return implClassTypeMap.get(formClass);
  }

  public boolean addDefinition(FormDefinition<?, ?, ?> definition) {
    FormType type = definition.formType();
    if (typeDefinitionMap.putIfAbsent(type, definition) != null) {
      return false;
    }

    Class<? extends FormImpl<?>> formImplClass = definition.formImplClass();
    if (implClassTypeMap.putIfAbsent(formImplClass, type) != null) {
      typeDefinitionMap.remove(type);
      return false;
    }
    return true;
  }

  private void ensureDefinitionAdded(FormDefinition<?, ?, ?> definition) {
    Preconditions.checkArgument(addDefinition(definition));
  }

  private FormDefinition<?, ?, ?> findDefinition(FormType formType) {
    FormDefinition<?, ?, ?> definition = typeDefinitionMap.get(formType);
    if (definition == null) {
      throw new RuntimeException("Cannot find implementation for FormType " + formType);
    }
    return definition;
  }

  public static FormDefinitions instance() {
    return definitions;
  }

  static {
    // add the default form definitions
    definitions.ensureDefinitionAdded(SimpleFormDefinition.instance());
    definitions.ensureDefinitionAdded(ModalFormDefinition.instance());
    definitions.ensureDefinitionAdded(CustomFormDefinition.instance());
  }
}
