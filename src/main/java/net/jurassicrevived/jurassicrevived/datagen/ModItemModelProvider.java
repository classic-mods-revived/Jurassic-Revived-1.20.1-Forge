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

        basicItem(ModItems.AMPOULE.get());
        basicItem(ModItems.SYRINGE.get());
        basicItem(ModItems.VELOCIRAPTORDNA.get());
        basicItem(ModItems.TYRANNOSAURUSDNA.get());
        basicItem(ModItems.TRICERATOPSDNA.get());
        basicItem(ModItems.SPINOSAURUSDNA.get());
        basicItem(ModItems.PTERANODONDNA.get());
        basicItem(ModItems.PARASAUROLOPHUSDNA.get());
        basicItem(ModItems.INDOMINUSDNA.get());
        basicItem(ModItems.GALLIMIMUSDNA.get());
        basicItem(ModItems.DIPLODOCUSDNA.get());
        basicItem(ModItems.DILOPHOSAURUSDNA.get());
        basicItem(ModItems.COMPSOGNATHUSDNA.get());
        basicItem(ModItems.CERATOSAURUSDNA.get());
        basicItem(ModItems.BRACHIOSAURUSDNA.get());
        basicItem(ModItems.VELOCIRAPTORSYRINGE.get());
        basicItem(ModItems.TYRANNOSAURUSSYRINGE.get());
        basicItem(ModItems.TRICERATOPSSYRINGE.get());
        basicItem(ModItems.SPINOSAURUSSYRINGE.get());
        basicItem(ModItems.PTERANODONSYRINGE.get());
        basicItem(ModItems.PARASAUROLOPHUSSYRINGE.get());
        basicItem(ModItems.INDOMINUSSYRINGE.get());
        basicItem(ModItems.GALLIMIMUSSYRINGE.get());
        basicItem(ModItems.DIPLODOCUSSYRINGE.get());
        basicItem(ModItems.DILOPHOSAURUSSYRINGE.get());
        basicItem(ModItems.COMPSOGNATHUSSYRINGE.get());
        basicItem(ModItems.CERATOSAURUSSYRINGE.get());
        basicItem(ModItems.BRACHIOSAURUSSYRINGE.get());
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

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(JRMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}
