package com.view.app.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.widget.Filter;
import android.widget.Filterable;

import com.view.app.bean.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * AutoCompleteTextViewDemo
 *
 * @author xugang
 * @date 2017/12/11
 * @describe
 */

public abstract class AutoCompleteAdapter extends CommonListViewAdapter<Student> implements Filterable {
    private ArrayFilter mFilter;

    public AutoCompleteAdapter(Context mContext, @LayoutRes int layoutId, List<Student> list) {
        super(mContext, layoutId, list);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    public class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (getList() == null) {
                mList = new ArrayList<>();
            }
            if (TextUtils.isEmpty(constraint)) {
                ArrayList<Student> list = (ArrayList<Student>) getOriginalList();
                results.values = list;
                results.count = list.size();
            } else {
                String content = constraint.toString();
                ArrayList<Student> unfilteredValues = (ArrayList<Student>) getOriginalList();
                int count = unfilteredValues.size();

                ArrayList<Student> newValues = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Student bean = unfilteredValues.get(i);
                    if (bean.getId().contains(content)) {
                        newValues.add(bean);
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList = (ArrayList<Student>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
