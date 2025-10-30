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

    public static final RegistryObject<Item> APATOSAURUS_SPAWN_EGG = ITEMS.register("apatosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.APATOSAURUS, 0x7f7d6f, 0x36373b, new Item.Properties()));
    public static final RegistryObject<Item> ALBERTOSAURUS_SPAWN_EGG = ITEMS.register("albertosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.ALBERTOSAURUS, 0x2b2315, 0x7a442d, new Item.Properties()));
    public static final RegistryObject<Item> VELOCIRAPTOR_SPAWN_EGG = ITEMS.register("velociraptor_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.VELOCIRAPTOR, 0x8A5837, 0x45220D, new Item.Properties()));
    public static final RegistryObject<Item> TYRANNOSAURUS_REX_SPAWN_EGG = ITEMS.register("tyrannosaurus_rex_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.TYRANNOSAURUS_REX, 0x4C3C2D, 0x241F1E, new Item.Properties()));
    public static final RegistryObject<Item> TRICERATOPS_SPAWN_EGG = ITEMS.register("triceratops_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.TRICERATOPS, 0x353A30, 0x121212, new Item.Properties()));
    public static final RegistryObject<Item> SPINOSAURUS_SPAWN_EGG = ITEMS.register("spinosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.SPINOSAURUS, 0x685E5A, 0x5D3831, new Item.Properties()));
    public static final RegistryObject<Item> PARASAUROLOPHUS_SPAWN_EGG = ITEMS.register("parasaurolophus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.PARASAUROLOPHUS, 0x856836, 0x442911, new Item.Properties()));
    public static final RegistryObject<Item> OURANOSAURUS_SPAWN_EGG = ITEMS.register("ouranosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.OURANOSAURUS, 0x6b4b31, 0xff8900, new Item.Properties()));
    public static final RegistryObject<Item> INDOMINUS_REX_SPAWN_EGG = ITEMS.register("indominus_rex_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.INDOMINUS_REX, 0x9C9B99, 0x60605F, new Item.Properties()));
    public static final RegistryObject<Item> GALLIMIMUS_SPAWN_EGG = ITEMS.register("gallimimus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.GALLIMIMUS, 0xAD7341, 0x5C3925, new Item.Properties()));
    public static final RegistryObject<Item> DIPLODOCUS_SPAWN_EGG = ITEMS.register("diplodocus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.DIPLODOCUS, 0xbf9a5e, 0x624d2c, new Item.Properties()));
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
    public static final RegistryObject<Item> BARYONYX_SPAWN_EGG = ITEMS.register("baryonyx_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.BARYONYX, 0x2e5325, 0x7dcf35, new Item.Properties()));
    public static final RegistryObject<Item> CARNOTAURUS_SPAWN_EGG = ITEMS.register("carnotaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.CARNOTAURUS, 0xa6996e, 0xc36e60, new Item.Properties()));
    public static final RegistryObject<Item> CONCAVENATOR_SPAWN_EGG = ITEMS.register("concavenator_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.CONCAVENATOR, 0xa6a49b, 0x964b22, new Item.Properties()));
    public static final RegistryObject<Item> DEINONYCHUS_SPAWN_EGG = ITEMS.register("deinonychus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.DEINONYCHUS, 0x8d7d51, 0x95c9a2, new Item.Properties()));
    public static final RegistryObject<Item> DISTORTUS_REX_SPAWN_EGG = ITEMS.register("distortus_rex_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.DISTORTUS_REX, 0x59422b, 0x33f2e1a, new Item.Properties()));
    public static final RegistryObject<Item> EDMONTOSAURUS_SPAWN_EGG = ITEMS.register("edmontosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.EDMONTOSAURUS, 0xeaa569, 0xbe783e, new Item.Properties()));
    public static final RegistryObject<Item> GIGANOTOSAURUS_SPAWN_EGG = ITEMS.register("giganotosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.GIGANOTOSAURUS, 0x5c483b, 0x2d2b30, new Item.Properties()));
    public static final RegistryObject<Item> GUANLONG_SPAWN_EGG = ITEMS.register("guanlong_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.GUANLONG, 0xefe7cb, 0x624e18, new Item.Properties()));
    public static final RegistryObject<Item> HERRERASAURUS_SPAWN_EGG = ITEMS.register("herrerasaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.HERRERASAURUS, 0x93211e, 0x987839, new Item.Properties()));
    public static final RegistryObject<Item> MAJUNGASAURUS_SPAWN_EGG = ITEMS.register("majungasaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.MAJUNGASAURUS, 0x657774, 0xce8039, new Item.Properties()));
    public static final RegistryObject<Item> PROCOMPSOGNATHUS_SPAWN_EGG = ITEMS.register("procompsognathus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.PROCOMPSOGNATHUS, 0xe3c775, 0x362c18, new Item.Properties()));
    public static final RegistryObject<Item> PROTOCERATOPS_SPAWN_EGG = ITEMS.register("protoceratops_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.PROTOCERATOPS, 0xfccdb4, 0xfdc079, new Item.Properties()));
    public static final RegistryObject<Item> RUGOPS_SPAWN_EGG = ITEMS.register("rugops_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.RUGOPS, 0xd3ecf0, 0xafef5a, new Item.Properties()));
    public static final RegistryObject<Item> SHANTUNGOSAURUS_SPAWN_EGG = ITEMS.register("shantungosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.SHANTUNGOSAURUS, 0x272727, 0xb57942, new Item.Properties()));
    public static final RegistryObject<Item> STEGOSAURUS_SPAWN_EGG = ITEMS.register("stegosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.STEGOSAURUS, 0x6b6e29, 0x441500, new Item.Properties()));
    public static final RegistryObject<Item> STYRACOSAURUS_SPAWN_EGG = ITEMS.register("styracosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.STYRACOSAURUS, 0x813b2b, 0x6a342c, new Item.Properties()));
    public static final RegistryObject<Item> THERIZINOSAURUS_SPAWN_EGG = ITEMS.register("therizinosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.THERIZINOSAURUS, 0x787878, 0x454545, new Item.Properties()));
    public static final RegistryObject<Item> CHICKENOSAURUS_SPAWN_EGG = ITEMS.register("chickenosaurus_spawn_egg",
            () -> new CustomGenderedSpawnEggItem(ModEntities.CHICKENOSAURUS, 0x5d3c11, 0x3a2934, new Item.Properties()));

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

    public static final RegistryObject<Item> APATOSAURUS_SKULL_FOSSIL = ITEMS.register("apatosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> ALBERTOSAURUS_SKULL_FOSSIL = ITEMS.register("albertosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
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
    public static final RegistryObject<Item> BARYONYX_SKULL_FOSSIL = ITEMS.register("baryonyx_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> CARNOTAURUS_SKULL_FOSSIL = ITEMS.register("carnotaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> CONCAVENATOR_SKULL_FOSSIL = ITEMS.register("concavenator_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> DEINONYCHUS_SKULL_FOSSIL = ITEMS.register("deinonychus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> EDMONTOSAURUS_SKULL_FOSSIL = ITEMS.register("edmontosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> GIGANOTOSAURUS_SKULL_FOSSIL = ITEMS.register("giganotosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> GUANLONG_SKULL_FOSSIL = ITEMS.register("guanlong_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> HERRERASAURUS_SKULL_FOSSIL = ITEMS.register("herrerasaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> MAJUNGASAURUS_SKULL_FOSSIL = ITEMS.register("majungasaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> PROCOMPSOGNATHUS_SKULL_FOSSIL = ITEMS.register("procompsognathus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> PROTOCERATOPS_SKULL_FOSSIL = ITEMS.register("protoceratops_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> RUGOPS_SKULL_FOSSIL = ITEMS.register("rugops_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> SHANTUNGOSAURUS_SKULL_FOSSIL = ITEMS.register("shantungosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> STEGOSAURUS_SKULL_FOSSIL = ITEMS.register("stegosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> STYRACOSAURUS_SKULL_FOSSIL = ITEMS.register("styracosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> THERIZINOSAURUS_SKULL_FOSSIL = ITEMS.register("therizinosaurus_skull_fossil", () -> new Item(new Item.Properties().stacksTo(16)));


    public static final RegistryObject<Item> FRESH_APATOSAURUS_SKULL = ITEMS.register("fresh_apatosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_ALBERTOSAURUS_SKULL = ITEMS.register("fresh_albertosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
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
    public static final RegistryObject<Item> FRESH_BARYONYX_SKULL = ITEMS.register("fresh_baryonyx_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_CARNOTAURUS_SKULL = ITEMS.register("fresh_carnotaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_CONCAVENATOR_SKULL = ITEMS.register("fresh_concavenator_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_DEINONYCHUS_SKULL = ITEMS.register("fresh_deinonychus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_EDMONTOSAURUS_SKULL = ITEMS.register("fresh_edmontosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_GIGANOTOSAURUS_SKULL = ITEMS.register("fresh_giganotosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_GUANLONG_SKULL = ITEMS.register("fresh_guanlong_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_HERRERASAURUS_SKULL = ITEMS.register("fresh_herrerasaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_MAJUNGASAURUS_SKULL = ITEMS.register("fresh_majungasaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_PROTOCERATOPS_SKULL = ITEMS.register("fresh_protoceratops_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_PROCOMPSOGNATHUS_SKULL = ITEMS.register("fresh_procompsognathus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_RUGOPS_SKULL = ITEMS.register("fresh_rugops_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_SHANTUNGOSAURUS_SKULL = ITEMS.register("fresh_shantungosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_STEGOSAURUS_SKULL = ITEMS.register("fresh_stegosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_STYRACOSAURUS_SKULL = ITEMS.register("fresh_styracosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_THERIZINOSAURUS_SKULL = ITEMS.register("fresh_therizinosaurus_skull", () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FRESH_DISTORTUS_REX_SKULL = ITEMS.register("fresh_distortus_rex_skull", () -> new Item(new Item.Properties().stacksTo(16)));


    public static final RegistryObject<Item> APATOSAURUS_TISSUE = ITEMS.register("apatosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ALBERTOSAURUS_TISSUE = ITEMS.register("albertosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
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
    public static final RegistryObject<Item> BARYONYX_TISSUE = ITEMS.register("baryonyx_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CARNOTAURUS_TISSUE = ITEMS.register("carnotaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CONCAVENATOR_TISSUE = ITEMS.register("concavenator_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> DEINONYCHUS_TISSUE = ITEMS.register("deinonychus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EDMONTOSAURUS_TISSUE = ITEMS.register("edmontosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GIGANOTOSAURUS_TISSUE = ITEMS.register("giganotosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GUANLONG_TISSUE = ITEMS.register("guanlong_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> HERRERASAURUS_TISSUE = ITEMS.register("herrerasaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> MAJUNGASAURUS_TISSUE = ITEMS.register("majungasaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PROCOMPSOGNATHUS_TISSUE = ITEMS.register("procompsognathus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PROTOCERATOPS_TISSUE = ITEMS.register("protoceratops_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> RUGOPS_TISSUE = ITEMS.register("rugops_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SHANTUNGOSAURUS_TISSUE = ITEMS.register("shantungosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> STEGOSAURUS_TISSUE = ITEMS.register("stegosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> STYRACOSAURUS_TISSUE = ITEMS.register("styracosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> THERIZINOSAURUS_TISSUE = ITEMS.register("therizinosaurus_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> DISTORTUS_REX_TISSUE = ITEMS.register("distortus_rex_tissue", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC)));


    public static final RegistryObject<Item> APATOSAURUS_DNA = ITEMS.register("apatosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ALBERTOSAURUS_DNA = ITEMS.register("albertosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
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
    public static final RegistryObject<Item> BARYONYX_DNA = ITEMS.register("baryonyx_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CARNOTAURUS_DNA = ITEMS.register("carnotaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CONCAVENATOR_DNA = ITEMS.register("concavenator_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DEINONYCHUS_DNA = ITEMS.register("deinonychus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> EDMONTOSAURUS_DNA = ITEMS.register("edmontosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GIGANOTOSAURUS_DNA = ITEMS.register("giganotosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GUANLONG_DNA = ITEMS.register("guanlong_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> HERRERASAURUS_DNA = ITEMS.register("herrerasaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MAJUNGASAURUS_DNA = ITEMS.register("majungasaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> PROCOMPSOGNATHUS_DNA = ITEMS.register("procompsognathus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> PROTOCERATOPS_DNA = ITEMS.register("protoceratops_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> RUGOPS_DNA = ITEMS.register("rugops_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SHANTUNGOSAURUS_DNA = ITEMS.register("shantungosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> STEGOSAURUS_DNA = ITEMS.register("stegosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> STYRACOSAURUS_DNA = ITEMS.register("styracosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> THERIZINOSAURUS_DNA = ITEMS.register("therizinosaurus_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DISTORTUS_REX_DNA = ITEMS.register("distortus_rex_dna", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.RARE)));


    public static final RegistryObject<Item> APATOSAURUS_SYRINGE = ITEMS.register("apatosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ALBERTOSAURUS_SYRINGE = ITEMS.register("albertosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
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
    public static final RegistryObject<Item> BARYONYX_SYRINGE = ITEMS.register("baryonyx_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CARNOTAURUS_SYRINGE = ITEMS.register("carnotaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CONCAVENATOR_SYRINGE = ITEMS.register("concavenator_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DEINONYCHUS_SYRINGE = ITEMS.register("deinonychus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EDMONTOSAURUS_SYRINGE = ITEMS.register("edmontosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GIGANOTOSAURUS_SYRINGE = ITEMS.register("giganotosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GUANLONG_SYRINGE = ITEMS.register("guanlong_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> HERRERASAURUS_SYRINGE = ITEMS.register("herrerasaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MAJUNGASAURUS_SYRINGE = ITEMS.register("majungasaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PROCOMPSOGNATHUS_SYRINGE = ITEMS.register("procompsognathus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PROTOCERATOPS_SYRINGE = ITEMS.register("protoceratops_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> RUGOPS_SYRINGE = ITEMS.register("rugops_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SHANTUNGOSAURUS_SYRINGE = ITEMS.register("shantungosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> STEGOSAURUS_SYRINGE = ITEMS.register("stegosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> STYRACOSAURUS_SYRINGE = ITEMS.register("styracosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> THERIZINOSAURUS_SYRINGE = ITEMS.register("therizinosaurus_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DISTORTUS_REX_SYRINGE = ITEMS.register("distortus_rex_syringe", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));


    public static final RegistryObject<Item> APATOSAURUS_EGG = ITEMS.register("apatosaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ALBERTOSAURUS_EGG = ITEMS.register("albertosaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
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
    public static final RegistryObject<Item> BARYONYX_EGG = ITEMS.register("baryonyx_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CARNOTAURUS_EGG = ITEMS.register("carnotaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CONCAVENATOR_EGG = ITEMS.register("concavenator_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DEINONYCHUS_EGG = ITEMS.register("deinonychus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> EDMONTOSAURUS_EGG = ITEMS.register("edmontosaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GIGANOTOSAURUS_EGG = ITEMS.register("giganotosaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GUANLONG_EGG = ITEMS.register("guanlong_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> HERRERASAURUS_EGG = ITEMS.register("herrerasaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MAJUNGASAURUS_EGG = ITEMS.register("majungasaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PROCOMPSOGNATHUS_EGG = ITEMS.register("procompsognathus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PROTOCERATOPS_EGG = ITEMS.register("protoceratops_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RUGOPS_EGG = ITEMS.register("rugops_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SHANTUNGOSAURUS_EGG = ITEMS.register("shantungosaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STEGOSAURUS_EGG = ITEMS.register("stegosaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STYRACOSAURUS_EGG = ITEMS.register("styracosaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> THERIZINOSAURUS_EGG = ITEMS.register("therizinosaurus_egg", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DISTORTUS_REX_EGG = ITEMS.register("distortus_rex_egg", () -> new Item(new Item.Properties().stacksTo(1)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
