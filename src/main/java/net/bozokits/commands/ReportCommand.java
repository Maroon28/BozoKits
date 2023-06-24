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

public class ReportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length < 1) {
            sender.sendMessage("Use /report (msg)");
            CommandUtils.runAsConsole("discordbroadcast staff **" + sender.getName() + " ** typed /report without anything");
        }
        if (!(sender instanceof Player player)) {
            return true;
        }
        if (CommandUtils.hasCooldown(player, command.getName())) {
            long timeLeft = CommandUtils.getTimeLeft(player, command.getName());
            player.sendMessage(MessageUtils.getMessage("on-cooldown", Placeholder.component("cooldown", Component.text(timeLeft))));
            return true;
        }
        MessageUtils.sendMessageList(player, "messages.report");
        String report = String.join(" ", args);
        CommandUtils.runAsConsole("discordbroadcast reports <@&1056806576964509719> **" + player.getName() + "**: \"`" + report +  "`\"");
        CommandUtils.setCooldown(player, command.getName(), 15 * 60); // 15 min
        return true;
    }
}
