package com.collegienproject.rank4.managecalories.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.collegienproject.rank4.managecalories.R;
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class ProgramListItem extends BaseCustomViewGroup {

    TextView tvProgramName;
    TextView tvDate;

    public ProgramListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public ProgramListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public ProgramListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public ProgramListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_program, this);
    }

    private void initInstances() {
        // findViewById here
        tvProgramName = (TextView) findViewById(R.id.tvProgramName);
        tvDate =(TextView) findViewById(R.id.tvDate);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }


    public void setNameText(String text){
        tvProgramName.setText(text);
    }

    public void setDateText(Date date){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(date);

        tvDate.setText(dateThai(format));
    }


    public static String dateThai(String strDate)
    {

        String Day[] = {
                "วันอาทิตย์ที่", "วันจันทร์ที่", "วันอังคารที่",
                "วันพุธที่", "วันพฤหัสบดีที่", "วันศุกร์ที่", "วันเสาร์ที่"};

        String Months[] = {
                "ม.ค", "ก.พ", "มี.ค", "เม.ย",
                "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
                "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        int year=0,month=0,day=0,dayofweek=0;
        try {
            Date date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DATE);
            dayofweek = c.get(Calendar.DAY_OF_WEEK);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return String.format("เริ่ม %s %s %s %s", Day[dayofweek-1],day,Months[month],year+543);
    }


}
