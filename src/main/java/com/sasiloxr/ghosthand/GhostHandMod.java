package com.sasiloxr.ghosthand;

import com.sasiloxr.ghosthand.asm.GhostHandHook;
import com.sasiloxr.ghosthand.command.CommandGhostHand;
import com.sasiloxr.ghosthand.command.CommandTeamInvisible;
import com.sasiloxr.ghosthand.mouseoverhandler.MouseOverHandler;
import com.sasiloxr.ghosthand.render.TeamInvisible;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = GhostHandMod.MODID, name = GhostHandMod.NAME, version = GhostHandMod.VERSION, acceptedMinecraftVersions = "1.8.9")
public class GhostHandMod {
    public static final String MODID = "ghosthand";
    public static final String NAME = "GhostHand";
    public static final String VERSION = "3.0";
    public static File configFile;
    public static boolean enabled = true;
    public static boolean legit = true;
    public static Configuration config;
    public static TeamInvisible teamInvisible;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configFile = event.getSuggestedConfigurationFile();

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        ClientCommandHandler.instance.registerCommand(new CommandGhostHand());
        ClientCommandHandler.instance.registerCommand(new CommandTeamInvisible());
        config = new Configuration(configFile);
        teamInvisible = new TeamInvisible();
        new GhostHandHook();
        MouseOverHandler handler = new MouseOverHandler();
        loadConfig();

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    public static void saveConfig() {
        updateConfig(false);
        config.save();
    }

    public static void loadConfig() {
        config.load();
        updateConfig(true);
    }

    public static void updateConfig(boolean load) {
        Property property = config.get("GhostHand", "enable", false);
        Property property3 = config.get("GhostHand", "legit", true);
        Property property1 = config.get("TeamInvisible", "enable", false);
        Property property2 = config.get("TeamInvisible", "range", 3.0);
        if (load) {
            teamInvisible.enabled = property1.getBoolean();
            teamInvisible.range = (float) property2.getDouble();
            enabled = property.getBoolean();
            legit = property3.getBoolean();
        } else {
            property.setValue(enabled);
            property3.setValue(legit);
            property1.setValue(teamInvisible.enabled);
            property2.setValue(teamInvisible.range);
        }
    }
}
