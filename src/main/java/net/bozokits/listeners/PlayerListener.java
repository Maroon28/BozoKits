package net.bozokits.listeners;

import net.bozokits.BozoKitsUtils;
import net.bozokits.utils.CommandUtils;
import net.bozokits.utils.MessageUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.bozokits.utils.MessageUtils.getMessage;
import static net.kyori.adventure.text.minimessage.tag.resolver.Placeholder.component;

public class PlayerListener implements Listener {
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private int totalPlayerCount;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Component message;
        if (!event.getPlayer().hasPlayedBefore()) {
            totalPlayerCount = getTotalCount() + 1;
            message = getMessage("first-join-message", component("player", event.getPlayer().name()), component("unique-players", Component.text(totalPlayerCount)));
        } else {
            message = getMessage("join-message", component("player", event.getPlayer().name()));
        }
        MessageUtils.sendMessageList(event.getPlayer(), "messages.join-motd", component("player", event.getPlayer().name()));
        event.joinMessage(message);
        event.getPlayer().showTitle(Title.title(getMessage("join-title"), getMessage("join-subtitle")));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Component message = getMessage("quit-message", component("player", event.getPlayer().name()));
        event.quitMessage(message);
        cooldowns.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerKillPlayer(PlayerDeathEvent event) {
        Player victim = event.getPlayer();
        Player killer = victim.getKiller();
        if (killer == null)
            return;
        if (victim == killer)
            return;
        Bukkit.getScheduler().runTaskLater(BozoKitsUtils.getInstance(), () -> {
            CommandUtils.runCommands("on-kill", killer);
            MessageUtils.sendMessageList(killer, "messages.kill-player", component("victim", victim.name()));
        }, 10);
        cooldowns.remove(victim.getUniqueId());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Bukkit.getScheduler().runTaskLater(BozoKitsUtils.getInstance(), () -> {
            CommandUtils.runCommands("on-death", event.getPlayer());
        }, 10);
    }
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage();
        if (command.contains("/plugins") || command.contains("/pl")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(getMessage("plugins"));
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack clickedItem = event.getItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        ItemMeta itemMeta = clickedItem.getItemMeta();
        if (itemMeta == null || !itemMeta.hasDisplayName()) {
            return;
        }

        String itemName = itemMeta.getDisplayName();

        // Levitation Crystal with cooldown
        if (itemName.contains("Levitation Crystal")) {
            handleLevitationCrystal(player, clickedItem);
            event.setCancelled(true);
        }
        // Speed & Jump
        else if (itemName.contains("Speed & Jump")) {
            handleSpeedAndJump(player, clickedItem);
            event.setCancelled(true);
        }
        // Extra Hearts
        else if (itemName.contains("Extra Hearts")) {
            handleExtraHearts(player, clickedItem);
            event.setCancelled(true);
        }
    }

    private void handleLevitationCrystal(Player player, ItemStack item) {
        if (hasCooldown(player)) {
            player.sendMessage("Item is on cooldown. Please wait.");
            return;
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 4 * 20, 2));
        setCooldown(player);
        item.setAmount(item.getAmount() - 1);
        player.getInventory().setItemInMainHand(item.getAmount() > 0 ? item : null);
    }

    private void handleSpeedAndJump(Player player, ItemStack item) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5 * 20, 4));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 5 * 20, 2));
        item.setAmount(item.getAmount() - 1);
        player.getInventory().setItemInMainHand(item.getAmount() > 0 ? item : null);
    }

    private void handleExtraHearts(Player player, ItemStack item) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 0));
        item.setAmount(item.getAmount() - 1);
        player.getInventory().setItemInMainHand(item.getAmount() > 0 ? item : null);
    }

    private boolean hasCooldown(Player player) {
        // 10 seconds in milliseconds
        long cooldownDuration = 10 * 1000;
        return cooldowns.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldowns.get(player) < cooldownDuration;
    }

    private void setCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }
    private int getTotalCount() {
        if (totalPlayerCount == 0) {
            return Bukkit.getOfflinePlayers().length;
        }
        return totalPlayerCount;
    }
}
