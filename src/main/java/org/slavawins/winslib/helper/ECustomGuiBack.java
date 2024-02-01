package org.slavawins.winslib.helper;

/**
 * Клас с иконами из респака
 */
public enum ECustomGuiBack {

    AVATAR_STALKER(""),
    AVATAR_STALKER2(""),
    AVATAR_STALKER3(""),
    TOOLS_CHESTSPAWN_LOOTRARE("");

    private final String filename;

    ECustomGuiBack(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return getFilename();
    }

    }
