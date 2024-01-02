package industrialelectricity.registries;

import org.apache.commons.compress.utils.Sets;

import industrialelectricity.References;
import industrialelectricity.common.tile.monopole.TileMonopole;
import industrialelectricity.core.multiblock.TileMultiblockSlave;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IndustrialElectricityTiles {
	
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, References.MOD_ID);
	
	public static final RegistryObject<BlockEntityType<TileMultiblockSlave>> TILE_MULTIBLOCK_SLAVE = BLOCK_ENTITY_TYPES.register("multiblockslave", () -> new BlockEntityType<>(TileMultiblockSlave::new, Sets.newHashSet(IndustrialElectricityBlocks.BLOCK_MULTIBLOCK_SLAVE.get()), null));
	
	public static final RegistryObject<BlockEntityType<TileMonopole>> TILE_MONOPOLE = BLOCK_ENTITY_TYPES.register("monopole", () -> new BlockEntityType<>(TileMonopole::new, Sets.newHashSet(IndustrialElectricityBlocks.BLOCK_MONOPOLE_CONTROLER.get()), null));

}
