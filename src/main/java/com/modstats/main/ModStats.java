package com.modstats.main;

import com.modstats.main.commands.ModStatsCommand;
import com.modstats.main.event.ModEventHandler;
import com.modstats.main.util.Constants;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber
@Mod(modid = Constants.MOD_ID, name = Constants.MOD_NAME, version = Constants.VERSION, acceptedMinecraftVersions = Constants.MC_VERSION, acceptableRemoteVersions = "*")
public class ModStats
{
    /**
     * The static capability.
     */
    @CapabilityInject(IModStatManager.class)
    public static Capability<IModStatManager> MOD_STAT_CAP;

    /**
     * The custom logger.
     */
    private static final Logger   logger = LogManager.getLogger(Constants.MOD_ID);

    /**
     * Forge created instance of the Mod.
     */
    @Mod.Instance(Constants.MOD_ID)
    public static        ModStats instance;

    /**
     * The network object.
     */
    private static SimpleNetworkWrapper network;


    /**
     * Getter for the minecolonies Logger.
     *
     * @return the logger.
     */
    public static Logger getLogger()
    {
        return logger;
    }

    /**
     * Event handler for forge pre init event.
     *
     * @param event the forge pre init event.
     */
    @Mod.EventHandler
    public void preInit(@NotNull final FMLPreInitializationEvent event)
    {
        CapabilityManager.INSTANCE.register(IModStatManager.class, new IModStatManager.Storage(), IModStatManager.Impl::new);
        MinecraftForge.EVENT_BUS.register(new ModEventHandler());

        @NotNull final Configuration configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }

    /**
     * Event handler for forge init event.
     *
     * @param event the forge init event.
     */
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event)
    {
        initializeNetwork();
    }

    /**
     * Method to init the network and register the messages.
     */
    private static synchronized void initializeNetwork()
    {
        int id = 0;
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MOD_NAME);

        //getNetwork().registerMessage(ServerUUIDMessage.class, ServerUUIDMessage.class, ++id, Side.CLIENT);

        //  ColonyView messages
        //getNetwork().registerMessage(ColonyViewMessage.class, ColonyViewMessage.class, ++id, Side.CLIENT);
    }

    /**
     * Get an instance of the network for message propagation.
     * @return the instance.
     */
    public static SimpleNetworkWrapper getNetwork()
    {
        return network;
    }

    @Mod.EventHandler
    public void serverLoad(final FMLServerStartingEvent event)
    {
        event.registerServerCommand(new ModStatsCommand());
    }
}