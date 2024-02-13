package org.slavawins.winslib.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Событие создания шума игроком. Выстрелы, бег, и прочее. Чтоб зомби спавнить ему
 */
public class PlayerNoiceEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final int level;

    public PlayerNoiceEvent(Player player, int level) {
        this.player = player;
        this.level = level;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


    public static HandlerList getHandlerList() {
        return handlers;

    }

    /**
     * Уровень шума до 3х
     *
     * @return
     */
    public int getLevel() {
        return level;
    }
}
