package com.frankui.extendtemp;

import android.view.View;

public interface IExtendable<T extends View> {
    ExtendHelper<T> getExtendHelper();
}
