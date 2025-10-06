package net.cmr.jurassicrevived.datagen.custom;

import com.google.gson.JsonObject;
import net.cmr.jurassicrevived.Config;
import net.cmr.jurassicrevived.JRMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.eventbus.api.IEventBus;

public class ConfigCondition implements ICondition {

    // Remove DeferredRegister-based registry; use CraftingHelper instead
    public static final IConditionSerializer<ConfigCondition> SERIALIZER = new Serializer();

    public static void register(IEventBus bus) {
        // Register the serializer at mod construction time
        CraftingHelper.register(SERIALIZER);
    }

    @Override
    public ResourceLocation getID() {
        return ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "require_power");
    }

    @Override
    public boolean test(ICondition.IContext context) {
        return Config.REQUIRE_POWER;
    }

    public static class Serializer implements IConditionSerializer<ConfigCondition> {
        @Override
        public void write(JsonObject json, ConfigCondition condition) {
            // No data to write for a simple boolean condition
        }

        @Override
        public ConfigCondition read(JsonObject json) {
            return new ConfigCondition();
        }

        @Override
        public ResourceLocation getID() {
            return ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "require_power");
        }
    }
}