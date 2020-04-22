package com.example.yexin.menu6.Sideslip.Sideslip_top;


import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;

/**
 * Created by Administrator on 2020/3/31.
 */

public class location {

    public static LatLng info;
    public static AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public static AMapLocationClientOption mLocationOption = null;
    public static String info_city;


    public static void getLocation(Context context) {
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //获取一次定位结果：
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    //声明定位回调监听器
    private static AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    if (amapLocation.getCity() != null && !amapLocation.getCity().equals("")) {
                        Log.e("location",amapLocation.toString());
                        info_city=amapLocation.getCity();
                        Log.e("location1",amapLocation.getAddress()+"经纬度："+amapLocation.getLatitude()+","+amapLocation.getLongitude());
                       // info=amapLocation.getLatitude()+","+amapLocation.getLongitude();
                        LatLng latLng=new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
                        info=latLng;


                    } else {
                        Log.e("location1","失败");
                        //mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    }
                    mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                } else {
                    Log.e("location1","高德地图失败");
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("高德地图", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }

    };
    public static LatLng Getinfo(){

        Log.e("latlng","经纬度为："+info.toString());
        return info;
    }
    public static String Getinfo_city(){

        Log.e("latlng","市区为："+info_city);
        return info_city;
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mLocationClient != null) {
//            //销毁定位客户端之后，若要重新开启定位请重新New一个AMapLocationClient对象。
//            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
//        }
//    }

    public static float Getdistance(LatLng l1, LatLng l2){
        float distance = AMapUtils.calculateLineDistance(l1, l2);
        Log.e("latlng","经纬度为："+info.toString());
        return distance;
    }
}
