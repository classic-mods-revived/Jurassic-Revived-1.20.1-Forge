package net.cmr.jurassicrevived;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = JRMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    // Power requirement toggle
    private static final ForgeConfigSpec.BooleanValue REQUIRE_POWER_SPEC = BUILDER
            .comment("If true, power systems are enabled. If false, disabled. Requires a restart to take effect")
            .define("requirePower", false);

    // Throughput (per second) with clamped defaults
    private static final int MAX_ITEMS_PER_SEC = 1024;
    private static final int MAX_MB_PER_SEC = 100000;
    private static final int MAX_FE_PER_SEC = 2097152;

    private static final ForgeConfigSpec.IntValue ITEMS_PER_SECOND = BUILDER
            .comment("Max items transferred per second by pipes")
            .comment("Default: 64")
            .defineInRange("itemsPerSecond", Math.min(64, MAX_ITEMS_PER_SEC), 0, MAX_ITEMS_PER_SEC);

    private static final ForgeConfigSpec.IntValue MB_PER_SECOND = BUILDER
            .comment("Max millibuckets transferred per second by pipes")
            .comment("Default: 1,000")
            .defineInRange("milliBucketsPerSecond", Math.min(1000, MAX_MB_PER_SEC), 0, MAX_MB_PER_SEC);

    private static final ForgeConfigSpec.IntValue FE_PER_SECOND = BUILDER
            .comment("Max FE transferred per second by pipes")
            .comment("Default: 2,048")
            .defineInRange("fePerSecond", Math.min(2048, MAX_FE_PER_SEC), 0, MAX_FE_PER_SEC);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    // Cached values
    public static boolean REQUIRE_POWER;
    public static int itemsPerSecond;
    public static int milliBucketsPerSecond;
    public static int fePerSecond;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        REQUIRE_POWER = REQUIRE_POWER_SPEC.get();
        itemsPerSecond = ITEMS_PER_SECOND.get();
        milliBucketsPerSecond = MB_PER_SECOND.get();
        fePerSecond = FE_PER_SECOND.get();
    }

    // Public setters for in-game screen to update spec and cache (no deprecated APIs required)
    public static void setRequirePower(boolean value) {
        REQUIRE_POWER_SPEC.set(value);
        REQUIRE_POWER = value;
    }

    public static void setItemsPerSecond(int value) {
        ITEMS_PER_SECOND.set(Math.max(0, Math.min(value, MAX_ITEMS_PER_SEC)));
        itemsPerSecond = ITEMS_PER_SECOND.get();
    }

    public static void setMilliBucketsPerSecond(int value) {
        MB_PER_SECOND.set(Math.max(0, Math.min(value, MAX_MB_PER_SEC)));
        milliBucketsPerSecond = MB_PER_SECOND.get();
    }

    public static void setFePerSecond(int value) {
        FE_PER_SECOND.set(Math.max(0, Math.min(value, MAX_FE_PER_SEC)));
        fePerSecond = FE_PER_SECOND.get();
    }
}
