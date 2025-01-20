package com.sasiloxr.ghosthand;

import com.sasiloxr.ghosthand.mouseoverhandler.MouseOverHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.nio.file.AtomicMoveNotSupportedException;

@Mod(modid = GhostHandMod.MODID, name = GhostHandMod.NAME, version = GhostHandMod.VERSION, acceptedMinecraftVersions = "1.8.9")
public class GhostHandMod
{
    public static final String MODID = "ghosthand";
    public static final String NAME = "GhostHand";
    public static final String VERSION = "1.0";
    public static boolean enabled  = true;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event){

    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MouseOverHandler handler = new MouseOverHandler();

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){

    }
}
