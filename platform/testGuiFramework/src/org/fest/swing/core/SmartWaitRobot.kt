/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fest.swing.core

import com.intellij.util.ui.EdtInvocationManager
import org.fest.swing.awt.AWT
import org.fest.swing.edt.GuiActionRunner
import org.fest.swing.edt.GuiQuery
import org.fest.swing.hierarchy.ComponentHierarchy
import org.fest.swing.keystroke.KeyStrokeMap
import org.fest.swing.timing.Pause.pause
import org.fest.swing.util.Modifiers
import java.awt.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.swing.JPopupMenu
import javax.swing.KeyStroke
import javax.swing.SwingUtilities

class SmartWaitRobot : Robot {

  override fun moveMouse(component: Component) {
    basicRobot.moveMouse(component)
  }

  override fun moveMouse(component: Component, point: Point) {
    basicRobot.moveMouse(component, point)
  }

  override fun moveMouse(point: Point) {
    basicRobot.moveMouse(point)
  }

  override fun click(component: Component) {
    basicRobot.click(component)
  }

  override fun click(component: Component, mouseButton: MouseButton) {
    basicRobot.click(component, mouseButton)
  }

  override fun click(component: Component, mouseButton: MouseButton, counts: Int) {
    basicRobot.click(component, mouseButton, counts)
  }

  override fun click(component: Component, point: Point) {
    basicRobot.click(component, point)
  }

  override fun showWindow(window: Window) {
    basicRobot.showWindow(window)
  }

  override fun showWindow(window: Window, dimension: Dimension) {
    basicRobot.showWindow(window, dimension)
  }

  override fun showWindow(window: Window, dimension: Dimension?, p2: Boolean) {
    basicRobot.showWindow(window, dimension, p2)
  }

  override fun isActive(): Boolean = basicRobot.isActive


  override fun pressAndReleaseKey(p0: Int, vararg p1: Int) {
    basicRobot.pressAndReleaseKey(p0, *p1)
  }

  override fun showPopupMenu(component: Component): JPopupMenu = basicRobot.showPopupMenu(component)

  override fun showPopupMenu(component: Component, point: Point): JPopupMenu = basicRobot.showPopupMenu(component, point)

  override fun jitter(component: Component) {
    basicRobot.jitter(component)
  }

  override fun jitter(component: Component, point: Point) {
    basicRobot.jitter(component, point)
  }

  override fun pressModifiers(p0: Int) {
    basicRobot.pressModifiers(p0)
  }

  override fun pressMouse(mouseButton: MouseButton) {
    basicRobot.pressMouse(mouseButton)
  }

  override fun pressMouse(component: Component, point: Point) {
    basicRobot.pressMouse(component, point)
  }

  override fun pressMouse(component: Component, point: Point, mouseButton: MouseButton) {
    basicRobot.pressMouse(component, point, mouseButton)
  }

  override fun pressMouse(point: Point, mouseButton: MouseButton) {
    basicRobot.pressMouse(point, mouseButton)
  }

  override fun hierarchy(): ComponentHierarchy =
    basicRobot.hierarchy()

  override fun releaseKey(p0: Int) {
    basicRobot.releaseKey(p0)
  }

  override fun isDragging(): Boolean = basicRobot.isDragging

  override fun printer(): ComponentPrinter = basicRobot.printer()

  override fun type(char: Char) {
    basicRobot.type(char)
  }

  override fun type(char: Char, component: Component) {
    basicRobot.type(char, component)
  }

  override fun requireNoJOptionPaneIsShowing() {
    basicRobot.requireNoJOptionPaneIsShowing()
  }

  override fun cleanUp() {
    basicRobot.cleanUp()
  }

  override fun releaseMouse(mouseButton: MouseButton) {
    basicRobot.releaseMouse(mouseButton)
  }

  override fun pressKey(p0: Int) {
    basicRobot.pressKey(p0)
  }

  override fun settings(): Settings = basicRobot.settings()

  override fun enterText(text: String) {
    basicRobot.enterText(text)
  }

  override fun enterText(text: String, component: Component) {
    basicRobot.enterText(text, component)
  }

  override fun releaseMouseButtons() {
    basicRobot.releaseMouseButtons()
  }

  override fun rightClick(component: Component) {
    basicRobot.rightClick(component)
  }

  override fun focus(component: Component) {
    basicRobot.focus(component)
  }

  override fun doubleClick(component: Component) {
    basicRobot.doubleClick(component)
  }

  override fun cleanUpWithoutDisposingWindows() {
    basicRobot.cleanUpWithoutDisposingWindows()
  }

  override fun isReadyForInput(component: Component): Boolean = basicRobot.isReadyForInput(component)

  override fun focusAndWaitForFocusGain(component: Component) {
    basicRobot.focusAndWaitForFocusGain(component)
  }

  override fun releaseModifiers(p0: Int) {
    basicRobot.releaseModifiers(p0)
  }

  override fun findActivePopupMenu(): JPopupMenu? = basicRobot.findActivePopupMenu()

  override fun rotateMouseWheel(component: Component, p1: Int) {
    basicRobot.rotateMouseWheel(component, p1)
  }

  override fun rotateMouseWheel(p0: Int) {
    basicRobot.rotateMouseWheel(p0)
  }

  override fun pressAndReleaseKeys(vararg p0: Int) {
    basicRobot.pressAndReleaseKeys(*p0)
  }

  override fun finder(): ComponentFinder = basicRobot.finder()

  private val basicRobot: BasicRobot = BasicRobot.robotWithCurrentAwtHierarchyWithoutScreenLock() as BasicRobot

  init {
    settings().delayBetweenEvents(10)
  }

  private val waitConst = 30L
  private var myAwareClick: Boolean = false
  private val fastRobot: java.awt.Robot = java.awt.Robot()

  override fun waitForIdle() {
    if (myAwareClick) {
      Thread.sleep(50)
    } else {
      pause(waitConst)
      if (!SwingUtilities.isEventDispatchThread()) EdtInvocationManager.getInstance().invokeAndWait({ })
    }
  }

  override fun close(w: Window) {
    basicRobot.close(w)
    basicRobot.waitForIdle()
  }

  //smooth mouse move
  override fun moveMouse(x: Int, y: Int) {
    val pauseConstMs = settings().delayBetweenEvents().toLong()
    val n = 20
    val start = MouseInfo.getPointerInfo().location
    val dx = (x - start.x) / n.toDouble()
    val dy = (y - start.y) / n.toDouble()
    for (step in 1..n) {
      try {
        pause(pauseConstMs)
      } catch (e: InterruptedException) {
        e.printStackTrace()
      }

      basicRobot.moveMouse(
        (start.x + dx * ((Math.log(1.0 * step / n) - Math.log(1.0 / n)) * n / (0 - Math.log(1.0 / n)))).toInt(),
        (start.y + dy * ((Math.log(1.0 * step / n) - Math.log(1.0 / n)) * n / (0 - Math.log(1.0 / n)))).toInt())
    }
    basicRobot.moveMouse(x, y)
  }

  //smooth mouse move to component
  override fun moveMouse(c: Component, x: Int, y: Int) {
    moveMouseWithAttempts(c, x, y)
  }

  //smooth mouse move for find and click actions
  override fun click(c: Component, where: Point, button: MouseButton, times: Int) {
    moveMouseAndClick(c, where, button, times)
  }

  //we are replacing BasicRobot click with our click because the original one cannot handle double click rightly (BasicRobot creates unnecessary move event between click event which breaks clickCount from 2 to 1)
  override fun click(where: Point, button: MouseButton, times: Int) {
    moveMouseAndClick(null, where, button, times)
  }

  private fun moveMouseAndClick(c: Component? = null, where: Point, button: MouseButton, times: Int) {
    if (c != null) moveMouse(c, where.x, where.y) else moveMouse(where.x, where.y)
    //pause between moving cursor and performing a click.
    pause(waitConst)
    myEdtAwareClick(button, times, where, c)
  }


  private fun moveMouseWithAttempts(c: Component, x: Int, y: Int, attempts: Int = 3) {
    if (attempts == 0) return
    waitFor { c.isShowing }

    val componentLocation: Point = requireNotNull(performOnEdt { AWT.translate(c, x, y) })
    moveMouse(componentLocation.x, componentLocation.y)

    val componentLocationAfterMove: Point = requireNotNull(performOnEdt { AWT.translate(c, x, y) })
    val mouseLocation = MouseInfo.getPointerInfo().location
    if (mouseLocation.x != componentLocationAfterMove.x || mouseLocation.y != componentLocationAfterMove.y)
      moveMouseWithAttempts(c, x, y, attempts - 1)
  }

  private fun myInnerClick(button: MouseButton, times: Int, point: Point, component: Component?) {
    if (component == null)
      basicRobot.click(point, button, times)
    else
      basicRobot.click(component, point, button, times)
  }

  private fun waitFor(condition: () -> Boolean) {
    val timeout = 5000 //5 sec
    val cdl = CountDownLatch(1)
    val executor = Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(Runnable {
      if (condition()) {
        cdl.countDown()
      }
    }, 0, 100, TimeUnit.MILLISECONDS)
    cdl.await(timeout.toLong(), TimeUnit.MILLISECONDS)
    executor.cancel(true)
  }

  fun fastPressAndReleaseKey(keyCode: Int, vararg modifiers: Int) {
    val unifiedModifiers = InputModifiers.unify(*modifiers)
    val updatedModifiers = Modifiers.updateModifierWithKeyCode(keyCode, unifiedModifiers)
    fastPressModifiers(updatedModifiers)
    if (updatedModifiers == unifiedModifiers) {
      fastPressKey(keyCode)
      fastReleaseKey(keyCode)
    }
    fastReleaseModifiers(updatedModifiers)
  }

  fun fastPressAndReleaseModifiers(vararg modifiers: Int) {
    val unifiedModifiers = InputModifiers.unify(*modifiers)
    fastPressModifiers(unifiedModifiers)
    pause(50)
    fastReleaseModifiers(unifiedModifiers)
  }

  fun fastPressAndReleaseKeyWithoutModifiers(keyCode: Int) {
    fastPressKey(keyCode)
    fastReleaseKey(keyCode)
  }

  fun shortcut(keyStoke: KeyStroke) {
    fastPressAndReleaseKey(keyStoke.keyCode, keyStoke.modifiers)
  }

  fun shortcutAndTypeString(keyStoke: KeyStroke, string: String, delayBetweenShortcutAndTypingMs: Int = 0) {
    fastPressAndReleaseKey(keyStoke.keyCode, keyStoke.modifiers)
    fastTyping(string, delayBetweenShortcutAndTypingMs)
  }

  fun fastTyping(string: String, delayBetweenShortcutAndTypingMs: Int = 0) {
    val keyCodeArray: IntArray = string
      .map { KeyStrokeMap.keyStrokeFor(it)?.keyCode ?: throw Exception("Unable to get keystroke for char '$it'") }
      .toIntArray()
    if (delayBetweenShortcutAndTypingMs > 0) pause(delayBetweenShortcutAndTypingMs.toLong())
    keyCodeArray.forEach { fastPressAndReleaseKeyWithoutModifiers(keyCode = it); pause(50) }
  }

  private fun fastPressKey(keyCode: Int) {
    fastRobot.keyPress(keyCode)
  }

  private fun fastReleaseKey(keyCode: Int) {
    fastRobot.keyRelease(keyCode)
  }

  private fun fastPressModifiers(modifierMask: Int) {
    val keys = Modifiers.keysFor(modifierMask)
    val keysSize = keys.size
    (0 until keysSize)
      .map { keys[it] }
      .forEach { fastPressKey(it) }
  }

  private fun fastReleaseModifiers(modifierMask: Int) {
    val modifierKeys = Modifiers.keysFor(modifierMask)
    for (i in modifierKeys.indices.reversed())
      fastReleaseKey(modifierKeys[i])
  }

  private fun myEdtAwareClick(button: MouseButton, times: Int, point: Point, component: Component?) {
    awareClick {
      //TODO: remove mouse clicks from EDT
      //      performOnEdt {
      myInnerClick(button, times, point, component)
      //      }
    }
    waitForIdle()
  }

  private fun <T> performOnEdt(body: () -> T): T? =
    GuiActionRunner.execute(object : GuiQuery<T>() {
      override fun executeInEDT() = body.invoke()
    })

  private fun awareClick(body: () -> Unit) {
    myAwareClick = true
    body.invoke()
    myAwareClick = false
  }

}