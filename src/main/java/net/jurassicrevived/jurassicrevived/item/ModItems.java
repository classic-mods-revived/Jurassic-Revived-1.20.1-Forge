package net.jurassicrevived.jurassicrevived.item;

import net.jurassicrevived.jurassicrevived.JRMod;
import net.jurassicrevived.jurassicrevived.entity.ModEntities;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, JRMod.MOD_ID);

    public static final RegistryObject<Item> ACHILLOBATOR_SPAWN_EGG = ITEMS.register("achillobator_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ACHILLOBATOR, 0xA6957D, 0x4D3425,
                    new Item.Properties()));

    public static final RegistryObject<Item> CERATOSAURUS_SPAWN_EGG = ITEMS.register("ceratosaurus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CERATOSAURUS, 0x37302E, 0xB05453,
                    new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
