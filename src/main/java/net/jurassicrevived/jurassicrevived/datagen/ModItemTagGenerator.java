package net.jurassicrevived.jurassicrevived.datagen;

import net.jurassicrevived.jurassicrevived.JRMod;
import net.jurassicrevived.jurassicrevived.block.ModBlocks;
import net.jurassicrevived.jurassicrevived.item.ModItems;
import net.jurassicrevived.jurassicrevived.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> future,
                               CompletableFuture<TagLookup<Block>> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, future, completableFuture, JRMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // Add Item Tags here
        this.tag(ModTags.Items.TISSUES)
                .add(ModItems.VELOCIRAPTOR_TISSUE.get())
                .add(ModItems.TYRANNOSAURUS_REX_TISSUE.get())
                .add(ModItems.TRICERATOPS_TISSUE.get())
                .add(ModItems.SPINOSAURUS_TISSUE.get())
                .add(ModItems.PTERANODON_TISSUE.get())
                .add(ModItems.PARASAUROLOPHUS_TISSUE.get())
                .add(ModItems.INDOMINUS_REX_TISSUE.get())
                .add(ModItems.GALLIMIMUS_TISSUE.get())
                //.add(ModItems.DIPLODOCUS_TISSUE.get())
                .add(ModItems.DILOPHOSAURUS_TISSUE.get())
                .add(ModItems.COMPSOGNATHUS_TISSUE.get())
                .add(ModItems.CERATOSAURUS_TISSUE.get())
                .add(ModItems.BRACHIOSAURUS_TISSUE.get());
    }

    @Override
    public String getName() {
        return "Item Tags";
    }
}
