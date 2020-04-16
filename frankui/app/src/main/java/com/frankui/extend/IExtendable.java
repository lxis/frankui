package com.frankui.extend;

import android.view.View;

public interface IExtendable<T extends View> {
    ExtendHelper<T> getExtendHelper();
}
