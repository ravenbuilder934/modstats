package com.modstats.main.commands;

import com.modstats.main.util.Constants;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;
import org.jetbrains.annotations.NotNull;

/**
 * Mod Stats super command.
 */
public class ModStatsCommand extends CommandTreeBase
{
    /**
     * The name of this command.
     */
    final static String NAME = Constants.MOD_ID;

    /**
     * Constructor which also initiates the sub structure.
     */
    public ModStatsCommand()
    {
        super.addSubcommand(new InfoCommand());
        super.addSubcommand(new ResetCommand());
        super.addSubcommand(new ListCommand());
    }
    
    @NotNull
    @Override
    public String getName()
    {
        return NAME;
    }
    
    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
    
    @Override
    public boolean checkPermission(final MinecraftServer server, final ICommandSender sender)
    {
        return true;
    }
    
    @NotNull
    @Override
    public String getUsage(@NotNull final ICommandSender sender)
    {
        
        final StringBuilder usage = new StringBuilder();
        usage.append("/");
        usage.append(NAME);
        usage.append(" <");
        for(final String sub : super.getCommandMap().keySet())
        {
            usage.append(sub);
            usage.append(" | ");
        }
        usage.delete(usage.length() - 3, usage.length());
        usage.append(">");
        return usage.toString();
    }
    
    @Override
    public void execute(@NotNull final MinecraftServer server, @NotNull final ICommandSender sender, final String[] args) throws CommandException
    {
        if (args.length < 1)
        {
            throw new WrongUsageException(this.getUsage(sender));
        }
        super.execute(server, sender, args);
    }
}