package net.cmr.jurassicrevived.block.entity.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class ModEnergyUtil {
    public static boolean move(BlockPos from, BlockPos to, int amount, Level level) {
        IEnergyStorage fromStorage = getEnergy(level, from, null);
        IEnergyStorage toStorage = getEnergy(level, to, null);

        if (fromStorage == null || toStorage == null) return false;

        if (canEnergyStorageExtractThisAmount(fromStorage, amount)) {
            return false;
        }
        if (canEnergyStorageStillReceiveEnergy(toStorage)) {
            return false;
        }

        int maxAmountToReceive = toStorage.receiveEnergy(amount, true);
        int extractedEnergy = fromStorage.extractEnergy(maxAmountToReceive, false);
        toStorage.receiveEnergy(extractedEnergy, false);

        return extractedEnergy > 0;
    }

    private static IEnergyStorage getEnergy(Level level, BlockPos pos, @Nullable Direction side) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be == null) return null;
        return be.getCapability(ForgeCapabilities.ENERGY, side).orElse(null);
    }

    private static boolean canEnergyStorageStillReceiveEnergy(IEnergyStorage toStorage) {
        return toStorage.getEnergyStored() >= toStorage.getMaxEnergyStored() || !toStorage.canReceive();
    }

    private static boolean canEnergyStorageExtractThisAmount(IEnergyStorage fromStorage, int amount) {
        return fromStorage.getEnergyStored() <= 0 || fromStorage.getEnergyStored() < amount || !fromStorage.canExtract();
    }

    public static boolean doesBlockHaveEnergyStorage(BlockPos positionToCheck, Level level) {
        BlockEntity be = level.getBlockEntity(positionToCheck);
        return be != null && be.getCapability(ForgeCapabilities.ENERGY, null).isPresent();
    }
}