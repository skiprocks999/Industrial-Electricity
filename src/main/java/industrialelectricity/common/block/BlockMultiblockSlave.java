package industrialelectricity.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import industrialelectricity.core.multiblock.TileMultiblockSlave;

public class BlockMultiblockSlave extends GenericMachineBlock {

	public BlockMultiblockSlave() {
		super(TileMultiblockSlave::new);
	}

}
