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
        this.tag(ModTags.Items.DNA)
                .add(ModItems.VELOCIRAPTOR_DNA.get())
                .add(ModItems.TYRANNOSAURUS_REX_DNA.get())
                .add(ModItems.TRICERATOPS_DNA.get())
                .add(ModItems.SPINOSAURUS_DNA.get())
                .add(ModItems.PTERANODON_DNA.get())
                .add(ModItems.PARASAUROLOPHUS_DNA.get())
                .add(ModItems.INDOMINUS_REX_DNA.get())
                .add(ModItems.GALLIMIMUS_DNA.get())
                //.add(ModItems.DIPLODOCUS_DNA.get())
                .add(ModItems.DILOPHOSAURUS_DNA.get())
                .add(ModItems.COMPSOGNATHUS_DNA.get())
                .add(ModItems.CERATOSAURUS_DNA.get())
                .add(ModItems.BRACHIOSAURUS_DNA.get());
        this.tag(ModTags.Items.FOSSILS)
                .add(ModItems.VELOCIRAPTOR_SKULL_FOSSIL.get())
                .add(ModItems.TYRANNOSAURUS_REX_SKULL_FOSSIL.get())
                .add(ModItems.TRICERATOPS_SKULL_FOSSIL.get())
                .add(ModItems.SPINOSAURUS_SKULL_FOSSIL.get())
                .add(ModItems.PTERANODON_SKULL_FOSSIL.get())
                .add(ModItems.PARASAUROLOPHUS_SKULL_FOSSIL.get())
                .add(ModItems.GALLIMIMUS_SKULL_FOSSIL.get())
                //.add(ModItems.DIPLODOCUS_SKULL_FOSSIL.get())
                .add(ModItems.DILOPHOSAURUS_SKULL_FOSSIL.get())
                .add(ModItems.COMPSOGNATHUS_SKULL_FOSSIL.get())
                .add(ModItems.CERATOSAURUS_SKULL_FOSSIL.get())
                .add(ModItems.BRACHIOSAURUS_SKULL_FOSSIL.get());
        this.tag(ModTags.Items.FOSSILS)
                .add(ModItems.FRESH_VELOCIRAPTOR_SKULL.get())
                .add(ModItems.FRESH_TYRANNOSAURUS_REX_SKULL.get())
                .add(ModItems.FRESH_TRICERATOPS_SKULL.get())
                .add(ModItems.FRESH_SPINOSAURUS_SKULL.get())
                .add(ModItems.FRESH_PTERANODON_SKULL.get())
                .add(ModItems.FRESH_PARASAUROLOPHUS_SKULL.get())
                .add(ModItems.FRESH_INDOMINUS_REX_SKULL.get())
                .add(ModItems.FRESH_GALLIMIMUS_SKULL.get())
                //.add(ModItems.FRESH_DIPLODOCUS_SKULL.get())
                .add(ModItems.FRESH_DILOPHOSAURUS_SKULL.get())
                .add(ModItems.FRESH_COMPSOGNATHUS_SKULL.get())
                .add(ModItems.FRESH_CERATOSAURUS_SKULL.get())
                .add(ModItems.FRESH_BRACHIOSAURUS_SKULL.get());
    }

    @Override
    public String getName() {
        return "Item Tags";
    }
}
