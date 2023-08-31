package net.bozokits.commands;

import net.bozokits.BozoKitsUtils;
import net.bozokits.utils.CommandUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import javax.sound.midi.MidiFileFormat;

public class DiscordBroadcastCommand implements CommandExecutor {
    private final String messageKey;

    public DiscordBroadcastCommand(String messageKey) {
        this.messageKey = messageKey;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        String message = BozoKitsUtils.getInstance().getConfig().getString(messageKey);
        message = message.replace("<player>", commandSender.getName());
        if (args.length > 2) {
            message = message.replace("<args1>", args[0]);
            message = message.replace("<args2>", args[1]);
        } else if (args.length > 1) {
            message = message.replace("<args1>", args[1]);
        }
        broadcast(message);
        return true;
    }

    private void broadcast(String text) {
        CommandUtils.runAsConsole("discordbroadcast keys " + text);
    }
}
