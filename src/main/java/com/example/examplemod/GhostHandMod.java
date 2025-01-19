package com.example.examplemod;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import scala.collection.parallel.ParIterableLike;

@Mod(modid = GhostHandMod.MODID, name = GhostHandMod.NAME, version = GhostHandMod.VERSION, acceptedMinecraftVersions = "1.8.9")
public class GhostHandMod
{
    public static final String MODID = "ghosthand";
    public static final String NAME = "GhostHand";
    public static final String VERSION = "1.0";


    @EventHandler
    public void preInit(FMLPreInitializationEvent event){

    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){

    }
}
