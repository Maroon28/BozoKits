package net.bozokits.commands;

import net.bozokits.utils.CommandUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShoutCommand implements CommandExecutor {
    private final String key;
    private static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.builder().character('&').hexColors().build();

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
        String text = lowercaseAllCodes(String.join(" ", args));
        CommandUtils.shout(splitComponent(text), key.replace("new", ""));
        return true;
    }

    private List<Component> splitComponent(String text) {
        String[] parts = text.split("\\\\n");
        List<Component> components = new ArrayList<>();
        for (String part: parts) {
            if (Objects.equals(part, parts[0])) {
                components.add(LEGACY_COMPONENT_SERIALIZER.deserialize(part));
            } else {
                components.add(getSpaces().append(LEGACY_COMPONENT_SERIALIZER.deserialize(part)));
            }
        }
        return components;
    }

    @NotNull
    private static TextComponent getSpaces() {
        @NotNull TextComponent builder = Component.space();
        for (int i = 0; i < 11; i++) {
            builder = builder.append(Component.space());
        }
        return builder;
    }

    private String lowercaseAllCodes(String input) {
        Matcher matcher = Pattern.compile("&([A-Z])").matcher(input);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "&" + Character.toLowerCase(matcher.group(1).charAt(0)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }


}
