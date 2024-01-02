package industrialelectricity.registries;

import industrialelectricity.References;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IndustrialElectricitySounds {
	
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, References.MOD_ID);
	
	private static RegistryObject<SoundEvent> sound(String name) {
		return SOUNDS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(References.MOD_ID + ":" + name), 16.0F));
	}

}
