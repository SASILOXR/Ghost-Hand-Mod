package com.sasiloxr.ghosthand.command;

import com.sasiloxr.ghosthand.GhostHandMod;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandGhostHand extends CommandBase {
    @Override
    public String getCommandName() {
        return "ghosthand";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/ghosthand";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sender.addChatMessage(new ChatComponentText("Usage: /ghosthand [true/false] {legitMode:only you attack by mouse}[true/false]"));
            sender.addChatMessage(new ChatComponentText("GhostHand status:" + GhostHandMod.enabled));
            sender.addChatMessage(new ChatComponentText("GhostHand legit:" + GhostHandMod.legit));
            return;
        }

        if (args.length != 2) {
            sender.addChatMessage(new ChatComponentText("Invalid Option"));
            return;
        }

        String enable = args[0];
        String isLegit = args[1];

        boolean enabled = Boolean.parseBoolean(enable);
        boolean legit = Boolean.parseBoolean(isLegit);
        if (enabled) {
            GhostHandMod.enabled = true;
            GhostHandMod.legit = legit;
            sender.addChatMessage(new ChatComponentText("GhostHand enable"));
        } else {
            GhostHandMod.enabled = false;
            sender.addChatMessage(new ChatComponentText("GhostHand disable"));
        }
        GhostHandMod.saveConfig();

    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
