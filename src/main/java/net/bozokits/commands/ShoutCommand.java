package net.bozokits.commands;

import net.bozokits.utils.CommandUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
        var legacyComponentSerializer = LegacyComponentSerializer.builder().character('&').hexColors().build();
        Component content = legacyComponentSerializer.deserialize(String.join(" ", args));
        CommandUtils.shout(content, key.replace("new", ""));
        return true;
    }

}
