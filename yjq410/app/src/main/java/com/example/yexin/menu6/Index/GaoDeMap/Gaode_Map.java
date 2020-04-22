package com.example.yexin.menu6.Index.GaoDeMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.example.yexin.menu6.R;
import com.example.yexin.menu6.Sideslip.Sideslip_top.location;

public class Gaode_Map extends Activity {

    AMap aMap=null;
    MapView mMapView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaode__map);
        Intent intent=getIntent();
        String address=intent.getStringExtra("address");
        String llg=intent.getStringExtra("location");

        String lltitu_string[]=llg.split(",");
        float longitude=Float.parseFloat(lltitu_string[0]);
        float latitude=Float.parseFloat(lltitu_string[1]);
        Log.e("map","金纬度为："+longitude+"，"+latitude);

        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        // //setMinZoomLevel(float zoomLevel)
        //设置最小缩放级别 缩放级别范围为[3, 20],超出范围将按最小级别计算
//        setMaxZoomLevel(float zoomLevel)
//        设置地图最大缩放级别 缩放级别范围为[3, 20],超出范围将按最大级别计算 。


        LatLng latLng = new LatLng(latitude,longitude);
        float distance = AMapUtils.calculateLineDistance(latLng, location.Getinfo());
        Log.e("map","距离为："+distance+"");
        if (aMap == null) {
            aMap = mMapView.getMap();
            Log.e("map","weikomg");
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));//缩放大小
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));//设置显示中心
            //mMapView.getMap().getNativeMapController();
            aMap.addMarker(new MarkerOptions().position(latLng).title("").snippet(address));
        }

//        mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件
//        mMapController = mMapView.getController(); // 得到mMapView
//
//        point = new GeoPoint((int) (39.982378 * 1E6), (int) (116.304923 * 1E6)); // 用给定的经纬度构造一个GeoPoint，单位是微度(度*
// 1E6)


        //自定义位置（坐标）
//        mInfoWindow = new InfoWindow(button, llText, -150);//信息描述的位置
//
//        OverlayOptions option1 =  new MarkerOptions()
//                .position(llText)
//                .icon(bitmap);
//        // options.add(option1);
//        // mBaiduMap.addOverlays(option1);//添加多个点  用列表List<OverlayOptions> options = new ArrayList<OverlayOptions>();
//        mBaiduMap.addOverlay(option1);
        //mMapView.getMap().addMarker(new MarkerOptions().position(latLng).title("南昌").snippet("东华理工大学南区食堂三楼"));

      //  LatLng position = new AMap.LngLat(116, 39);  // 标准写法
// 简写 var position = [116, 39];
       // mMapView

        mMapView.onCreate(savedInstanceState);





    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}