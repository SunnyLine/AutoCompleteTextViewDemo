package com.view.app;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.view.app.adapter.AutoCompleteAdapter;
import com.view.app.adapter.CommonListViewAdapter;
import com.view.app.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private AppCompatAutoCompleteTextView autoTextView;
    private CheckBox checkbox;
    private AutoCompleteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoTextView = (AppCompatAutoCompleteTextView) findViewById(R.id.autoTextView);
        autoTextView.setThreshold(1);
        checkbox = (CheckBox) findViewById(R.id.checkbox);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            autoTextView.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
                @Override
                public void onDismiss() {
                    /**此处加延时是为了防止PopupWindow再次弹出*/
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkbox.setChecked(false);
                        }
                    }, 200);

                }
            });
        }
        autoTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("============", "" + autoTextView.isPopupShowing());
                if (autoTextView.isPopupShowing()) {
                    checkbox.setChecked(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        checkbox.setOnCheckedChangeListener(this);
        mAdapter = new AutoCompleteAdapter(this, R.layout.item, getList()) {

            @Override
            public void bindViewAndData(ViewHolder holder, final Student obj) {
                holder.setText(R.id.text, obj.getId());
                holder.setOnClickListener(R.id.image, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        remove(obj);
                        Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        autoTextView.setAdapter(mAdapter);
    }

    private List<Student> getList() {
        List<Student> list = new ArrayList<>();
        list.add(new Student("10016"));
        list.add(new Student("10017"));
        list.add(new Student("10026"));
        list.add(new Student("10023"));
        list.add(new Student("10231"));
        list.add(new Student("11123"));
        return list;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked && !autoTextView.isPopupShowing()) {
            autoTextView.showDropDown();
        }
    }
}
