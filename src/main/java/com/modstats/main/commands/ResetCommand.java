package com.modstats.main.commands;

import com.modstats.main.ModStats;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import org.jetbrains.annotations.NotNull;

/**
 * Command for opening WindowScanTool or scanning a structure into a file
 */
public class ResetCommand extends CommandBase
{
    protected final static String NAME = "reset";

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
        return "/" + ModStatsCommand.NAME + " " + NAME;
    }

    @Override
    public void execute(@NotNull final MinecraftServer server, @NotNull final ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length != 0)
        {
            throw new WrongUsageException(this.getUsage(sender));
        }

        server.worlds[0].getCapability(ModStats.MOD_STAT_CAP, null).resetModUsage();
        sender.sendMessage(new TextComponentString("Successfully reset the usage stats!"));
    }
}