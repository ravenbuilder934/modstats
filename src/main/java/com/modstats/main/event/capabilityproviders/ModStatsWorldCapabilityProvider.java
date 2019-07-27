package com.modstats.main.event.capabilityproviders;

import com.modstats.main.IModStatManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.modstats.main.ModStats.MOD_STAT_CAP;

/**
 * Capability provider for the world modStats capability.
 */
public class ModStatsWorldCapabilityProvider implements ICapabilitySerializable<NBTBase>
{
    /**
     * The chunk map capability.
     */
    private final IModStatManager chunkMap;

    /**
     * Constructor of the provider.
     */
    public ModStatsWorldCapabilityProvider()
    {
        this.chunkMap = new IModStatManager.Impl();
    }

    @Override
    public NBTBase serializeNBT()
    {
        return MOD_STAT_CAP.getStorage().writeNBT(MOD_STAT_CAP, chunkMap, null);
    }

    @Override
    public void deserializeNBT(final NBTBase nbt)
    {
        MOD_STAT_CAP.getStorage().readNBT(MOD_STAT_CAP, chunkMap, null, nbt);
    }

    @Override
    public boolean hasCapability(@Nonnull final Capability<?> capability, @Nullable final EnumFacing facing)
    {
        return capability == MOD_STAT_CAP;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull final Capability<T> capability, @Nullable final EnumFacing facing)
    {
        return capability == MOD_STAT_CAP ? MOD_STAT_CAP.cast(chunkMap) : null;
    }
}