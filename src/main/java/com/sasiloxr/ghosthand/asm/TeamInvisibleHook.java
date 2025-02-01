package com.sasiloxr.ghosthand.asm;

import com.sasiloxr.ghosthand.GhostHandMod;
import com.sasiloxr.ghosthand.render.TeamInvisible;
import com.sasiloxr.ghosthand.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class TeamInvisibleHook {
    public static TeamInvisibleHook INSTANCE;

    public TeamInvisibleHook() {
        INSTANCE = this;
    }

    public boolean shouldRenderLayer(EntityLivingBase entityLivingBase) {
        Minecraft mc = Minecraft.getMinecraft();
        if (!GhostHandMod.teamInvisible.enabled) {
            return true;
        }
        if (entityLivingBase instanceof EntityPlayer) {
            if (entityLivingBase == mc.thePlayer) {
                return true;
            }
            if (entityLivingBase.getDistanceToEntity(mc.thePlayer) <= GhostHandMod.teamInvisible.range && Utils.isTeamMate(entityLivingBase)) {
                return false;
            }
        }
        return true;
    }
}
