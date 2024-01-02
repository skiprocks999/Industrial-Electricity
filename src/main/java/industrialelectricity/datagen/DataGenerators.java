package industrialelectricity.datagen;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import electrodynamics.datagen.client.ElectrodynamicsLangKeyProvider.Locale;
import industrialelectricity.References;
import industrialelectricity.datagen.client.IndustialElectricityBlockStateProvider;
import industrialelectricity.datagen.client.IndustrialElectricityItemModelsProvider;
import industrialelectricity.datagen.client.IndustrialElectricityLangKeyProvider;
import industrialelectricity.datagen.client.IndustrialElectricitySoundProvider;
import industrialelectricity.datagen.server.IndustrialElectricityLootTablesProvider;
import industrialelectricity.datagen.server.multiblock.IndustrialElectricityMultiblockProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {

		DataGenerator generator = event.getGenerator();

		PackOutput output = generator.getPackOutput();

		ExistingFileHelper helper = event.getExistingFileHelper();

		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
	
		
		if(event.includeServer()) {
			
			generator.addProvider(true, new LootTableProvider(output, Collections.emptySet(), List.of(new LootTableProvider.SubProviderEntry(IndustrialElectricityLootTablesProvider::new, LootContextParamSets.BLOCK))));
			generator.addProvider(true, new IndustrialElectricityMultiblockProvider(output, helper));
			
			
			
		} 
		
		if(event.includeClient()) {
			
			generator.addProvider(true, new IndustialElectricityBlockStateProvider(output, helper));
			generator.addProvider(true, new IndustrialElectricityItemModelsProvider(output, helper));
			generator.addProvider(true, new IndustrialElectricityLangKeyProvider(output, Locale.EN_US));
			generator.addProvider(true, new IndustrialElectricitySoundProvider(output, helper));
			
		}
		
		
	}

}
