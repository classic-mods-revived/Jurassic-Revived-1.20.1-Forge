package net.jurassicrevived.jurassicrevived.item;

import net.jurassicrevived.jurassicrevived.JRMod;
import net.jurassicrevived.jurassicrevived.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JRMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> JR_ITEM_TAB = CREATIVE_MODE_TABS.register("jurassicrevived_item_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MOSQUITO_IN_AMBER.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_item_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.MOSQUITO_IN_AMBER.get());
                        output.accept(ModItems.AMPOULE.get());
                        output.accept(ModItems.SYRINGE.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> JR_BLOCK_TAB = CREATIVE_MODE_TABS.register("jurassicrevived_block_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.GYPSUM_STONE_BRICKS.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_block_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModBlocks.CAT_PLUSHIE.get());
                        output.accept(ModBlocks.GYPSUM_STONE_BRICKS.get());
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
                        output.accept(ModItems.TYRANNOSAURUS_REX_SKULL_FOSSIL.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> JR_DNA_TAB = CREATIVE_MODE_TABS.register("jurassicrevived_dna_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.JR_DNA_TAB_ICON.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_dna_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.VELOCIRAPTOR_DNA.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_DNA.get());
                        output.accept(ModItems.TRICERATOPS_DNA.get());
                        output.accept(ModItems.SPINOSAURUS_DNA.get());
                        output.accept(ModItems.PTERANODON_DNA.get());
                        output.accept(ModItems.PARASAUROLOPHUS_DNA.get());
                        output.accept(ModItems.INDOMINUS_REX_DNA.get());
                        output.accept(ModItems.GALLIMIMUS_DNA.get());
                        //output.accept(ModItems.DIPLODOCUS_DNA.get());
                        output.accept(ModItems.DILOPHOSAURUS_DNA.get());
                        output.accept(ModItems.COMPSOGNATHUS_DNA.get());
                        output.accept(ModItems.CERATOSAURUS_DNA.get());
                        output.accept(ModItems.BRACHIOSAURUS_DNA.get());

                        output.accept(ModItems.VELOCIRAPTOR_SYRINGE.get());
                        output.accept(ModItems.TYRANNOSAURUS_REX_SYRINGE.get());
                        output.accept(ModItems.TRICERATOPS_SYRINGE.get());
                        output.accept(ModItems.SPINOSAURUS_SYRINGE.get());
                        output.accept(ModItems.PTERANODON_SYRINGE.get());
                        output.accept(ModItems.PARASAUROLOPHUS_SYRINGE.get());
                        output.accept(ModItems.INDOMINUS_REX_SYRINGE.get());
                        output.accept(ModItems.GALLIMIMUS_SYRINGE.get());
                        //output.accept(ModItems.DIPLODOCUS_SYRINGE.get());
                        output.accept(ModItems.DILOPHOSAURUS_SYRINGE.get());
                        output.accept(ModItems.COMPSOGNATHUS_SYRINGE.get());
                        output.accept(ModItems.CERATOSAURUS_SYRINGE.get());
                        output.accept(ModItems.BRACHIOSAURUS_SYRINGE.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> JR_DINO_TAB = CREATIVE_MODE_TABS.register("jurassicrevived_dino_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.JR_DINO_TAB_ICON.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_dino_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.VELOCIRAPTOR_SPAWN_EGG.get());
                        output.accept(ModItems.CERATOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.BRACHIOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.DILOPHOSAURUS_SPAWN_EGG.get());
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
