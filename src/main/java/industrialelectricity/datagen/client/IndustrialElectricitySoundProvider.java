package industrialelectricity.datagen.client;

import electrodynamics.datagen.client.ElectrodynamicsSoundProvider;
import industrialelectricity.References;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public class IndustrialElectricitySoundProvider extends ElectrodynamicsSoundProvider {

	public IndustrialElectricitySoundProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, helper, References.MOD_ID);
	}
	
	@Override
	public void registerSounds() {
		
	}

}
