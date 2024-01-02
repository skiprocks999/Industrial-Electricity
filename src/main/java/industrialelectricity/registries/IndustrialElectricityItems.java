package industrialelectricity.registries;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.creativetab.CreativeTabSupplier;
import electrodynamics.common.blockitem.types.BlockItemDescriptable;
import industrialelectricity.References;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IndustrialElectricityItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MOD_ID);
	
	public static final RegistryObject<Item> ITEM_MONOPOLE_CONTROLLER = ITEMS.register("monopolecontroller", () -> new BlockItemDescriptable(() -> IndustrialElectricityBlocks.BLOCK_MONOPOLE_CONTROLER.get(), new Item.Properties(), () -> IndustrialElectricityCreativeTabs.MAIN.get()));
	
	@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = References.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	private static class ElectroCreativeRegistry {

		@SubscribeEvent
		public static void registerItems(BuildCreativeModeTabContentsEvent event) {

			ITEMS.getEntries().forEach(reg -> {

				CreativeTabSupplier supplier = (CreativeTabSupplier) reg.get();

				if (supplier.hasCreativeTab() && supplier.isAllowedInCreativeTab(event.getTab())) {
					List<ItemStack> toAdd = new ArrayList<>();
					supplier.addCreativeModeItems(event.getTab(), toAdd);
					event.acceptAll(toAdd);
				}

			});

		}

	}

}
