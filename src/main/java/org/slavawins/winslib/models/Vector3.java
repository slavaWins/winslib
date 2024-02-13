package org.slavawins.winslib.models;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Vector3 {


    public double x = 0;
    public double y = 0;
    public double z = 0;
    public String world = "world";


    public static Vector3 From(Entity player) {
        Vector3 p = new Vector3();

        p.x = player.getLocation().getX();
        p.y = player.getLocation().getY();
        p.z = player.getLocation().getZ();
        p.world = player.getWorld().getName();

        return p;
    }

    public static Vector3 From(Location location) {
        Vector3 p = new Vector3();

        p.x = location.getX();
        p.y = location.getY();
        p.z = location.getZ();
        p.world = location.getWorld().getName();

        return p;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);

    }
}
