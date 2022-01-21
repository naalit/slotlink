package badasintended.slotlink.datagen

import badasintended.slotlink.datagen.provider.LootTableProvider
import badasintended.slotlink.datagen.provider.RecipeProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class SlotlinkDataGenerator : DataGeneratorEntrypoint {

    override fun onInitializeDataGenerator(generator: FabricDataGenerator) {
        generator.addProvider(::LootTableProvider)
        generator.addProvider(::RecipeProvider)
    }

}