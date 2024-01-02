package industrialelectricity.registries;

import electrodynamics.prefab.block.GenericMachineBlock;
import industrialelectricity.References;
import industrialelectricity.common.block.BlockMonopoleController;
import industrialelectricity.core.multiblock.TileMultiblockSlave;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IndustrialElectricityBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.MOD_ID);
	
	public static final RegistryObject<Block> BLOCK_MULTIBLOCK_SLAVE = BLOCKS.register("multiblockslave", () -> new GenericMachineBlock(TileMultiblockSlave::new));
	
	public static final RegistryObject<Block> BLOCK_MONOPOLE_CONTROLER = BLOCKS.register("monopolecontroller", () -> new BlockMonopoleController());

}
