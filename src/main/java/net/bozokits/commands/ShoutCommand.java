package net.bozokits.commands;

import net.bozokits.utils.CommandUtils;
import net.bozokits.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShoutCommand implements CommandExecutor {
    private final String key;

    public ShoutCommand(String key) {
        this.key = key;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!commandSender.hasPermission("bozokits." + key)) {
            commandSender.sendMessage(Component.text("No permission to use /shout!", NamedTextColor.RED));
            return true;
        }
        if (args.length < 1) {
            commandSender.sendMessage(Component.text("Must provide content to broadcast!", NamedTextColor.RED));
            return true;
        }
        String content = String.join(" ", args);
        CommandUtils.shout(content, key);
        return true;
    }

}
