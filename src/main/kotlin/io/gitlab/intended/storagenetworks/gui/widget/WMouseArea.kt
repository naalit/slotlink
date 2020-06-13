@file:Suppress("MemberVisibilityCanBePrivate")

package io.gitlab.intended.storagenetworks.gui.widget

import spinnery.widget.WAbstractWidget

class WMouseArea : WAbstractWidget() {

    var onMouseClicked: (mouseButton: Int) -> Unit = {}
    var onMouseReleased: (mouseButton: Int) -> Unit = {}
    var onMouseScrolled: (deltaY: Double) -> Unit = {}
    var onMouseMoved: (isInside: Boolean) -> Unit = {}

    override fun onMouseClicked(mouseX: Float, mouseY: Float, mouseButton: Int) {
        super.onMouseClicked(mouseX, mouseY, mouseButton)
        if (isWithinBounds(mouseX, mouseY)) onMouseClicked.invoke(mouseButton)
    }

    override fun onMouseReleased(mouseX: Float, mouseY: Float, mouseButton: Int) {
        super.onMouseReleased(mouseX, mouseY, mouseButton)
        if (isWithinBounds(mouseX, mouseY)) onMouseReleased.invoke(mouseButton)
    }

    override fun onMouseMoved(mouseX: Float, mouseY: Float) {
        super.onMouseMoved(mouseX, mouseY)
        onMouseMoved.invoke(isWithinBounds(mouseX, mouseY))
    }

    override fun onMouseScrolled(mouseX: Float, mouseY: Float, deltaY: Double) {
        super.onMouseScrolled(mouseX, mouseY, deltaY)
        if (isWithinBounds(mouseX, mouseY)) onMouseScrolled.invoke(deltaY)
    }

}
