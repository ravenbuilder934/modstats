package com.modstats.main;

import com.google.common.collect.ImmutableMap;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Capability for the colony tag for chunks
 */
public interface IModStatManager
{
    /**
     * Get the usage statistic for a certain mod.
     * @param mod the mod.
     * @return the usage counter.
     */
    int getModUsage(final String mod);

    /**
     * Increment the usage of a certain mod.
     * @param mod the mod id.
     */
    void incrementModUsage(final String mod);

    /**
     * Set the usage of a certain mod.
     * @param mod the mod id.
     * @param val the value to set.
     */
    void setModUsage(final String mod, final int val);

    /**
     * Get the mod usage storage.
     * @return the info.
     */
    Map<String, Integer> getModUsage();

    /**
     * Reset the mod usage counters.
     */
    void resetModUsage();

    /**
     * The implementation of the colonyTagCapability.
     */
    class Impl implements IModStatManager
    {
        /**
         * Map of the usage counter per mod.
         */
        private final Map<String, Integer> modUseStorage = new HashMap<>();

        @Override
        public int getModUsage(final String mod)
        {
            return modUseStorage.getOrDefault(mod, 0);
        }

        @Override
        public void incrementModUsage(final String mod)
        {
            modUseStorage.put(mod, modUseStorage.getOrDefault(mod, 1) + 1);
        }

        @Override
        public void setModUsage(final String mod, final int val)
        {
            modUseStorage.put(mod, val);
        }

        @Override
        public Map<String, Integer> getModUsage()
        {
            return ImmutableMap.copyOf(modUseStorage);
        }

        @Override
        public void resetModUsage()
        {
            modUseStorage.clear();
        }
    }

    /**
     * The storage class of the capability.
     */
    class Storage implements Capability.IStorage<IModStatManager>
    {
        @Override
        public NBTBase writeNBT(@NotNull final Capability<IModStatManager> capability, @NotNull final IModStatManager instance, @Nullable final EnumFacing side)
        {
            final NBTTagCompound compound = new NBTTagCompound();
            compound.setTag("modStats", instance.getModUsage().entrySet().stream().map(entry -> write(entry.getKey(), entry.getValue())).collect(NBTUtils.toNBTTagList()));
            return compound;
        }

        @Override
        public void readNBT(@NotNull final Capability<IModStatManager> capability, @NotNull final IModStatManager instance,
          @Nullable final EnumFacing side, @NotNull final NBTBase nbt)
        {
            if(nbt instanceof NBTTagCompound && ((NBTTagCompound) nbt).hasKey("modStats"))
            {
                NBTUtils.streamCompound(((NBTTagCompound) nbt).getTagList("modStats", Constants.NBT.TAG_COMPOUND))
                  .map(Storage::read).forEach(key -> instance.setModUsage(key.getFirst(), key.getSecond()));
            }
        }

        /**
         * Write a single ChunkPos, ChunkLoadStorage pair to nbt.
         * @param key the key.
         * @param value the value
         * @return the resulting compound.
         */
        private static NBTTagCompound write(final String key, final int value)
        {
            final NBTTagCompound compound = new NBTTagCompound();
            compound.setString("mod", key);
            compound.setInteger("value", value);
            return compound;
        }

        /**
         * Read a key value pair for the chunkloadstorages.
         * @param compound the compound to read it from.
         * @return a tuple for both.
         */
        private static Tuple<String, Integer> read(final NBTTagCompound compound)
        {
            final String mod = compound.getString("mod");
            final int value = compound.getInteger("value");
            return new Tuple<>(mod, value);
        }
    }
}
