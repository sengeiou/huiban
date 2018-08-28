package com.bshuiban.baselibrary.view.activity;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        RecyclerView recyclerView=findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0,0,150,100);
            }
        });
        recyclerView.setAdapter(new TestAdapter());
    }
    class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewholder> {
        @NonNull
        @Override
        public TestViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(TestActivity.this);
            GradientDrawable drawable=new GradientDrawable();
            drawable.setStroke(1, Color.GRAY);
            drawable.setColor(Color.WHITE);
            textView.setBackground(drawable);
            textView.setGravity(Gravity.CENTER);//360, 180
            int width = getResources().getDimensionPixelSize(R.dimen.dp_120);
            int height = getResources().getDimensionPixelSize(R.dimen.dp_60);
            Log.e("TAG", "onCreateViewHolder: "+width+", "+height );
            textView.setLayoutParams(new ViewGroup.LayoutParams(width,height));
            return new TestViewholder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull TestViewholder holder, int position) {
            holder.textView.setText("第"+position+"个");
            holder.textView.post(()-> Log.e("TAG", "onBindViewHolder: "+holder.textView.getMeasuredWidth()+", "+holder.textView.getMeasuredHeight() ));
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class TestViewholder extends RecyclerView.ViewHolder{
            TextView textView;
            public TestViewholder(View itemView) {
                super(itemView);
                textView=(TextView)itemView;
            }
        }
    }
}
