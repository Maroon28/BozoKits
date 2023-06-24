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

public class FreeRankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player player)) {
            return true;
        }
        if (CommandUtils.hasCooldown(player, command.getName())) {
            long timeLeft = CommandUtils.getTimeLeft(player, command.getName());
            player.sendMessage(MessageUtils.getMessage("on-cooldown", Placeholder.component("cooldown", Component.text(String.valueOf(timeLeft)))));
            return true;
        }
        CommandUtils.runAsConsole("kit herorank1d " + player);
        MessageUtils.sendMessageList(player, "free-rank");
        CommandUtils.setCooldown(player, command.getName(), 24 * 60 * 60);
        return true;
    }
}
