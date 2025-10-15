package net.cmr.jurassicrevived.entity;

import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.entity.custom.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, JRMod.MOD_ID);

    public static final RegistryObject<EntityType<TyrannosaurusRexEntity>> TYRANNOSAURUS_REX =
            ENTITY_TYPES.register("tyrannosaurus_rex",
                    () -> EntityType.Builder.of(TyrannosaurusRexEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "tyrannosaurus_rex").toString()));

    public static final RegistryObject<EntityType<VelociraptorEntity>> VELOCIRAPTOR =
            ENTITY_TYPES.register("velociraptor",
                    () -> EntityType.Builder.of(VelociraptorEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "velociraptor").toString()));

    public static final RegistryObject<EntityType<CeratosaurusEntity>> CERATOSAURUS =
            ENTITY_TYPES.register("ceratosaurus",
                    () -> EntityType.Builder.of(CeratosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "ceratosaurus").toString()));

    public static final RegistryObject<EntityType<CompsognathusEntity>> COMPSOGNATHUS =
            ENTITY_TYPES.register("compsognathus",
                    () -> EntityType.Builder.of(CompsognathusEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "compsognathus").toString()));

    public static final RegistryObject<EntityType<BrachiosaurusEntity>> BRACHIOSAURUS =
            ENTITY_TYPES.register("brachiosaurus",
                    () -> EntityType.Builder.of(BrachiosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "brachiosaurus").toString()));

    public static final RegistryObject<EntityType<DilophosaurusEntity>> DILOPHOSAURUS =
            ENTITY_TYPES.register("dilophosaurus",
                    () -> EntityType.Builder.of(DilophosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "dilophosaurus").toString()));

    public static final RegistryObject<EntityType<DiplodocusEntity>> DIPLODOCUS =
            ENTITY_TYPES.register("diplodocus",
                    () -> EntityType.Builder.of(DiplodocusEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "diplodocus").toString()));

    public static final RegistryObject<EntityType<FDuckEntity>> FDUCK =
            ENTITY_TYPES.register("fduck",
                    () -> EntityType.Builder.of(FDuckEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "fduck").toString()));

    public static final RegistryObject<EntityType<GallimimusEntity>> GALLIMIMUS =
            ENTITY_TYPES.register("gallimimus",
                    () -> EntityType.Builder.of(GallimimusEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "gallimimus").toString()));

    public static final RegistryObject<EntityType<IndominusRexEntity>> INDOMINUS_REX =
            ENTITY_TYPES.register("indominus_rex",
                    () -> EntityType.Builder.of(IndominusRexEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "indominus_rex").toString()));

    public static final RegistryObject<EntityType<OuranosaurusEntity>> OURANOSAURUS =
            ENTITY_TYPES.register("ouranosaurus",
                    () -> EntityType.Builder.of(OuranosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "ouranosaurus").toString()));

    public static final RegistryObject<EntityType<ParasaurolophusEntity>> PARASAUROLOPHUS =
            ENTITY_TYPES.register("parasaurolophus",
                    () -> EntityType.Builder.of(ParasaurolophusEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "parasaurolophus").toString()));

    public static final RegistryObject<EntityType<SpinosaurusEntity>> SPINOSAURUS =
            ENTITY_TYPES.register("spinosaurus",
                    () -> EntityType.Builder.of(SpinosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "spinosaurus").toString()));

    public static final RegistryObject<EntityType<TriceratopsEntity>> TRICERATOPS =
            ENTITY_TYPES.register("triceratops",
                    () -> EntityType.Builder.of(TriceratopsEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "triceratops").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}