package net.cmr.jurassicrevived;

import com.mojang.logging.LogUtils;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.block.entity.custom.ModBlockEntities;
import net.cmr.jurassicrevived.datagen.custom.ConfigCondition;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.entity.client.*;
import net.cmr.jurassicrevived.event.FenceDiagonalUpdateHandler;
import net.cmr.jurassicrevived.item.ModCreativeModeTabs;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.loot.ModLootProviders;
import net.cmr.jurassicrevived.recipe.ModRecipes;
import net.cmr.jurassicrevived.screen.ModMenuTypes;
import net.cmr.jurassicrevived.screen.custom.*;
import net.cmr.jurassicrevived.util.FenceClimbClientHandler;
import net.cmr.jurassicrevived.util.FenceClimbHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JRMod.MOD_ID)
public class JRMod {
    public static final String MOD_ID = "jurassicrevived";
    public static final Logger LOGGER = LogUtils.getLogger();

    // Cache the non-deprecated instance context
    private static ModLoadingContext MOD_CTX;

    public JRMod(FMLJavaModLoadingContext javaCtx) {
        IEventBus modEventBus = javaCtx.getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModEntities.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        ModLootProviders.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        FenceClimbHandler.register();

        ConfigCondition.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        MinecraftForge.EVENT_BUS.addListener(FenceDiagonalUpdateHandler::onNeighborNotify);
        MinecraftForge.EVENT_BUS.addListener(FenceDiagonalUpdateHandler::onEntityPlace);
        MinecraftForge.EVENT_BUS.addListener(FenceDiagonalUpdateHandler::onBreak);

        javaCtx.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.ROYAL_FERN.getId(), ModBlocks.POTTED_ROYAL_FERN);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.HORSETAIL_FERN.getId(), ModBlocks.POTTED_HORSETAIL_FERN);
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.WESTERN_SWORD_FERN.getId(), ModBlocks.POTTED_WESTERN_SWORD_FERN);
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {

        }

        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {

        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // Client-only events
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.BRACHIOSAURUS.get(), BrachiosaurusRenderer::new);
            EntityRenderers.register(ModEntities.CERATOSAURUS.get(), CeratosaurusRenderer::new);
            EntityRenderers.register(ModEntities.COMPSOGNATHUS.get(), CompsognathusRenderer::new);
            EntityRenderers.register(ModEntities.DILOPHOSAURUS.get(), DilophosaurusRenderer::new);
            EntityRenderers.register(ModEntities.FDUCK.get(), FDuckRenderer::new);
            EntityRenderers.register(ModEntities.TYRANNOSAURUS_REX.get(), TyrannosaurusRexRenderer::new);
            EntityRenderers.register(ModEntities.VELOCIRAPTOR.get(), VelociraptorRenderer::new);
            // Config screen is registered in ClientConfigScreenBinder

            FenceClimbClientHandler.register();

            MenuScreens.register(ModMenuTypes.GENERATOR_MENU.get(), GeneratorScreen::new);
            MenuScreens.register(ModMenuTypes.DNA_EXTRACTOR_MENU.get(), DNAExtractorScreen::new);
            MenuScreens.register(ModMenuTypes.FOSSIL_GRINDER_MENU.get(), FossilGrinderScreen::new);
            MenuScreens.register(ModMenuTypes.FOSSIL_CLEANER_MENU.get(), FossilCleanerScreen::new);
            MenuScreens.register(ModMenuTypes.DNA_HYBRIDIZER_MENU.get(), DNAHybridizerScreen::new);
            MenuScreens.register(ModMenuTypes.EMBRYONIC_MACHINE_MENU.get(), EmbryonicMachineScreen::new);
            MenuScreens.register(ModMenuTypes.EMBRYO_CALCIFICATION_MACHINE_MENU.get(), EmbryoCalcificationMachineScreen::new);
            MenuScreens.register(ModMenuTypes.INCUBATOR_MENU.get(), IncubatorScreen::new);
        }
    }
}
