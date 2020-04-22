package com.example.yexin.menu6.Index.Collect;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.example.yexin.menu6.Common.Public.UserPublic;
import com.example.yexin.menu6.Common.Url.Web_url;
import com.example.yexin.menu6.Index.SearchReasult;
import com.example.yexin.menu6.R;
import com.example.yexin.menu6.Sideslip.Sideslip_top.location;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 23646 on 2019/11/5.
 */

public class Fragment_Focus_BallClub extends Fragment{
    private List<Focus_Item> mData;
    private LinkedList<SearchReasult> mData1=null;
    private Context mContext;
    private Focus_Item_Adapter focus_item_adapter = null;
    private ListView lv_focus_fans;
    private TextView tv_focus_ballclub_number;
    private JSONObject jsonObject;
    private JSONArray jsonArr;
    public static Fragment newInstance() {
        Fragment_Focus_BallClub fragment = new Fragment_Focus_BallClub();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_focus_ballclub, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lv_focus_fans = (ListView)rootView.findViewById(R.id.lv_focus_fans);
        tv_focus_ballclub_number = (TextView)rootView.findViewById(R.id.tv_focus_ballclub_number);
        mData = new LinkedList<Focus_Item>();
        mContext = getContext();
        initial_Data();

        initial_Listener();
        return rootView;
    }

    public void initial_Data(){

        http();
    }

    //加载推荐数据
  /*  public void initial_Recommend_Data(){

        mData.add(new Focus_Item(R.drawable.quanshui,"54644846","篮球"));
    }*/

    public void initial_Listener(){
        lv_focus_fans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }
    private void http(){

        Log.e("one","网络请求中用户id:"+ UserPublic.getUser());

        RequestParams params = new RequestParams(Web_url.URL_GetCollection);
        params.addHeader("Content-Type", "application/json-rpc"); //设置请求头部
        params.setAsJsonContent(true);//设置为json内容(这句个本人感觉不加也没有影响)
        try {
            jsonObject=new JSONObject();
            jsonObject.put("userid",UserPublic.getUser());
        }catch (Exception e){
            e.printStackTrace();
        }
        params.setBodyContent(jsonObject.toString());//添加json内容到请求参数里
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("yjq","网络请求成功"+result);  //接收JSON的字符串
//               HashMap<String,String> map=Web_Json.getJson(result);
                //HashMap<String,String> map=Web_Json.getJson(result);
                Log.e("yjqresult:",result.toString());
                Log.e("yjqresult:","长度："+result.length());
                if(result.length()>0){

                    try{
                        //int jsonSize = result.length();//获取数据组的长度
                        jsonArr=new JSONArray(result);
                        if(jsonArr.length()>0){
                            mData1=null;
                            mData1 = new LinkedList<SearchReasult>();
                        }
                       // float a[]=new float[jsonArr.length()];
                       // int b[]=new int[jsonArr.length()];

//
                        //将距离 for(int i=0;i<jsonArr.length();i++) {
//                            Log.e("yjqresult:", "jsonArr长度：" + jsonArr.length());
//                            jsonObject = (JSONObject) jsonArr.getJSONObject(i);
//                            String latitude_long []=jsonObject.getString("坐标").split(",");
//                            float longitude=Float.parseFloat(latitude_long[0]);
//                            float latitude=Float.parseFloat(latitude_long[1]);
//                            Log.e("position",jsonObject.getString("坐标"));
//                            LatLng latLng = new LatLng(latitude,longitude);
//                            float distance = AMapUtils.calculateLineDistance(latLng, location.Getinfo());
//                            a[i]=distance;
//                            b[i]=i;
//                        }排序
//                        for(int i=0;i<a.length;i++){
//                            for(int j=i+1;j<a.length;j++){
//                                if(a[i]>a[j]){
//                                    int itemp;
//                                    float ftemp;
//                                    ftemp=a[i]; a[i]=a[j]; a[j]=ftemp;
//                                    itemp=b[i]; b[i]=b[j]; b[j]=itemp;
//                                }
//                            }
//                        }
                        for(int i=0;i<jsonArr.length();i++){
                            Log.e("yjqresult:","jsonArr长度："+jsonArr.length());
                            //jsonObject = (JSONObject)jsonArr.getJSONObject(b[i]);
//                            float Fresult_distance;
//                            String Sdistance;
//                            if(a[i]>=1000){
//                                Fresult_distance=a[i]/1000;
//                                Sdistance=(Fresult_distance+"").substring(0,(Fresult_distance+"").indexOf(".")+2)+"km";
//
//                                // Sdistance=Fresult_distance+"km";
//                            }else{
//                                Fresult_distance=a[i];
//                                Sdistance=(Fresult_distance+"").substring(0,(Fresult_distance+"").indexOf(".")+2)+"m";
//                            }
                            //String Sdistance=Fresult_distance.toFixed();
                           // Log.e("map","距离为："+a[i]+"");


                            Log.e("yjqresult:","jsonArr长度："+jsonArr.length());
                            jsonObject = (JSONObject)jsonArr.getJSONObject(i);
                            mData.add(new Focus_Item(R.drawable.quanshui,jsonObject.getString("场馆名"),jsonObject.getString("场馆地址")));
                            Log.e("yjqresult:",i+"jsonObject："+jsonObject.toString());
                            Log.e("数据的变化",jsonObject.getString("场馆编号"));
                            Log.e("数据的变化",jsonObject.getString("场馆名"));
                            Log.e("数据的变化",jsonObject.getString("场馆地址"));
                            mData1.add(new SearchReasult(jsonObject.getString("场馆编号"),jsonObject.getString("场馆名"),
                                    jsonObject.getString("场馆地址"),"100m","￥100",jsonObject.getString("场馆负责人"),
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
                }else{

                }




            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("yjq1","失败");
                Toast.makeText(mContext, "连接超时，请查看网络连接", Toast.LENGTH_SHORT).show();
                //refreshDialog.dismiss();
                //  swipeRefreshView.setRefreshing(false);
                //  Refresh_status=Refresh_Fail;
            }
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("yjq","取消");
            }
            @Override
            public void onFinished() {
                focus_item_adapter = new Focus_Item_Adapter((LinkedList<Focus_Item>) mData, mContext);
                lv_focus_fans.setAdapter(focus_item_adapter);
                Log.e("yjq","完成");
                //完成时候运行
            }
        });

    }

}
