/*
package com.bshuiban.baselibrary.view.treeview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

*/
/**
 * Created by xinheng on 2018/8/21.<br/>
 * describe：树形适配器
 *//*

public class TreeAdapter<T extends TreeBean> extends RecyclerView.Adapter<TreeAdapter.TreeViewHolder> {
    protected Context mContext;
    */
/**
     * 每一层级缩进的距离
     * 默认20 px
     *//*

    protected int mMarginLeftEveryRank = 20;
    */
/**
     * 备份数据-即全部展开的数据
     *//*

    private List<T> mBackupData;
    */
/**
     * ui展示所用的数据
     *//*

    protected List<T> mUIList;

    public void TreeAdapter(Context context) {
        this.mContext = context;
    }

    public void setmMarginLeftEveryRank(int marginLeft) {
        this.mMarginLeftEveryRank = marginLeft;
    }

    */
/**
     * 设置原始数据
     * 即全部展开的数据
     *
     * @param list
     *//*

    public void setBackupData(List<T> list) {
        this.mBackupData = list;
    }

    */
/**
     * 设置首次加载的数据
     *
     * @param allowAllShow 是否允许全部展开
     *//*

    public void setFirstUIStatue(boolean allowAllShow) {
        clearUIList();
        if (null != mBackupData) {
            if (allowAllShow) {//全部展开
                mUIList = copyList(mBackupData);
            } else {//收起
                mUIList = copyListFilter(mBackupData, TreeBean.FIRST_RANK);
            }
        }
        notifyDataSetChanged();
    }

    protected void updateUIList(List<T> list) {
        if (mUIList == null) {
            //非常规
            throw new RuntimeException(getClass().getPackage().getName() + ": mUIList=null，数据错误");
        } else {
            if (null != list && list.size() > 0) {
                mUIList.addAll(list);
            }
        }
    }

    */
/**
     * 获取此层级上的数据
     * （包含当前层级）
     *
     * @param list 数据
     * @param rank 层级
     * @return
     *//*

    protected List<T> copyListFilter(List<T> list, int rank) {
        ArrayList<T> copyList = new ArrayList<>();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next != null && next.getRank() <= rank) {
                copyList.add(next);
            }
        }
        return copyList;
    }

    protected List<T> copyList(List<T> list) {
        ArrayList<T> copyList = new ArrayList<>(list.size());
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            copyList.add(next);
        }
        return copyList;
    }

    protected void clearUIList() {
        if (null != mUIList) {
            mUIList.clear();
            mUIList = null;
        }
    }

    @NonNull
    @Override
    public TreeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TreeViewHolder(getViewForType(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull TreeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mUIList == null ? 0 : mUIList.size();
    }

    */
/**
     * @param position
     * @return < 0 -->无下级子元素；>0 -->有下级子元素
     *//*

    @Override
    public int getItemViewType(int position) {
        T t = mUIList.get(position);
        int rank = t.getRank();
        boolean haveChildNode = t.isHaveChildNode();
        return (haveChildNode ? 1 : -1) * rank;
    }

    private View getViewForType(int viewType) {
        boolean haveChildNode = viewType > 0;
        int rank = haveChildNode ? viewType : -viewType;
        int marginLeft = (rank - 1) * mMarginLeftEveryRank;
        View view;
        if(haveChildNode){
            view = createCatalogView(marginLeft);
        }else{
            view = createTextView(marginLeft);
        }
        return view;
    }

    private TextView createTextView(int marginLeft) {
        TextView tv = new TextView(mContext);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        RecyclerView.LayoutParams layoutParams = getRecycleLayoutParams(marginLeft);
        tv.setLayoutParams(layoutParams);
        return tv;
    }

    */
/**
     * 含标志图片
     * @param marginLeft
     * @return
     *//*

    private CatalogView createCatalogView(int marginLeft) {
        CatalogView catalogView = new CatalogView(mContext);
        RecyclerView.LayoutParams layoutParams = getRecycleLayoutParams(marginLeft);
        catalogView.setLayoutParams(layoutParams);
        //catalogView.setDrawableSrc(R.drawable.);
        return catalogView;
    }
    private RecyclerView.LayoutParams getRecycleLayoutParams(int marginLeft) {
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        params.leftMargin=marginLeft;
        return params;
    }

    class TreeViewHolder extends RecyclerView.ViewHolder {

        public TreeViewHolder(View itemView) {
            super(itemView);
        }
        protected void setTextView(String s){
            if(itemView instanceof TextView){
                ((TextView) itemView).setText(s);
            }else if(itemView instanceof CatalogView){
                ((CatalogView) itemView).setText(s);
            }
        }
    }
}
*/
