package com.modstats.main.event;

import com.modstats.main.IModStatManager;
import com.modstats.main.Configurations;
import com.modstats.main.ModStats;
import com.modstats.main.event.capabilityproviders.ModStatsWorldCapabilityProvider;
import com.modstats.main.util.Constants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import sun.net.ResourceManager;

/**
 * This class handles all permission checks on events and cancels them if needed.
 */
public class ModEventHandler
{
    @SubscribeEvent
    public void on(final BlockEvent.PlaceEvent event)
    {
        if (!event.getWorld().isRemote && Configurations.blockPlaceEvent)
        {
            final IModStatManager statCap = event.getWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getPlacedBlock().getBlock().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(final BlockEvent.BreakEvent event)
    {
        if (!event.getWorld().isRemote && Configurations.blockBreakEvent)
        {
            final IModStatManager statCap = event.getWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getState().getBlock().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(final BlockEvent.HarvestDropsEvent event)
    {
        if (!event.getWorld().isRemote && Configurations.blockHarvestEvent)
        {
            final IModStatManager statCap = event.getWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getState().getBlock().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(@NotNull final PlayerInteractEvent.RightClickBlock event)
    {
        if (!event.getWorld().isRemote && Configurations.rightClickBlockEvent)
        {
            final IModStatManager statCap = event.getWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getWorld().getBlockState(event.getPos()).getBlock().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(@NotNull final PlayerInteractEvent.LeftClickBlock event)
    {
        if (!event.getWorld().isRemote && Configurations.rightClickBlockEvent)
        {
            final IModStatManager statCap = event.getWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getItemStack().getItem().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(@NotNull final BlockEvent.CropGrowEvent event)
    {
        if (!event.getWorld().isRemote && Configurations.blockGrowEvent)
        {
            final IModStatManager statCap = event.getWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getState().getBlock().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(@NotNull final PlayerEvent.ItemCraftedEvent event)
    {
        if (!event.player.getEntityWorld().isRemote && Configurations.itemCraft)
        {
            final IModStatManager statCap = event.player.getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.crafting.getItem().getRegistryName().getNamespace());
            for (int i = 0; i < event.craftMatrix.getSizeInventory(); i++)
            {
                statCap.incrementModUsage(event.craftMatrix.getStackInSlot(i).getItem().getRegistryName().getNamespace());
            }
        }
    }

    @SubscribeEvent
    public void on(@NotNull final PlayerEvent.ItemSmeltedEvent event)
    {
        if (!event.player.getEntityWorld().isRemote && Configurations.itemSmelt)
        {
            final IModStatManager statCap = event.player.getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.smelting.getItem().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(@NotNull final PlayerInteractEvent.RightClickItem event)
    {
        if (!event.getWorld().isRemote && Configurations.rightClickItem)
        {
            final IModStatManager statCap = event.getWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getItemStack().getItem().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(final ItemTossEvent event)
    {
        if (!event.getPlayer().getEntityWorld().isRemote && Configurations.itemToss)
        {
            final IModStatManager statCap = event.getPlayer().getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getEntityItem().getItem().getItem().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(final EntityItemPickupEvent event)
    {
        if (!event.getEntity().getEntityWorld().isRemote && Configurations.itemPickUp)
        {
            final IModStatManager statCap = event.getEntity().getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getItem().getItem().getItem().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(final PlayerInteractEvent.EntityInteract event)
    {
        if (!event.getEntity().getEntityWorld().isRemote && Configurations.entityInteract)
        {
            final IModStatManager statCap = event.getEntity().getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            if (event.getTarget() != null && EntityList.getKey(event.getTarget()) != null)
            {
                statCap.incrementModUsage(EntityList.getKey(event.getTarget()).getNamespace());
            }
        }
    }

    @SubscribeEvent
    public void on(final PlayerInteractEvent.EntityInteractSpecific event)
    {
        if (!event.getEntity().getEntityWorld().isRemote && Configurations.entityInteract)
        {
            final IModStatManager statCap = event.getEntity().getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(EntityList.getKey(event.getTarget()).getNamespace());
        }
    }

    @SubscribeEvent
    public void on(final AttackEntityEvent event)
    {
        if (!event.getEntity().getEntityWorld().isRemote && Configurations.entityAttack)
        {
            final IModStatManager statCap = event.getEntity().getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            if (event.getTarget() != null)
            {
                final ResourceLocation key = EntityList.getKey(event.getTarget());
                if (key != null)
                {
                    statCap.incrementModUsage(key.getNamespace());
                }
            }
            event.getEntity().getEquipmentAndArmor().forEach(stack -> statCap.incrementModUsage(stack.getItem().getRegistryName().getNamespace()));
        }
    }

    @SubscribeEvent
    public void on(@NotNull final EnchantmentLevelSetEvent event)
    {
        if (!event.getWorld().isRemote && Configurations.itemEnchantment)
        {
            final IModStatManager statCap = event.getWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getItem().getItem().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(@NotNull final LivingAttackEvent event)
    {
        final Entity entity = event.getEntity();
        if (entity != null && Configurations.entityAttack)
        {
            final World world = entity.getEntityWorld();
            if (world instanceof WorldServer && world.getMinecraftServer() != null)
            {
                final IModStatManager statCap = world.getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
                final ResourceLocation loc = EntityList.getKey(event.getEntity());
                if (loc != null)
                {
                    statCap.incrementModUsage(loc.getNamespace());
                }

                if (event.getSource().getTrueSource() != null)
                {
                    final ResourceLocation key = EntityList.getKey(event.getSource().getTrueSource());
                    if (key != null)
                    {
                        statCap.incrementModUsage(key.getNamespace());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void on(@NotNull final ItemFishedEvent event)
    {
        if (!event.getEntity().getEntityWorld().isRemote && Configurations.itemFished)
        {
            final IModStatManager statCap = event.getEntity().getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            event.getDrops().stream().forEach(item -> statCap.incrementModUsage(item.getItem().getRegistryName().getNamespace()));
        }
    }

    @SubscribeEvent
    public void on(@NotNull final FillBucketEvent event)
    {
        if (!event.getEntity().getEntityWorld().isRemote && Configurations.bucketFilled)
        {
            final IModStatManager statCap = event.getEntity().getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getEmptyBucket().getItem().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(@NotNull final ArrowNockEvent event)
    {
        if (!event.getEntity().getEntityWorld().isRemote && Configurations.arrowNock)
        {
            final IModStatManager statCap = event.getEntity().getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getBow().getItem().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(@NotNull final ArrowLooseEvent event)
    {
        if (!event.getEntity().getEntityWorld().isRemote && Configurations.arrowLoose)
        {
            final IModStatManager statCap = event.getEntity().getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getBow().getItem().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(@NotNull final AnvilRepairEvent event)
    {
        if (!event.getEntity().getEntityWorld().isRemote && Configurations.anvilRepair)
        {
            final IModStatManager statCap = event.getEntity().getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(event.getIngredientInput().getItem().getRegistryName().getNamespace());
        }
    }

    @SubscribeEvent
    public void on(@NotNull final ProjectileImpactEvent event)
    {
        if (!event.getEntity().getEntityWorld().isRemote && Configurations.entityAttack)
        {
            final IModStatManager statCap = event.getEntity().getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            if (event.getEntity() != null)
            {
                statCap.incrementModUsage(EntityList.getKey(event.getEntity()).getNamespace());
            }
        }
    }

    @SubscribeEvent
    public void on(@NotNull final EntityMountEvent event)
    {
        if (!event.getEntity().getEntityWorld().isRemote && Configurations.entityMount)
        {
            final IModStatManager statCap = event.getEntity().getEntityWorld().getMinecraftServer().worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            statCap.incrementModUsage(EntityList.getKey(event.getEntityBeingMounted()).getNamespace());
        }
    }

    @SubscribeEvent
    public void onAttachingCapabilitiesWorld(@NotNull final AttachCapabilitiesEvent<World> event)
    {
        event.addCapability(new ResourceLocation(Constants.MOD_ID, "chunkUpdate"), new ModStatsWorldCapabilityProvider());
    }
}
