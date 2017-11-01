package com.idirin.catfactstask.event;

/**
 * Created by
 * idirin on 01/11/2017...
 */

public class ShareClickedEvent {

    private int clickedFactPosition;

    public ShareClickedEvent(int clickedFactPosition) {
        this.clickedFactPosition = clickedFactPosition;
    }

    public int getClickedFactPosition() {
        return clickedFactPosition;
    }
}
