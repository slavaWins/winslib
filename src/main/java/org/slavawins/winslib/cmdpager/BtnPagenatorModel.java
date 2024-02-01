package org.slavawins.winslib.cmdpager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;

public class BtnPagenatorModel {

    public String label = "EXAMPLE";
    public String cmd = "/say wath";

    public boolean isForOp = true;
    public ChatColor color = ChatColor.GREEN;
    public String description = "Описание кнопки";
    public ClickEvent.Action actionType = ClickEvent.Action.RUN_COMMAND;
}