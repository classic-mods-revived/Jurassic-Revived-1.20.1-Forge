package net.cmr.jurassicrevived.screen;

import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.screen.custom.*;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, JRMod.MOD_ID);

    public static final RegistryObject<MenuType<GeneratorMenu>> GENERATOR_MENU =
            registerMenuType(GeneratorMenu::new, "generator_menu");
    public static final RegistryObject<MenuType<DNAExtractorMenu>> DNA_EXTRACTOR_MENU =
            registerMenuType(DNAExtractorMenu::new, "dna_extractor_menu");
    public static final RegistryObject<MenuType<DNAAnalyzerMenu>> DNA_ANALYZER_MENU =
            registerMenuType(DNAAnalyzerMenu::new, "dna_analyzer_menu");
    public static final RegistryObject<MenuType<FossilGrinderMenu>> FOSSIL_GRINDER_MENU =
            registerMenuType(FossilGrinderMenu::new, "fossil_grinder_menu");
    public static final RegistryObject<MenuType<FossilCleanerMenu>> FOSSIL_CLEANER_MENU =
            registerMenuType(FossilCleanerMenu::new, "fossil_cleaner_menu");
    public static final RegistryObject<MenuType<DNAHybridizerMenu>> DNA_HYBRIDIZER_MENU =
            registerMenuType(DNAHybridizerMenu::new, "dna_hybridizer_menu");
    public static final RegistryObject<MenuType<EmbryonicMachineMenu>> EMBRYONIC_MACHINE_MENU =
            registerMenuType(EmbryonicMachineMenu::new, "embryonic_machine_menu");
    public static final RegistryObject<MenuType<EmbryoCalcificationMachineMenu>> EMBRYO_CALCIFICATION_MACHINE_MENU =
            registerMenuType(EmbryoCalcificationMachineMenu::new, "embryo_calcification_machine_menu");
    public static final RegistryObject<MenuType<IncubatorMenu>> INCUBATOR_MENU =
            registerMenuType(IncubatorMenu::new, "incubator_menu");
    public static final RegistryObject<MenuType<TankMenu>> TANK_MENU =
            registerMenuType(TankMenu::new, "tank_menu");


    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
