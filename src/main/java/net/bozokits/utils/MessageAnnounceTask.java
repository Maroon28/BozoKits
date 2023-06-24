package net.bozokits.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageAnnounceTask implements Runnable {
    private int index = 1;
    private final List<String> ANNOUNCEMENTS = MessageUtils.getMessageList("announcements");
    @Override
    public void run() {
        for (Player p: Bukkit.getOnlinePlayers()) {
            MessageUtils.sendMessageList(p, getListAnnouncementKey());
        }
        if (index >= ANNOUNCEMENTS.size()) {
            index = 1;
            return;
        }
        index++;
    }

    private String getListAnnouncementKey() {
        return "announcements." + index;
    }
}
