package com.modstats.main.commands;

import com.modstats.main.IModStatManager;
import com.modstats.main.ModStats;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command for opening WindowScanTool or scanning a structure into a file
 */
public class InfoCommand extends CommandBase
{
    protected final static String NAME = "info";

    @NotNull
    @Override
    public String getName()
    {
        return NAME;
    }

    @NotNull
    @Override
    public String getUsage(@NotNull final ICommandSender sender)
    {
        return "/" + ModStatsCommand.NAME + " " + NAME + " <Mod-ID>";
    }
    
    @NotNull
    @Override
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender, final String[] args, @Nullable final BlockPos targetPos)
    {
        if (args.length == 1 )
        {
            final IModStatManager statCap = server.worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            return new ArrayList<>(statCap.getModUsage().keySet());
        }
        else
        {
            return Collections.emptyList();
        }
    }

    @Override
    public void execute(@NotNull final MinecraftServer server, @NotNull final ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length != 1)
        {
            throw new WrongUsageException(this.getUsage(sender));
        }

        final IModStatManager statCap = server.worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
        sender.sendMessage(new TextComponentString(args[0] + " usage: " + statCap.getModUsage(args[0])));
    }
}