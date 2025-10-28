package net.cmr.jurassicrevived.event;

import net.cmr.jurassicrevived.Config;
import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.entity.custom.*;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JRMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.APATOSAURUS.get(), ApatosaurusEntity.createAttributes().build());
        event.put(ModEntities.ALBERTOSAURUS.get(), AlbertosaurusEntity.createAttributes().build());
        event.put(ModEntities.BARYONYX.get(), BaryonyxEntity.createAttributes().build());
        event.put(ModEntities.BRACHIOSAURUS.get(), BrachiosaurusEntity.createAttributes().build());
        event.put(ModEntities.CARNOTAURUS.get(), CarnotaurusEntity.createAttributes().build());
        event.put(ModEntities.CERATOSAURUS.get(), CeratosaurusEntity.createAttributes().build());
        event.put(ModEntities.COMPSOGNATHUS.get(), CompsognathusEntity.createAttributes().build());
        event.put(ModEntities.CONCAVENATOR.get(), ConcavenatorEntity.createAttributes().build());
        event.put(ModEntities.DEINONYCHUS.get(), DeinonychusEntity.createAttributes().build());
        event.put(ModEntities.DILOPHOSAURUS.get(), DilophosaurusEntity.createAttributes().build());
        event.put(ModEntities.DIPLODOCUS.get(), DiplodocusEntity.createAttributes().build());
        event.put(ModEntities.DISTORTUS_REX.get(), DistortusRexEntity.createAttributes().build());
        event.put(ModEntities.EDMONTOSAURUS.get(), EdmontosaurusEntity.createAttributes().build());
        event.put(ModEntities.FDUCK.get(), FDuckEntity.createAttributes().build());
        event.put(ModEntities.GALLIMIMUS.get(), GallimimusEntity.createAttributes().build());
        event.put(ModEntities.GIGANOTOSAURUS.get(), GiganotosaurusEntity.createAttributes().build());
        event.put(ModEntities.GUANLONG.get(), GuanlongEntity.createAttributes().build());
        event.put(ModEntities.HERRERASAURUS.get(), HerrerasaurusEntity.createAttributes().build());
        event.put(ModEntities.INDOMINUS_REX.get(), IndominusRexEntity.createAttributes().build());
        event.put(ModEntities.MAJUNGASAURUS.get(), MajungasaurusEntity.createAttributes().build());
        event.put(ModEntities.OURANOSAURUS.get(), OuranosaurusEntity.createAttributes().build());
        event.put(ModEntities.PARASAUROLOPHUS.get(), ParasaurolophusEntity.createAttributes().build());
        event.put(ModEntities.PROCOMPSOGNATHUS.get(), ProcompsognathusEntity.createAttributes().build());
        event.put(ModEntities.PROTOCERATOPS.get(), ProtoceratopsEntity.createAttributes().build());
        event.put(ModEntities.RUGOPS.get(), RugopsEntity.createAttributes().build());
        event.put(ModEntities.SHANTUNGOSAURUS.get(), ShantungosaurusEntity.createAttributes().build());
        event.put(ModEntities.SPINOSAURUS.get(), SpinosaurusEntity.createAttributes().build());
        event.put(ModEntities.STEGOSAURUS.get(), StegosaurusEntity.createAttributes().build());
        event.put(ModEntities.STYRACOSAURUS.get(), StyracosaurusEntity.createAttributes().build());
        event.put(ModEntities.THERIZINOSAURUS.get(), TherizinosaurusEntity.createAttributes().build());
        event.put(ModEntities.TRICERATOPS.get(), TriceratopsEntity.createAttributes().build());
        event.put(ModEntities.TYRANNOSAURUS_REX.get(), TyrannosaurusRexEntity.createAttributes().build());
        event.put(ModEntities.VELOCIRAPTOR.get(), VelociraptorEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements (SpawnPlacementRegisterEvent event) {
        if (!Config.NATURAL_DINOSAUR_SPAWNING) {
            event.register(ModEntities.BRACHIOSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        }
    }
}
