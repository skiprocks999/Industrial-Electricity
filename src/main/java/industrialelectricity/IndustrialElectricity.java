package industrialelectricity;

import industrialelectricity.client.ClientRegister;
import industrialelectricity.registries.UnifiedIndustrialElectricityRegister;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(References.MOD_ID)
@EventBusSubscriber(modid = References.MOD_ID, bus = Bus.MOD)
public class IndustrialElectricity {

	public IndustrialElectricity() {

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		UnifiedIndustrialElectricityRegister.register(bus);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}

	@SubscribeEvent
	public static void onCommonSetup(final FMLCommonSetupEvent event) {

	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> ClientRegister.setup());
	}
}
