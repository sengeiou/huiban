package com.bshuiban.baselibrary.view.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.view.customer.TitleView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectImgActivity extends BaseActivity {
    private RecyclerView mRecycleView;
    private List<String> list_path = new ArrayList<>();
    private String select_path;
    private MyAdapter adapter;
    private boolean effic = true;
    public static final int SELECT_PICTURE = 0x000008;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_img);
        if (list_path.size() <= 0) {
            add_img_path();
        }
        mRecycleView = findViewById(R.id.recycleView);
        TitleView titleView = findViewById(R.id.titleView);
        titleView.setOnClickListener(new TitleView.OnClickListener() {
            @Override
            public void leftClick(View v) {
                finish();
            }

            @Override
            public void rightClick(View v) {
                if (TextUtils.isEmpty(select_path)) {
                    toast("请选择图片");
                    return;
                }
                Intent i = new Intent();
                i.putExtra("path", select_path);
                setResult(RESULT_OK, i);
                finish();
            }
        });
        mRecycleView.setPadding(0, 0, 40, 0);
        initRecycleView();
        if (adapter != null) {
            mRecycleView.setAdapter(adapter);
        }
    }

    @SuppressLint("CheckResult")
    private void add_img_path() {
        showLoadingDialog();
        new Thread() {
            @Override
            public void run() {
                Cursor mCursor = getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
                if (mCursor != null) {
                    while (mCursor.moveToNext()) {
                        //获取图片的路径
                        String path = mCursor.getString(mCursor
                                .getColumnIndex(MediaStore.Images.Media.DATA));
                        //Log.e("TAG", "path=" + path);
                        list_path.add(path);
                    }
                    runOnUiThread(() -> {
                        dismissLoadingDialog();
                        if (HomeworkBean.isEffictive(list_path)) {
                            //刷新adapter
                            if (adapter == null) {
                                adapter = new MyAdapter();
                            }
                            if (mRecycleView != null) {
                                mRecycleView.setAdapter(adapter);
                            }
                        }
                    });
                }
            }
        }.start();

    }

    private void initRecycleView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(gridLayoutManager);
        mRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                //super.getItemOffsets(outRect, itemPosition, parent);
                //int x40 = (int) getResources().getDimension(R.dimen.dp_13);
                //int x38 = (int) getResources().getDimension(R.dimen.dp_12);
                outRect.set(40, 38, 0, 0);
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.image_select_item, null);
            return new MyHolder(inflate);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            final String path = list_path.get(position);
            Glide.with(SelectImgActivity.this).load(path).into(holder.iv);
            effic = false;
            if (path.equals(select_path)) {
                holder.cb.setChecked(true);
            } else {
                holder.cb.setChecked(false);
            }
            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked && effic) {
                        select_path = path;
                        notifyDataSetChanged();
                    }
                }
            });
            effic = true;
        }

        @Override
        public int getItemCount() {
            return list_path.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            private CheckBox cb;
            private ImageView iv;

            public MyHolder(View itemView) {
                super(itemView);
                cb = (CheckBox) itemView.findViewById(R.id.cb);
                iv = (ImageView) itemView.findViewById(R.id.iv);
            }
        }
    }
}
