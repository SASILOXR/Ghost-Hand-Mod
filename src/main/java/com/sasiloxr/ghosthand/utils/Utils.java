package com.sasiloxr.ghosthand.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;

public class Utils {
  public static Minecraft mc = Minecraft.getMinecraft();

  public static boolean isTeamMate(Entity teamMate) {
    if (teamMate instanceof EntityPlayer || teamMate instanceof EntityWither) {
      return (isSameColorWithPlayer(teamMate) && isPrefixSameWithPlayer(teamMate))
          || isTeamWither(teamMate);
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

  public static boolean isPrefixSameWithPlayer(Entity entity) {
    if (!(entity instanceof EntityPlayer)) {
      return false;
    }
    Pattern pattern = Pattern.compile("\\[(.*?)\\]");

    ArrayList<String> prefixes = new ArrayList<>();
    String playerName = mc.thePlayer.getDisplayName().getUnformattedText();
    int index = playerName.indexOf(mc.thePlayer.getDisplayNameString());
    String playerPrefix = playerName.substring(0, index);
    Matcher matcher = pattern.matcher(playerPrefix);
    while (matcher.find()) {
      String context = matcher.group(0);
      if (context.equals("[§2S§6]")) {
        continue;
      } else {
        prefixes.add(context);
      }
    }
    if (prefixes.isEmpty()) {
      return true;
    }

    String entityName = entity.getDisplayName().getUnformattedText();
    int index1 = entityName.indexOf(((EntityPlayer) entity).getDisplayNameString());
    String entityPrefix = entityName.substring(0, index1);
    Matcher matcher1 = pattern.matcher(entityPrefix);
    while (matcher1.find()) {
      String context = matcher1.group(0);
      for (String prefix : prefixes) {
        if (prefix.equals(context)) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean isTeamWither(Entity entity) {
    if (!(entity instanceof EntityWither)) {
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
    String entityColor = entityName.substring(0, 2);

    return playerColor.equals(entityColor);
  }
}
