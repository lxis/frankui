package com.frankui.custom;

public enum PageScrollStatus {
    BOTTOM(0),
    TOP(1),
    NULL(2);

    private int scrollStatus;

    private PageScrollStatus(int scrollStatus) {
        this.scrollStatus = scrollStatus;
    }

    public int getScrollStatus() {
        return this.scrollStatus;
    }
}
