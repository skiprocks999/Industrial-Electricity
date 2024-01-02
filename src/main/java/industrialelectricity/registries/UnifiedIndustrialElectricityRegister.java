package industrialelectricity.registries;

import net.minecraftforge.eventbus.api.IEventBus;

public class UnifiedIndustrialElectricityRegister {
	
	public static void register(IEventBus bus) {
		
		IndustrialElectricityBlocks.BLOCKS.register(bus);
		IndustrialElectricityContainers.MENU_TYPES.register(bus);
		IndustrialElectricityCreativeTabs.CREATIVE_TABS.register(bus);
		IndustrialElectricityItems.ITEMS.register(bus);
		IndustrialElectricitySounds.SOUNDS.register(bus);
		IndustrialElectricityTiles.BLOCK_ENTITY_TYPES.register(bus);
		
	}

}
