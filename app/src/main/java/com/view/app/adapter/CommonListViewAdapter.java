package com.view.app.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * AutoCompleteTextViewDemo
 *
 * @author xugang
 * @date 2017/12/11
 * @describe
 */

public abstract class CommonListViewAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mList;
    /**
     * 原始数据
     */
    private List<T> mOriginalList;
    private int layoutId;

    public CommonListViewAdapter(Context mContext, @LayoutRes int layoutId) {
        this(mContext, layoutId, null);
    }

    public CommonListViewAdapter(Context mContext, @LayoutRes int layoutId, List<T> list) {
        this.mContext = mContext;
        this.layoutId = layoutId;
        this.mList = list == null ? new ArrayList<T>() : list;
        this.mOriginalList = this.mList;
    }

    public List<T> getList() {
        return mList;
    }

    public List<T> getOriginalList() {
        return mOriginalList;
    }

    public void remove(T t) {
        if (mList.contains(t)) {
            mList.remove(t);
        }
        if (mOriginalList.contains(t)) {
            mOriginalList.remove(t);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 绑定Item和数据
     *
     * @param holder
     * @param obj
     */
    public abstract void bindViewAndData(ViewHolder holder, T obj);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getInstance(mContext, position, layoutId, convertView, parent);
        bindViewAndData(holder, getItem(position));
        return holder.getItem();
    }

    public static class ViewHolder {
        /**
         * 存储ListView的item的View
         */
        private SparseArray<View> mView;
        private View item;
        private int position;

        private ViewHolder() {
        }

        public static ViewHolder getInstance(Context context, int position, int layoutRes, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
                convertView.setTag(new ViewHolder());
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.item = convertView;
            holder.position = position;
            holder.mView = new SparseArray<>();
            return holder;
        }

        /**
         * 获取Item View
         */
        public View getItem() {
            return item;
        }

        /**
         * 获取Item位置
         */
        public int getPosition() {
            return position;
        }

        /**
         * 通过ID获取View
         */
        public <T extends View> T getView(int id) {
            T t = (T) mView.get(id);
            if (t == null) {
                t = (T) item.findViewById(id);
                mView.put(id, t);
            }
            return t;
        }

        /**
         * 设置文字
         */
        public ViewHolder setText(int id, CharSequence text) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        /**
         * 设置图片
         */
        public ViewHolder setImageResource(int id, int drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }

        /**
         * 设置点击监听
         */
        public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
            getView(id).setOnClickListener(listener);
            return this;
        }

        public ViewHolder setOnClickListener(int[] viewIds,
                                             View.OnClickListener listener) {
            if (viewIds != null) {
                for (int viewId : viewIds) {
                    View view = getView(viewId);
                    if (view != null) {
                        view.setOnClickListener(listener);
                    }
                }
            }
            return this;
        }

        /**
         * 设置可见
         */
        public ViewHolder setVisibility(int id, int visible) {
            getView(id).setVisibility(visible);
            return this;
        }

        /**
         * 设置标签
         */
        public ViewHolder setTag(int id, Object obj) {
            getView(id).setTag(obj);
            return this;
        }
    }
}
