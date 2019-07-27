package com.modstats.main;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * NBT utility class.
 */
public final class NBTUtils
{
    /**
     * Private constructor to hide implicit one.
     */
    private NBTUtils()
    {
        /*
         * Intentionally left empty.
         */
    }

    /**
     * Stream a compound list.
     * @param list the list to stream.
     * @return the resulting stream.
     */
    public static Stream<NBTTagCompound> streamCompound(final NBTTagList list)
    {
        return streamBase(list).filter(b -> b instanceof NBTTagCompound).map(b -> (NBTTagCompound) b);
    }

    /**
     * Stream to a compound base.
     * @param list the list to stream.
     * @return the base stream.
     */
    public static Stream<NBTBase> streamBase(final NBTTagList list)
    {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new TagListIterator(list), Spliterator.ORDERED), false);
    }

    /**
     * Convert a collection to a taglist.
     * @return the resulting list.
     */
    public static Collector<NBTTagCompound, ?, NBTTagList> toNBTTagList()
    {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    final NBTTagList tagList = new NBTTagList();
                    list.forEach(tagList::appendTag);

                    return tagList;
                });
    }

    /**
     * The tag list iterator class.
     */
    private static class TagListIterator implements Iterator<NBTBase>
    {
        private final NBTTagList list;
        private int currentIndex = 0;
        private TagListIterator(final NBTTagList list) {this.list = list;}

        @Override
        public boolean hasNext()
        {
            return currentIndex < list.tagCount();
        }

        @Override
        public NBTBase next()
        {
            return list.getCompoundTagAt(currentIndex++);
        }
    }
}
