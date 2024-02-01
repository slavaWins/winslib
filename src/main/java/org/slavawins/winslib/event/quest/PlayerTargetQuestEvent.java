package org.slavawins.winslib.event.quest;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *  Кастомный эвент для квестов. Создает событие для игрока в виде kill:skeleton + kill:any
 *  PlayerTargetQuestEvent customEvent = new PlayerTargetQuestEvent(player, "kill", "skeleton", 1);
 *  Bukkit.getPluginManager().callEvent(customEvent);
 */
public class PlayerTargetQuestEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final int amount;
    private final String eventId;
    private final String eventType;

    public PlayerTargetQuestEvent(Player player, String eventType, String eventId, int amount) {
        this.player = player;
        this.eventType = eventType;
        this.amount = amount;
        this.eventId = eventId;
    }

    public Player getPlayer() {
        return player;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventId() {
        return eventId;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


    public static HandlerList getHandlerList() {
        return handlers;

    }
}
