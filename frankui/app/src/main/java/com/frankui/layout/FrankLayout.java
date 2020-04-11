package com.frankui.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.frankui.frankui.R;
import com.frankui.utils.JavaTypesHelper;
import com.frankui.utils.SafeObject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

public class FrankLayout extends ViewGroup {
    private static final int DEFAULT_CHILD_GRAVITY = Gravity.TOP | Gravity.START;

    private FrankLayoutResModel mResModel;

    public FrankLayout(Context context) {
        super(context);
    }

    public FrankLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadLayout(context, attrs);
    }

    public FrankLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadLayout(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren(l, t, r, b, false /* no force left gravity */);
    }

    void layoutChildren(int left, int top, int right, int bottom,
                        boolean forceLeftGravity) {
        final int count = getChildCount();

        final int parentLeft = 0;
        final int parentRight = right - left;

        final int parentTop = 0;
        final int parentBottom = bottom - top;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();

                int childLeft;
                int childTop;

                int gravity = DEFAULT_CHILD_GRAVITY;


                final int layoutDirection = getLayoutDirection();
                final int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
                final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

                switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                    case Gravity.CENTER_HORIZONTAL:
                        childLeft = parentLeft + (parentRight - parentLeft - width) / 2;
                        break;
                    case Gravity.RIGHT:
                        if (!forceLeftGravity) {
                            childLeft = parentRight - width;
                            break;
                        }
                    case Gravity.LEFT:
                    default:
                        childLeft = parentLeft;
                }

                switch (verticalGravity) {
                    case Gravity.TOP:
                        childTop = parentTop;
                        break;
                    case Gravity.CENTER_VERTICAL:
                        childTop = parentTop + (parentBottom - parentTop - height) / 2;
                        break;
                    case Gravity.BOTTOM:
                        childTop = parentBottom - height;
                        break;
                    default:
                        childTop = parentTop;
                }

                child.layout(childLeft, childTop, childLeft + width, childTop + height);
            }
        }
    }

    private final ArrayList<View> mMatchParentChildren = new ArrayList<View>(1);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        final boolean measureMatchParentChildren =
                MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY ||
                        MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
        mMatchParentChildren.clear();

        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            int index = JavaTypesHelper.toInt(SafeObject.toString(child.getTag()), -1);
            if (child.getVisibility() != GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                maxWidth = Math.max(maxWidth,
                        child.getMeasuredWidth());
                maxHeight = Math.max(maxHeight,
                        child.getMeasuredHeight());
                childState = combineMeasuredStates(childState, child.getMeasuredState());
                if (measureMatchParentChildren) {
                    if (lp.width == LayoutParams.MATCH_PARENT ||
                            lp.height == LayoutParams.MATCH_PARENT) {
                        mMatchParentChildren.add(child);
                    }
                }
            }
        }

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));

        count = mMatchParentChildren.size();
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                final View child = mMatchParentChildren.get(i);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                final int childWidthMeasureSpec;
                if (lp.width == LayoutParams.MATCH_PARENT) {
                    final int width = Math.max(0, getMeasuredWidth()
                    );
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                            width, MeasureSpec.EXACTLY);
                } else {
                    childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0
                            ,
                            lp.width);
                }

                final int childHeightMeasureSpec;
                if (lp.height == LayoutParams.MATCH_PARENT) {
                    final int height = Math.max(0, getMeasuredHeight()
                    );
                    childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                            height, MeasureSpec.EXACTLY);
                } else {
                    childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                            0,
                            lp.height);
                }

                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }

    }

    private void loadLayout(Context context, AttributeSet attrs) {
        XmlPullParser parser = null;
        try {
            parser = loadLayoutParser(context, attrs);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        try {
            mResModel = parseResModel(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2. 重写onMeasure和onLayout方法
    }

    private FrankLayoutResModel parseResModel(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser == null) {
            return null;
        }
        FrankLayoutResModel model = new FrankLayoutResModel();

        FrankLayoutResModel currentItem = model;
        XmlPullParser xmlParser = parser;
        int event = xmlParser.getEventType();   //先获取当前解析器光标在哪

        while (event != XmlPullParser.END_DOCUMENT) {    //如果还没到文档的结束标志，那么就继续往下处理
            String name = xmlParser.getName();
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    //一般都是获取标签的属性值，所以在这里数据你需要的数据
                    //xmlParser.getName();
                    FrankLayoutResModel item = new FrankLayoutResModel();
                    item.parent = currentItem;
                    if (currentItem.child == null) {
                        currentItem.child = new ArrayList<>();
                    }
                    currentItem.child.add(item);
                    currentItem = item;

                    if (SafeObject.equals(name, "Horizen")) {
                        currentItem.vertical = false;
                        currentItem.cell = false;
                    } else if (SafeObject.equals(name, "Vertical")) {
                        currentItem.vertical = true;
                        currentItem.cell = false;
                    } else if (SafeObject.equals(name, "Cell")) {
                        currentItem.vertical = false;
                        currentItem.cell = true;
                    }
                    String order = xmlParser.getAttributeValue(null, "order");
                    currentItem.reverse = SafeObject.equals(order, "start");

                    String indexText = xmlParser.getAttributeValue(null, "index");
                    currentItem.index = JavaTypesHelper.toInt(indexText, -1);
                    break;
                case XmlPullParser.TEXT:
                    break;
                case XmlPullParser.END_TAG:
                    currentItem = currentItem.parent;
                    break;
                default:
                    break;
            }
            event = xmlParser.next();   //将当前解析器光标往下一步移
        }

        return model;
    }

    private XmlPullParser loadLayoutParser(Context context, AttributeSet attrs) throws XmlPullParserException {
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.FrankLayout);
        String fileName = typeArray.getString(R.styleable.FrankLayout_layout_name);
        String textTemp = getFromAssets(fileName + ".xml");
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(new StringReader(textTemp));

        typeArray.recycle();
        return parser;
    }

    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getContext().getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
