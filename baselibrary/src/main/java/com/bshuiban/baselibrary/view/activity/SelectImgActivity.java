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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.utils.ViewUtils;
import com.bshuiban.baselibrary.view.customer.TitleView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


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
                if(TextUtils.isEmpty(select_path)){
                    toast("请选择图片");
                    return;
                }
                Intent i = new Intent();
                i.putExtra("path", select_path);
                setResult(RESULT_OK, i);
                finish();
            }
        });
        mRecycleView.setPadding(0,0,40,0);
        initRecycleView();
        if (adapter != null) {
            mRecycleView.setAdapter(adapter);
        }
    }

    @SuppressLint("CheckResult")
    private void add_img_path() {
        Flowable.create((FlowableOnSubscribe<String>) e -> {
            Log.e("TAG", "add_img_path");
            Cursor mCursor = getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            if (mCursor == null) {
                e.onComplete();
            }
            while (mCursor.moveToNext()) {
                //获取图片的路径
                String path = mCursor.getString(mCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                Log.e("TAG", "path=" + path);
                list_path.add(path);
            }
            e.onNext("");
            e.onComplete();
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.e("TAG", "accept");
                    if (list_path.size() == 0) {
                        return;
                    }
                    //刷新adapter
                    if (adapter == null) {
                        adapter = new MyAdapter();
                    }
                    if (mRecycleView != null) {
                        mRecycleView.setAdapter(adapter);
                    }
                });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
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
//                int x40 = (int) getResources().getDimension(R.dimen.x40);
//                int x38 = (int) getResources().getDimension(R.dimen.x38);
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
