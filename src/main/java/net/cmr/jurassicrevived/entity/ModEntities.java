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

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, JRMod.MOD_ID);

    public static final RegistryObject<EntityType<SeatEntity>> SEAT =
            ENTITY_TYPES.register("seat", () ->
                    EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC)
                            .sized(0.001f, 0.001f)           // no hitbox
                            .clientTrackingRange(16)      // optional
                            .updateInterval(1)           // optional
                            .build("jurassicrevived:seat")  // your modid:id
            );

    public static final RegistryObject<EntityType<AlbertosaurusEntity>> ALBERTOSAURUS =
            ENTITY_TYPES.register("albertosaurus",
                    () -> EntityType.Builder.of(AlbertosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "albertosaurus").toString()));

    public static final RegistryObject<EntityType<ApatosaurusEntity>> APATOSAURUS =
            ENTITY_TYPES.register("apatosaurus",
                    () -> EntityType.Builder.of(ApatosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "apatosaurus").toString()));

    public static final RegistryObject<EntityType<BaryonyxEntity>> BARYONYX =
            ENTITY_TYPES.register("baryonyx",
                    () -> EntityType.Builder.of(BaryonyxEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "baryonyx").toString()));

    public static final RegistryObject<EntityType<CarnotaurusEntity>> CARNOTAURUS =
            ENTITY_TYPES.register("carnotaurus",
                    () -> EntityType.Builder.of(CarnotaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "carnotaurus").toString()));

    public static final RegistryObject<EntityType<ConcavenatorEntity>> CONCAVENATOR =
            ENTITY_TYPES.register("concavenator",
                    () -> EntityType.Builder.of(ConcavenatorEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "concavenator").toString()));

    public static final RegistryObject<EntityType<DeinonychusEntity>> DEINONYCHUS =
            ENTITY_TYPES.register("deinonychus",
                    () -> EntityType.Builder.of(DeinonychusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "deinonychus").toString()));

    public static final RegistryObject<EntityType<EdmontosaurusEntity>> EDMONTOSAURUS =
            ENTITY_TYPES.register("edmontosaurus",
                    () -> EntityType.Builder.of(EdmontosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "edmontosaurus").toString()));

    public static final RegistryObject<EntityType<GiganotosaurusEntity>> GIGANOTOSAURUS =
            ENTITY_TYPES.register("giganotosaurus",
                    () -> EntityType.Builder.of(GiganotosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "giganotosaurus").toString()));

    public static final RegistryObject<EntityType<GuanlongEntity>> GUANLONG =
            ENTITY_TYPES.register("guanlong",
                    () -> EntityType.Builder.of(GuanlongEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "guanlong").toString()));

    public static final RegistryObject<EntityType<HerrerasaurusEntity>> HERRERASAURUS =
            ENTITY_TYPES.register("herrerasaurus",
                    () -> EntityType.Builder.of(HerrerasaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "herrerasaurus").toString()));

    public static final RegistryObject<EntityType<MajungasaurusEntity>> MAJUNGASAURUS =
            ENTITY_TYPES.register("majungasaurus",
                    () -> EntityType.Builder.of(MajungasaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "majungasaurus").toString()));

    public static final RegistryObject<EntityType<ProcompsognathusEntity>> PROCOMPSOGNATHUS =
            ENTITY_TYPES.register("procompsognathus",
                    () -> EntityType.Builder.of(ProcompsognathusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "procompsognathus").toString()));

    public static final RegistryObject<EntityType<ProtoceratopsEntity>> PROTOCERATOPS =
            ENTITY_TYPES.register("protoceratops",
                    () -> EntityType.Builder.of(ProtoceratopsEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "protoceratops").toString()));

    public static final RegistryObject<EntityType<RugopsEntity>> RUGOPS =
            ENTITY_TYPES.register("rugops",
                    () -> EntityType.Builder.of(RugopsEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "rugops").toString()));

    public static final RegistryObject<EntityType<ShantungosaurusEntity>> SHANTUNGOSAURUS =
            ENTITY_TYPES.register("shantungosaurus",
                    () -> EntityType.Builder.of(ShantungosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "shantungosaurus").toString()));

    public static final RegistryObject<EntityType<StegosaurusEntity>> STEGOSAURUS =
            ENTITY_TYPES.register("stegosaurus",
                    () -> EntityType.Builder.of(StegosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "stegosaurus").toString()));

    public static final RegistryObject<EntityType<StyracosaurusEntity>> STYRACOSAURUS =
            ENTITY_TYPES.register("styracosaurus",
                    () -> EntityType.Builder.of(StyracosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "styracosaurus").toString()));

    public static final RegistryObject<EntityType<TherizinosaurusEntity>> THERIZINOSAURUS =
            ENTITY_TYPES.register("therizinosaurus",
                    () -> EntityType.Builder.of(TherizinosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "therizinosaurus").toString()));

    public static final RegistryObject<EntityType<TyrannosaurusRexEntity>> TYRANNOSAURUS_REX =
            ENTITY_TYPES.register("tyrannosaurus_rex",
                    () -> EntityType.Builder.of(TyrannosaurusRexEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "tyrannosaurus_rex").toString()));

    public static final RegistryObject<EntityType<VelociraptorEntity>> VELOCIRAPTOR =
            ENTITY_TYPES.register("velociraptor",
                    () -> EntityType.Builder.of(VelociraptorEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "velociraptor").toString()));

    public static final RegistryObject<EntityType<CeratosaurusEntity>> CERATOSAURUS =
            ENTITY_TYPES.register("ceratosaurus",
                    () -> EntityType.Builder.of(CeratosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "ceratosaurus").toString()));

    public static final RegistryObject<EntityType<CompsognathusEntity>> COMPSOGNATHUS =
            ENTITY_TYPES.register("compsognathus",
                    () -> EntityType.Builder.of(CompsognathusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "compsognathus").toString()));

    public static final RegistryObject<EntityType<BrachiosaurusEntity>> BRACHIOSAURUS =
            ENTITY_TYPES.register("brachiosaurus",
                    () -> EntityType.Builder.of(BrachiosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "brachiosaurus").toString()));

    public static final RegistryObject<EntityType<DilophosaurusEntity>> DILOPHOSAURUS =
            ENTITY_TYPES.register("dilophosaurus",
                    () -> EntityType.Builder.of(DilophosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "dilophosaurus").toString()));

    public static final RegistryObject<EntityType<DiplodocusEntity>> DIPLODOCUS =
            ENTITY_TYPES.register("diplodocus",
                    () -> EntityType.Builder.of(DiplodocusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "diplodocus").toString()));

    public static final RegistryObject<EntityType<FDuckEntity>> FDUCK =
            ENTITY_TYPES.register("fduck",
                    () -> EntityType.Builder.of(FDuckEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "fduck").toString()));

    public static final RegistryObject<EntityType<GallimimusEntity>> GALLIMIMUS =
            ENTITY_TYPES.register("gallimimus",
                    () -> EntityType.Builder.of(GallimimusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "gallimimus").toString()));

    public static final RegistryObject<EntityType<IndominusRexEntity>> INDOMINUS_REX =
            ENTITY_TYPES.register("indominus_rex",
                    () -> EntityType.Builder.of(IndominusRexEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "indominus_rex").toString()));

    public static final RegistryObject<EntityType<OuranosaurusEntity>> OURANOSAURUS =
            ENTITY_TYPES.register("ouranosaurus",
                    () -> EntityType.Builder.of(OuranosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "ouranosaurus").toString()));

    public static final RegistryObject<EntityType<ParasaurolophusEntity>> PARASAUROLOPHUS =
            ENTITY_TYPES.register("parasaurolophus",
                    () -> EntityType.Builder.of(ParasaurolophusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "parasaurolophus").toString()));

    public static final RegistryObject<EntityType<SpinosaurusEntity>> SPINOSAURUS =
            ENTITY_TYPES.register("spinosaurus",
                    () -> EntityType.Builder.of(SpinosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "spinosaurus").toString()));

    public static final RegistryObject<EntityType<TriceratopsEntity>> TRICERATOPS =
            ENTITY_TYPES.register("triceratops",
                    () -> EntityType.Builder.of(TriceratopsEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "triceratops").toString()));

    public static final RegistryObject<EntityType<DistortusRexEntity>> DISTORTUS_REX =
            ENTITY_TYPES.register("distortus_rex",
                    () -> EntityType.Builder.of(DistortusRexEntity::new, MobCategory.CREATURE)
                            .sized(1.875f, 2.375f)
                            .build(ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "distortus_rex").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}