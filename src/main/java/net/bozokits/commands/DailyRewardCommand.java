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

import static net.bozokits.utils.MessageUtils.*;

public class DailyRewardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) {
            return true;
        }
        if (CommandUtils.hasCooldown(player, command.getName())) {
            long timeLeft = CommandUtils.getTimeLeft(player, command.getName());
            player.sendMessage(getMessage("on-cooldown", Placeholder.component("cooldown", Component.text(timeLeft))));
            return true;
        }
        String content = getMiniMessage().serialize(getMessage("daily-reward-shout", Placeholder.component("player", player.name())));
        player.sendMessage(getMessage("daily-reward"));
        CommandUtils.runAsConsole("crazycrate give physical Daily 1 " + player.getName());
        CommandUtils.shout(content, "shoutsmallol");
        CommandUtils.setCooldown(player, command.getName(), 60 * 60 * 24);
        return false;
    }
}
