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

    public static final RegistryObject<CreativeModeTab> JR_BLOCKS_TAB = CREATIVE_MODE_TABS.register("jr_block_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.JR_ITEM_TAB_ICON.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_block_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModBlocks.CAT_PLUSHIE.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> JR_DINO_TAB = CREATIVE_MODE_TABS.register("jr_dino_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.JR_ITEM_TAB_ICON.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_dino_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.VELOCIRAPTOR_SPAWN_EGG.get());
                        output.accept(ModItems.CERATOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.BRACHIOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.DILOPHOSAURUS_SPAWN_EGG.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> JR_DNA_TAB = CREATIVE_MODE_TABS.register("jr_dna_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.JR_ITEM_TAB_ICON.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_dna_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.AMPOULE.get());
                        output.accept(ModItems.SYRINGE.get());
                        output.accept(ModItems.VELOCIRAPTORDNA.get());
                        output.accept(ModItems.TYRANNOSAURUSDNA.get());
                        output.accept(ModItems.TRICERATOPSDNA.get());
                        output.accept(ModItems.SPINOSAURUSDNA.get());
                        output.accept(ModItems.PTERANODONDNA.get());
                        output.accept(ModItems.PARASAUROLOPHUSDNA.get());
                        output.accept(ModItems.INDOMINUSDNA.get());
                        output.accept(ModItems.GALLIMIMUSDNA.get());
                        output.accept(ModItems.DIPLODOCUSDNA.get());
                        output.accept(ModItems.DILOPHOSAURUSDNA.get());
                        output.accept(ModItems.COMPSOGNATHUSDNA.get());
                        output.accept(ModItems.CERATOSAURUSDNA.get());
                        output.accept(ModItems.BRACHIOSAURUSDNA.get());
                        output.accept(ModItems.VELOCIRAPTORSYRINGE.get());
                        output.accept(ModItems.TYRANNOSAURUSSYRINGE.get());
                        output.accept(ModItems.TRICERATOPSSYRINGE.get());
                        output.accept(ModItems.SPINOSAURUSSYRINGE.get());
                        output.accept(ModItems.PTERANODONSYRINGE.get());
                        output.accept(ModItems.PARASAUROLOPHUSSYRINGE.get());
                        output.accept(ModItems.INDOMINUSSYRINGE.get());
                        output.accept(ModItems.GALLIMIMUSSYRINGE.get());
                        output.accept(ModItems.DIPLODOCUSSYRINGE.get());
                        output.accept(ModItems.DILOPHOSAURUSSYRINGE.get());
                        output.accept(ModItems.COMPSOGNATHUSSYRINGE.get());
                        output.accept(ModItems.CERATOSAURUSSYRINGE.get());
                        output.accept(ModItems.BRACHIOSAURUSSYRINGE.get());
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
