package industrialelectricity.datagen.client;

import electrodynamics.datagen.client.ElectrodynamicsLangKeyProvider;
import industrialelectricity.References;
import net.minecraft.data.PackOutput;

public class IndustrialElectricityLangKeyProvider extends ElectrodynamicsLangKeyProvider {

	public IndustrialElectricityLangKeyProvider(PackOutput output, Locale local) {
		super(output, local, References.MOD_ID);
	}
	
	@Override
	protected void addTranslations() {
		
	}

}
