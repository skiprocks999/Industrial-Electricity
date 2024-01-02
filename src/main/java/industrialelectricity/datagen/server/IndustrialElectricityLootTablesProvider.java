package industrialelectricity.datagen.server;

import java.util.List;

import electrodynamics.datagen.server.ElectrodynamicsLootTablesProvider;
import industrialelectricity.References;
import industrialelectricity.registries.IndustrialElectricityBlocks;
import net.minecraft.world.level.block.Block;

public class IndustrialElectricityLootTablesProvider extends ElectrodynamicsLootTablesProvider {

	public IndustrialElectricityLootTablesProvider() {
		super(References.MOD_ID);
	}
	
	@Override
	protected void generate() {
		
	}
	
	@Override
	public List<Block> getExcludedBlocks() {
		return List.of(IndustrialElectricityBlocks.BLOCK_MULTIBLOCK_SLAVE.get());
	}
	
}
