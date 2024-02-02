package org.slavawins.winslib.menucore;

public enum ENegativeSpaceGui {
    LEFT_8("❏"),
    WINDOW("\uD83D\uDF96");

    private final String filename;

    ENegativeSpaceGui(String filename) {
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
