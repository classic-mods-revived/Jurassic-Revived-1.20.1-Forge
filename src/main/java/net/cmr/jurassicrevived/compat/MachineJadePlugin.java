package net.cmr.jurassicrevived.compat;

import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.block.custom.*;
import net.cmr.jurassicrevived.block.entity.custom.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IBoxStyle;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.ui.IProgressStyle;

@WailaPlugin
public class MachineJadePlugin implements IWailaPlugin {
    private static final ResourceLocation MACHINE_UID = ResourceLocation.fromNamespaceAndPath(JRMod.MOD_ID, "machine_progress");
    private static final String NBT_PROGRESS = "jr_progress";
    private static final String NBT_MAX = "jr_max";

    @Override
    public void registerClient(IWailaClientRegistration reg) {
        IBlockComponentProvider provider = new IBlockComponentProvider() {
            @Override
            public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                CompoundTag data = accessor.getServerData();
                if (!data.contains(NBT_PROGRESS) || !data.contains(NBT_MAX)) return;

                int progress = data.getInt(NBT_PROGRESS);
                int max = Math.max(1, data.getInt(NBT_MAX));
                float ratio = Mth.clamp(progress / (float) max, 0.0f, 1.0f);

                IElementHelper h = tooltip.getElementHelper();
                IProgressStyle pStyle = h.progressStyle()
                        .color(0xFFFFFFFF)
                        .textColor(0xFFFFFFFF);
                IBoxStyle box = new ThickBorderBox(1.0f);

                tooltip.add(h.progress(ratio, Component.empty(), pStyle, box, false));
            }

            @Override
            public ResourceLocation getUid() {
                return MACHINE_UID;
            }
        };

        reg.registerBlockComponent(provider, DNAExtractorBlock.class);
        reg.registerBlockComponent(provider, DNAHybridizerBlock.class);
        reg.registerBlockComponent(provider, EmbryoCalcificationMachineBlock.class);
        reg.registerBlockComponent(provider, EmbryonicMachineBlock.class);
        reg.registerBlockComponent(provider, FossilGrinderBlock.class);
        reg.registerBlockComponent(provider, FossilCleanerBlock.class);
        reg.registerBlockComponent(provider, IncubatorBlock.class);
    }

    @Override
    public void register(IWailaCommonRegistration reg) {
        IServerDataProvider<BlockAccessor> server = new IServerDataProvider<>() {
            @Override
            public void appendServerData(CompoundTag data, BlockAccessor accessor) {
                BlockEntity be = accessor.getBlockEntity();

                // Expecting fields: private int progress; private int maxProgress;
                // Read via the menu ContainerData if available, else reflect as fallback.
                int progress = -1;
                int max = -1;

                try {
                    var cls = be.getClass();
                    var fProg = cls.getDeclaredField("progress");
                    var fMax = cls.getDeclaredField("maxProgress");
                    fProg.setAccessible(true);
                    fMax.setAccessible(true);
                    progress = (int) fProg.get(be);
                    max = (int) fMax.get(be);
                } catch (Throwable ignored) {
                }

                if (progress >= 0 && max > 0) {
                    data.putInt(NBT_PROGRESS, progress);
                    data.putInt(NBT_MAX, max);
                }
            }

            @Override
            public ResourceLocation getUid() {
                return MACHINE_UID;
            }
        };

        reg.registerBlockDataProvider(server, DNAExtractorBlockEntity.class);
        reg.registerBlockDataProvider(server, DNAHybridizerBlockEntity.class);
        reg.registerBlockDataProvider(server, EmbryoCalcificationMachineBlockEntity.class);
        reg.registerBlockDataProvider(server, EmbryonicMachineBlockEntity.class);
        reg.registerBlockDataProvider(server, FossilGrinderBlockEntity.class);
        reg.registerBlockDataProvider(server, FossilCleanerBlockEntity.class);
        reg.registerBlockDataProvider(server, IncubatorBlockEntity.class);
    }
}
