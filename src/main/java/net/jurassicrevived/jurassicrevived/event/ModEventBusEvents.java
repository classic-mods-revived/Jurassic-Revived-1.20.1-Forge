package net.jurassicrevived.jurassicrevived.event;

import net.jurassicrevived.jurassicrevived.JRMod;
import net.jurassicrevived.jurassicrevived.entity.ModEntities;
import net.jurassicrevived.jurassicrevived.entity.custom.AchillobatorEntity;
import net.jurassicrevived.jurassicrevived.entity.custom.BrachiosaurusEntity;
import net.jurassicrevived.jurassicrevived.entity.custom.CeratosaurusEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JRMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CERATOSAURUS.get(), CeratosaurusEntity.createAttributes().build());
        event.put(ModEntities.ACHILLOBATOR.get(), AchillobatorEntity.createAttributes().build());
        event.put(ModEntities.BRACHIOSAURUS.get(), BrachiosaurusEntity.createAttributes().build());
        event.put(ModEntities.DILOPHOSAURUS.get(), BrachiosaurusEntity.createAttributes().build());
    }

}
