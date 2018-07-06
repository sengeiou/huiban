package com.bshuiban.baselibrary.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.contract.ChangeUserContract;
import com.bshuiban.baselibrary.contract.LoginContract;
import com.bshuiban.baselibrary.model.HomeworkBean;
import com.bshuiban.baselibrary.model.LoginResultBean;
import com.bshuiban.baselibrary.model.User;
import com.bshuiban.baselibrary.model.UserMoreBean;
import com.bshuiban.baselibrary.present.ChangeUserPresent;
import com.bshuiban.baselibrary.utils.DensityUtil;
import com.bshuiban.baselibrary.utils.SpaceItemDecoration;
import com.bshuiban.baselibrary.utils.SpaceItemDecorationUtils;
import com.bshuiban.baselibrary.utils.TextUtils;
import com.bshuiban.baselibrary.utils.UserSharedPreferencesUtils;
import com.bshuiban.baselibrary.view.customer.TitleView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChangeUserActivity extends BaseActivity<ChangeUserPresent> implements ChangeUserContract.View, LoginContract.View {

    private RecyclerView recyclerView;
    private UserMoreBean.DataBean dataBean;
    private TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);
        tPresent = new ChangeUserPresent(this);
        titleView = findViewById(R.id.titleView);
        recyclerView = findViewById(R.id.recycleView);
        titleView.setOnClickListener(new TitleView.OnClickListener() {
            @Override
            public void leftClick(View v) {
                finish();
            }

            @Override
            public void rightClick(View v) {
                int typeId = dataBean.getTypeId();
                if (typeId == 100) {//更改孩子的userId
                    LoginResultBean.Data userData = User.getInstance().getUserData();
                    userData.setUserId(dataBean.getUserId());
                    userData.setClassId1(dataBean.getClassId());
                    userData.setSchoolId(dataBean.getSchoolId());
                    userData.setGradeId(dataBean.getGradeId());
                    finish();
                    return;
                }
                User.getInstance().changeUser(dataBean);
                String action = getNextActivityAction(typeId);
                if (null != action) {
                    //startActivity(new Intent(getApplicationContext(), nextActivity).putExtra("change",true));
                    //String action="com.bshuiban.student.ParentsHomeActivity";
                    Intent intent = new Intent(action);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        ChangeUserActivity.this.finish();
                    }
                } else {
                    toast("账号类型错误");
                }
            }
        });
        recyclerView.addItemDecoration(new SpaceItemDecoration(this, LinearLayoutManager.VERTICAL, (int) DensityUtil.dip2px(this, 1), ContextCompat.getColor(this, R.color.line_bord)));
        tPresent.loadMoreUserData();
    }

    @Override
    public void updateView(List<UserMoreBean.DataBean> data) {
        if (data != null) {
            Iterator<UserMoreBean.DataBean> iterator = data.iterator();
            while (iterator.hasNext()) {
                UserMoreBean.DataBean next = iterator.next();
                if (next != null && next.getTypeId() == User.getInstance().getUserType()) {
                    iterator.remove();
                }
            }
        }
        setRecycleData(data);
    }

    private void setRecycleData(List<UserMoreBean.DataBean> data) {
        if (User.getInstance().isParents()) {
            List<LoginResultBean.Data.StuArrBean> stuArr = User.getInstance().getUserData().getStuArr();
            if (null == data) {
                data = new ArrayList<>();
            }
            for (int i = 0; stuArr != null && i < stuArr.size(); i++) {
                LoginResultBean.Data.StuArrBean stuArrBean = stuArr.get(i);
                UserMoreBean.DataBean dataBean = new UserMoreBean.DataBean();
                dataBean.setChildName(stuArrBean.getStuName());
                dataBean.setUserId(stuArrBean.getStuId());
                dataBean.setTypeId(100);
                dataBean.setClassId(stuArrBean.getClassId() + "");
                dataBean.setGradeId(stuArrBean.getGradeId() + "");
                dataBean.setSchoolId(stuArrBean.getSchoolId() + "");
                data.add(dataBean);
            }
            for (int d = 0; d < data.size(); d++) {
                if (data.get(d).getUserId().equals(User.getInstance().getUserId())) {
                    this.lastSelectPosition=d;
                }
            }
        }
        if (HomeworkBean.isEffictive(data)) {
            MyAdapter myAdapter = new MyAdapter(data);
            recyclerView.setAdapter(myAdapter);
            //titleView.setRight_text("确认", Color.WHITE, (int) getResources().getDimension(R.dimen.dp_13));
        } else {
            titleView.setRight_text(null, -1, 0);
        }
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
        setRecycleData(null);
    }

    @Override
    public void loginSuccessToNextActivity(Class<?> cls, LoginResultBean.Data loginData) {
        User.getInstance().setData(loginData);
        UserSharedPreferencesUtils.saveUserData(getApplicationContext(), new Gson().toJson(loginData));
        dismissDialog();
        startActivity(new Intent(getApplicationContext(), cls));
        finish();
    }

    private int lastSelectPosition = -1;

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<UserMoreBean.DataBean> list;

        public MyAdapter(List<UserMoreBean.DataBean> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_change_user_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            UserMoreBean.DataBean dataBean1 = list.get(position);
            int typeId = dataBean1.getTypeId();
            String childName = dataBean1.getChildName();
            String type = getType(typeId);
            String str = "";
            if (!android.text.TextUtils.isEmpty(childName)) {
                str = "<br/></font><font color='#2F2E2E'><small>" + childName + "</small></font>";
            }
            holder.textView.setText(Html.fromHtml(type + str));
            holder.itemView.setOnClickListener(v -> {
                if (lastSelectPosition != position) {
                    dataBean = list.get(position);
                    lastSelectPosition = position;
                    notifyDataSetChanged();
                    if(!titleView.isRightTextEffic()){
                        titleView.setRight_text("确认", Color.WHITE, (int) getResources().getDimension(R.dimen.dp_13));
                    }
                }
            });
            if (lastSelectPosition != position) {
                holder.imageView.setVisibility(View.GONE);
            } else {
                holder.imageView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            ImageView imageView;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tv);
                imageView = itemView.findViewById(R.id.iv_select);
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
            case 4:
                return "家长";
            default:
                return "孩子";
        }
    }

    public String getNextActivityAction(int userType) {
        String classes;
        switch (userType) {//1学生、2老师、3管理者、4家长，
            case 1:
                classes = "com.bshuiban.student.StudentHomeActivity";
                break;
            case 2:
            case 3:
                classes = "com.bshuiban.teacher.TeacherHomeActivity";
                break;
            case 4:
                classes = "com.bshuiban.parents.ParentsHomeActivity";
                break;
            default:
                classes = null;
        }
        return classes;
    }
}
