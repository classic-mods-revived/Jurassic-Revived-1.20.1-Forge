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

    public static final RegistryObject<Item> JR_ITEM_TAB_ICON = ITEMS.register("jr_item_tab_icon", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> AMPOULE = ITEMS.register("ampoule", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SYRINGE = ITEMS.register("syringe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VELOCIRAPTORDNA = ITEMS.register("velociraptor_dna", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TYRANNOSAURUSDNA = ITEMS.register("tyrannosaurus_dna", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRICERATOPSDNA = ITEMS.register("triceratops_dna", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPINOSAURUSDNA = ITEMS.register("spinosaurus_dna", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PTERANODONDNA = ITEMS.register("pteranodon_dna", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PARASAUROLOPHUSDNA = ITEMS.register("parasaurolophus_dna", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INDOMINUSDNA = ITEMS.register("indominus_dna", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GALLIMIMUSDNA = ITEMS.register("gallimimus_dna", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIPLODOCUSDNA = ITEMS.register("diplodocus_dna", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DILOPHOSAURUSDNA = ITEMS.register("dilophosaurus_dna", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COMPSOGNATHUSDNA = ITEMS.register("compsognathus_dna", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CERATOSAURUSDNA = ITEMS.register("ceratosaurus_dna", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRACHIOSAURUSDNA = ITEMS.register("brachiosaurus_dna", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VELOCIRAPTORSYRINGE = ITEMS.register("velociraptor_syringe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TYRANNOSAURUSSYRINGE = ITEMS.register("tyrannosaurus_syringe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRICERATOPSSYRINGE = ITEMS.register("triceratops_syringe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPINOSAURUSSYRINGE = ITEMS.register("spinosaurus_syringe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PTERANODONSYRINGE = ITEMS.register("pteranodon_syringe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PARASAUROLOPHUSSYRINGE = ITEMS.register("parasaurolophus_syringe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INDOMINUSSYRINGE = ITEMS.register("indominus_syringe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GALLIMIMUSSYRINGE = ITEMS.register("gallimimus_syringe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIPLODOCUSSYRINGE = ITEMS.register("diplodocus_syringe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DILOPHOSAURUSSYRINGE = ITEMS.register("dilophosaurus_syringe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COMPSOGNATHUSSYRINGE = ITEMS.register("compsognathus_syringe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CERATOSAURUSSYRINGE = ITEMS.register("ceratosaurus_syringe", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRACHIOSAURUSSYRINGE = ITEMS.register("brachiosaurus_syringe", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> VELOCIRAPTOR_SPAWN_EGG = ITEMS.register("velociraptor_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.VELOCIRAPTOR, 0xA6957D, 0x4D3425,
                    new Item.Properties()));

    public static final RegistryObject<Item> CERATOSAURUS_SPAWN_EGG = ITEMS.register("ceratosaurus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CERATOSAURUS, 0x37302E, 0xB05453,
                    new Item.Properties()));

    public static final RegistryObject<Item> BRACHIOSAURUS_SPAWN_EGG = ITEMS.register("brachiosaurus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BRACHIOSAURUS, 0xB6A386, 0x504638,
                    new Item.Properties()));

    public static final RegistryObject<Item> DILOPHOSAURUS_SPAWN_EGG = ITEMS.register("dilophosaurus_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.DILOPHOSAURUS, 0xA8A581, 0x6b7936,
                    new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
