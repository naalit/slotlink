package io.gitlab.intended.storagenetworks.gui.widget

import net.minecraft.util.Identifier
import spinnery.client.render.BaseRenderer
import spinnery.widget.WPanel

class WTexturedPanel(
    private val texture: Identifier
) : WPanel(){

    override fun draw() {
        if (isHidden) return
        BaseRenderer.drawImage(x.toDouble(), y.toDouble(), z.toDouble(), width.toDouble(), height.toDouble(), texture)
        orderedWidgets.forEach { it.draw() }
    }

}
