package industrialelectricity.registries;

import industrialelectricity.References;
import industrialelectricity.core.utils.IndustrialTextUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

public class IndustrialElectricityCreativeTabs {
	
	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, References.MOD_ID);
	
	public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder().title(IndustrialTextUtils.creativeTab("main")).icon(() -> new ItemStack(Items.TUFF)).build());

}
