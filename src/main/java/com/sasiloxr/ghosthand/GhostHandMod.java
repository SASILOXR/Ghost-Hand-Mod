package com.sasiloxr.ghosthand;

import com.sasiloxr.ghosthand.command.CommandGhostHand;
import com.sasiloxr.ghosthand.mouseoverhandler.MouseOverHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.nio.file.AtomicMoveNotSupportedException;

@Mod(modid = GhostHandMod.MODID, name = GhostHandMod.NAME, version = GhostHandMod.VERSION, acceptedMinecraftVersions = "1.8.9")
public class GhostHandMod
{
    public static final String MODID = "ghosthand";
    public static final String NAME = "GhostHand";
    public static final String VERSION = "1.0";
    public static File configFile;
    public static boolean enabled  = true;
    public static Configuration config;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        configFile = event.getSuggestedConfigurationFile();

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

        ClientCommandHandler.instance.registerCommand(new CommandGhostHand());
        config = new Configuration(configFile);
        loadConfig();
        MouseOverHandler handler = new MouseOverHandler();

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){

    }
    public static void saveConfig(){
        updateConfig(false);
        config.save();
    }

    public static void loadConfig(){
        config.load();
        updateConfig(true);
    }

    public static void updateConfig(boolean load){
        Property property = config.get("GhostHand", "enable", false);
        if (load){
            enabled = property.getBoolean();
        }else{
            property.setValue(enabled);
        }
    }
}
