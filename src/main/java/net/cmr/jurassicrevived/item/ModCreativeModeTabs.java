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
                    }).build());

    public static final RegistryObject<CreativeModeTab> JR_BLOCK_TAB = CREATIVE_MODE_TABS.register("jurassicrevived_block_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.GYPSUM_STONE_BRICKS.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_block_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModBlocks.CAT_PLUSHIE.get());
                        output.accept(ModBlocks.GYPSUM_STONE.get());
                        output.accept(ModBlocks.GYPSUM_COBBLESTONE.get());
                        output.accept(ModBlocks.GYPSUM_STONE_BRICKS.get());

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

    public static final RegistryObject<CreativeModeTab> JR_FOSSIL_TAB = CREATIVE_MODE_TABS.register("jurassicrevived_fossil_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.TYRANNOSAURUS_REX_SKULL_FOSSIL.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_fossil_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.BRACHIOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.CERATOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.COMPSOGNATHUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.DILOPHOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.DIPLODOCUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.GALLIMIMUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.PARASAUROLOPHUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.SPINOSAURUS_SKULL_FOSSIL.get());
                        output.accept(ModItems.TRICERATOPS_SKULL_FOSSIL.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_SKULL_FOSSIL.get());
                        output.accept(ModItems.VELOCIRAPTOR_SKULL_FOSSIL.get());

                        output.accept(ModItems.FRESH_BRACHIOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_CERATOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_COMPSOGNATHUS_SKULL.get());
                        output.accept(ModItems.FRESH_DILOPHOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_DIPLODOCUS_SKULL.get());
                        output.accept(ModItems.FRESH_GALLIMIMUS_SKULL.get());
                        output.accept(ModItems.FRESH_INDOMINUS_REX_SKULL.get());
                        output.accept(ModItems.FRESH_PARASAUROLOPHUS_SKULL.get());
                        output.accept(ModItems.FRESH_SPINOSAURUS_SKULL.get());
                        output.accept(ModItems.FRESH_TRICERATOPS_SKULL.get());
                        output.accept(ModItems.FRESH_TYRANNOSAURUS_REX_SKULL.get());
                        output.accept(ModItems.FRESH_VELOCIRAPTOR_SKULL.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> JR_DNA_TAB = CREATIVE_MODE_TABS.register("jurassicrevived_dna_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.TYRANNOSAURUS_REX_DNA.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_dna_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.BRACHIOSAURUS_TISSUE.get());
                        output.accept(ModItems.CERATOSAURUS_TISSUE.get());
                        output.accept(ModItems.COMPSOGNATHUS_TISSUE.get());
                        output.accept(ModItems.DILOPHOSAURUS_TISSUE.get());
                        output.accept(ModItems.DIPLODOCUS_TISSUE.get());
                        output.accept(ModItems.GALLIMIMUS_TISSUE.get());
                        output.accept(ModItems.INDOMINUS_REX_TISSUE.get());
                        output.accept(ModItems.PARASAUROLOPHUS_TISSUE.get());
                        output.accept(ModItems.SPINOSAURUS_TISSUE.get());
                        output.accept(ModItems.TRICERATOPS_TISSUE.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_TISSUE.get());
                        output.accept(ModItems.VELOCIRAPTOR_TISSUE.get());

                        output.accept(ModItems.BRACHIOSAURUS_DNA.get());
                        output.accept(ModItems.CERATOSAURUS_DNA.get());
                        output.accept(ModItems.COMPSOGNATHUS_DNA.get());
                        output.accept(ModItems.DILOPHOSAURUS_DNA.get());
                        output.accept(ModItems.DIPLODOCUS_DNA.get());
                        output.accept(ModItems.GALLIMIMUS_DNA.get());
                        output.accept(ModItems.INDOMINUS_REX_DNA.get());
                        output.accept(ModItems.PARASAUROLOPHUS_DNA.get());
                        output.accept(ModItems.SPINOSAURUS_DNA.get());
                        output.accept(ModItems.TRICERATOPS_DNA.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_DNA.get());
                        output.accept(ModItems.VELOCIRAPTOR_DNA.get());

                        output.accept(ModItems.BRACHIOSAURUS_SYRINGE.get());
                        output.accept(ModItems.CERATOSAURUS_SYRINGE.get());
                        output.accept(ModItems.COMPSOGNATHUS_SYRINGE.get());
                        output.accept(ModItems.DILOPHOSAURUS_SYRINGE.get());
                        output.accept(ModItems.DIPLODOCUS_SYRINGE.get());
                        output.accept(ModItems.GALLIMIMUS_SYRINGE.get());
                        output.accept(ModItems.INDOMINUS_REX_SYRINGE.get());
                        output.accept(ModItems.PARASAUROLOPHUS_SYRINGE.get());
                        output.accept(ModItems.SPINOSAURUS_SYRINGE.get());
                        output.accept(ModItems.TRICERATOPS_SYRINGE.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_SYRINGE.get());
                        output.accept(ModItems.VELOCIRAPTOR_SYRINGE.get());

                        output.accept(ModItems.BRACHIOSAURUS_EGG.get());
                        output.accept(ModItems.CERATOSAURUS_EGG.get());
                        output.accept(ModItems.COMPSOGNATHUS_EGG.get());
                        output.accept(ModItems.DILOPHOSAURUS_EGG.get());
                        output.accept(ModItems.DIPLODOCUS_EGG.get());
                        output.accept(ModItems.GALLIMIMUS_EGG.get());
                        output.accept(ModItems.INDOMINUS_REX_EGG.get());
                        output.accept(ModItems.PARASAUROLOPHUS_EGG.get());
                        output.accept(ModItems.SPINOSAURUS_EGG.get());
                        output.accept(ModItems.TRICERATOPS_EGG.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_EGG.get());
                        output.accept(ModItems.VELOCIRAPTOR_EGG.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> JR_DINO_TAB = CREATIVE_MODE_TABS.register("jurassicrevived_dino_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VELOCIRAPTOR_SPAWN_EGG.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_dino_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.BRACHIOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.CERATOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.DILOPHOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.VELOCIRAPTOR_SPAWN_EGG.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_SPAWN_EGG.get());
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
