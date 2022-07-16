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

package org.geysermc.cumulus.response;

import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.geysermc.cumulus.response.impl.CustomFormResponseImpl;
import org.geysermc.cumulus.util.AbsentComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class CustomFormResponseTest {

  private static CustomFormResponse RESPONSE;

  private static List<Consumer<Integer>> getters() {
    // the print will only occur if an exception is not thrown. The exception will be thrown when
    // successfully testing for wrong type exceptions
    return new ArrayList<>(Arrays.asList(
        i -> System.out.println("indexed input: " + RESPONSE.asInput(i)), // string
        i -> {
          setIndex(i - 1);
          System.out.println("input: " + RESPONSE.asInput());
        },
        i -> System.out.println("indexed dropdown: " + RESPONSE.asDropdown(i)), // int
        i -> {
          setIndex(i - 1);
          System.out.println("dropdown: " + RESPONSE.asDropdown());
        },
        i -> System.out.println("indexed stepslider: " + RESPONSE.asStepSlider(i)), // int
        i -> {
          setIndex(i - 1);
          System.out.println("stepslider: " + RESPONSE.asStepSlider());
        },
        i -> System.out.println("indexed toggle: " + RESPONSE.asToggle(i)), // boolean
        i -> {
          setIndex(i - 1);
          System.out.println("toggle: " + RESPONSE.asToggle());
        },
        i -> System.out.println("indexed slider: " + RESPONSE.asSlider(i)), // float
        i -> {
          setIndex(i - 1);
          System.out.println("slider: " + RESPONSE.asSlider());
        }
    ));
  }

  @BeforeAll
  public static void generate() {
    List<Object> responses = new ArrayList<>();
    responses.add(null); // Label (no response from client)
    responses.add(5); // a Dropdown or a StepSlider
    responses.add(AbsentComponent.instance()); // optional component that was not added
    responses.add("spinbom"); // Input
    responses.add(AbsentComponent.instance());
    responses.add(true); // Toggle
    responses.add(3.4f); // Slider

    // we won't test deprecated members, so only responses is required
    RESPONSE = CustomFormResponseImpl.of(responses, new JsonArray(), Collections.emptyList());
  }

  @BeforeEach
  public void reset() {
    RESPONSE.index(-1); // the defaults
    RESPONSE.includeLabels(false);
  }

  private static void setIndex(int index) {
    RESPONSE.index(index);
  }

  @Test
  public void testIncludeLabels() {
    // should skip the label at the start because includeLabels is false by default
    Assertions.assertEquals(5, RESPONSE.asDropdown());

    RESPONSE.includeLabels(true);
    RESPONSE.index(-1);
    Assertions.assertNull(RESPONSE.next());
  }

  @Test
  public void testIncorrectType() {
    RESPONSE.includeLabels(true); // required for the getters that don't use direct indexing (next())
    List<Consumer<Integer>> testers = getters();

    testers.forEach(assertTypeException(0)); // Label, all getters should throw

    testers.remove(2); // exclude dropdown getters
    testers.remove(2);
    testers.remove(2); // exclude stepslider getters
    testers.remove(2);
    testers.forEach(assertTypeException(1)); // location of dropdown response

    testers = getters();
    testers.remove(0); // exclude input getters
    testers.remove(0);
    testers.forEach(assertTypeException(3)); // location of input response

    testers = getters();
    testers.remove(6); // exclude toggle getters
    testers.remove(6);
    testers.forEach(assertTypeException(5)); // location of toggle response

    testers = getters();
    testers.remove(8); // exclude slider getters
    testers.remove(8);
    testers.forEach(assertTypeException(6)); // location of slider response
  }

  private Consumer<Consumer<Integer>> assertTypeException(int index) {
    return c -> assertTypeException(() -> c.accept(index));
  }

  private void assertTypeException(Executable executable) {
    Assertions.assertThrows(IllegalStateException.class, executable);
  }

  @Test
  public void testCorrectValues() {
    // label
    Assertions.assertNull(RESPONSE.valueAt(0));
    RESPONSE.includeLabels(true);
    Assertions.assertNull(RESPONSE.next());
    reset();

    // dropdown/stepslider
    Assertions.assertEquals(5, RESPONSE.asDropdown(1));
    Assertions.assertEquals(5, RESPONSE.asStepSlider(1));
    Assertions.assertEquals(5, RESPONSE.asDropdown()); // skips label
    setIndex(0);
    Assertions.assertEquals(5, RESPONSE.asStepSlider()); // skips label

    // absent component
    Assertions.assertNull(RESPONSE.valueAt(2));
    Assertions.assertNull(RESPONSE.valueAt(4));
    setIndex(1);
    Assertions.assertNull(RESPONSE.next());
    setIndex(13);
    Assertions.assertNull(RESPONSE.next());

    // input
    Assertions.assertEquals("spinbom", RESPONSE.asInput(3));
    setIndex(2);
    Assertions.assertEquals("spinbom", RESPONSE.asInput());

    // toggle
    Assertions.assertTrue(RESPONSE.asToggle(5));
    setIndex(4);
    Assertions.assertTrue(RESPONSE.asToggle());

    // slider
    Assertions.assertEquals(3.4f, RESPONSE.asSlider(6));
    setIndex(5);
    Assertions.assertEquals(3.4f, RESPONSE.asSlider());
  }

  @Test
  public void testDefaultValues() {
    // dropdown
    Assertions.assertEquals(0, RESPONSE.asDropdown(2));
    setIndex(1);
    Assertions.assertEquals(0, RESPONSE.asDropdown());

    // stepslider
    Assertions.assertEquals(0, RESPONSE.asStepSlider(2));
    setIndex(1);
    Assertions.assertEquals(0, RESPONSE.asStepSlider());

    // input
    Assertions.assertNull(RESPONSE.asInput(2));
    setIndex(1);
    Assertions.assertNull(RESPONSE.asInput());

    // toggle
    Assertions.assertFalse(RESPONSE.asToggle(2));
    setIndex(1);
    Assertions.assertFalse(RESPONSE.asToggle());

    // slider
    Assertions.assertEquals(0f, RESPONSE.asSlider(2));
    setIndex(1);
    Assertions.assertEquals(0f, RESPONSE.asSlider());
  }
}
