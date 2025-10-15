package net.cmr.jurassicrevived.datagen;

import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.block.ModBlocks;
import net.cmr.jurassicrevived.block.custom.FencePoleBlock;
import net.cmr.jurassicrevived.block.custom.FenceWireBlock;
import net.cmr.jurassicrevived.block.custom.PipeBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, JRMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.ROYAL_FERN.get(),
                models().cross(blockTexture(ModBlocks.ROYAL_FERN.get()).getPath(), blockTexture(ModBlocks.ROYAL_FERN.get())).renderType("cutout"));
        simpleBlock(ModBlocks.POTTED_ROYAL_FERN.get(), models().singleTexture("potted_royal_fern", ResourceLocation.parse("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.ROYAL_FERN.get())).renderType("cutout"));

        simpleBlock(ModBlocks.HORSETAIL_FERN.get(),
                models().cross(blockTexture(ModBlocks.HORSETAIL_FERN.get()).getPath(), blockTexture(ModBlocks.HORSETAIL_FERN.get())).renderType("cutout"));
        simpleBlock(ModBlocks.POTTED_HORSETAIL_FERN.get(), models().singleTexture("potted_horsetail_fern", ResourceLocation.parse("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.HORSETAIL_FERN.get())).renderType("cutout"));

        simpleBlock(ModBlocks.WESTERN_SWORD_FERN.get(),
                models().cross(blockTexture(ModBlocks.WESTERN_SWORD_FERN.get()).getPath(), blockTexture(ModBlocks.WESTERN_SWORD_FERN.get())).renderType("cutout"));
        simpleBlock(ModBlocks.POTTED_WESTERN_SWORD_FERN.get(), models().singleTexture("potted_western_sword_fern", ResourceLocation.parse("flower_pot_cross"), "plant",
                blockTexture(ModBlocks.WESTERN_SWORD_FERN.get())).renderType("cutout"));

        blockWithItem(ModBlocks.GYPSUM_STONE);
        blockWithItem(ModBlocks.GYPSUM_COBBLESTONE);
        blockWithItem(ModBlocks.GYPSUM_STONE_BRICKS);

        blockWithItem(ModBlocks.STONE_FOSSIL);
        blockWithItem(ModBlocks.DEEPSLATE_FOSSIL);
        blockWithItem(ModBlocks.AMBER_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_ICE_SHARD_ORE);

        blockWithItem(ModBlocks.REINFORCED_STONE);
        blockWithItem(ModBlocks.REINFORCED_STONE_BRICKS);

        horizontalFacingWithItem(ModBlocks.CAT_PLUSHIE);

        horizontalFacingWithItem(ModBlocks.GENERATOR);
        horizontalFacingWithItem(ModBlocks.DNA_EXTRACTOR);
        horizontalFacingWithItem(ModBlocks.FOSSIL_GRINDER);
        horizontalFacingWithItem(ModBlocks.FOSSIL_CLEANER);
        horizontalFacingWithItem(ModBlocks.DNA_HYBRIDIZER);
        horizontalFacingWithItem(ModBlocks.EMBRYONIC_MACHINE);
        horizontalFacingWithItem(ModBlocks.EMBRYO_CALCIFICATION_MACHINE);
        horizontalFacingWithItem(ModBlocks.INCUBATOR);

        eggLike(ModBlocks.HATCHED_VELOCIRAPTOR_EGG);
        eggLike(ModBlocks.HATCHED_TYRANNOSAURUS_REX_EGG);
        eggLike(ModBlocks.HATCHED_TRICERATOPS_EGG);
        //eggLike(ModBlocks.HATCHED_SPINOSAURUS_EGG);
        eggLike(ModBlocks.HATCHED_PARASAUROLOPHUS_EGG);
        eggLike(ModBlocks.HATCHED_OURANOSAURUS_EGG);
        //eggLike(ModBlocks.HATCHED_INDOMINUS_REX_EGG);
        //eggLike(ModBlocks.HATCHED_GALLIMIMUS_EGG);
        //eggLike(ModBlocks.HATCHED_DIPLODOCUS_EGG);
        eggLike(ModBlocks.HATCHED_DILOPHOSAURUS_EGG);
        eggLike(ModBlocks.HATCHED_COMPSOGNATHUS_EGG);
        eggLike(ModBlocks.HATCHED_CERATOSAURUS_EGG);
        eggLike(ModBlocks.HATCHED_BRACHIOSAURUS_EGG);

        customFenceMultipart(
                ModBlocks.LOW_SECURITY_FENCE_POLE,
                "low_security_fence_pole",
                "low_security_fence_pole_part",
                "low_security_fence_pole_diagonal_part",
                FencePoleBlock.NE,
                FencePoleBlock.SE,
                FencePoleBlock.SW,
                FencePoleBlock.NW
        );

        customFenceMultipart(
                ModBlocks.LOW_SECURITY_FENCE_WIRE,
                "low_security_fence_wire",
                "low_security_fence_wire_part",
                "low_security_fence_wire_diagonal_part",
                FenceWireBlock.NE,
                FenceWireBlock.SE,
                FenceWireBlock.SW,
                FenceWireBlock.NW
        );

        customFenceMultipart(
                ModBlocks.MEDIUM_SECURITY_FENCE_POLE,
                "medium_security_fence_pole",
                "medium_security_fence_pole_part",
                "medium_security_fence_pole_diagonal_part",
                FencePoleBlock.NE,
                FencePoleBlock.SE,
                FencePoleBlock.SW,
                FencePoleBlock.NW
        );

        customFenceMultipart(
                ModBlocks.MEDIUM_SECURITY_FENCE_WIRE,
                "medium_security_fence_wire",
                "medium_security_fence_wire_part",
                "medium_security_fence_wire_diagonal_part",
                FenceWireBlock.NE,
                FenceWireBlock.SE,
                FenceWireBlock.SW,
                FenceWireBlock.NW
        );

        pipeMultipartWithItem(ModBlocks.ITEM_PIPE, "item_pipe");
        pipeMultipartWithItem(ModBlocks.FLUID_PIPE, "fluid_pipe");
        pipeMultipartWithItem(ModBlocks.POWER_PIPE, "power_pipe");
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().cubeAll(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    public void makeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName);
        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName) {
        return new ConfiguredModel[0];
    }

    private void customLamp() {
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject, String appendix) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("mccourse:block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + appendix));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("mccourse:block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void horizontalFacingWithItem(RegistryObject<Block> block) {
        ModelFile model = new ModelFile.UncheckedModelFile(modLoc("block/" + block.getId().getPath()));
        horizontalBlock(block.get(), model);
        simpleBlockItem(block.get(), model);
    }

    private void eggLike(RegistryObject<Block> block) {
        ModelFile eggModel = new ModelFile.UncheckedModelFile(modLoc("block/egg"));
        simpleBlock(block.get(), eggModel);
    }

    private void pipeMultipartWithItem(RegistryObject<? extends Block> regBlock, String modelBaseName) {
        pipeMultipart(regBlock, modelBaseName);
        ModelFile itemParent = new ModelFile.UncheckedModelFile(modLoc("block/" + modelBaseName));
        simpleBlockItem(regBlock.get(), itemParent);
    }

    private void pipeMultipart(RegistryObject<? extends Block> regBlock, String modelBaseName) {
        Block block = regBlock.get();
        var multipart = getMultipartBuilder(block);

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + modelBaseName)))
                .addModel()
                .end();

        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.UP,   PipeBlock.ConnectionType.PIPE, 90, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.DOWN, PipeBlock.ConnectionType.PIPE, 270, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.NORTH, PipeBlock.ConnectionType.PIPE, 0, 180);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.EAST,  PipeBlock.ConnectionType.PIPE, 0, 270);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.SOUTH, PipeBlock.ConnectionType.PIPE, 0, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_interchange", PipeBlock.WEST,  PipeBlock.ConnectionType.PIPE, 0, 90);

        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.UP,   PipeBlock.ConnectionType.CONNECTOR, 90, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.DOWN, PipeBlock.ConnectionType.CONNECTOR, 270, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.NORTH, PipeBlock.ConnectionType.CONNECTOR, 0, 180);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.EAST,  PipeBlock.ConnectionType.CONNECTOR, 0, 270);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.SOUTH, PipeBlock.ConnectionType.CONNECTOR, 0, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector", PipeBlock.WEST,  PipeBlock.ConnectionType.CONNECTOR, 0, 90);

        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.UP,   PipeBlock.ConnectionType.CONNECTOR_PULL, 90, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.DOWN, PipeBlock.ConnectionType.CONNECTOR_PULL, 270, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.NORTH, PipeBlock.ConnectionType.CONNECTOR_PULL, 0, 180);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.EAST,  PipeBlock.ConnectionType.CONNECTOR_PULL, 0, 270);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.SOUTH, PipeBlock.ConnectionType.CONNECTOR_PULL, 0, 0);
        addDirectionalEnumPart(multipart, "block/" + modelBaseName + "_connector_pull", PipeBlock.WEST,  PipeBlock.ConnectionType.CONNECTOR_PULL, 0, 90);
    }

    private void addDirectionalEnumPart(MultiPartBlockStateBuilder multipart,
                                        String modelPath,
                                        EnumProperty<PipeBlock.ConnectionType> prop,
                                        PipeBlock.ConnectionType value,
                                        int rotX,
                                        int rotY) {
        multipart.part()
                .modelFile(models().getExistingFile(modLoc(modelPath)))
                .rotationX(rotX)
                .rotationY(rotY)
                .addModel()
                .condition(prop, value)
                .end();
    }

    private void customFenceMultipart(
            RegistryObject<? extends Block> block,
            String baseModelName,
            String straightArmModelName,
            String diagonalArmModelName,
            BooleanProperty neProp,
            BooleanProperty seProp,
            BooleanProperty swProp,
            BooleanProperty nwProp
    ) {
        var multipart = getMultipartBuilder(block.get());

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + baseModelName)))
                .addModel()
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + straightArmModelName)))
                .rotationY(0)
                .addModel()
                .condition(BlockStateProperties.NORTH, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + straightArmModelName)))
                .rotationY(90)
                .addModel()
                .condition(BlockStateProperties.EAST, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + straightArmModelName)))
                .rotationY(180)
                .addModel()
                .condition(BlockStateProperties.SOUTH, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + straightArmModelName)))
                .rotationY(270)
                .addModel()
                .condition(BlockStateProperties.WEST, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + diagonalArmModelName)))
                .rotationY(90)
                .addModel()
                .condition(neProp, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + diagonalArmModelName)))
                .rotationY(180)
                .addModel()
                .condition(seProp, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + diagonalArmModelName)))
                .rotationY(270)
                .addModel()
                .condition(swProp, true)
                .end();

        multipart.part()
                .modelFile(models().getExistingFile(modLoc("block/" + diagonalArmModelName)))
                .rotationY(0)
                .addModel()
                .condition(nwProp, true)
                .end();
    }
}
