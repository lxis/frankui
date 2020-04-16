package com.frankui.frankui.index;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.frankui.frankui.R;
import com.frankui.utils.ListUtils;
import com.frankui.utils.SafeObject;
import com.frankui.utils.TypeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class IndexAdapter extends BaseAdapter {

    private List<IndexData> mDataList = new ArrayList<>();

    private Context mContext;

    private IListListener mListener;

    public IndexAdapter(Context context, IListListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public int getCount() {
        return ListUtils.getCount(mDataList);
    }

    @Override
    public Object getItem(int position) {
        return ListUtils.getItem(mDataList, position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final IndexViewHolder viewHolder;
        View view;
        IndexData data = ListUtils.getItem(mDataList, position);
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.index_itemview, null);
            viewHolder = new IndexViewHolder();
            viewHolder.text = view.findViewById(R.id.text);
            view.setTag(viewHolder);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.click(viewHolder.data);
                }
            });
        } else {
            viewHolder = TypeUtils.safeCast(convertView.getTag(), IndexViewHolder.class);
            view = convertView;
        }
        viewHolder.data = data;

        if (data != null) {
            viewHolder.text.setText(data.getText());
        }

        return view;
    }

    public void setDataList(List<IndexData> data) {
        mDataList = data;
    }
}
