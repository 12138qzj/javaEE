package com.example.yexin.menu6.BallCludInfoShow;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.example.yexin.menu6.BallCludInfoShow.BallClubAdapter.ListViewBallAdapter;
import com.example.yexin.menu6.BallCludInfoShow.BallClubAdapter.ListViewPlaceAdapter;
import com.example.yexin.menu6.BallCludInfoShow.BallClubData.ListViewBallData;
import com.example.yexin.menu6.BallCludInfoShow.BallClubData.ListViewPlaceData;
import com.example.yexin.menu6.BallCludInfoShow.BallClubData.SelectData;
import com.example.yexin.menu6.BallCludInfoShow.BallClubSupport.CommonUtil;
import com.example.yexin.menu6.Ballculb.Payfaceture;
import com.example.yexin.menu6.ChatModule.Support.ItemClickSupport;
import com.example.yexin.menu6.Common.Public.UserPublic;
import com.example.yexin.menu6.Common.Url.Web_url;
import com.example.yexin.menu6.Index.GaoDeMap.Gaode_Map;
import com.example.yexin.menu6.Index.MainActivity;
import com.example.yexin.menu6.Index.SearchReasult;
import com.example.yexin.menu6.Index.stadiums_balls;
import com.example.yexin.menu6.Index.stadiums_balls_adapter;
import com.example.yexin.menu6.Index.stadiums_evalutes_adapter;
import com.example.yexin.menu6.Index.userinfo_adapter;
import com.example.yexin.menu6.R;
import com.example.yexin.menu6.Sideslip.Sideslip_top.location;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StadiumMainActivity extends Activity implements OnBannerListener {
    //mData,mContext,mAdapter,fragmentone_select_listview 演示数据，界面，数据适配，列表
    private LinkedList<userinfo_adapter> mDate2 = null;
    private ArrayList<Integer> imagePath = null;
    public static LinkedList<SearchReasult> mDatainformation;
    private static Context main_stadiumsContext;
    private Context mContext;

    private stadiums_evalutes_adapter mAdapter2 = null;

    private ListView list_user_evalutes;  //用户评论的listview
    private TextView ballclubname;         //界面的球馆名称
    private TextView distance;              //界面的距离，计算出具体的距离，超出一千米没有两千米就是一千米，类推
    private TextView facility;              //场馆设施的内容
    private TextView server;                //场馆服务得内容
    private TextView address;               //打开地图的具体内容地址
    private LinearLayout iv_stadium_phone; //设置了监听事件，点击打开电话
    private LinearLayout iv_stadium_location; //设置监听事件，点击去预定
    private LinearLayout Reserve;            //设置监听事件点击继续去预定
    private ImageView ball_collect;         //设置监听，点击收藏
    private ImageView back;                  //返回设置了监听
    private ImageView iv_grade;             //球馆的评分
    private TextView tv_numberOforders;    //球馆的总订单数
    private TextView ev_NeedMoney;          //显示给用户的最低的价格

    private SelectData selectData;          //在选择是用的javabean  用来存储每个阶段的选择内容
    private PopupWindow PopWindow;          //主界面的popwindow
    private ImageView ImageOne;              //选择成功的第一个狗
    private ImageView ImageTwo;               //选择的第二个狗
    private ImageView ImageThree;              //选择的第三个狗
    private Button ButtonNextStep;              //主弹出界面的下一步
    private Button ButtonCancel;                 //主弹出界面的取消

    private PopupWindow BallWindow;             //选择球的弹出界面
    private  LinkedList<ListViewBallData> mDataBall; //选择球弹出界面的数据javabean
    private ListViewBallAdapter listViewBallAdapterBall; //相应的适配器
    private TextView windowballselect_text;  //看了就知道


    private PopupWindow TimeWindow;
    private TextView windowtimeselect_time_day;  //看了就知道
    private TextView windowtimeselect_time_time;//看了就知道
    private TextView show_name;
    private TextView show_location;

    private PopupWindow PlaceWindow;   //场地选择弹出窗
    private LinkedList<ListViewPlaceData> mDataPlace=new LinkedList<>();   //适配器
    private ListViewPlaceAdapter listViewPlaceAdapter;                    //数据
    private TextView windowplaceselect_text;



    private List<CheckBox> radios; // 单选组
    private List<CheckBox> checkBoxes; // 多选组

    private int positionInt;
    public static int TimeStatus;
    private String phone;
    private JSONArray jsonArr;
    private JSONObject jsonObject;
    private Boolean bool = false;
    private String no;
    private String price;
    private String Tempstr;
    private String placenum;

    private SwipeRefreshLayout swipeRefreshView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_stadiums);
        mContext = StadiumMainActivity.this;

        location.getLocation(mContext);//进入界面就开始定位


        initView();//初始化变量

        /*取出场馆编号*/
        Intent intent = getIntent();
        no = intent.getStringExtra("no");
        http(Web_url.URL_AddCollection, "2");
        String positionString = intent.getStringExtra("position");
        phone = intent.getStringExtra("phone");
        positionInt = Integer.parseInt(positionString);

        price= intent.getStringExtra("price");


        Log.e("weizhi", "球类型" + mDatainformation.get(positionInt).getBall()+"价格"+mDatainformation.get(positionInt).getPrice());
        ballclubname.setText(mDatainformation.get(positionInt).getBallclub_name());
        address.setText(mDatainformation.get(positionInt).getAddress());
        distance.setText(mDatainformation.get(positionInt).getDistance());
        facility.setText("   " + mDatainformation.get(positionInt).getIntroduce());//场馆介绍
        server.setText("   " + mDatainformation.get(positionInt).getServer());
        Log.e("pricejg",price+"");
        ev_NeedMoney.setText(" "+mDatainformation.get(positionInt).getPrice());//价格赋值

        /**
         * 评论适配器
         */
        mDate2 = new LinkedList<userinfo_adapter>();
        mDate2.add(new userinfo_adapter("阿亮", "不错，我很喜欢"));
        mDate2.add(new userinfo_adapter("一个小虎牙", "场馆很宽敞，我很喜欢"));
        mAdapter2 = new stadiums_evalutes_adapter(mDate2, mContext);
        list_user_evalutes.setAdapter(mAdapter2);


        /**
         * 球类适配器我这里已经去掉了xml中的适配器，这里等于无效了
         */
        //球类适配器
//        mData = new LinkedList<stadiums_balls>();
//        if (mDatainformation.get(positionInt).getBall().indexOf("A") != -1)
//            mData.add(new stadiums_balls("羽毛球", "￥100", mDatainformation.get(positionInt).getNo(), mDatainformation.get(positionInt).getBallclub_name()));
//        if (mDatainformation.get(positionInt).getBall().indexOf("B") != -1)
//            mData.add(new stadiums_balls("篮球", "￥100", mDatainformation.get(positionInt).getNo(), mDatainformation.get(positionInt).getBallclub_name()));
//        if (mDatainformation.get(positionInt).getBall().indexOf("C") != -1)
//            mData.add(new stadiums_balls("足球", "￥100", mDatainformation.get(positionInt).getNo(), mDatainformation.get(positionInt).getBallclub_name()));
//        if (mDatainformation.get(positionInt).getBall().indexOf("D") != -1)
//            mData.add(new stadiums_balls("网球", "￥100", mDatainformation.get(positionInt).getNo(), mDatainformation.get(positionInt).getBallclub_name()));
//        mAdapter = new stadiums_balls_adapter(mData, mContext);
//        list_ballsselect.setAdapter(mAdapter);

        /**
         * 图片加载使用
         */
        Banner banner = (Banner) findViewById(R.id.stadiums_banner);
        initDate_banner();
        //设置图片加载器
        banner.setImageLoader(new StadiumMainActivity.GlideImageLoader());
        //设置图片集合
        banner.setImages(imagePath);
        //设置监听
        banner.setOnBannerListener(this);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        //banner.setBannerTitles(imageTitle);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.isAutoPlay(true);
        //start必须放在最后
        banner.start();

        //设置地图图标的监听器  将坐标和地址传入**12138
        iv_stadium_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Mapintent = new Intent(StadiumMainActivity.this, Gaode_Map.class);
                Mapintent.putExtra("address", mDatainformation.get(positionInt).getAddress());
                Mapintent.putExtra("location", mDatainformation.get(positionInt).getLocation());
                startActivity(Mapintent);
            }
        });

        /**
         * 拨打电话
         */
        iv_stadium_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(StadiumMainActivity.this);
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(StadiumMainActivity.this, "没有商家电话", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intentcall = new Intent();
                    intentcall.setAction(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + phone);
                    intentcall.setData(data);
                    startActivity(intentcall);
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(StadiumMainActivity.this, MainActivity.class);
                startActivity(intent_back);
                finish();
            }
        });


        /**
         * 收藏
         */
        ball_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 功能未实现
                 * */
                //获取用户id  球馆id mDatainformation.get(positionInt).getNo()，球馆名mDatainformation.get(positionInt).getBallclub_name()
                //加入到数据库表中去
                //每次点击 状态为 收藏/不收藏
                if(bool){//true  已经收藏
                    //ball_collect.setImageDrawable (getResources().getDrawable(R.drawable.likeit));
                    //bool=false;
                    http(Web_url.URL_AddCollection,"0");
                }else{//false 没有 收藏
                    // ball_collect.setImageDrawable (getResources().getDrawable(R.drawable.likeis));
                    //bool=true;
                    http(Web_url.URL_AddCollection,"1");
                }

                Toast.makeText(StadiumMainActivity.this, "收藏" + mDatainformation.get(positionInt).getNo() + " " +
                        mDatainformation.get(positionInt).getBallclub_name(), Toast.LENGTH_SHORT).show();

            }
        });

        /**
         * 预定的点击监听,显示第一个主弹出界面
         */
        Reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow();
            }
        });
    }

    private void initView() {

       // ev_NeedMoney
        list_user_evalutes = (ListView) findViewById(R.id.list_user_evalutes);

        ballclubname = (TextView) findViewById(R.id.tv_ballclubname);
        address = (TextView) findViewById(R.id.tv_address);
        distance = (TextView) findViewById(R.id.tv_distance);
        facility = (TextView) findViewById(R.id.tv_facility);
        server = (TextView) findViewById(R.id.tv_server);
        iv_stadium_location = (LinearLayout) findViewById(R.id.iv_stadium_location);
        iv_stadium_phone = (LinearLayout) findViewById(R.id.iv_stadium_phone);
        ball_collect = (ImageView) findViewById(R.id.ball_collect);
        back = (ImageView) findViewById(R.id.back);
        iv_grade = (ImageView) findViewById(R.id.iv_grade);
        tv_numberOforders = (TextView) findViewById(R.id.tv_numberOforders);
        ev_NeedMoney = (TextView) findViewById(R.id.ev_NeedMoney);
        Reserve = (LinearLayout) findViewById(R.id.Reserve);
        mDataBall=new LinkedList<>();
        radios=new LinkedList<>();
        checkBoxes=new LinkedList<>();
    }

    /**
     * 下拉刷新
     */
    private void pulldownRefresh() {
        swipeRefreshView.setRefreshing(false);
        swipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 开始刷新，设置当前为刷新状态
                //swipeRefreshLayout.setRefreshing(true);

                // 这里是主线程
                // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
                // TODO 获取数据
                //final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        mList.add(0, "我是天才" + random.nextInt(100) + "号");
//                        mAdapter.notifyDataSetChanged();
                        try {
                            //http();  在这可以执行一些耗时间的程序
                        } catch (Exception e) {
                        }
                        //Toast.makeText(mContext, "刷新了一条数据", Toast.LENGTH_SHORT).show();
                        // 加载完数据设置为不刷新状态，将下拉进度收起来

                    }
                }, 1200);
            }

        });
    }
    //将搜索界面的数传入该界面来 **12138  context留出空间 目前不使用
    public static void setmData(LinkedList<SearchReasult> mData, Context fragmentone_stadiums_adapterContext) {
        mDatainformation = mData;
        // main_stadiumsContext=fragmentone_stadiums_adapterContext;
    }

    //初始数据banner
    private void initDate_banner() {
        imagePath = new ArrayList<>();
        imagePath.add(R.drawable.b1);
        imagePath.add(R.drawable.b2);
        imagePath.add(R.drawable.b3);
    }

    @Override
    public void OnBannerClick(int position) {
        /*图片监听器*/
        switch (position) {
            case 0:
                Toast.makeText(this, "第一个", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "第二个", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "第三个", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    //系统按键返回上一界面
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(StadiumMainActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    //重写图片加载器
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 权限申请
     *
     * @param activity
     */
    //权限检测  并打开权限
    private void checkPermission(Activity activity) {
        // Storage Permissions
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.CALL_PHONE,/*CALL_PHONE/*READ_EXTERNAL_STORAGE*/
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        try {
            //检测是否有拨号的权限
            int permission = ActivityCompat.checkSelfPermission(StadiumMainActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                // requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 0);
                ActivityCompat.requestPermissions(StadiumMainActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 网络请求
     *
     * @param URL
     * @param code
     */
    private void http(String URL, String code) {

        Log.e("one", "网络请求中用户id:" + UserPublic.getUser());

        RequestParams params = new RequestParams(URL);
        params.addHeader("Content-Type", "application/json-rpc"); //设置请求头部
        params.setAsJsonContent(true);//设置为json内容(这句个本人感觉不加也没有影响)
        try {
            jsonObject = new JSONObject();
            jsonObject.put("userid", UserPublic.getUser());
            jsonObject.put("no", no);
            jsonObject.put("code", code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.setBodyContent(jsonObject.toString());//添加json内容到请求参数里
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("yjq", "网络请求成功" + result);  //接收JSON的字符串
//               HashMap<String,String> map=Web_Json.getJson(result);
                //HashMap<String,String> map=Web_Json.getJson(result);
                Log.e("yjqresult:", result.toString());
                Log.e("yjqresult:", "长度：" + result.length());
                if(code.equals("2")) {//查询收藏
                    if (result.toString().equals("true")) {
                        bool = true;
                        ball_collect.setImageDrawable(getResources().getDrawable(R.drawable.likeis));
                    }
                    if (result.toString().equals("false")) {
                        bool = false;
                        ball_collect.setImageDrawable(getResources().getDrawable(R.drawable.likeit));
                    }
                }
                if(code.equals("1")){//加入收藏
                    if (result.toString().equals("true")) {
                        bool = true;
                        ball_collect.setImageDrawable(getResources().getDrawable(R.drawable.likeis));
                    }
                    if (result.toString().equals("false")) {
                        bool = false;
                        ball_collect.setImageDrawable(getResources().getDrawable(R.drawable.likeit));
                    }
                }
                if(code.equals("0")){//取消收藏成功
                    if (result.toString().equals("true")) {//取消收藏成功
                        bool = false;
                        ball_collect.setImageDrawable(getResources().getDrawable(R.drawable.likeit));
                        //  ball_collect.setImageDrawable(getResources().getDrawable(R.drawable.likeis));
                    }
                    if (result.toString().equals("false")) {
                        bool = true;
                        ball_collect.setImageDrawable(getResources().getDrawable(R.drawable.likeis));
                    }
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("yjq1", "失败");
                Toast.makeText(mContext, "连接超时，请查看网络连接", Toast.LENGTH_SHORT).show();
                //refreshDialog.dismiss();
                //  swipeRefreshView.setRefreshing(false);
                //  Refresh_status=Refresh_Fail;
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("yjq", "取消");
            }

            @Override
            public void onFinished() {
                Log.e("yjq", "完成");
                //完成时候运行
            }
        });

    }

    /**
     * 预定的一个选择pop
     */
    private void showPopupWindow() {
        selectData = new SelectData();
        //设置contentView
        View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow, null);
        PopWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        PopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        ImageOne = contentView.findViewById(R.id.select_one);
        ImageTwo = contentView.findViewById(R.id.select_two);
        ImageThree = contentView.findViewById(R.id.select_three);
        RelativeLayout selectBall = contentView.findViewById(R.id.select_ball);
        RelativeLayout selectTime = contentView.findViewById(R.id.select_time);
        RelativeLayout selectPlace = contentView.findViewById(R.id.select_place);
        ButtonNextStep = contentView.findViewById(R.id.button_next);
        ButtonCancel = contentView.findViewById(R.id.button_cancel);

        show_name=contentView.findViewById(R.id.show_name);//球馆名称显示
        show_location=contentView.findViewById(R.id.show_location);//球馆地址显示
        show_name.setText(mDatainformation.get(positionInt).getBallclub_name());
        show_location.setText(mDatainformation.get(positionInt).getAddress());
        selectBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectBall();
            }
        });
        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectData.getSelectBall()==null){
                    Toast.makeText(StadiumMainActivity.this, "完成上一步", Toast.LENGTH_SHORT).show();
                }else{
                    selectTime(windowballselect_text.getText().toString());
                }
            }
        });
        selectPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectData.getSelectBall()==null||selectData.getSelectTimeDay()==null){
                    Toast.makeText(StadiumMainActivity.this, "完成上一步", Toast.LENGTH_SHORT).show();
                }else{
                    selectPlace();
                }
            }
        });
        ButtonNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if()
//                if(selectData.getSelectBall()==""){
//
//                }else if(selectData.getSelectPlace()==""){
//
//                }else if(selectData.getSelectTimeDay()){
//
//                }
                Log.e("next", selectData.getSelectBall() + "   " + selectData.getSelectPlace() + "   " + selectData.getSelectTimeDay() + "   " + selectData.getSelectTimeTime());
               // Intent intent=new Intent()
                Log.e("next", mDatainformation.get(positionInt).getNo() + "   "
                        + mDatainformation.get(positionInt).getBallclub_name() + "   "
                        + selectData.getSelectBall() + "   "
                        + selectData.getSelectPlace()+"  "
                        +mDatainformation.get(positionInt).getPrice());

                Intent intent = new Intent(StadiumMainActivity.this, Payfaceture.class);
                intent.putExtra("ballNo",mDatainformation.get(positionInt).getNo());
                intent.putExtra("changguan",mDatainformation.get(positionInt).getBallclub_name());
                intent.putExtra("ball_kind",selectData.getSelectBall());
                intent.putExtra("changdi",selectData.getSelectPlace());
                intent.putExtra("time_quantum",selectData.getSelectTimeDay() + " " + selectData.getSelectTimeTime());
                intent.putExtra("jiage",mDatainformation.get(positionInt).getPrice());
                startActivity(intent);
            }
        });
        ButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopWindow.dismiss();
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        PopWindow.showAtLocation(rootview, Gravity.TOP, 0, 0);
    }




    void changeRadios(CheckBox checkBox) {
        if(!checkBox.isChecked()){
            checkBox.setChecked(false);
        }else{
            CommonUtil.unCheck(radios);
            checkBox.setChecked(true);
        }
    }


    /**
     * 选球的弹出界面
     */
    private void selectBall(){
        mDataBall.clear();
        if (mDatainformation.get(positionInt).getBall().indexOf("A") != -1)
            mDataBall.add(new ListViewBallData(R.drawable.showymq,"羽毛球","1000",false));//名字和价格
        if (mDatainformation.get(positionInt).getBall().indexOf("B") != -1)
            mDataBall.add(new ListViewBallData(R.drawable.showymq,"篮球","100",false));
        if (mDatainformation.get(positionInt).getBall().indexOf("C") != -1)
            mDataBall.add(new ListViewBallData(R.drawable.showymq,"乒乓球","10",false));
        if (mDatainformation.get(positionInt).getBall().indexOf("D") != -1)
            mDataBall.add(new ListViewBallData(R.drawable.showymq,"网球","1",false));
        listViewBallAdapterBall =new ListViewBallAdapter(this,mDataBall);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //设置contentView
        View contentView = LayoutInflater.from(this).inflate(R.layout.windowballselect, null);
        BallWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        BallWindow.setContentView(contentView);
        //设置各个控件的点击响应
        RecyclerView recyclerView=contentView.findViewById(R.id.listview);
        windowballselect_text=contentView.findViewById(R.id.windowballselect_text);
        Button windowballselect_cancel=contentView.findViewById(R.id.windowballselect_cancel);
        Button windowballselect_submit=contentView.findViewById(R.id.windowballselect_submit);
        windowballselect_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                BallWindow.dismiss();
                Log.e("cancel","点击了球选择的取消");
            }
        });
        windowballselect_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(windowballselect_text.getText().toString())){
                    selectData.setSelectBall(windowballselect_text.getText().toString());
                    Log.e("submit","选择值为："+windowballselect_text.getText().toString());
                    ImageOne.setImageResource(R.drawable.showxz);
                    BallWindow.dismiss();
                }else{
                    Log.e("submit","未选择");
                    Toast.makeText(StadiumMainActivity.this,"未选择",Toast.LENGTH_SHORT).show();
                }
                Log.e("submit","点击了球选择的确定");
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listViewBallAdapterBall);
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        BallWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

        //点击监听
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            private ListViewBallData BallData=null;
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Log.e("列表点击","你点击了"+mDataBall.get(position).getBallName());
                for(int i=0;i<mDataBall.size();i++){
                    BallData=mDataBall.get(i);
                    if(BallData.isBallChoose()){
                        BallData.setBallChoose(false);
                    }
                }
                BallData=mDataBall.get(position);
                BallData.setBallChoose(true);
                listViewBallAdapterBall.ChangeDate(mDataBall);
                windowballselect_text.setText(mDataBall.get(position).getBallName());
            }
        });
    }

    /**
     * 时间选择
     */
    private void selectTime(String BallType){
        radios.clear();
        checkBoxes.clear();

        //设置contentView
        View contentView = LayoutInflater.from(this).inflate(R.layout.windowtimeselect, null);
        TimeWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        TimeWindow.setContentView(contentView);
        //设置各个控件的点击响应
        windowtimeselect_time_day=contentView.findViewById(R.id.windowtimeselect_text_day);
        windowtimeselect_time_time=contentView.findViewById(R.id.windowtimeselect_text_time);
        Button windowtimeselect_cancel=contentView.findViewById(R.id.windowtimeselect_cancel);
        windowtimeselect_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TimeWindow.dismiss();
                Log.e("cancel","点击了时间选择的取消");
            }
        });
        Button windowtimeselect_submit=contentView.findViewById(R.id.windowtimeselect_submit);
        windowtimeselect_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(windowtimeselect_time_day.getText().toString())&&!TextUtils.isEmpty(windowtimeselect_time_time.getText().toString())){
                    selectData.setSelectTimeDay(windowtimeselect_time_day.getText().toString());
                    selectData.setSelectTimeTime(windowtimeselect_time_time.getText().toString());
                    Log.e("submit","时间选择值为："+windowtimeselect_time_time.getText().toString());
                    String time[]=windowtimeselect_time_time.getText().toString().split(",");
                    for (int i=0;i<time.length;i++){
                        time[i]=time[i].substring(0,2);
                        Log.e("submit","i的值"+time[i]);
                    }
                    ImageTwo.setImageResource(R.drawable.showxz);
                    TimeWindow.dismiss();
                    //HttpGetData(mDatainformation.get(positionInt).getNo(),BallType,time);
                }else{
                    Toast.makeText(StadiumMainActivity.this,"未选择",Toast.LENGTH_SHORT).show();
                }
                Log.e("cancel","点击了时间选择的确定");
            }
        });
        final CheckBox radioone=contentView.findViewById(R.id.radioOne);
        radioone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeRadios(radioone);
                windowtimeselect_time_day.setText(CommonUtil.getOne(radios));
                Log.e("radio","点击单选一"+CommonUtil.getOne(radios));
            }
        });
        final CheckBox radiotwo=contentView.findViewById(R.id.radioTwo);
        radiotwo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeRadios(radiotwo);
                windowtimeselect_time_day.setText(CommonUtil.getOne(radios));
                Log.e("radio","点击单选二"+CommonUtil.getOne(radios));
            }
        });
        final CheckBox radiothree=contentView.findViewById(R.id.radioThree);
        radiothree.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeRadios(radiothree);
                windowtimeselect_time_day.setText(CommonUtil.getOne(radios));
                Log.e("radio","点击单选三,内容"+CommonUtil.getOne(radios));
            }
        });

        radioone.setTag("1");
        radiotwo.setTag("2");
        radiothree.setTag("3");

        radioone.setText("1");
        radiotwo.setText("2");
        radiothree.setText("3");
        radios.add(radioone);
        radios.add(radiotwo);
        radios.add(radiothree);
        CheckBox one=contentView.findViewById(R.id.checkboxOne);
        one.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox two=contentView.findViewById(R.id.checkboxTwo);
        two.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox three=contentView.findViewById(R.id.checkboxThree);
        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox four=contentView.findViewById(R.id.checkboxFour);
        four.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox five=contentView.findViewById(R.id.checkboxFive);
        five.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox sex=contentView.findViewById(R.id.checkboxSex);
        sex.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox serven=contentView.findViewById(R.id.checkboxServen);
        serven.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox eight=contentView.findViewById(R.id.checkboxEight);
        eight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox nine=contentView.findViewById(R.id.checkboxNight);
        nine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox ten=contentView.findViewById(R.id.checkboxTen);
        ten.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox tenone=contentView.findViewById(R.id.checkboxTenOne);
        tenone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox tentwo=contentView.findViewById(R.id.checkboxTenTwo);
        tentwo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox tenthree=contentView.findViewById(R.id.checkboxTenThree);
        tenthree.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox tenfour=contentView.findViewById(R.id.checkboxTenFour);
        tenfour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });
        CheckBox tenfive=contentView.findViewById(R.id.checkboxFive);
        tenfive.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                windowtimeselect_time_time.setText(CommonUtil.getMany(checkBoxes));
                Log.e("CheckBox",CommonUtil.getMany(checkBoxes));
            }
        });

        //checkBoxes

        checkBoxes.add(one);
        checkBoxes.add(two);
        checkBoxes.add(three);
        checkBoxes.add(four);
        checkBoxes.add(five);
        checkBoxes.add(sex);
        checkBoxes.add(serven);
        checkBoxes.add(eight);
        checkBoxes.add(nine);
        checkBoxes.add(ten);
        checkBoxes.add(tenone);
        checkBoxes.add(tentwo);
        checkBoxes.add(tenthree);
        checkBoxes.add(tenfour);
        checkBoxes.add(tenfive);
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        TimeWindow.showAtLocation(rootview, Gravity.TOP, 0, 0);
    }

    /**
     * 选择场地
     */
    private void selectPlace(){
        mDataPlace.clear();
        mDataPlace.add(new ListViewPlaceData("场地一号",true));
        mDataPlace.add(new ListViewPlaceData("场地二号",true));
        mDataPlace.add(new ListViewPlaceData("场地三号",true));
        mDataPlace.add(new ListViewPlaceData("场地四号",true));
        mDataPlace.add(new ListViewPlaceData("场地五号",true));
        mDataPlace.add(new ListViewPlaceData("场地六号",false));
        mDataPlace.add(new ListViewPlaceData("场地七号",true));
        mDataPlace.add(new ListViewPlaceData("场地八号",false));
        mDataPlace.add(new ListViewPlaceData("场地九号",true));
        mDataPlace.add(new ListViewPlaceData("场地十号",false));
        listViewPlaceAdapter=new ListViewPlaceAdapter(this,mDataPlace);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        View contentView = LayoutInflater.from(this).inflate(R.layout.windowplaceselect, null);
        PlaceWindow = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        PlaceWindow.setContentView(contentView);
        //设置各个控件的点击响应
        RecyclerView recyclerView=contentView.findViewById(R.id.listview);
        Button windowplaceselect_cancel=contentView.findViewById(R.id.windowplaceselect_cancel);
        Button windowplaceselect_sumbit=contentView.findViewById(R.id.windowplaceselect_submit);

        windowplaceselect_text=contentView.findViewById(R.id.windowplaceselect_text);
        windowplaceselect_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                PlaceWindow.dismiss();
            }
        });
        windowplaceselect_sumbit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(!TextUtils.isEmpty(windowplaceselect_text.getText().toString())){
                    selectData.setSelectPlace(windowplaceselect_text.getText().toString());
                    Log.e("submit","场地选择值为qzj："+windowplaceselect_text.getText().toString());
                    Log.e("submit","场地选择值为："+windowballselect_text.getText().toString());
                    ImageThree.setImageResource(R.drawable.showxz);
                    PlaceWindow.dismiss();
                }else{
                    Toast.makeText(StadiumMainActivity.this,"未选择",Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listViewPlaceAdapter);
        //显示PopupWindow
        View rootview = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        PlaceWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);
        //点击监听
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            private ListViewPlaceData PlaceData=null;
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Log.e("列表点击","你点击了"+mDataPlace.get(position).getPlaceName());
                windowplaceselect_text.setText(mDataPlace.get(position).getPlaceName());
            }
        });
    }
    private void HttpGetData(String BallNo, String BallType,String time){
        Log.e("SelectD", "搜索内容：" + BallNo+"   "+BallType+"  "+time);
        //String temp[]=BallType.split(" ");
        //this.prices=temp[3];
        RequestParams params=null;
        jsonObject = new JSONObject();
        try {
           // Log.e("temp",temp[0]+"  "+temp[1]);
            jsonObject.put("content_balltype",BallType);
            jsonObject.put("content_ballno",BallNo);
            jsonObject.put("time",time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        params = new RequestParams(Web_url.URL_GetABCball);
        params.addHeader("Content-Type", "application/json-rpc"); //设置请求头部
        params.setAsJsonContent(true);//设置为json内容(这句个本人感觉不加也没有影响)
        params.setBodyContent(jsonObject.toString());//添加json内容到请求参数里

        if(params!=null){
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("yjq","网络请求成功"+result);  //接收JSON的字符串
                    Log.e("yjqresult:",result.toString());
                    Tempstr=result;
                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e("yjq1","失败");
                    Toast.makeText(StadiumMainActivity.this, "连接超时，请查看网络连接", Toast.LENGTH_SHORT).show();
                    swipeRefreshView.setRefreshing(false);
                }
                @Override
                public void onCancelled(CancelledException cex) {
                    Log.e("yjq","取消");
                }
                @Override
                public void onFinished() {
                    jsonObject = new JSONObject();
                    Log.e("yjq","完成");
                    try{
                        jsonArr=new JSONArray(Tempstr);
                        Log.e("jsonObject:","长度为a:"+jsonArr.length());//求出数据中的个数
                        jsonObject = (JSONObject)jsonArr.getJSONObject(jsonArr.length()-1);
                        String [] placeStateString=new String [jsonObject.getString("ballnum").length()];
                        Log.e("jsonObject","jsonObject:"+jsonObject.getString("ballnum"));
                        placenum=jsonObject.getString("ballnum");
                        Log.e("json11","placenum:"+"... "+placenum.length());
                        for(int i=0;i<placenum.length();i++){
                            String a=((JSONObject)jsonArr.getJSONObject(i)).getString(placenum.substring(i,i+1));
                            Log.e("json11","a:"+a);
                            placeStateString[i]=a;
                            Log.e("json11","placeStateString:"+i+"... "+placeStateString[i]);
                        }
                      //  init(placeStateString);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //install();
                    //swipeRefreshView.setRefreshing(false);
                    //mAdapter=new SearchResultAdapter((LinkedList<SearchReasult>) mData,mContext);
                    // search_result_listview.setAdapter(mAdapter);
                    //完成时候运行
                }
            });
        }else{
            Toast.makeText(StadiumMainActivity.this, "请求数据错误", Toast.LENGTH_SHORT).show();
        }
    }
}
