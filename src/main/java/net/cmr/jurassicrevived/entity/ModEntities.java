package net.cmr.jurassicrevived.entity;

import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.entity.custom.VelociraptorEntity;
import net.cmr.jurassicrevived.entity.custom.BrachiosaurusEntity;
import net.cmr.jurassicrevived.entity.custom.CeratosaurusEntity;
import net.cmr.jurassicrevived.entity.custom.DilophosaurusEntity;
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

    public static final RegistryObject<EntityType<VelociraptorEntity>> VELOCIRAPTOR =
            ENTITY_TYPES.register("velociraptor",
                    () -> EntityType.Builder.of(VelociraptorEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(new ResourceLocation(JRMod.MOD_ID, "velociraptor").toString()));

    public static final RegistryObject<EntityType<CeratosaurusEntity>> CERATOSAURUS =
            ENTITY_TYPES.register("ceratosaurus",
                    () -> EntityType.Builder.of(CeratosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(new ResourceLocation(JRMod.MOD_ID, "ceratosaurus").toString()));

    public static final RegistryObject<EntityType<BrachiosaurusEntity>> BRACHIOSAURUS =
            ENTITY_TYPES.register("brachiosaurus",
                    () -> EntityType.Builder.of(BrachiosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(new ResourceLocation(JRMod.MOD_ID, "brachiosaurus").toString()));

    public static final RegistryObject<EntityType<DilophosaurusEntity>> DILOPHOSAURUS =
            ENTITY_TYPES.register("dilophosaurus",
                    () -> EntityType.Builder.of(DilophosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(new ResourceLocation(JRMod.MOD_ID, "dilophosaurus").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}