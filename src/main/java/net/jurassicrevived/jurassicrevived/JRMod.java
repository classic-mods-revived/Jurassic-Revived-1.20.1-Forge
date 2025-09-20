package net.jurassicrevived.jurassicrevived;

import com.mojang.logging.LogUtils;
import net.jurassicrevived.jurassicrevived.block.ModBlocks;
import net.jurassicrevived.jurassicrevived.block.entity.custom.ModBlockEntities;
import net.jurassicrevived.jurassicrevived.entity.ModEntities;
import net.jurassicrevived.jurassicrevived.entity.client.VelociraptorRenderer;
import net.jurassicrevived.jurassicrevived.entity.client.BrachiosaurusRenderer;
import net.jurassicrevived.jurassicrevived.entity.client.CeratosaurusRenderer;
import net.jurassicrevived.jurassicrevived.entity.client.DilophosaurusRenderer;
import net.jurassicrevived.jurassicrevived.event.FenceDiagonalUpdateHandler;
import net.jurassicrevived.jurassicrevived.item.ModCreativeModeTabs;
import net.jurassicrevived.jurassicrevived.item.ModItems;
import net.jurassicrevived.jurassicrevived.loot.ModLootProviders;
import net.jurassicrevived.jurassicrevived.recipe.ModRecipes;
import net.jurassicrevived.jurassicrevived.screen.ModMenuTypes;
import net.jurassicrevived.jurassicrevived.screen.custom.DNAExtractorScreen;
import net.jurassicrevived.jurassicrevived.screen.custom.FossilCleanerScreen;
import net.jurassicrevived.jurassicrevived.screen.custom.FossilGrinderScreen;
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
import net.minecraftforge.client.ConfigScreenHandler;
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
            EntityRenderers.register(ModEntities.VELOCIRAPTOR.get(), VelociraptorRenderer::new);
            EntityRenderers.register(ModEntities.CERATOSAURUS.get(), CeratosaurusRenderer::new);
            EntityRenderers.register(ModEntities.BRACHIOSAURUS.get(), BrachiosaurusRenderer::new);
            EntityRenderers.register(ModEntities.DILOPHOSAURUS.get(), DilophosaurusRenderer::new);
            // Config screen is registered in ClientConfigScreenBinder

            MenuScreens.register(ModMenuTypes.DNA_EXTRACTOR_MENU.get(), DNAExtractorScreen::new);
            MenuScreens.register(ModMenuTypes.FOSSIL_GRINDER_MENU.get(), FossilGrinderScreen::new);
            MenuScreens.register(ModMenuTypes.FOSSIL_CLEANER_MENU.get(), FossilCleanerScreen::new);
        }
    }
}
