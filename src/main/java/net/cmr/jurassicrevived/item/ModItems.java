package net.cmr.jurassicrevived.item;

import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.item.custom.CustomGenderedSpawnEggItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, JRMod.MOD_ID);

    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAC_N_CHEESE = ITEMS.register("mac_n_cheese", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationMod(0.6f).build())));
    public static final RegistryObject<Item> WALNUT_PUMPKIN_PIE = ITEMS.register("walnut_pumpkin_pie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationMod(0.6f).build())));
    public static final RegistryObject<Item> BANANA_NUT_COOKIE = ITEMS.register("banana_nut_cookie", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationMod(0.6f).build())));

    public static final RegistryObject<Item> VELOCIRAPTOR_SPAWN_EGG = ITEMS.register("velociraptor_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.VELOCIRAPTOR, 0x8A5837, 0x45220D, new Item.Properties()));
    public static final RegistryObject<Item> TYRANNOSAURUS_REX_SPAWN_EGG = ITEMS.register("tyrannosaurus_rex_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.TYRANNOSAURUS_REX, 0x4C3C2D, 0x241F1E, new Item.Properties()));
    //public static final RegistryObject<Item> TRICERATOPS_SPAWN_EGG = ITEMS.register("triceratops_spawn_egg",
    //        () -> new CustomGenderedSpawnEggItem(ModEntities.TRICERATOPS, 0x353A30, 0x121212, new Item.Properties()));
    //public static final RegistryObject<Item> SPINOSAURUS_SPAWN_EGG = ITEMS.register("spinosaurus_spawn_egg",
    //        () -> new CustomGenderedSpawnEggItem(ModEntities.SPINOSAURUS, 0x685E5A, 0x5D3831, new Item.Properties()));
    public static final RegistryObject<Item> PARASAUROLOPHUS_SPAWN_EGG = ITEMS.register("parasaurolophus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.PARASAUROLOPHUS, 0x856836, 0x442911, new Item.Properties()));
    public static final RegistryObject<Item> OURANOSAURUS_SPAWN_EGG = ITEMS.register("ouranosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.OURANOSAURUS, 0x6b4b31, 0xff8900, new Item.Properties()));
    //public static final RegistryObject<Item> INDOMINUS_REX_SPAWN_EGG = ITEMS.register("indominus_rex_spawn_egg",
    //        () -> new CustomGenderedSpawnEggItem(ModEntities.INDOMINUX_REX, 0x9C9B99, 0x60605F, new Item.Properties()));
    //public static final RegistryObject<Item> GALLIMIMUS_SPAWN_EGG = ITEMS.register("gallimimus_spawn_egg",
    //        () -> new CustomGenderedSpawnEggItem(ModEntities.GALLIMIMUX, 0xAD7341, 0x5C3925, new Item.Properties()));
    //public static final RegistryObject<Item> DIPLODOCUS_SPAWN_EGG = ITEMS.register("diplodocus_spawn_egg",
    //        () -> new CustomGenderedSpawnEggItem(ModEntities.DIPLODOCUS, 0x, 0x, new Item.Properties()));
    public static final RegistryObject<Item> FDUCK_SPAWN_EGG = ITEMS.register("fduck_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.FDUCK, 0xff8800, 0x421111, new Item.Properties()));
    public static final RegistryObject<Item> DILOPHOSAURUS_SPAWN_EGG = ITEMS.register("dilophosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.DILOPHOSAURUS, 0x575D32, 0x16191C, new Item.Properties()));
    public static final RegistryObject<Item> COMPSOGNATHUS_SPAWN_EGG = ITEMS.register("compsognathus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.COMPSOGNATHUS, 0x676D24, 0x373E16, new Item.Properties()));
    public static final RegistryObject<Item> CERATOSAURUS_SPAWN_EGG = ITEMS.register("ceratosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.CERATOSAURUS, 0x954846, 0x221F1D, new Item.Properties()));
    public static final RegistryObject<Item> BRACHIOSAURUS_SPAWN_EGG = ITEMS.register("brachiosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.BRACHIOSAURUS, 0x95846D, 0x4B4236, new Item.Properties()));

    public static final RegistryObject<Item> TEST_TUBE = ITEMS.register("test_tube", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> SYRINGE = ITEMS.register("syringe", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> MOSQUITO_IN_AMBER = ITEMS.register("mosquito_in_amber", () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CRUSHED_FOSSIL = ITEMS.register("crushed_fossil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FROZEN_LEECH = ITEMS.register("frozen_leech", () -> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CABLE = ITEMS.register("cable", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> SCREEN = ITEMS.register("screen", () -> new Item(new Item.Properties().stacksTo(8)));
    public static final RegistryObject<Item> PROCESSOR = ITEMS.register("processor", () -> new Item(new Item.Properties().stacksTo(8)));
    public static final RegistryObject<Item> TIRE = ITEMS.register("tire", () -> new Item(new Item.Properties().stacksTo(4)));
    public static final RegistryObject<Item> CUTTING_BLADES = ITEMS.register("cutting_blades", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> BRACHIOSAURUS_SKULL_FOSSIL = ITEMS.register("brachiosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> CERATOSAURUS_SKULL_FOSSIL = ITEMS.register("ceratosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> COMPSOGNATHUS_SKULL_FOSSIL = ITEMS.register("compsognathus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> DILOPHOSAURUS_SKULL_FOSSIL = ITEMS.register("dilophosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> DIPLODOCUS_SKULL_FOSSIL = ITEMS.register("diplodocus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> GALLIMIMUS_SKULL_FOSSIL = ITEMS.register("gallimimus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> OURANOSAURUS_SKULL_FOSSIL = ITEMS.register("ouranosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> PARASAUROLOPHUS_SKULL_FOSSIL = ITEMS.register("parasaurolophus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> SPINOSAURUS_SKULL_FOSSIL = ITEMS.register("spinosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> TRICERATOPS_SKULL_FOSSIL = ITEMS.register("triceratops_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> TYRANNOSAURUS_REX_SKULL_FOSSIL = ITEMS.register("tyrannosaurus_rex_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> VELOCIRAPTOR_SKULL_FOSSIL = ITEMS.register("velociraptor_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> FRESH_BRACHIOSAURUS_SKULL = ITEMS.register("fresh_brachiosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_CERATOSAURUS_SKULL = ITEMS.register("fresh_ceratosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_COMPSOGNATHUS_SKULL = ITEMS.register("fresh_compsognathus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_DILOPHOSAURUS_SKULL = ITEMS.register("fresh_dilophosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_DIPLODOCUS_SKULL = ITEMS.register("fresh_diplodocus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_GALLIMIMUS_SKULL = ITEMS.register("fresh_gallimimus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_INDOMINUS_REX_SKULL = ITEMS.register("fresh_indominus_rex_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_OURANOSAURUS_SKULL = ITEMS.register("fresh_ouranosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_PARASAUROLOPHUS_SKULL = ITEMS.register("fresh_parasaurolophus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_SPINOSAURUS_SKULL = ITEMS.register("fresh_spinosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_TRICERATOPS_SKULL = ITEMS.register("fresh_triceratops_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_TYRANNOSAURUS_REX_SKULL = ITEMS.register("fresh_tyrannosaurus_rex_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_VELOCIRAPTOR_SKULL = ITEMS.register("fresh_velociraptor_skull", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> BRACHIOSAURUS_TISSUE = ITEMS.register("brachiosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CERATOSAURUS_TISSUE = ITEMS.register("ceratosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> COMPSOGNATHUS_TISSUE = ITEMS.register("compsognathus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> DILOPHOSAURUS_TISSUE = ITEMS.register("dilophosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> DIPLODOCUS_TISSUE = ITEMS.register("diplodocus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GALLIMIMUS_TISSUE = ITEMS.register("gallimimus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> INDOMINUS_REX_TISSUE = ITEMS.register("indominus_rex_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> OURANOSAURUS_TISSUE = ITEMS.register("ouranosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PARASAUROLOPHUS_TISSUE = ITEMS.register("parasaurolophus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SPINOSAURUS_TISSUE = ITEMS.register("spinosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> TRICERATOPS_TISSUE = ITEMS.register("triceratops_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> TYRANNOSAURUS_REX_TISSUE = ITEMS.register("tyrannosaurus_rex_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> VELOCIRAPTOR_TISSUE = ITEMS.register("velociraptor_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> BRACHIOSAURUS_DNA = ITEMS.register("brachiosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CERATOSAURUS_DNA = ITEMS.register("ceratosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> COMPSOGNATHUS_DNA = ITEMS.register("compsognathus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DILOPHOSAURUS_DNA = ITEMS.register("dilophosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DIPLODOCUS_DNA = ITEMS.register("diplodocus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GALLIMIMUS_DNA = ITEMS.register("gallimimus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> INDOMINUS_REX_DNA = ITEMS.register("indominus_rex_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> OURANOSAURUS_DNA = ITEMS.register("ouranosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> PARASAUROLOPHUS_DNA = ITEMS.register("parasaurolophus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SPINOSAURUS_DNA = ITEMS.register("spinosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> TRICERATOPS_DNA = ITEMS.register("triceratops_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> TYRANNOSAURUS_REX_DNA = ITEMS.register("tyrannosaurus_rex_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VELOCIRAPTOR_DNA = ITEMS.register("velociraptor_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));

    public static final RegistryObject<Item> BRACHIOSAURUS_SYRINGE = ITEMS.register("brachiosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CERATOSAURUS_SYRINGE = ITEMS.register("ceratosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> COMPSOGNATHUS_SYRINGE = ITEMS.register("compsognathus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DILOPHOSAURUS_SYRINGE = ITEMS.register("dilophosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DIPLODOCUS_SYRINGE = ITEMS.register("diplodocus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GALLIMIMUS_SYRINGE = ITEMS.register("gallimimus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> INDOMINUS_REX_SYRINGE = ITEMS.register("indominus_rex_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> OURANOSAURUS_SYRINGE = ITEMS.register("ouranosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PARASAUROLOPHUS_SYRINGE = ITEMS.register("parasaurolophus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SPINOSAURUS_SYRINGE = ITEMS.register("spinosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> TRICERATOPS_SYRINGE = ITEMS.register("triceratops_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> TYRANNOSAURUS_REX_SYRINGE = ITEMS.register("tyrannosaurus_rex_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> VELOCIRAPTOR_SYRINGE = ITEMS.register("velociraptor_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> BRACHIOSAURUS_EGG = ITEMS.register("brachiosaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CERATOSAURUS_EGG = ITEMS.register("ceratosaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> COMPSOGNATHUS_EGG = ITEMS.register("compsognathus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DILOPHOSAURUS_EGG = ITEMS.register("dilophosaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DIPLODOCUS_EGG = ITEMS.register("diplodocus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GALLIMIMUS_EGG = ITEMS.register("gallimimus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> INDOMINUS_REX_EGG = ITEMS.register("indominus_rex_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> OURANOSAURUS_EGG = ITEMS.register("ouranosaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PARASAUROLOPHUS_EGG = ITEMS.register("parasaurolophus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SPINOSAURUS_EGG = ITEMS.register("spinosaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> TRICERATOPS_EGG = ITEMS.register("triceratops_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> TYRANNOSAURUS_REX_EGG = ITEMS.register("tyrannosaurus_rex_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> VELOCIRAPTOR_EGG = ITEMS.register("velociraptor_egg", () -> new Item(new Item.Properties().stacksTo(1)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
