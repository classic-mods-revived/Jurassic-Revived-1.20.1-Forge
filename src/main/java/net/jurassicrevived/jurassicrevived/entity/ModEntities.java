package net.jurassicrevived.jurassicrevived.entity;

import net.jurassicrevived.jurassicrevived.JRMod;
import net.jurassicrevived.jurassicrevived.entity.custom.AchillobatorEntity;
import net.jurassicrevived.jurassicrevived.entity.custom.CeratosaurusEntity;
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

    public static final RegistryObject<EntityType<AchillobatorEntity>> ACHILLOBATOR =
            ENTITY_TYPES.register("achillobator",
                    () -> EntityType.Builder.of(AchillobatorEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(new ResourceLocation(JRMod.MOD_ID, "achillobator").toString()));

    public static final RegistryObject<EntityType<CeratosaurusEntity>> CERATOSAURUS =
            ENTITY_TYPES.register("ceratosaurus",
                    () -> EntityType.Builder.of(CeratosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(new ResourceLocation(JRMod.MOD_ID, "ceratosaurus").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}