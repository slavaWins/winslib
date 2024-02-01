package org.slavawins.winslib.helper;


import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;


public class MobMetadataHelper {


    public static void setCustomClassMetadata(Entity entity, String CUSTOM_METADATA_KEY, String val,  Plugin plugin ) {

        if (entity instanceof Metadatable) {
            Metadatable metadatable = (Metadatable) entity;
            metadatable.setMetadata(CUSTOM_METADATA_KEY, new FixedMetadataValue(plugin, val));

        }
    }

    public static String getCustomClassMetadata(Entity entity, String CUSTOM_METADATA_KEY,   Plugin plugin ) {


        if (entity instanceof Metadatable) {
            Metadatable metadatable = (Metadatable) entity;
            for (MetadataValue metadata : metadatable.getMetadata(CUSTOM_METADATA_KEY)) {
                if (metadata.getOwningPlugin().equals(plugin)) {
                    return metadata.asString();
                }
            }
        }
        return null;
    }
}
