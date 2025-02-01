package com.sasiloxr.ghosthand.render;

import com.sasiloxr.ghosthand.GhostHandMod;
import com.sasiloxr.ghosthand.asm.TeamInvisibleHook;
import com.sasiloxr.ghosthand.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TeamInvisible {
    public boolean enabled = true;
    public float range = 3;
    public Minecraft mc = Minecraft.getMinecraft();

    public TeamInvisible() {
        MinecraftForge.EVENT_BUS.register(this);
        new TeamInvisibleHook();
    }


    @SubscribeEvent
    public void onPreRender(RenderPlayerEvent.Pre event) {
        if (!enabled) {
            return;
        }
        if (event.entity == mc.thePlayer) {
            return;
        }
        if (Utils.isTeamMate(event.entity) && event.entityPlayer.getDistanceToEntity(mc.thePlayer) <= GhostHandMod.teamInvisible.range) {
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.alphaFunc(516, 0.003921569F);
        }
    }

    @SubscribeEvent
    public void onPostRender(RenderPlayerEvent.Post event) {
        if (!enabled) {
            return;
        }
        if (event.entity == mc.thePlayer) {
            return;
        }
        if (Utils.isTeamMate(event.entity) && event.entityPlayer.getDistanceToEntity(mc.thePlayer) <= GhostHandMod.teamInvisible.range) {
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
        }
    }
}
