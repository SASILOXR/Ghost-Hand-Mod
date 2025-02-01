package com.sasiloxr.ghosthand.command;

import com.sasiloxr.ghosthand.GhostHandMod;
import com.sasiloxr.ghosthand.render.TeamInvisible;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import org.apache.commons.lang3.math.NumberUtils;

public class CommandTeamInvisible extends CommandBase {
    @Override
    public String getCommandName() {
        return "teaminvisible";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/teaminvisible";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText("Usage: /teaminvisible [true/false] [range]"));
            return;
        }
        if (args.length != 2) {
            sender.addChatMessage(new ChatComponentText("Invalid Option"));
            return;
        }

        boolean enable = Boolean.parseBoolean(args[0]);
        float range = NumberUtils.toFloat(args[1]);
        if (enable) {
            GhostHandMod.teamInvisible.enabled = true;
            sender.addChatMessage(new ChatComponentText("TeamInvisible enable"));
        } else {
            GhostHandMod.teamInvisible.enabled = false;
            sender.addChatMessage(new ChatComponentText("TeamInvisible disable"));
        }
        GhostHandMod.teamInvisible.range = range;
        GhostHandMod.saveConfig();
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
