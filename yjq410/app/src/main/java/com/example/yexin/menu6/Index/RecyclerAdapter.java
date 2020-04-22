package com.example.yexin.menu6.Index;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;

import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;

import com.example.yexin.menu6.Common.GetSystemTime;
import com.example.yexin.menu6.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ye xin on 2019/10/31.
 * 二级导航栏运动选项适配器
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.AuthorViewHolder>{

    //startdateTv = findViewById(R.id.startdateTv);
    private TextView time_setlect1,time_setlect2,time_setlect3;
    private Button button;
    private ArrayList<String> mTimeList,mTimeList2,mTimeList3;
    private String timeday,time,room;
    private static String type;

    private TimePickerView mStartDatePickerView;
    private OptionsPickerView pvNoLinkOptions;//选择器
    LayoutInflater inflater;
    ViewGroup parent;
    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent=parent;
        inflater = LayoutInflater.from(parent.getContext());
        View childView = inflater.inflate(R.layout.item, parent, false);
        AuthorViewHolder viewHolder = new AuthorViewHolder(childView);
        time_setlect1=(TextView)childView.findViewById(R.id.time_select1);
        time_setlect2=(TextView)childView.findViewById(R.id.time_select2);
        time_setlect3=(TextView)childView.findViewById(R.id.time_select3);
        button=(Button)childView.findViewById(R.id.button);

        Log.e("viewType","在这里显示viewType:"+viewType);

        //initStartTimePicker();//初始化开始日期选择器控件
        initNoLinkOptionsPicker();
        time_setlect1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.e("0407","在这里显示第一个");
                pvNoLinkOptions.show();
            }
        });
        time_setlect2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.e("0407","在这里显示第二个");
                pvNoLinkOptions.show();
            }
        });
        time_setlect3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.e("0407","在这里显示第三个");
                pvNoLinkOptions.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(parent.getContext(),Main_search_result.class);
                if(type==null){
                    Log.e("0407position","在这里显示type为空");
                    intent.putExtra("type","羽毛球");
                }else{
                    intent.putExtra("type",type);
                }
                intent.putExtra("timeday",timeday);
                intent.putExtra("time",time);
                intent.putExtra("room",room);
                parent.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AuthorViewHolder holder, int position) {
        Log.e("0407position","在这里显示"+position);

    }

    @Override
    public int getItemCount() {
        return 1;
    }


    class AuthorViewHolder extends RecyclerView.ViewHolder {

        public AuthorViewHolder(View itemView) {
            super(itemView);

        }
    }
   /*private void initStartTimePicker() {
        View childView = inflater.inflate(R.layout.item, parent, false);
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        //设置最小日期和最大日期
        Calendar startDate = Calendar.getInstance();
        try {
            startDate.setTime(DateTimeHelper.parseStringToDate("1970-01-01"));//设置为2006年4月28日
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar endDate = Calendar.getInstance();//最大日期是今天
        //Calendar endDate = Calendar.setTime();
        mStartDatePickerView = new TimePickerBuilder(parent.getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                time_setlect1.setText(DateTimeHelper.formatToString(date, "MM月dd "));
            }
        })
                .setDecorView((LinearLayout) childView.findViewById(R.id.activity_rootview))//必须是RelativeLayout，不设置setDecorView的话，底部虚拟导航栏会显示在弹出的选择器区域
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{false, false, true, true, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isCenterLabel(false)//是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setTitleText("开始日期")//标题文字
                .setTitleSize(20)//标题文字大小
                // .setTitleColor(getResources().getColor(R.color.pickerview_title_text_color))//标题文字颜色
                .setCancelText("取消")//取消按钮文字
                // .setCancelColor(getResources().getColor(R.color.pickerview_cancel_text_color))//取消按钮文字颜色
                .setSubmitText("确定")//确认按钮文字
                //  .setSubmitColor(getResources().getColor(R.color.pickerview_submit_text_color))//确定按钮文字颜色
                .setContentTextSize(20)//滚轮文字大小
                //  .setTextColorCenter(getResources().getColor(R.color.pickerview_center_text_color))//设置选中文本的颜色值
                .setLineSpacingMultiplier(1.8f)//行间距
                //  .setDividerColor(getResources().getColor(R.color.pickerview_divider_color))//设置分割线的颜色
                .setRangDate(startDate, endDate)//设置最小和最大日期
                .setDate(selectedDate)//设置选中的日期
                .build();
    }*/
   private void initNoLinkOptionsPicker() {// 不联动的多级选项
       View childView = inflater.inflate(R.layout.item, parent, false);
       initDatas();
       pvNoLinkOptions = new OptionsPickerBuilder(parent.getContext(), new OnOptionsSelectListener() {

           //回调函数
           @Override
           public void onOptionsSelect(int options1, int options2, int options3, View v) {

               String str = "food:" + mTimeList2.get(options1)
                       + "\nclothes:" + mTimeList.get(options2)
                       + "\ncomputer:" + mTimeList3.get(options3);

                   // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                time_setlect1.setText(mTimeList2.get(options1));
                time_setlect2.setText(mTimeList.get(options2));
                time_setlect3.setText(mTimeList3.get(options3));
                timeday=mTimeList2.get(options1);
                time=mTimeList.get(options2);
                room=mTimeList3.get(options3);
                Log.e("str",str);

           }
       })
               .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                   @Override
                   public void onOptionsSelectChanged(int options1, int options2, int options3) {
                       String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                       Toast.makeText(parent.getContext(), str, Toast.LENGTH_SHORT).show();
                   }
               })
               //.setItemVisibleCount(5)
               //.setSelectOptions(0, 1, 1)
               .isRestoreItem(true)
               .setDecorView((LinearLayout) childView.findViewById(R.id.activity_rootview))
               .build();
       pvNoLinkOptions.setNPicker(mTimeList2, mTimeList, mTimeList3);
       pvNoLinkOptions.setSelectOptions(0, 0, 0);
       //默认选择
       time_setlect1.setText(mTimeList2.get(0));
       time_setlect2.setText(mTimeList.get(0));
       time_setlect3.setText(mTimeList3.get(0));

       timeday=mTimeList2.get(0);
       time=mTimeList.get(0);
       room=mTimeList3.get(0);

   }
    private void initDatas() {
        //========================================初始化数据列表集合========================================
        //mHobbyList = new ArrayList<SpinnearBean>();
       // mHobbyNameList = new ArrayList<String>();
        mTimeList = new ArrayList<String>();
        mTimeList2 = new ArrayList<String>();
        mTimeList3 = new ArrayList<String>();
        mTimeList2.add(GetSystemTime.GetSystemTime()+"(今天)");
        mTimeList2.add(GetSystemTime.GetSystemTimeAdd_oneday()+"(明天)");
        mTimeList2.add(GetSystemTime.GetSystemTimeAdd_twoday()+"(后天)");
        mTimeList.add("7:00～8:00");
        mTimeList.add("9:00～10:00");
        mTimeList.add("10:00～11:00");
        mTimeList.add("11:00～12:00");
        mTimeList.add("12:00～13:00");
        mTimeList.add("13:00～14:00");
        mTimeList.add("14:00～15:00");
        mTimeList.add("15:00～16:00");
        mTimeList.add("16:00～17:00");
        mTimeList.add("18:00～19:00");
        mTimeList.add("19:00～20:00");
        mTimeList.add("20:00～21:00");
        mTimeList3.add("室内/外");
        mTimeList3.add("室内");
        mTimeList3.add("室外");
    }

    public static void setdata(String type){
        RecyclerAdapter.type=type;
    }


}
