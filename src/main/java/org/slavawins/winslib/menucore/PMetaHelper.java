package org.slavawins.winslib.menucore;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.slavawins.winslib.Winslib;

import java.util.List;

public class PMetaHelper {

    public static void setPlayerMetadata(Player player, String key, String value) {
        player.setMetadata(key, new FixedMetadataValue(Winslib.getInstanse(), value));
    }

    public static void remove(Player player, String key) {
        player.removeMetadata(key, Winslib.getInstanse());
    }
    public static String get(Player player, String key) {
        List<MetadataValue> metadata = player.getMetadata(key);
        for (MetadataValue value : metadata) {
            if (value.getOwningPlugin().equals(Winslib.getInstanse())) {
                return value.asString();
            }
        }
        return null;
    }

}
