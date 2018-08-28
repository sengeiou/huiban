package com.bshuiban.baselibrary.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.view.customer.TitleView;

/**
 * 文本答题
 */
public class TextActivity extends BaseActivity {
    EditText et;
    public static final int TEXT_HOMEWORK = 0x000009;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        TitleView titleView=findViewById(R.id.titleView);
        et=findViewById(R.id.et);
        titleView.setOnClickListener(new TitleView.OnClickListener() {
            @Override
            public void leftClick(View v) {
                finish();
            }

            @Override
            public void rightClick(View v) {
                String s = et.getText().toString();
                if(TextUtils.isEmpty(s)){
                    toast("请输入答案");
                    return;
                }
                Intent i = new Intent();
                i.putExtra("text", s);
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
