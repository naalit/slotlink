package badasintended.slotlink.client.gui.widget

import badasintended.slotlink.client.util.c2s
import badasintended.slotlink.client.util.client
import badasintended.slotlink.client.util.wrap
import badasintended.slotlink.compat.recipe.RecipeViewer
import badasintended.slotlink.init.Packets
import badasintended.slotlink.screen.FilterScreenHandler
import badasintended.slotlink.util.bool
import badasintended.slotlink.util.int
import badasintended.slotlink.util.stack
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting

@Environment(EnvType.CLIENT)
class FilterSlotWidget(
    handler: FilterScreenHandler,
    private val index: Int,
    x: Int, y: Int
) : SlotWidget<FilterScreenHandler>(x, y, 18, handler, { handler.filter[index].first }) {

    private val nbt get() = handler.filter[index].second

    fun setStack(stack: ItemStack, nbt: Boolean = Screen.hasControlDown()) {
        handler.filterSlotClick(index, stack, nbt)
        c2s(Packets.FILTER_SLOT_CLICK) {
            int(handler.syncId)
            int(index)
            stack(stack)
            bool(nbt)
        }
    }

    override fun appendTooltip(tooltip: MutableList<Text>) {
        tooltip.add(Text.translatable("container.slotlink.filter.slot.nbt.${nbt}").formatted(Formatting.GRAY))
        tooltip.add(Text.translatable("container.slotlink.filter.slot.nbt.scroll").formatted(Formatting.GRAY))
    }

    override fun renderOverlay(context: DrawContext, stack: ItemStack) {
        super.renderOverlay(context, stack)

        client.apply {
            context.drawItemInSlot(textRenderer, stack, x + 1, y + 1, "")

            context.matrices.wrap {
                context.matrices.translate(0.0, 0.0, 250.0)
                context.fill(x + 1, y + 1, x + 17, y + 17, if (nbt) 0x70aa27ba else 0x408b8b8b)
                if (nbt) {
                    context.drawTextWithShadow(
                        textRenderer,
                        "+",
                        x + 17 - textRenderer.getWidth("+"),
                        y + 17 - textRenderer.fontHeight,
                        0xaa27ba
                    )
                }
            }
        }
    }

    override fun renderTooltip(context: DrawContext, mouseX: Int, mouseY: Int) {
        super.renderTooltip(context, mouseX, mouseY)

        if (!handler.cursorStack.isEmpty || RecipeViewer.instance?.isDraggingStack == true) context.matrices.wrap {
            context.matrices.translate(0.0, 0.0, +256.0)

            val tlKey = "container.slotlink.filter.slot.tip." +
                if (Screen.hasControlDown()) "pressed" else
                    if (MinecraftClient.IS_SYSTEM_MAC) "cmd"
                    else "ctrl"
            context.drawTooltip(client.textRenderer, Text.translatable(tlKey), mouseX, mouseY)
        }
    }

    override fun onClick(button: Int) {
        setStack(handler.cursorStack)
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double): Boolean {
        val player = client.player ?: return false
        if (hovered && visible && !player.isSpectator) {
            setStack(stack, !nbt)
            return true
        }
        return false
    }

}
