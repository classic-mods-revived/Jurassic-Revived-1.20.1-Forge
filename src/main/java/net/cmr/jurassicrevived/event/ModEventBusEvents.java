package net.cmr.jurassicrevived.event;

import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.entity.custom.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JRMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.BRACHIOSAURUS.get(), BrachiosaurusEntity.createAttributes().build());
        event.put(ModEntities.CERATOSAURUS.get(), CeratosaurusEntity.createAttributes().build());
        event.put(ModEntities.COMPSOGNATHUS.get(), CompsognathusEntity.createAttributes().build());
        event.put(ModEntities.DILOPHOSAURUS.get(), DilophosaurusEntity.createAttributes().build());
        event.put(ModEntities.DIPLODOCUS.get(), DiplodocusEntity.createAttributes().build());
        event.put(ModEntities.FDUCK.get(), FDuckEntity.createAttributes().build());
        event.put(ModEntities.GALLIMIMUS.get(), GallimimusEntity.createAttributes().build());
        event.put(ModEntities.OURANOSAURUS.get(), OuranosaurusEntity.createAttributes().build());
        event.put(ModEntities.PARASAUROLOPHUS.get(), ParasaurolophusEntity.createAttributes().build());
        event.put(ModEntities.SPINOSAURUS.get(), SpinosaurusEntity.createAttributes().build());
        event.put(ModEntities.TRICERATOPS.get(), TriceratopsEntity.createAttributes().build());
        event.put(ModEntities.TYRANNOSAURUS_REX.get(), TyrannosaurusRexEntity.createAttributes().build());
        event.put(ModEntities.VELOCIRAPTOR.get(), VelociraptorEntity.createAttributes().build());
    }

}
