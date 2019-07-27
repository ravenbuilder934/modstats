package com.modstats.main.commands;

import com.modstats.main.IModStatManager;
import com.modstats.main.ModStats;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Command for opening WindowScanTool or scanning a structure into a file
 */
public class ListCommand extends CommandBase
{
    /**
     * The description per mod sent to the player.
     */
    private static final String MOD_DESCRIPTION    = "§2ID: §f %s §2 Usage: §f %d";

    /**
     * The list page top.
     */
    private static final String PAGE_TOP               = "§2   ------------------ page %d of %d ------------------";

    /**
     * The prev arrow "button".
     */
    private static final String PREV_PAGE              = " <- prev";

    /**
     * The next arrow "button".
     */
    private static final String NEXT_PAGE              = "next -> ";

    /**
     * The page line.
     */
    private static final String PAGE_LINE              = "§2 ----------------";

    /**
     * The page line divider.
     */
    private static final String PAGE_LINE_DIVIDER      = "§2 | ";

    /**
     * The quantity of mods to display per page.
     */
    private static final int    MODS_PER_PAGE       = 9;

    /**
     * The identifier of this command.
     */
    protected final static String NAME = "show";

    /**
     * The suggested command for the next and prev page.
     */
    private static final String LIST_COMMAND_SUGGESTED = "/" + ModStatsCommand.NAME + " " + NAME + "%d";

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
        return "/" + ModStatsCommand.NAME + " " + NAME + " [page]";
    }
    
    @NotNull
    @Override
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender, final String[] args, @Nullable final BlockPos targetPos)
    {
        if (args.length == 0)
        {
            final IModStatManager statCap = server.worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);
            final int size = statCap.getModUsage().keySet().size();
            final List<String> pages = new ArrayList<>();
            for (int i = 0; i < size; i++)
            {
                pages.add(Integer.toString(i));
            }
            return pages;
        }
        else
        {
            return Collections.emptyList();
        }
    }

    @Override
    public void execute(@NotNull final MinecraftServer server, @NotNull final ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length > 1)
        {
            throw new WrongUsageException(this.getUsage(sender));
        }

        final IModStatManager statCap = server.worlds[0].getCapability(ModStats.MOD_STAT_CAP, null);


        List<Tuple<String, Integer>> listOfMods = statCap.getModUsage().entrySet().stream().map(entry -> new Tuple<>(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        Collections.sort(listOfMods, new Comparator<Tuple<String, Integer>>() {
            @Override
            public int compare(final Tuple<String, Integer> t1, final Tuple<String, Integer> t2)
            {
                return t2.getSecond() - t1.getSecond();
            }
        });

        final int count = listOfMods.size();

        // check to see if we have to add one page to show the half page
        final int halfPage = (count % MODS_PER_PAGE == 0) ? 0 : 1;
        final int pageCount = ((count) / MODS_PER_PAGE) + halfPage;
        int page = 1;
        if (args.length > 0)
        {
            try
            {
                page = Integer.parseInt(args[0]);
                if (page < 1 || page > pageCount)
                {
                    page = 1;
                }
            }
            catch (NumberFormatException ex)
            {
                throw new WrongUsageException(this.getUsage(sender));
            }
        }

        final int pageStartIndex = MODS_PER_PAGE * (page - 1);
        final int pageStopIndex = Math.min(MODS_PER_PAGE * page, count);

        final List<Tuple<String, Integer>> modsPage;

        if (pageStartIndex < 0 || pageStartIndex >= page)
        {
            modsPage = new ArrayList<>();
        }
        else
        {
            modsPage = listOfMods.subList(pageStartIndex, pageStopIndex);
        }

        final ITextComponent headerLine = new TextComponentString(String.format(PAGE_TOP, page, pageCount));
        sender.sendMessage(headerLine);

        for (final Tuple<String, Integer> mod : modsPage)
        {
            sender.sendMessage(new TextComponentString(String.format(MOD_DESCRIPTION,
              mod.getFirst(),
              mod.getSecond())));
        }
        drawPageSwitcher(sender, page, count, halfPage);
    }

    /**
     * Draws the page switcher at the bottom.
     *
     * @param sender   the sender.
     * @param page     the page number.
     * @param count    number of citizens.
     * @param halfPage the halfPage.
     */
    private static void drawPageSwitcher(@NotNull final ICommandSender sender, final int page, final int count, final int halfPage)
    {
        final int prevPage = Math.max(0, page - 1);
        final int nextPage = Math.min(page + 1, (count / MODS_PER_PAGE) + halfPage);

        final ITextComponent prevButton = new TextComponentString(PREV_PAGE).setStyle(new Style().setBold(true).setColor(TextFormatting.GOLD).setClickEvent(
          new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format(LIST_COMMAND_SUGGESTED, prevPage))
        ));
        final ITextComponent nextButton = new TextComponentString(NEXT_PAGE).setStyle(new Style().setBold(true).setColor(TextFormatting.GOLD).setClickEvent(
          new ClickEvent(ClickEvent.Action.RUN_COMMAND, String.format(LIST_COMMAND_SUGGESTED, nextPage))
        ));

        final ITextComponent beginLine = new TextComponentString(PAGE_LINE);
        final ITextComponent endLine = new TextComponentString(PAGE_LINE);
        sender.sendMessage(beginLine.appendSibling(prevButton).appendSibling(new TextComponentString(PAGE_LINE_DIVIDER)).appendSibling(nextButton).appendSibling(endLine));
    }
}