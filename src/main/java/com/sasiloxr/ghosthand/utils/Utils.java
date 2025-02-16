package com.sasiloxr.ghosthand.utils;

import jdk.nashorn.internal.runtime.regexp.joni.constants.EncloseType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.apache.commons.lang3.mutable.MutableInt;
import scala.tools.nsc.doc.base.comment.Bold;

import java.awt.*;

public class Utils {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static boolean isTeamMate(Entity teamMate) {
        if (teamMate instanceof EntityPlayer) {
            return mc.thePlayer.isOnSameTeam((EntityLivingBase) teamMate) || isSameColorWithPlayer(teamMate);
        }
        return false;
    }

    public static boolean isSameColorWithPlayer(Entity entity) {
        if (!(entity instanceof EntityPlayer)) {
            return false;
        }
        String playerName = mc.thePlayer.getDisplayName().getUnformattedText();
        int index = playerName.indexOf(mc.thePlayer.getDisplayNameString());
        if (index < 1) {
            return false;
        }
        String playerColorstring = playerName.substring(0, index);
        int index2 = playerColorstring.lastIndexOf("§");
        if (index2 < 0) {
            return false;
        }
        String playerColor = playerColorstring.substring(index2, index2 + 2);
        String entityName = entity.getDisplayName().getUnformattedText();
        int index1 = entityName.indexOf(((EntityPlayer) entity).getDisplayNameString());
        if (index1 < 1) {
            return false;
        }
        String entityColorString = entityName.substring(0, index1);
        int index3 = entityColorString.lastIndexOf("§");
        if (index3 < 0) {
            return false;
        }
        String entityColor = entityColorString.substring(index3, index3 + 2);
        return playerColor.equals(entityColor);
    }
}
