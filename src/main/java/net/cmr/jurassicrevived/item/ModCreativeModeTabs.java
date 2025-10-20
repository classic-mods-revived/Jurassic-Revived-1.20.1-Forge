package net.cmr.jurassicrevived.item;

import net.cmr.jurassicrevived.Config;
import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JRMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> JR_ITEM_TAB = CREATIVE_MODE_TABS.register("jurassicrevived_item_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MOSQUITO_IN_AMBER.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_item_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.TEST_TUBE.get());
                        output.accept(ModItems.SYRINGE.get());
                        output.accept(ModItems.CRUSHED_FOSSIL.get());
                        output.accept(ModItems.MOSQUITO_IN_AMBER.get());
                        output.accept(ModItems.FROZEN_LEECH.get());
                        output.accept(ModItems.CABLE.get());
                        output.accept(ModItems.SCREEN.get());
                        output.accept(ModItems.PROCESSOR.get());
                        output.accept(ModItems.TIRE.get());
                        output.accept(ModItems.CUTTING_BLADES.get());
                        output.accept(ModItems.WRENCH.get());
                        output.accept(ModItems.MAC_N_CHEESE.get());
                        output.accept(ModItems.WALNUT_PUMPKIN_PIE.get());
                        output.accept(ModItems.BANANA_NUT_COOKIE.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> JR_BLOCK_TAB = CREATIVE_MODE_TABS.register("jurassicrevived_block_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.GYPSUM_STONE_BRICKS.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_block_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModBlocks.CAT_PLUSHIE.get());
                        output.accept(ModBlocks.GYPSUM_STONE.get());
                        output.accept(ModBlocks.GYPSUM_COBBLESTONE.get());
                        output.accept(ModBlocks.GYPSUM_STONE_BRICKS.get());
                        output.accept(ModBlocks.SMOOTH_GYPSUM_STONE.get());

                        output.accept(ModBlocks.REINFORCED_STONE.get());
                        output.accept(ModBlocks.REINFORCED_STONE_BRICKS.get());

                        output.accept(ModBlocks.LOW_SECURITY_FENCE_POLE.get());
                        output.accept(ModBlocks.LOW_SECURITY_FENCE_WIRE.get());
                        output.accept(ModBlocks.MEDIUM_SECURITY_FENCE_POLE.get());
                        output.accept(ModBlocks.MEDIUM_SECURITY_FENCE_WIRE.get());

                        output.accept(ModBlocks.ITEM_PIPE.get());
                        output.accept(ModBlocks.FLUID_PIPE.get());
                        output.accept(ModBlocks.POWER_PIPE.get());

                        output.accept(ModBlocks.GENERATOR.get());
                        output.accept(ModBlocks.DNA_EXTRACTOR.get());
                        output.accept(ModBlocks.FOSSIL_GRINDER.get());
                        output.accept(ModBlocks.FOSSIL_CLEANER.get());
                        output.accept(ModBlocks.DNA_HYBRIDIZER.get());
                        output.accept(ModBlocks.EMBRYONIC_MACHINE.get());
                        output.accept(ModBlocks.EMBRYO_CALCIFICATION_MACHINE.get());
                        output.accept(ModBlocks.INCUBATOR.get());

                        output.accept(ModBlocks.STONE_FOSSIL.get());
                        output.accept(ModBlocks.DEEPSLATE_FOSSIL.get());
                        output.accept(ModBlocks.AMBER_ORE.get());
                        output.accept(ModBlocks.DEEPSLATE_ICE_SHARD_ORE.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> JR_PLANT_TAB = CREATIVE_MODE_TABS.register("jurassicrevived_plant_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.ROYAL_FERN.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_plant_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModBlocks.ROYAL_FERN.get());
                        output.accept(ModBlocks.HORSETAIL_FERN.get());
                        output.accept(ModBlocks.WESTERN_SWORD_FERN.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> JR_DNA_TAB = CREATIVE_MODE_TABS.register("jurassicrevived_dna_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.TYRANNOSAURUS_REX_DNA.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_dna_tab"))
                    .displayItems((displayParameters, output) -> {
                        // Skull fossils (alphabetical)
                        output.accept(ModItems.ALBERTOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.APATOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.BARYONYX_SKULL_FOSSIL.get());
                        output.accept(ModItems.BRACHIOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.CARNOTAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.CERATOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.COMPSOGNATHUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.CONCAVENATOR_SKULL_FOSSIL.get());
                        output.accept(ModItems.DEINONYCHUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.DILOPHOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.DIPLODOCUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.EDMONTOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.GALLIMIMUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.GIGANOTOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.GUANLONG_SKULL_FOSSIL.get());
                        output.accept(ModItems.HERRERASAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.MAJUNGASAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.OURANOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.PARASAUROLOPHUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.PROCOMPSOGNATHUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.PROTOCERATOPS_SKULL_FOSSIL.get());
                        output.accept(ModItems.RUGOPS_SKULL_FOSSIL.get());
                        output.accept(ModItems.SHANTUNGOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.SPINOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.STEGOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.STYRACOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.THERIZINOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.TRICERATOPS_SKULL_FOSSIL.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_SKULL_FOSSIL.get());
                        output.accept(ModItems.VELOCIRAPTOR_SKULL_FOSSIL.get());

                        // Fresh skulls (alphabetical)
                        output.accept(ModItems.FRESH_ALBERTOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_APATOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_BARYONYX_SKULL.get());
                        output.accept(ModItems.FRESH_BRACHIOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_CARNOTAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_CERATOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_COMPSOGNATHUS_SKULL.get());
                        output.accept(ModItems.FRESH_CONCAVENATOR_SKULL.get());
                        output.accept(ModItems.FRESH_DEINONYCHUS_SKULL.get());
                        output.accept(ModItems.FRESH_DILOPHOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_DIPLODOCUS_SKULL.get());
                        output.accept(ModItems.FRESH_DISTORTUS_REX_SKULL.get());
                        output.accept(ModItems.FRESH_EDMONTOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_GALLIMIMUS_SKULL.get());
                        output.accept(ModItems.FRESH_GIGANOTOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_GUANLONG_SKULL.get());
                        output.accept(ModItems.FRESH_HERRERASAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_INDOMINUS_REX_SKULL.get());
                        output.accept(ModItems.FRESH_MAJUNGASAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_OURANOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_PARASAUROLOPHUS_SKULL.get());
                        output.accept(ModItems.FRESH_PROCOMPSOGNATHUS_SKULL.get());
                        output.accept(ModItems.FRESH_PROTOCERATOPS_SKULL.get());
                        output.accept(ModItems.FRESH_RUGOPS_SKULL.get());
                        output.accept(ModItems.FRESH_SHANTUNGOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_STEGOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_STEGOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_STYRACOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_THERIZINOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_TRICERATOPS_SKULL.get());
                        output.accept(ModItems.FRESH_TYRANNOSAURUS_REX_SKULL.get());
                        output.accept(ModItems.FRESH_VELOCIRAPTOR_SKULL.get());

                        // Tissue (alphabetical)
                        output.accept(ModItems.ALBERTOSAURUS_TISSUE.get());
                        output.accept(ModItems.APATOSAURUS_TISSUE.get());
                        output.accept(ModItems.BARYONYX_TISSUE.get());
                        output.accept(ModItems.BRACHIOSAURUS_TISSUE.get());
                        output.accept(ModItems.CARNOTAURUS_TISSUE.get());
                        output.accept(ModItems.CERATOSAURUS_TISSUE.get());
                        output.accept(ModItems.COMPSOGNATHUS_TISSUE.get());
                        output.accept(ModItems.CONCAVENATOR_TISSUE.get());
                        output.accept(ModItems.DEINONYCHUS_TISSUE.get());
                        output.accept(ModItems.DILOPHOSAURUS_TISSUE.get());
                        output.accept(ModItems.DIPLODOCUS_TISSUE.get());
                        output.accept(ModItems.DISTORTUS_REX_TISSUE.get());
                        output.accept(ModItems.EDMONTOSAURUS_TISSUE.get());
                        output.accept(ModItems.GALLIMIMUS_TISSUE.get());
                        output.accept(ModItems.GIGANOTOSAURUS_TISSUE.get());
                        output.accept(ModItems.GUANLONG_TISSUE.get());
                        output.accept(ModItems.HERRERASAURUS_TISSUE.get());
                        output.accept(ModItems.INDOMINUS_REX_TISSUE.get());
                        output.accept(ModItems.MAJUNGASAURUS_TISSUE.get());
                        output.accept(ModItems.OURANOSAURUS_TISSUE.get());
                        output.accept(ModItems.PARASAUROLOPHUS_TISSUE.get());
                        output.accept(ModItems.PROCOMPSOGNATHUS_TISSUE.get());
                        output.accept(ModItems.PROTOCERATOPS_TISSUE.get());
                        output.accept(ModItems.RUGOPS_TISSUE.get());
                        output.accept(ModItems.SHANTUNGOSAURUS_TISSUE.get());
                        output.accept(ModItems.SPINOSAURUS_TISSUE.get());
                        output.accept(ModItems.STEGOSAURUS_TISSUE.get());
                        output.accept(ModItems.STYRACOSAURUS_TISSUE.get());
                        output.accept(ModItems.THERIZINOSAURUS_TISSUE.get());
                        output.accept(ModItems.TRICERATOPS_TISSUE.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_TISSUE.get());
                        output.accept(ModItems.VELOCIRAPTOR_TISSUE.get());

                        // DNA (alphabetical)
                        output.accept(ModItems.ALBERTOSAURUS_DNA.get());
                        output.accept(ModItems.APATOSAURUS_DNA.get());
                        output.accept(ModItems.BARYONYX_DNA.get());
                        output.accept(ModItems.BRACHIOSAURUS_DNA.get());
                        output.accept(ModItems.CARNOTAURUS_DNA.get());
                        output.accept(ModItems.CERATOSAURUS_DNA.get());
                        output.accept(ModItems.COMPSOGNATHUS_DNA.get());
                        output.accept(ModItems.CONCAVENATOR_DNA.get());
                        output.accept(ModItems.DEINONYCHUS_DNA.get());
                        output.accept(ModItems.DILOPHOSAURUS_DNA.get());
                        output.accept(ModItems.DIPLODOCUS_DNA.get());
                        output.accept(ModItems.DISTORTUS_REX_DNA.get());
                        output.accept(ModItems.EDMONTOSAURUS_DNA.get());
                        output.accept(ModItems.GALLIMIMUS_DNA.get());
                        output.accept(ModItems.GIGANOTOSAURUS_DNA.get());
                        output.accept(ModItems.GUANLONG_DNA.get());
                        output.accept(ModItems.HERRERASAURUS_DNA.get());
                        output.accept(ModItems.INDOMINUS_REX_DNA.get());
                        output.accept(ModItems.MAJUNGASAURUS_DNA.get());
                        output.accept(ModItems.OURANOSAURUS_DNA.get());
                        output.accept(ModItems.PARASAUROLOPHUS_DNA.get());
                        output.accept(ModItems.PROCOMPSOGNATHUS_DNA.get());
                        output.accept(ModItems.PROTOCERATOPS_DNA.get());
                        output.accept(ModItems.RUGOPS_DNA.get());
                        output.accept(ModItems.SHANTUNGOSAURUS_DNA.get());
                        output.accept(ModItems.SPINOSAURUS_DNA.get());
                        output.accept(ModItems.STEGOSAURUS_DNA.get());
                        output.accept(ModItems.STYRACOSAURUS_DNA.get());
                        output.accept(ModItems.THERIZINOSAURUS_DNA.get());
                        output.accept(ModItems.TRICERATOPS_DNA.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_DNA.get());
                        output.accept(ModItems.VELOCIRAPTOR_DNA.get());

                        // Syringes (alphabetical)
                        output.accept(ModItems.ALBERTOSAURUS_SYRINGE.get());
                        output.accept(ModItems.APATOSAURUS_SYRINGE.get());
                        output.accept(ModItems.BARYONYX_SYRINGE.get());
                        output.accept(ModItems.BRACHIOSAURUS_SYRINGE.get());
                        output.accept(ModItems.CARNOTAURUS_SYRINGE.get());
                        output.accept(ModItems.CERATOSAURUS_SYRINGE.get());
                        output.accept(ModItems.COMPSOGNATHUS_SYRINGE.get());
                        output.accept(ModItems.CONCAVENATOR_SYRINGE.get());
                        output.accept(ModItems.DEINONYCHUS_SYRINGE.get());
                        output.accept(ModItems.DILOPHOSAURUS_SYRINGE.get());
                        output.accept(ModItems.DIPLODOCUS_SYRINGE.get());
                        output.accept(ModItems.DISTORTUS_REX_SYRINGE.get());
                        output.accept(ModItems.EDMONTOSAURUS_SYRINGE.get());
                        output.accept(ModItems.GALLIMIMUS_SYRINGE.get());
                        output.accept(ModItems.GIGANOTOSAURUS_SYRINGE.get());
                        output.accept(ModItems.GUANLONG_SYRINGE.get());
                        output.accept(ModItems.HERRERASAURUS_SYRINGE.get());
                        output.accept(ModItems.INDOMINUS_REX_SYRINGE.get());
                        output.accept(ModItems.MAJUNGASAURUS_SYRINGE.get());
                        output.accept(ModItems.OURANOSAURUS_SYRINGE.get());
                        output.accept(ModItems.PARASAUROLOPHUS_SYRINGE.get());
                        output.accept(ModItems.PROCOMPSOGNATHUS_SYRINGE.get());
                        output.accept(ModItems.PROTOCERATOPS_SYRINGE.get());
                        output.accept(ModItems.RUGOPS_SYRINGE.get());
                        output.accept(ModItems.SHANTUNGOSAURUS_SYRINGE.get());
                        output.accept(ModItems.SPINOSAURUS_SYRINGE.get());
                        output.accept(ModItems.STEGOSAURUS_SYRINGE.get());
                        output.accept(ModItems.STYRACOSAURUS_SYRINGE.get());
                        output.accept(ModItems.THERIZINOSAURUS_SYRINGE.get());
                        output.accept(ModItems.TRICERATOPS_SYRINGE.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_SYRINGE.get());
                        output.accept(ModItems.VELOCIRAPTOR_SYRINGE.get());

                        // Eggs (alphabetical)
                        output.accept(ModItems.ALBERTOSAURUS_EGG.get());
                        output.accept(ModItems.APATOSAURUS_EGG.get());
                        output.accept(ModItems.BARYONYX_EGG.get());
                        output.accept(ModItems.BRACHIOSAURUS_EGG.get());
                        output.accept(ModItems.CARNOTAURUS_EGG.get());
                        output.accept(ModItems.CERATOSAURUS_EGG.get());
                        output.accept(ModItems.COMPSOGNATHUS_EGG.get());
                        output.accept(ModItems.CONCAVENATOR_EGG.get());
                        output.accept(ModItems.DEINONYCHUS_EGG.get());
                        output.accept(ModItems.DILOPHOSAURUS_EGG.get());
                        output.accept(ModItems.DIPLODOCUS_EGG.get());
                        output.accept(ModItems.DISTORTUS_REX_EGG.get());
                        output.accept(ModItems.EDMONTOSAURUS_EGG.get());
                        output.accept(ModItems.GALLIMIMUS_EGG.get());
                        output.accept(ModItems.GIGANOTOSAURUS_EGG.get());
                        output.accept(ModItems.GUANLONG_EGG.get());
                        output.accept(ModItems.HERRERASAURUS_EGG.get());
                        output.accept(ModItems.INDOMINUS_REX_EGG.get());
                        output.accept(ModItems.MAJUNGASAURUS_EGG.get());
                        output.accept(ModItems.OURANOSAURUS_EGG.get());
                        output.accept(ModItems.PARASAUROLOPHUS_EGG.get());
                        output.accept(ModItems.PROCOMPSOGNATHUS_EGG.get());
                        output.accept(ModItems.PROTOCERATOPS_EGG.get());
                        output.accept(ModItems.RUGOPS_EGG.get());
                        output.accept(ModItems.SHANTUNGOSAURUS_EGG.get());
                        output.accept(ModItems.SPINOSAURUS_EGG.get());
                        output.accept(ModItems.STEGOSAURUS_EGG.get());
                        output.accept(ModItems.STYRACOSAURUS_EGG.get());
                        output.accept(ModItems.THERIZINOSAURUS_EGG.get());
                        output.accept(ModItems.TRICERATOPS_EGG.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_EGG.get());
                        output.accept(ModItems.VELOCIRAPTOR_EGG.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> JR_DINO_TAB = CREATIVE_MODE_TABS.register("jurassicrevived_dino_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.TYRANNOSAURUS_REX_SPAWN_EGG.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_dino_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.ALBERTOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.APATOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.BARYONYX_SPAWN_EGG.get());
                        output.accept(ModItems.BRACHIOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.CARNOTAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.CERATOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.COMPSOGNATHUS_SPAWN_EGG.get());
                        output.accept(ModItems.CONCAVENATOR_SPAWN_EGG.get());
                        output.accept(ModItems.DEINONYCHUS_SPAWN_EGG.get());
                        output.accept(ModItems.DILOPHOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.DIPLODOCUS_SPAWN_EGG.get());
                        output.accept(ModItems.DISTORTUS_REX_SPAWN_EGG.get());
                        output.accept(ModItems.EDMONTOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.GALLIMIMUS_SPAWN_EGG.get());
                        output.accept(ModItems.GIGANOTOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.GUANLONG_SPAWN_EGG.get());
                        output.accept(ModItems.HERRERASAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.OURANOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.INDOMINUS_REX_SPAWN_EGG.get());
                        output.accept(ModItems.MAJUNGASAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.PARASAUROLOPHUS_SPAWN_EGG.get());
                        output.accept(ModItems.PROCOMPSOGNATHUS_SPAWN_EGG.get());
                        output.accept(ModItems.PROTOCERATOPS_SPAWN_EGG.get());
                        output.accept(ModItems.RUGOPS_SPAWN_EGG.get());
                        output.accept(ModItems.SHANTUNGOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.SPINOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.STEGOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.STYRACOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.THERIZINOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.TRICERATOPS_SPAWN_EGG.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_SPAWN_EGG.get());
                        output.accept(ModItems.VELOCIRAPTOR_SPAWN_EGG.get());
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
