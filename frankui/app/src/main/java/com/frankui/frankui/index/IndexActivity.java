package com.frankui.frankui.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.frankui.frankui.R;

import java.util.List;

public class IndexActivity extends AppCompatActivity {

    private final IndexModel mModel = new IndexModel();

    private IndexAdapter mAdapter = new IndexAdapter(this, new IListListener() {
        @Override
        public void click(IndexData data) {
            List<IndexData> list = mModel.getList(data);
            if (list != null) {
                mAdapter.setDataList(list);
                mAdapter.notifyDataSetChanged();
            } else {
                Class<?> clz = mModel.getClz(data);
                if (clz != null) {
                    Intent intent = new Intent(IndexActivity.this, clz);
                    startActivity(intent);
                } else {
                    Toast.makeText(IndexActivity.this, "没有对应页面", Toast.LENGTH_LONG).show();
                }
            }

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        try {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_activity);
//
        ListView listView = findViewById(R.id.list);
        List<IndexData> data = mModel.getList(null);
        mAdapter.setDataList(data);
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
    }
}
