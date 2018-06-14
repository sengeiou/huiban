package com.bshuiban.baselibrary.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.ChangeUserContract;
import com.bshuiban.baselibrary.contract.LoginContract;
import com.bshuiban.baselibrary.model.LoginResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.model.UserMoreBean;
import com.bshuiban.baselibrary.present.ChangeUserPresent;
import com.bshuiban.baselibrary.utils.UserSharedPreferencesUtils;
import com.bshuiban.baselibrary.view.customer.TitleView;
import com.google.gson.Gson;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class ChangeUserActivity extends BaseActivity<ChangeUserPresent> implements ChangeUserContract.View,LoginContract.View{

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);
        tPresent = new ChangeUserPresent(this);
        TitleView titleView = findViewById(R.id.titleView);
        recyclerView = findViewById(R.id.recycleView);
        titleView.setOnClickListener(v -> finish());
        tPresent.loadMoreUserData();
    }

    @Override
    public void updateView(List<UserMoreBean.DataBean> data) {
        if(data!=null){
            Iterator<UserMoreBean.DataBean> iterator = data.iterator();
            while (iterator.hasNext()){
                UserMoreBean.DataBean next = iterator.next();
                if(next!=null&&next.getTypeId()==User.getInstance().getUserType()){
                    iterator.remove();
                }
            }
        }
        MyAdapter myAdapter = new MyAdapter(data);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void startDialog() {
        showLoadingDialog();
    }

    @Override
    public void dismissDialog() {
        dismissLoadingDialog();
    }

    @Override
    public void fail(String error) {
        toast(error);
    }

    @Override
    public void loginSuccessToNextActivity(Class<?> cls, LoginResultBean.Data loginData) {
        User.getInstance().setData(loginData);
        UserSharedPreferencesUtils.saveUserData(getApplicationContext(),new Gson().toJson(loginData));
        dismissDialog();
        startActivity(new Intent(getApplicationContext(), cls));
        finish();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<UserMoreBean.DataBean> list;

        public MyAdapter(List<UserMoreBean.DataBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_slide_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            UserMoreBean.DataBean dataBean = list.get(position);
            int typeId = dataBean.getTypeId();
            holder.textView.setText(getType(typeId));
            holder.textView.setOnClickListener(v->{
                String userId = dataBean.getUserId()+"";
                String password = dataBean.getPassword();
                try {
                    Class clazz=Class.forName("com.bshuiban.present.LoginPresent");
                    Constructor constructor = clazz.getConstructor(LoginContract.View.class);
                    Object obj = constructor.newInstance(ChangeUserActivity.this);
                    Method method = clazz.getMethod("login", String.class,String.class);
                    method.invoke(obj, userId,password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }

        @Override
        public int getItemCount() {
            return list==null?0:list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tv);
            }
        }
    }

    private String getType(int type) {
        switch (type) {//1学生，2或3老师，4家长
            case 1:
                return "学生";
            case 2:
            case 3:
                return "老师";
            default:
                return "家长";
        }
    }
}
