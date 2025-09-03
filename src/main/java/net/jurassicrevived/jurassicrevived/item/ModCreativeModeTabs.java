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

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JRMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> COURSE_TAB = CREATIVE_MODE_TABS.register("jr_item_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.JR_TAB_ICON.get()))
                    .title(Component.translatable("creativetab.jurassicrevived_items_tab"))
                    .displayItems((displayParameters, output) -> {
                        output.accept(ModItems.ACHILLOBATOR_SPAWN_EGG.get());
                        output.accept(ModItems.CERATOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.BRACHIOSAURUS_SPAWN_EGG.get());
                        output.accept(ModItems.DILOPHOSARUS_SPAWN_EGG.get());
                        output.accept(ModBlocks.CAT_PLUSHIE.get());
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
