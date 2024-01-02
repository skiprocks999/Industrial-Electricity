package industrialelectricity.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import industrialelectricity.common.tile.monopole.TileMonopole;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class BlockMonopoleController extends GenericMachineBlock {

	public BlockMonopoleController() {
		super(TileMonopole::new);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.INVISIBLE;
	}

}
