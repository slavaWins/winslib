package org.slavawins.winslib.event.quest;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *  Кастомный эвент для квестов. Вызывается когда надо заспавнить в радиусе мобов
 *  Bukkit.getPluginManager().callEvent(new CallCreateXMobEvent());
 */
public class CallCreateXMobEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Location location;
    private final String mobId;

    public CallCreateXMobEvent(String mobId, Location location) {
        this.mobId = mobId;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public String getMobId() {
        return mobId;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


    public static HandlerList getHandlerList() {
        return handlers;

    }
}
