package com.din.myhjc.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.din.myhjc.R;
import com.din.myhjc.adapter.TableAdapter;
import com.din.myhjc.content.ListTable;
import com.din.myhjc.databases.DataDiary;
import com.din.myhjc.databinding.TableFragmentBinding;

import org.litepal.crud.DataSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Tables extends Fragment {

    private List<ListTable> listTables = new ArrayList<ListTable>();
    private TableFragmentBinding bind;
    private TableAdapter adapter;
    private int[] count = new int[7];

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bind = DataBindingUtil.inflate(inflater, R.layout.table_fragment, container, false);

        //-------- RecyclerView设置Layout和Adapter
//        calendar();

        return bind.getRoot();
    }


    private void initData(String date) {

        Calendar calendar = Calendar.getInstance();
//        String date = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH);
//        int yearFirst = 0, monthFirst = 0, dayFirst = 0, yearLast = 0, monthLast = 0, dayLast = 0;
        long first = 0, last = 0;
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM/dd").parse(date));
            int d;
            if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                d = -6;
            } else {
                d = 2 - calendar.get(Calendar.DAY_OF_WEEK);
            }
            calendar.add(Calendar.DAY_OF_WEEK, d);
            first = calendar.getTimeInMillis();
            // 所在周开始日期
//            yearFirst = calendar.get(Calendar.YEAR);
//            monthFirst = calendar.get(Calendar.MONTH) + 1;
//            dayFirst = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.add(Calendar.DAY_OF_WEEK, 6);
            last = calendar.getTimeInMillis();
            // 所在周结束日期
//            yearLast = calendar.get(Calendar.YEAR);
//            monthLast = calendar.get(Calendar.MONTH) + 1;
//            dayLast = calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<DataDiary> dataDiaries = DataSupport.order("datetime").find(DataDiary.class);
        for (DataDiary dataDiary : dataDiaries) {
            if ((dataDiary.getDatetime() >= first) &&
                    (dataDiary.getDatetime() <= last)) {
                if (dataDiary.getWeek().equals("周一")) {
                    count[0]++;
                } else if (dataDiary.getWeek().equals("周二")) {
                    count[1]++;
                } else if (dataDiary.getWeek().equals("周三")) {
                    count[2]++;
                } else if (dataDiary.getWeek().equals("周四")) {
                    count[3]++;
                } else if (dataDiary.getWeek().equals("周五")) {
                    count[4]++;
                } else if (dataDiary.getWeek().equals("周六")) {
                    count[5]++;
                } else if (dataDiary.getWeek().equals("周日")) {
                    count[6]++;
                }
            }
        }
    }

    private String date;

//    private void calendar() {
//        bind.calendarDateView.setAdapter(new CaledarAdapter() {
//            @Override
//            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
//                TextView view;
//                if (convertView == null) {
//                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.calendar_select, null);
//                }
//                LayoutInflater.from(parentView.getContext()).inflate(R.layout.calendar_select, null);
//                view = (TextView) convertView.findViewById(R.id.text);
//
//                view.setText("" + bean.day);
//                if (bean.mothFlag != 0) {
//                    view.setTextColor(0x50ffffff);
//                } else {
//                    view.setTextColor(0xffffffff);
//                }
//
//                return convertView;
//            }
//        });
//
//        TextView title = getActivity().findViewById(R.id.title);
//        bind.calendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int postion, CalendarBean bean) {
//                date = bean.year + "-" + getDisPlayNumber(bean.moth) + "/" + getDisPlayNumber(bean.day);
//                initData(date);
//
//                title.setText(bean.year + "/" + getDisPlayNumber(bean.moth) + "/" + getDisPlayNumber(bean.day));
//            }
//        });
//
//        int[] data = CalendarUtil.getYMD(new Date());
//        date = data[0] + "-" + data[1] + "/" + data[2];
//        title.setText(data[0] + "/" + getDisPlayNumber(data[1]) + "/" + getDisPlayNumber(data[2]));
//    }

    private String getDisPlayNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }
}