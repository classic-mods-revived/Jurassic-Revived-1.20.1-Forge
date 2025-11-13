package net.cmr.jurassicrevived.painting;

import net.cmr.jurassicrevived.JRMod;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS =
            DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, JRMod.MOD_ID);

    public static final RegistryObject<PaintingVariant> CHIC = PAINTING_VARIANTS.register("chic",
            () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> LONELY_TREE = PAINTING_VARIANTS.register("lonely_tree",
            () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> MEG = PAINTING_VARIANTS.register("meg",
            () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> SEEING_EYE = PAINTING_VARIANTS.register("seeing_eye",
            () -> new PaintingVariant(16, 16));

    public static void register(IEventBus eventBus) {
        PAINTING_VARIANTS.register(eventBus);
    }
}