package net.jurassicrevived.jurassicrevived.datagen;

import net.jurassicrevived.jurassicrevived.JRMod;
import net.jurassicrevived.jurassicrevived.block.ModBlocks;
import net.jurassicrevived.jurassicrevived.item.ModItems;
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
        withExistingParent(ModItems.VELOCIRAPTOR_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CERATOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.BRACHIOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.DILOPHOSAURUS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        basicItem(ModItems.MOSQUITO_IN_AMBER.get());
        basicItem(ModItems.AMPOULE.get());
        basicItem(ModItems.SYRINGE.get());

        basicItem(ModItems.TYRANNOSAURUS_REX_SKULL_FOSSIL.get());

        basicItem(ModItems.VELOCIRAPTOR_DNA.get());
        basicItem(ModItems.TYRANNOSAURUS_REX_DNA.get());
        basicItem(ModItems.TRICERATOPS_DNA.get());
        basicItem(ModItems.SPINOSAURUS_DNA.get());
        basicItem(ModItems.PTERANODON_DNA.get());
        basicItem(ModItems.PARASAUROLOPHUS_DNA.get());
        basicItem(ModItems.INDOMINUS_REX_DNA.get());
        basicItem(ModItems.GALLIMIMUS_DNA.get());
        //basicItem(ModItems.DIPLODOCUS_DNA.get());
        basicItem(ModItems.DILOPHOSAURUS_DNA.get());
        basicItem(ModItems.COMPSOGNATHUS_DNA.get());
        basicItem(ModItems.CERATOSAURUS_DNA.get());
        basicItem(ModItems.BRACHIOSAURUS_DNA.get());

        basicItem(ModItems.VELOCIRAPTOR_SYRINGE.get());
        basicItem(ModItems.TYRANNOSAURUS_REX_SYRINGE.get());
        basicItem(ModItems.TRICERATOPS_SYRINGE.get());
        basicItem(ModItems.SPINOSAURUS_SYRINGE.get());
        basicItem(ModItems.PTERANODON_SYRINGE.get());
        basicItem(ModItems.PARASAUROLOPHUS_SYRINGE.get());
        basicItem(ModItems.INDOMINUS_REX_SYRINGE.get());
        basicItem(ModItems.GALLIMIMUS_SYRINGE.get());
        //basicItem(ModItems.DIPLODOCUS_SYRINGE.get());
        basicItem(ModItems.DILOPHOSAURUS_SYRINGE.get());
        basicItem(ModItems.COMPSOGNATHUS_SYRINGE.get());
        basicItem(ModItems.CERATOSAURUS_SYRINGE.get());
        basicItem(ModItems.BRACHIOSAURUS_SYRINGE.get());


        simpleBlockItemBlockTexture(ModBlocks.ROYAL_FERN);
        simpleBlockItemBlockTexture(ModBlocks.HORSETAIL_FERN);
        simpleBlockItemBlockTexture(ModBlocks.WESTERN_SWORD_FERN);
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(JRMod.MOD_ID,"block/" + item.getId().getPath()));
    }

    private ItemModelBuilder complexBlock(Block block) {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(), new ResourceLocation(JRMod.MOD_ID,
                "block/" + ForgeRegistries.BLOCKS.getKey(block).getPath()));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(JRMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(JRMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(JRMod.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }


    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(JRMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(JRMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(JRMod.MOD_ID,"block/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(JRMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}
