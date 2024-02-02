package org.slavawins.winslib.helper;

/**
 * Клас с иконами из респака
 */
public enum ECustomGuiBack {

    AVATAR_STALKER("\uf101"),
    AVATAR_STALKER2("\uf102"),
    AVATAR_STALKER3("\uf103"),
    TOOLS_CHESTSPAWN_LOOTRARE("\uf831"),
    CASE_WIN_CASE_CENTRED("\uf832"),
    CASE_WIN_CASE_RESULT("\uf835");

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
