package teamport.moonmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.moonmod.block.MMBlocks;
import teamport.moonmod.entity.EntityUFO;
import teamport.moonmod.entity.render.UFOModel;
import teamport.moonmod.entity.render.UFORenderer;
import teamport.moonmod.item.MMItems;
import teamport.moonmod.world.BiomeProviderMoon;
import teamport.moonmod.world.ModDimensions;
import teamport.moonmod.world.WorldTypeMoon;
import teamport.moonmod.world.biome.MoonBiomes;
import turniplabs.halplibe.helper.EntityHelper;
import turniplabs.halplibe.helper.SoundHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import useless.dragonfly.helper.ModelHelper;


public class MoonMod implements ModInitializer, GameStartEntrypoint, ClientStartEntrypoint {
	public static final String MOD_ID = "moonmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static WorldType MOON_WORLD;

	@Override
	public void onInitialize() {
	  LOGGER.info("MoonMod has been initialized. Have fun, brave astronaut!");
	}

	@Override
	public void beforeClientStart() {
		EntityHelper.Client.assignEntityRenderer(EntityUFO.class,
			new UFORenderer(ModelHelper.getOrCreateEntityModel(MOD_ID,
				"entity/ufo.json",
				UFOModel.class)));

		SoundHelper.Client.addSound(MOD_ID, "yippee.wav");

		MobInfoRegistry.register(EntityUFO.class, "moon.ufo.name", "moon.ufo.desc", 10, 10, new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(new ItemStack(Item.dustRedstone), 1.0f, 0 ,2)});
	}

	@Override
	public void afterClientStart() {

	}

	@Override
	public void beforeGameStart() {
		MMConfig.cfg.loadConfig();

		new MoonBiomes().initializeBiomes();
		BiomeProviderMoon.init();
		MOON_WORLD = WorldTypes.register(MoonMod.MOD_ID + ":moon", new WorldTypeMoon("moonmod.worldtype.moon"));

		new MMBlocks().initializeBlocks();
		new MMItems().initializeItems();
		ModDimensions.register();

		EntityHelper.Core.createEntity(EntityUFO.class, MMConfig.cfg.getInt("IDs.ufo"), "ufo");
	}

	@Override
	public void afterGameStart() {
	}
}
