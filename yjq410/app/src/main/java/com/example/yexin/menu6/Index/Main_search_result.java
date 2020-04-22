package com.example.yexin.menu6.Index;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.example.yexin.menu6.Common.Public.UserPublic;
import com.example.yexin.menu6.Common.Url.Web_url;
import com.example.yexin.menu6.R;
import com.example.yexin.menu6.Sideslip.Sideslip_top.location;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.LinkedList;

public class Main_search_result extends Activity {

    public JSONObject jsonObject;
    public JSONArray jsonArr;
    private LinkedList<SearchReasult> mData=null;
    private fragmentone_stadiums_adapter mAdapter = null;
    private ListView searchans_listview;
    private TextView tv_searchans_local,tv_game_objects,tv_gam_time;
    private ImageView iv_searchans_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsearchresult);
        Intent intent=getIntent();
        searchans_listview=(ListView)findViewById(R.id.searchans_listview);
        tv_searchans_local=(TextView)findViewById(R.id.tv_searchans_local);
        tv_game_objects=(TextView)findViewById(R.id.tv_game_objects);
        tv_gam_time=(TextView)findViewById(R.id.tv_game_time);
        iv_searchans_back=(ImageView)findViewById(R.id.iv_searchans_back);


/*json.getString("type"), json.getString("timeday"),
					json.getString("time"), json.getString("room")*/
        String  type =intent.getStringExtra("type");
        String timeday=intent.getStringExtra("timeday");
        String time=intent.getStringExtra("time");
        String room=intent.getStringExtra("room");
        tv_game_objects.setText(type);
        tv_gam_time.setText(time);
        Log.e("onejsonObject","位置:"+location.Getinfo_city());
        tv_searchans_local.setText(location.Getinfo_city());

        try {
            jsonObject=new JSONObject();
            jsonObject.put("type",type);
            jsonObject.put("timeday",timeday);
            jsonObject.put("time",time);
            jsonObject.put("room",room);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.e("onejsonObject","网络请求中用户id:"+jsonObject.toString());
        http();


        iv_searchans_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main_search_result.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void http(){
        Log.e("one","网络请求中用户id:"+UserPublic.getUser());

        RequestParams params = new RequestParams(Web_url.URL_GetSearchMain);
        params.addHeader("Content-Type", "application/json-rpc"); //设置请求头部
        params.setAsJsonContent(true);//设置为json内容(这句个本人感觉不加也没有影响)

      params.setBodyContent(jsonObject.toString());//添加json内容到请求参数里
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("yjq","网络请求成功"+result);  //接收JSON的字符串
//               HashMap<String,String> map=Web_Json.getJson(result);
                //HashMap<String,String> map=Web_Json.getJson(result);
                Log.e("yjqresult:",result.toString());
                Log.e("yjqresult:","长度："+result.length());

                try{
                    //int jsonSize = result.length();//获取数据组的长度
                    jsonArr=new JSONArray(result);
                    if(jsonArr.length()>0){
                        mData=null;
                        mData = new LinkedList<SearchReasult>();
                    }
                    float a[]=new float[jsonArr.length()];
                    int b[]=new int[jsonArr.length()];

                    for(int i=0;i<jsonArr.length();i++) {
                        Log.e("yjqresult:", "jsonArr长度：" + jsonArr.length());
                        jsonObject = (JSONObject) jsonArr.getJSONObject(i);
                        String latitude_long []=jsonObject.getString("坐标").split(",");
                        float longitude=Float.parseFloat(latitude_long[0]);
                        float latitude=Float.parseFloat(latitude_long[1]);
                        Log.e("position",jsonObject.getString("坐标"));
                        LatLng latLng = new LatLng(latitude,longitude);
                        float distance = AMapUtils.calculateLineDistance(latLng, location.Getinfo());
                        a[i]=distance;
                        b[i]=i;
                    }
                    //将距离排序
                    for(int i=0;i<a.length;i++){
                        for(int j=i+1;j<a.length;j++){
                            if(a[i]>a[j]){
                                int itemp;
                                float ftemp;
                                ftemp=a[i]; a[i]=a[j]; a[j]=ftemp;
                                itemp=b[i]; b[i]=b[j]; b[j]=itemp;
                            }
                        }
                    }
                    for(int i=0;i<jsonArr.length();i++){
                        Log.e("yjqresult:","jsonArr长度："+jsonArr.length());
                        jsonObject = (JSONObject)jsonArr.getJSONObject(b[i]);
                        float Fresult_distance;
                        String Sdistance;
                        if(a[i]>=1000){
                            Fresult_distance=a[i]/1000;
                            Sdistance=(Fresult_distance+"").substring(0,(Fresult_distance+"").indexOf(".")+2)+"km";

                            // Sdistance=Fresult_distance+"km";
                        }else{
                            Fresult_distance=a[i];
                            Sdistance=(Fresult_distance+"").substring(0,(Fresult_distance+"").indexOf(".")+2)+"m";
                        }
                        //String Sdistance=Fresult_distance.toFixed();
                        Log.e("map","距离为："+a[i]+"");

                        Log.e("yjqresult:","jsonArr长度："+jsonArr.length());
                        jsonObject = (JSONObject)jsonArr.getJSONObject(i);
                        Log.e("yjqresult:",i+"jsonObject："+jsonObject.toString());
                        Log.e("数据的变化",jsonObject.getString("场馆编号"));
                        Log.e("数据的变化",jsonObject.getString("场馆名"));
                        Log.e("数据的变化",jsonObject.getString("场馆地址"));
                        mData.add(new SearchReasult(jsonObject.getString("场馆编号"),jsonObject.getString("场馆名"),
                                jsonObject.getString("场馆地址"),Sdistance,"￥100",jsonObject.getString("场馆负责人"),
                                jsonObject.getString("负责人电话"),jsonObject.getString("场馆图片"),jsonObject.getString("场馆评价"),jsonObject.getString("场馆球类型"),/*球类型未添加*/jsonObject.getString("场馆服务"),
                                jsonObject.getString("场馆介绍"),jsonObject.getString("下单量"),jsonObject.getString("地板"),jsonObject.getString("灯光"),
                                jsonObject.getString("休息区"),jsonObject.getString("售卖"),
                                jsonObject.getString("体育用品售卖"),jsonObject.getString("坐标")));
                    }
                   /*
                   * 此处不能运行*/
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("yjq1","失败");
                Toast.makeText(Main_search_result.this, "连接超时，请查看网络连接", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("yjq","取消");
            }
            @Override
            public void onFinished() {
                Log.e("yjq","完成search");
//                mData.add(new SearchReasult("12138","小邱球馆",
//                        "福田15号","距离<100","￥100","邱在杰",
//                        "15112347784","无","6分","AB",/*球类型未添加*/"无",
//                        "无","无","水泥地","无",
//                        "无","无",
//                        "无","0,0"));



                mAdapter=new fragmentone_stadiums_adapter(mData,Main_search_result.this);
                searchans_listview.setAdapter(mAdapter);

                //完成时候运行
            }
        });
    }
}
