package com.codificador.contactapp;

import java.util.TimerTask;

public class AutoAdjustSeekBarTask extends TimerTask {
    ListItem mListItem;

    public AutoAdjustSeekBarTask(ListItem listItem) {
        this.mListItem = listItem;
    }

    public void run() {
        this.mListItem.autoAdjust();
    }
}
