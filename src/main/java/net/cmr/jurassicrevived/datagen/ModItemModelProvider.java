package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, JRMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModItems.APATOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.ALBERTOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.BRACHIOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CERATOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.COMPSOGNATHUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.DILOPHOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.DIPLODOCUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.FDUCK_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GALLIMIMUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.INDOMINUS_REX_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.OURANOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.PARASAUROLOPHUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SPINOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.TRICERATOPS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.TYRANNOSAURUS_REX_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.VELOCIRAPTOR_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        withExistingParent(ModItems.BARYONYX_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CARNOTAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CONCAVENATOR_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.DEINONYCHUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.DISTORTUS_REX_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.EDMONTOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GIGANOTOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GUANLONG_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.HERRERASAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.MAJUNGASAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.PROCOMPSOGNATHUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.PROTOCERATOPS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.RUGOPS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SHANTUNGOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.STEGOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.STYRACOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.THERIZINOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CHICKENOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        basicItem(ModItems.TEST_TUBE.get());
        basicItem(ModItems.SYRINGE.get());
        basicItem(ModItems.CRUSHED_FOSSIL.get());
        basicItem(ModItems.MOSQUITO_IN_AMBER.get());
        basicItem(ModItems.FROZEN_LEECH.get());
        basicItem(ModItems.CABLE.get());
        basicItem(ModItems.SCREEN.get());
        basicItem(ModItems.PROCESSOR.get());
        basicItem(ModItems.TIRE.get());
        basicItem(ModItems.CUTTING_BLADES.get());
        basicItem(ModItems.WRENCH.get());
        basicItem(ModItems.MAC_N_CHEESE.get());
        basicItem(ModItems.WALNUT_PUMPKIN_PIE.get());
        basicItem(ModItems.BANANA_NUT_COOKIE.get());

        basicItem(ModItems.APATOSAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.ALBERTOSAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.BRACHIOSAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.CERATOSAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.COMPSOGNATHUS_SKULL_FOSSIL.get());
        basicItem(ModItems.DILOPHOSAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.DIPLODOCUS_SKULL_FOSSIL.get());
        basicItem(ModItems.GALLIMIMUS_SKULL_FOSSIL.get());
        basicItem(ModItems.OURANOSAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.PARASAUROLOPHUS_SKULL_FOSSIL.get());
        basicItem(ModItems.SPINOSAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.TRICERATOPS_SKULL_FOSSIL.get());
        basicItem(ModItems.TYRANNOSAURUS_REX_SKULL_FOSSIL.get());
        basicItem(ModItems.VELOCIRAPTOR_SKULL_FOSSIL.get());
        basicItem(ModItems.BARYONYX_SKULL_FOSSIL.get());
        basicItem(ModItems.CARNOTAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.CONCAVENATOR_SKULL_FOSSIL.get());
        basicItem(ModItems.DEINONYCHUS_SKULL_FOSSIL.get());
        basicItem(ModItems.EDMONTOSAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.GIGANOTOSAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.GUANLONG_SKULL_FOSSIL.get());
        basicItem(ModItems.HERRERASAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.MAJUNGASAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.PROCOMPSOGNATHUS_SKULL_FOSSIL.get());
        basicItem(ModItems.PROTOCERATOPS_SKULL_FOSSIL.get());
        basicItem(ModItems.RUGOPS_SKULL_FOSSIL.get());
        basicItem(ModItems.SHANTUNGOSAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.STEGOSAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.STYRACOSAURUS_SKULL_FOSSIL.get());
        basicItem(ModItems.THERIZINOSAURUS_SKULL_FOSSIL.get());



        basicItem(ModItems.FRESH_APATOSAURUS_SKULL.get());
        basicItem(ModItems.FRESH_ALBERTOSAURUS_SKULL.get());
        basicItem(ModItems.FRESH_BRACHIOSAURUS_SKULL.get());
        basicItem(ModItems.FRESH_CERATOSAURUS_SKULL.get());
        basicItem(ModItems.FRESH_COMPSOGNATHUS_SKULL.get());
        basicItem(ModItems.FRESH_DILOPHOSAURUS_SKULL.get());
        basicItem(ModItems.FRESH_DIPLODOCUS_SKULL.get());
        basicItem(ModItems.FRESH_GALLIMIMUS_SKULL.get());
        basicItem(ModItems.FRESH_INDOMINUS_REX_SKULL.get());
        basicItem(ModItems.FRESH_OURANOSAURUS_SKULL.get());
        basicItem(ModItems.FRESH_PARASAUROLOPHUS_SKULL.get());
        basicItem(ModItems.FRESH_SPINOSAURUS_SKULL.get());
        basicItem(ModItems.FRESH_TRICERATOPS_SKULL.get());
        basicItem(ModItems.FRESH_TYRANNOSAURUS_REX_SKULL.get());
        basicItem(ModItems.FRESH_VELOCIRAPTOR_SKULL.get());
        basicItem(ModItems.FRESH_BARYONYX_SKULL.get());
        basicItem(ModItems.FRESH_CARNOTAURUS_SKULL.get());
        basicItem(ModItems.FRESH_CONCAVENATOR_SKULL.get());
        basicItem(ModItems.FRESH_DEINONYCHUS_SKULL.get());
        basicItem(ModItems.FRESH_EDMONTOSAURUS_SKULL.get());
        basicItem(ModItems.FRESH_GIGANOTOSAURUS_SKULL.get());
        basicItem(ModItems.FRESH_GUANLONG_SKULL.get());
        basicItem(ModItems.FRESH_HERRERASAURUS_SKULL.get());
        basicItem(ModItems.FRESH_MAJUNGASAURUS_SKULL.get());
        basicItem(ModItems.FRESH_PROCOMPSOGNATHUS_SKULL.get());
        basicItem(ModItems.FRESH_PROTOCERATOPS_SKULL.get());
        basicItem(ModItems.FRESH_RUGOPS_SKULL.get());
        basicItem(ModItems.FRESH_SHANTUNGOSAURUS_SKULL.get());
        basicItem(ModItems.FRESH_STEGOSAURUS_SKULL.get());
        basicItem(ModItems.FRESH_STYRACOSAURUS_SKULL.get());
        basicItem(ModItems.FRESH_THERIZINOSAURUS_SKULL.get());
        basicItem(ModItems.FRESH_DISTORTUS_REX_SKULL.get());

        basicItem(ModItems.APATOSAURUS_TISSUE.get());
        basicItem(ModItems.ALBERTOSAURUS_TISSUE.get());
        basicItem(ModItems.BRACHIOSAURUS_TISSUE.get());
        basicItem(ModItems.CERATOSAURUS_TISSUE.get());
        basicItem(ModItems.COMPSOGNATHUS_TISSUE.get());
        basicItem(ModItems.DILOPHOSAURUS_TISSUE.get());
        basicItem(ModItems.DIPLODOCUS_TISSUE.get());
        basicItem(ModItems.GALLIMIMUS_TISSUE.get());
        basicItem(ModItems.INDOMINUS_REX_TISSUE.get());
        basicItem(ModItems.OURANOSAURUS_TISSUE.get());
        basicItem(ModItems.PARASAUROLOPHUS_TISSUE.get());
        basicItem(ModItems.SPINOSAURUS_TISSUE.get());
        basicItem(ModItems.TRICERATOPS_TISSUE.get());
        basicItem(ModItems.TYRANNOSAURUS_REX_TISSUE.get());
        basicItem(ModItems.VELOCIRAPTOR_TISSUE.get());
        basicItem(ModItems.BARYONYX_TISSUE.get());
        basicItem(ModItems.CARNOTAURUS_TISSUE.get());
        basicItem(ModItems.CONCAVENATOR_TISSUE.get());
        basicItem(ModItems.DEINONYCHUS_TISSUE.get());
        basicItem(ModItems.EDMONTOSAURUS_TISSUE.get());
        basicItem(ModItems.GIGANOTOSAURUS_TISSUE.get());
        basicItem(ModItems.GUANLONG_TISSUE.get());
        basicItem(ModItems.HERRERASAURUS_TISSUE.get());
        basicItem(ModItems.MAJUNGASAURUS_TISSUE.get());
        basicItem(ModItems.PROCOMPSOGNATHUS_TISSUE.get());
        basicItem(ModItems.PROTOCERATOPS_TISSUE.get());
        basicItem(ModItems.RUGOPS_TISSUE.get());
        basicItem(ModItems.SHANTUNGOSAURUS_TISSUE.get());
        basicItem(ModItems.STEGOSAURUS_TISSUE.get());
        basicItem(ModItems.STYRACOSAURUS_TISSUE.get());
        basicItem(ModItems.THERIZINOSAURUS_TISSUE.get());
        basicItem(ModItems.DISTORTUS_REX_TISSUE.get());

        basicItem(ModItems.ALBERTOSAURUS_DNA.get());
        basicItem(ModItems.APATOSAURUS_DNA.get());
        basicItem(ModItems.BRACHIOSAURUS_DNA.get());
        basicItem(ModItems.CERATOSAURUS_DNA.get());
        basicItem(ModItems.COMPSOGNATHUS_DNA.get());
        basicItem(ModItems.DILOPHOSAURUS_DNA.get());
        basicItem(ModItems.DIPLODOCUS_DNA.get());
        basicItem(ModItems.GALLIMIMUS_DNA.get());
        basicItem(ModItems.INDOMINUS_REX_DNA.get());
        basicItem(ModItems.OURANOSAURUS_DNA.get());
        basicItem(ModItems.PARASAUROLOPHUS_DNA.get());
        basicItem(ModItems.SPINOSAURUS_DNA.get());
        basicItem(ModItems.TRICERATOPS_DNA.get());
        basicItem(ModItems.TYRANNOSAURUS_REX_DNA.get());
        basicItem(ModItems.VELOCIRAPTOR_DNA.get());
        basicItem(ModItems.BARYONYX_DNA.get());
        basicItem(ModItems.CARNOTAURUS_DNA.get());
        basicItem(ModItems.CONCAVENATOR_DNA.get());
        basicItem(ModItems.DEINONYCHUS_DNA.get());
        basicItem(ModItems.EDMONTOSAURUS_DNA.get());
        basicItem(ModItems.GIGANOTOSAURUS_DNA.get());
        basicItem(ModItems.GUANLONG_DNA.get());
        basicItem(ModItems.HERRERASAURUS_DNA.get());
        basicItem(ModItems.MAJUNGASAURUS_DNA.get());
        basicItem(ModItems.PROCOMPSOGNATHUS_DNA.get());
        basicItem(ModItems.PROTOCERATOPS_DNA.get());
        basicItem(ModItems.RUGOPS_DNA.get());
        basicItem(ModItems.SHANTUNGOSAURUS_DNA.get());
        basicItem(ModItems.STEGOSAURUS_DNA.get());
        basicItem(ModItems.STYRACOSAURUS_DNA.get());
        basicItem(ModItems.THERIZINOSAURUS_DNA.get());
        basicItem(ModItems.DISTORTUS_REX_DNA.get());

        basicItem(ModItems.ALBERTOSAURUS_SYRINGE.get());
        basicItem(ModItems.APATOSAURUS_SYRINGE.get());
        basicItem(ModItems.BRACHIOSAURUS_SYRINGE.get());
        basicItem(ModItems.CERATOSAURUS_SYRINGE.get());
        basicItem(ModItems.COMPSOGNATHUS_SYRINGE.get());
        basicItem(ModItems.DILOPHOSAURUS_SYRINGE.get());
        basicItem(ModItems.DIPLODOCUS_SYRINGE.get());
        basicItem(ModItems.GALLIMIMUS_SYRINGE.get());
        basicItem(ModItems.INDOMINUS_REX_SYRINGE.get());
        basicItem(ModItems.OURANOSAURUS_SYRINGE.get());
        basicItem(ModItems.PARASAUROLOPHUS_SYRINGE.get());
        basicItem(ModItems.SPINOSAURUS_SYRINGE.get());
        basicItem(ModItems.TRICERATOPS_SYRINGE.get());
        basicItem(ModItems.TYRANNOSAURUS_REX_SYRINGE.get());
        basicItem(ModItems.VELOCIRAPTOR_SYRINGE.get());
        basicItem(ModItems.BARYONYX_SYRINGE.get());
        basicItem(ModItems.CARNOTAURUS_SYRINGE.get());
        basicItem(ModItems.CONCAVENATOR_SYRINGE.get());
        basicItem(ModItems.DEINONYCHUS_SYRINGE.get());
        basicItem(ModItems.EDMONTOSAURUS_SYRINGE.get());
        basicItem(ModItems.GIGANOTOSAURUS_SYRINGE.get());
        basicItem(ModItems.GUANLONG_SYRINGE.get());
        basicItem(ModItems.HERRERASAURUS_SYRINGE.get());
        basicItem(ModItems.MAJUNGASAURUS_SYRINGE.get());
        basicItem(ModItems.PROCOMPSOGNATHUS_SYRINGE.get());
        basicItem(ModItems.PROTOCERATOPS_SYRINGE.get());
        basicItem(ModItems.RUGOPS_SYRINGE.get());
        basicItem(ModItems.SHANTUNGOSAURUS_SYRINGE.get());
        basicItem(ModItems.STEGOSAURUS_SYRINGE.get());
        basicItem(ModItems.STYRACOSAURUS_SYRINGE.get());
        basicItem(ModItems.THERIZINOSAURUS_SYRINGE.get());
        basicItem(ModItems.DISTORTUS_REX_SYRINGE.get());

        simpleBlockItemBlockTexture(ModBlocks.ALBERTOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.APATOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.BRACHIOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.CERATOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.COMPSOGNATHUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.DILOPHOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.DIPLODOCUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.GALLIMIMUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INDOMINUS_REX_EGG);
        simpleBlockItemBlockTexture(ModBlocks.OURANOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.PARASAUROLOPHUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.SPINOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.TRICERATOPS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.TYRANNOSAURUS_REX_EGG);
        simpleBlockItemBlockTexture(ModBlocks.VELOCIRAPTOR_EGG);
        simpleBlockItemBlockTexture(ModBlocks.BARYONYX_EGG);
        simpleBlockItemBlockTexture(ModBlocks.CARNOTAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.CONCAVENATOR_EGG);
        simpleBlockItemBlockTexture(ModBlocks.DEINONYCHUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.EDMONTOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.GIGANOTOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.GUANLONG_EGG);
        simpleBlockItemBlockTexture(ModBlocks.HERRERASAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.MAJUNGASAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.PROCOMPSOGNATHUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.PROTOCERATOPS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.RUGOPS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.SHANTUNGOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.STEGOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.STYRACOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.THERIZINOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.DISTORTUS_REX_EGG);

        basicItem(ModBlocks.LOW_SECURITY_FENCE_POLE.get().asItem());
        basicItem(ModBlocks.LOW_SECURITY_FENCE_WIRE.get().asItem());
        basicItem(ModBlocks.MEDIUM_SECURITY_FENCE_POLE.get().asItem());
        basicItem(ModBlocks.MEDIUM_SECURITY_FENCE_WIRE.get().asItem());

        wallItem(ModBlocks.GYPSUM_BRICK_WALL, ModBlocks.GYPSUM_STONE_BRICKS);
        wallItem(ModBlocks.REINFORCED_BRICK_WALL, ModBlocks.REINFORCED_STONE_BRICKS);

        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_ALBERTOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_APATOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_BRACHIOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_CERATOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_COMPSOGNATHUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_DILOPHOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_DIPLODOCUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_GALLIMIMUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_INDOMINUS_REX_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_OURANOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_PARASAUROLOPHUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_SPINOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_TRICERATOPS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_TYRANNOSAURUS_REX_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_VELOCIRAPTOR_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_BARYONYX_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_CARNOTAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_CONCAVENATOR_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_DEINONYCHUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_EDMONTOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_GIGANOTOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_GUANLONG_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_HERRERASAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_MAJUNGASAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_PROCOMPSOGNATHUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_PROTOCERATOPS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_RUGOPS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_SHANTUNGOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_STEGOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_STYRACOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_THERIZINOSAURUS_EGG);
        simpleBlockItemBlockTexture(ModBlocks.INCUBATED_DISTORTUS_REX_EGG);

        simpleBlockItemBlockTexture(ModBlocks.ROYAL_FERN);
        simpleBlockItemBlockTexture(ModBlocks.HORSETAIL_FERN);
        simpleBlockItemBlockTexture(ModBlocks.WESTERN_SWORD_FERN);
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.fromNamespaceAndPath("minecraft", "item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID,"block/" + item.getId().getPath()));
    }

    private ItemModelBuilder complexBlock(Block block) {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(), ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID,
                "block/" + ForgeRegistries.BLOCKS.getKey(block).getPath()));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }


    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.fromNamespaceAndPath("minecraft","item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.fromNamespaceAndPath("minecraft", "item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.fromNamespaceAndPath("minecraft","item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID,"block/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.fromNamespaceAndPath("minecraft","item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}
