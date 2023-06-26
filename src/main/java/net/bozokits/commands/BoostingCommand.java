package net.bozokits.commands;

import net.bozokits.utils.CommandUtils;
import net.bozokits.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BoostingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return true;
        }
        if (!player.hasPermission("bozokits.boosting")) {
            player.sendMessage(MessageUtils.getMessage("no-permission"));
            return true;
        }
        if (CommandUtils.hasCooldown(player, command.getName())) {
            long timeLeft = CommandUtils.getTimeLeft(player, command.getName());
            player.sendMessage(MessageUtils.getMessage("on-cooldown", Placeholder.component("cooldown", Component.text(CommandUtils.formatTime(timeLeft)))));
            return true;
        }
        CommandUtils.runAsConsole("cc give physical Ultra 1 " + player.getName());
        CommandUtils.runAsConsole("discordbroadcast keys " + MessageUtils.getMessageRaw("boosting").replace("<player>", player.getName()));
        return false;
    }
}
