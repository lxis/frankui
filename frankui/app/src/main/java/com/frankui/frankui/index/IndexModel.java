package com.frankui.frankui.index;

import com.frankui.frankui.gesture.Gesture1Activity;
import com.frankui.utils.SafeObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IndexModel {

    private List<IndexData> mIndex;
    private HashMap<String, List<IndexData>> mMap = new HashMap<>();

    public IndexModel() {
        init();
    }

    private void init() {
        mIndex = new ArrayList<>();
        mIndex.add(new IndexData("扩展控件", true));
        mIndex.add(new IndexData("布局", true));
        mIndex.add(new IndexData("动画", true));
        mIndex.add(new IndexData("手势冲突", true));

        List<IndexData> extend = new ArrayList<>();
        extend.add(new IndexData("扩展控件1"));
        extend.add(new IndexData("扩展控件2"));
        extend.add(new IndexData("扩展控件3"));
        extend.add(new IndexData("扩展控件4"));
        extend.add(new IndexData("扩展控件5"));
        List<IndexData> layout = new ArrayList<>();
        layout.add(new IndexData("布局1"));
        layout.add(new IndexData("布局2"));
        layout.add(new IndexData("布局3"));
        layout.add(new IndexData("布局4"));
        layout.add(new IndexData("布局5"));
        List<IndexData> animation = new ArrayList<>();
        animation.add(new IndexData("动画1"));
        animation.add(new IndexData("动画2"));
        animation.add(new IndexData("动画3"));
        animation.add(new IndexData("动画4"));
        animation.add(new IndexData("动画5"));
        List<IndexData> gesture = new ArrayList<>();
        gesture.add(new IndexData("手势冲突1", Gesture1Activity.class));
        gesture.add(new IndexData("手势冲突2"));
        gesture.add(new IndexData("手势冲突3"));
        gesture.add(new IndexData("手势冲突4"));
        gesture.add(new IndexData("手势冲突5"));

        mMap.put("扩展控件", extend);
        mMap.put("布局", layout);
        mMap.put("动画", animation);
        mMap.put("手势冲突", gesture);
    }

    public List<IndexData> getList(IndexData data) {
        if (data == null) {
            return mIndex;
        }
        if (SafeObject.equals(data.getText(), "返回")) {
            return mIndex;
        } else {
            return mMap.get(data.getText());
        }
    }
}
