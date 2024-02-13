package org.slavawins.winslib.event.quest;

import org.bukkit.entity.Skeleton;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *  Когда создался новый моб
 *  XmobCreateEvent customEvent = new XmobCreateEvent(player, "kill", "skeleton", 1);
 *  Bukkit.getPluginManager().callEvent(customEvent);
 */
public class XmobCreateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Skeleton skeleton;
    private final String mobId;

    public XmobCreateEvent(String mobId, Skeleton skeleton) {
        this.mobId = mobId;
        this.skeleton = skeleton;
    }

    public Skeleton getSkeleton() {
        return skeleton;
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
