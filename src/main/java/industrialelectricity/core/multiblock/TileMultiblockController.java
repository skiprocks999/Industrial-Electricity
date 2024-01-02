package industrialelectricity.core.multiblock;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.gas.GasTank;
import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.Scheduler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public abstract class TileMultiblockController extends TileReplaceable {

	public final List<TileMultiblockSlave> slaveList = new ArrayList<>();

	public final Property<List<BlockPos>> slavePositions = property(new Property<List<BlockPos>>(PropertyType.BlockPosList, "slavepositions", List.of())).onLoad((prop, list) -> {

		if (level.isClientSide) {
			return;
		}

		Scheduler.schedule(2, () -> {
			slaveList.clear();
			list.forEach(blockPos -> {
				slaveList.add((TileMultiblockSlave) level.getBlockEntity(worldPosition));
			});
		});

	});

	public final Property<Boolean> isFormed = property(new Property<>(PropertyType.Boolean, "isformed", false));

	private boolean isDestroyed = false;

	public TileMultiblockController(BlockEntityType<?> tileEntityTypeIn, BlockPos worldPos, BlockState blockState) {
		super(tileEntityTypeIn, worldPos, blockState);

		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentTickable(this).tickServer(this::tickServerTotal).tickCommon(this::tickCommon).tickClient(this::tickClient));

	}

	private void tickServerTotal(ComponentTickable tickable) {

		if (isFormed.get()) {
			tickServer(tickable);
		} else if (tickable.getTicks() % 10 == 0) {
			checkFormed();
			if (isFormed.get()) {

				formMultiblock();

			}
		}

	}

	public void tickServer(ComponentTickable tickable) {

	}

	public void tickCommon(ComponentTickable tickable) {

	}

	public void tickClient(ComponentTickable tickable) {

	}

	private void checkFormed() {

		Direction facing = getFacing();

		List<MultiblockSlaveNode> nodes = Multiblock.getNodes(level, getResourceKey(), facing);

		BlockPos nodePos;
		BlockState nodeState;

		boolean formed = true;

		for (MultiblockSlaveNode node : nodes) {

			nodePos = getBlockPos().offset(node.offset());

			nodeState = level.getBlockState(nodePos);

			if (node.taggedBlocks() != null && !nodeState.is(node.taggedBlocks()) || nodeState != node.replaceState()) {

				formed = false;
				break;
			}

			if (node.taggedBlocks() != null) {

				for (net.minecraft.world.level.block.state.properties.Property<?> prop : node.placeState().getProperties()) {

					if (!nodeState.hasProperty(prop)) {

						formed = false;
						break;
					}

				}

				if (!formed) {
					break;
				}

			}

		}

		if (formed) {
			isFormed.set(true);
		}

	}

	private void formMultiblock() {

		Direction facing = getFacing();

		List<MultiblockSlaveNode> nodes = Multiblock.getNodes(level, getResourceKey(), facing);

		BlockPos nodePos;

		TileMultiblockSlave slave;

		int index = 0;

		for (MultiblockSlaveNode node : nodes) {

			nodePos = getBlockPos().offset(node.offset());

			slavePositions.get().add(nodePos);

			level.setBlockAndUpdate(nodePos, node.placeState().setValue(GenericEntityBlock.FACING, getFacing()));

			slave = (TileMultiblockSlave) level.getBlockEntity(nodePos);

			slaveList.add(slave);

			slave.setDisguise(node.replaceState());

			slave.controller.set(getBlockPos());

			slave.index.set(index);

			index++;

		}
		
		slavePositions.forceDirty();

	}

	private void destroyMultiblock() {

		isDestroyed = true;

		for (BlockPos pos : slavePositions.get()) {

			level.destroyBlock(pos, false);

		}

		isFormed.set(false);

		slavePositions.set(new ArrayList<>());
		
		slavePositions.forceDirty();
		
		slaveList.clear();
		
		isDestroyed = false;

	}

	public int getSlaveComparatorSignal(TileMultiblockSlave slave) {
		return getComparatorSignal();
	}

	public int getSlaveDirectSignal(TileMultiblockSlave slave, Direction slaveDir) {
		return getDirectSignal(slaveDir);
	}

	public int getSlaveSignal(TileMultiblockSlave slave, Direction slaveDir) {
		return getSignal(slaveDir);
	}

	public boolean isSlavePoweredByRedstone(TileMultiblockSlave slave) {
		return isPoweredByRedstone();
	}

	@Override
	public void onBlockDestroyed() {
		super.onBlockDestroyed();
		if (level.isClientSide) {
			destroyMultiblock();
		}
	}

	public void onSlaveBlockStateUpdate(TileMultiblockSlave slave, BlockState slaveOldState, BlockState slaveNewState) {

	}

	public void onSlaveEnergyChange(TileMultiblockSlave slave, ComponentElectrodynamic slaveCap) {

	}

	public void onSlaveEntityInside(TileMultiblockSlave slave, BlockState slaveState, Level level, BlockPos slavePos, Entity slaveEntity) {

	}

	public void onSlaveFluidTankChange(TileMultiblockSlave slave, FluidTank slaveTank) {

	}

	public void onSlaveGasTankChange(TileMultiblockSlave slave, GasTank slaveTank) {

	}

	public void onSlaveInventoryChange(TileMultiblockSlave slave, ComponentInventory slaveInv, int slaveSlot) {

	}

	public InteractionResult slaveUse(TileMultiblockSlave slave, Player player, InteractionHand hand, BlockHitResult hitResult) {
		return use(player, hand, hitResult);
	}

	public void onSlaveNeightborChanged(TileMultiblockSlave slave, BlockPos slaveNeighbor, boolean blockStateTrigger) {

	}

	public void onSlavePlace(TileMultiblockSlave slave, BlockState slaveOldState, boolean isMoving) {

	}

	public void onSlaveDestroyed(TileMultiblockSlave slave) {
		if (isDestroyed) {
			return;
		}
		if (!level.isClientSide) {
			destroyMultiblock();
		}
	}

	public abstract ResourceLocation getMultiblockId();

	public abstract ResourceKey<Multiblock> getResourceKey();

}
