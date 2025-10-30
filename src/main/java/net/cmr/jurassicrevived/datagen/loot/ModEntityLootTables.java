package net.cmr.jurassicrevived.datagen.loot;

import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.item.ModItems;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Set;
import java.util.stream.Stream;

public class ModEntityLootTables extends EntityLootSubProvider {
    public ModEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        add(ModEntities.CHICKENOSAURUS.get(), LootTable.lootTable());
        add(ModEntities.FDUCK.get(), LootTable.lootTable());

        // Albertosaurus
        this.add(ModEntities.ALBERTOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_ALBERTOSAURUS_SKULL.get()))
                )
        );

// Apatosaurus
        this.add(ModEntities.APATOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_APATOSAURUS_SKULL.get()))
                )
        );

// Baryonyx
        this.add(ModEntities.BARYONYX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_BARYONYX_SKULL.get()))
                )
        );

// Brachiosaurus
        this.add(ModEntities.BRACHIOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_BRACHIOSAURUS_SKULL.get()))
                )
        );

// Carnotaurus
        this.add(ModEntities.CARNOTAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_CARNOTAURUS_SKULL.get()))
                )
        );

// Ceratosaurus
        this.add(ModEntities.CERATOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_CERATOSAURUS_SKULL.get()))
                )
        );

// Compsognathus
        this.add(ModEntities.COMPSOGNATHUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_COMPSOGNATHUS_SKULL.get()))
                )
        );

// Concavenator
        this.add(ModEntities.CONCAVENATOR.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_CONCAVENATOR_SKULL.get()))
                )
        );

// Deinonychus
        this.add(ModEntities.DEINONYCHUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_DEINONYCHUS_SKULL.get()))
                )
        );

// Dilophosaurus
        this.add(ModEntities.DILOPHOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_DILOPHOSAURUS_SKULL.get()))
                )
        );

// Diplodocus
        this.add(ModEntities.DIPLODOCUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_DIPLODOCUS_SKULL.get()))
                )
        );

// D Rex (Distortus Rex)
        this.add(ModEntities.DISTORTUS_REX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_DISTORTUS_REX_SKULL.get()))
                )
        );

// Edmontosaurus
        this.add(ModEntities.EDMONTOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_EDMONTOSAURUS_SKULL.get()))
                )
        );

// Gallimimus
        this.add(ModEntities.GALLIMIMUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_GALLIMIMUS_SKULL.get()))
                )
        );

// Giganotosaurus
        this.add(ModEntities.GIGANOTOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_GIGANOTOSAURUS_SKULL.get()))
                )
        );

// Guanlong
        this.add(ModEntities.GUANLONG.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_GUANLONG_SKULL.get()))
                )
        );

// Herrerasaurus
        this.add(ModEntities.HERRERASAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_HERRERASAURUS_SKULL.get()))
                )
        );

// Indominus Rex
        this.add(ModEntities.INDOMINUS_REX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_INDOMINUS_REX_SKULL.get()))
                )
        );

// Majungasaurus
        this.add(ModEntities.MAJUNGASAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_MAJUNGASAURUS_SKULL.get()))
                )
        );

// Ouranosaurus
        this.add(ModEntities.OURANOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_OURANOSAURUS_SKULL.get()))
                )
        );

// Parasaurolophus
        this.add(ModEntities.PARASAUROLOPHUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_PARASAUROLOPHUS_SKULL.get()))
                )
        );

// Procompsognathus
        this.add(ModEntities.PROCOMPSOGNATHUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_PROCOMPSOGNATHUS_SKULL.get()))
                )
        );

// Protoceratops
        this.add(ModEntities.PROTOCERATOPS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_PROTOCERATOPS_SKULL.get()))
                )
        );

// Rugops
        this.add(ModEntities.RUGOPS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_RUGOPS_SKULL.get()))
                )
        );

// Shantungosaurus
        this.add(ModEntities.SHANTUNGOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_SHANTUNGOSAURUS_SKULL.get()))
                )
        );

// Spinosaurus
        this.add(ModEntities.SPINOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_SPINOSAURUS_SKULL.get()))
                )
        );

// Stegosaurus
        this.add(ModEntities.STEGOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_STEGOSAURUS_SKULL.get()))
                )
        );

// Styracosaurus
        this.add(ModEntities.STYRACOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_STYRACOSAURUS_SKULL.get()))
                )
        );

// Therizinosaurus
        this.add(ModEntities.THERIZINOSAURUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_THERIZINOSAURUS_SKULL.get()))
                )
        );

// Triceratops
        this.add(ModEntities.TRICERATOPS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_TRICERATOPS_SKULL.get()))
                )
        );

// Tyrannosaurus Rex
        this.add(ModEntities.TYRANNOSAURUS_REX.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_TYRANNOSAURUS_REX_SKULL.get()))
                )
        );

// Velociraptor
        this.add(ModEntities.VELOCIRAPTOR.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.FRESH_VELOCIRAPTOR_SKULL.get()))
                )
        );
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return ModEntities.ENTITY_TYPES.getEntries().stream().map(ro -> (EntityType<?>) ro.get());
    }
}
