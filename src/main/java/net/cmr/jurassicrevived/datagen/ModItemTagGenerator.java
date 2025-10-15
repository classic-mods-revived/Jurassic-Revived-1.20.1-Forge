package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.item.ModItems;
import net.cmr.jurassicrevived.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
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
                .add(ModItems.ALBERTOSAURUS_TISSUE.get())
                .add(ModItems.BRACHIOSAURUS_TISSUE.get())
                .add(ModItems.CERATOSAURUS_TISSUE.get())
                .add(ModItems.COMPSOGNATHUS_TISSUE.get())
                .add(ModItems.DILOPHOSAURUS_TISSUE.get())
                .add(ModItems.DIPLODOCUS_TISSUE.get())
                .add(ModItems.GALLIMIMUS_TISSUE.get())
                .add(ModItems.INDOMINUS_REX_TISSUE.get())
                .add(ModItems.OURANOSAURUS_TISSUE.get())
                .add(ModItems.PARASAUROLOPHUS_TISSUE.get())
                .add(ModItems.SPINOSAURUS_TISSUE.get())
                .add(ModItems.TRICERATOPS_TISSUE.get())
                .add(ModItems.TYRANNOSAURUS_REX_TISSUE.get())
                .add(ModItems.VELOCIRAPTOR_TISSUE.get());
        this.tag(ModTags.Items.DNA)
                .add(ModItems.ALBERTOSAURUS_DNA.get())
                .add(ModItems.BRACHIOSAURUS_DNA.get())
                .add(ModItems.CERATOSAURUS_DNA.get())
                .add(ModItems.COMPSOGNATHUS_DNA.get())
                .add(ModItems.DILOPHOSAURUS_DNA.get())
                .add(ModItems.DIPLODOCUS_DNA.get())
                .add(ModItems.GALLIMIMUS_DNA.get())
                .add(ModItems.INDOMINUS_REX_DNA.get())
                .add(ModItems.OURANOSAURUS_DNA.get())
                .add(ModItems.PARASAUROLOPHUS_DNA.get())
                .add(ModItems.SPINOSAURUS_DNA.get())
                .add(ModItems.TRICERATOPS_DNA.get())
                .add(ModItems.TYRANNOSAURUS_REX_DNA.get())
                .add(ModItems.VELOCIRAPTOR_DNA.get());
        this.tag(ModTags.Items.SYRINGES)
                .add(ModItems.ALBERTOSAURUS_SYRINGE.get())
                .add(ModItems.BRACHIOSAURUS_SYRINGE.get())
                .add(ModItems.CERATOSAURUS_SYRINGE.get())
                .add(ModItems.COMPSOGNATHUS_SYRINGE.get())
                .add(ModItems.DILOPHOSAURUS_SYRINGE.get())
                .add(ModItems.DIPLODOCUS_SYRINGE.get())
                .add(ModItems.GALLIMIMUS_SYRINGE.get())
                .add(ModItems.INDOMINUS_REX_SYRINGE.get())
                .add(ModItems.OURANOSAURUS_SYRINGE.get())
                .add(ModItems.PARASAUROLOPHUS_SYRINGE.get())
                .add(ModItems.SPINOSAURUS_SYRINGE.get())
                .add(ModItems.TRICERATOPS_SYRINGE.get())
                .add(ModItems.TYRANNOSAURUS_REX_SYRINGE.get())
                .add(ModItems.VELOCIRAPTOR_SYRINGE.get());
        this.tag(ModTags.Items.EGGS)
                .add(ModItems.ALBERTOSAURUS_EGG.get())
                .add(ModItems.BRACHIOSAURUS_EGG.get())
                .add(ModItems.CERATOSAURUS_EGG.get())
                .add(ModItems.COMPSOGNATHUS_EGG.get())
                .add(ModItems.DILOPHOSAURUS_EGG.get())
                .add(ModItems.DIPLODOCUS_EGG.get())
                .add(ModItems.GALLIMIMUS_EGG.get())
                .add(ModItems.INDOMINUS_REX_EGG.get())
                .add(ModItems.OURANOSAURUS_EGG.get())
                .add(ModItems.PARASAUROLOPHUS_EGG.get())
                .add(ModItems.SPINOSAURUS_EGG.get())
                .add(ModItems.TRICERATOPS_EGG.get())
                .add(ModItems.TYRANNOSAURUS_REX_EGG.get())
                .add(ModItems.VELOCIRAPTOR_EGG.get());
        this.tag(ModTags.Items.FOSSILS)
                .add(ModItems.ALBERTOSAURUS_SKULL_FOSSIL.get())
                .add(ModItems.BRACHIOSAURUS_SKULL_FOSSIL.get())
                .add(ModItems.CERATOSAURUS_SKULL_FOSSIL.get())
                .add(ModItems.COMPSOGNATHUS_SKULL_FOSSIL.get())
                .add(ModItems.DILOPHOSAURUS_SKULL_FOSSIL.get())
                .add(ModItems.DIPLODOCUS_SKULL_FOSSIL.get())
                .add(ModItems.GALLIMIMUS_SKULL_FOSSIL.get())
                .add(ModItems.OURANOSAURUS_SKULL_FOSSIL.get())
                .add(ModItems.PARASAUROLOPHUS_SKULL_FOSSIL.get())
                .add(ModItems.SPINOSAURUS_SKULL_FOSSIL.get())
                .add(ModItems.TRICERATOPS_SKULL_FOSSIL.get())
                .add(ModItems.TYRANNOSAURUS_REX_SKULL_FOSSIL.get())
                .add(ModItems.VELOCIRAPTOR_SKULL_FOSSIL.get());
        this.tag(ModTags.Items.SKULLS)
                .add(ModItems.FRESH_ALBERTOSAURUS_SKULL.get())
                .add(ModItems.FRESH_BRACHIOSAURUS_SKULL.get())
                .add(ModItems.FRESH_CERATOSAURUS_SKULL.get())
                .add(ModItems.FRESH_COMPSOGNATHUS_SKULL.get())
                .add(ModItems.FRESH_DILOPHOSAURUS_SKULL.get())
                .add(ModItems.FRESH_DIPLODOCUS_SKULL.get())
                .add(ModItems.FRESH_GALLIMIMUS_SKULL.get())
                .add(ModItems.FRESH_INDOMINUS_REX_SKULL.get())
                .add(ModItems.FRESH_OURANOSAURUS_SKULL.get())
                .add(ModItems.FRESH_PARASAUROLOPHUS_SKULL.get())
                .add(ModItems.FRESH_SPINOSAURUS_SKULL.get())
                .add(ModItems.FRESH_TRICERATOPS_SKULL.get())
                .add(ModItems.FRESH_TYRANNOSAURUS_REX_SKULL.get())
                .add(ModItems.FRESH_VELOCIRAPTOR_SKULL.get());
    }

    @Override
    public String getName() {
        return "Item Tags";
    }
}
