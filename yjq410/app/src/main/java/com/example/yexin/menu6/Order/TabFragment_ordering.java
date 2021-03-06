package com.example.yexin.menu6.Order;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yexin.menu6.Common.Refresh.RefreshDialog;
import com.example.yexin.menu6.Common.Url.Web_url;
import com.example.yexin.menu6.Order.Order;
import com.example.yexin.menu6.Index.SearchReasult;
import com.example.yexin.menu6.Index.fragmentone_stadiums_adapter;
import com.example.yexin.menu6.R;
import com.example.yexin.menu6.Sideslip.Sideslip_center.Setting.Account;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 23646 on 2019/10/31.
 */

public class TabFragment_ordering extends Fragment{

    private List<Order> mData = null;

    private JSONArray jsonArr;
    private JSONObject jsonObject=null;


    private RefreshDialog refreshDialog;
    private int Refresh_status=2;
    private final int Refresh_Success=1;
    private final int Refresh_Fail=0;

    private SwipeRefreshLayout swipeRefreshView;

    public static Fragment newInstance() {
        TabFragment_ordering fragment = new TabFragment_ordering();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        swipeRefreshView=(SwipeRefreshLayout)rootView.findViewById(R.id.gg_srfl);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        mData = new LinkedList<Order>();
        refreshContent(recyclerView);
        pulldownRefresh(recyclerView);
       // recyclerView.setAdapter(new RecyclerAdapter((LinkedList<Order>)mData,getActivity()));

        return rootView;
    }

    private void pulldownRefresh(final RecyclerView recyclerView) {
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
                            InitData(recyclerView);
                        } catch (Exception e) {
                        }
                       // Toast.makeText(getContext(), "刷新了一条数据", Toast.LENGTH_SHORT).show();

                        // 加载完数据设置为不刷新状态，将下拉进度收起来

                    }
                }, 1200);
            }

        });
    }
    private void refreshContent(final RecyclerView recyclerView) {
        refreshDialog = new RefreshDialog(getContext(),"正在加载...",R.mipmap.ic_dialog_loading);
        refreshDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InitData(recyclerView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((Activity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //在之中可以终止线程
                    }
                });
            }
        }).start();
    }
    public void InitData(final RecyclerView recyclerView){

        String Account="18879942330";
        RequestParams params = new RequestParams(Web_url.URL_GetReserveOrder);
        params.addHeader("Content-Type", "application/json-rpc"); //设置请求头部
        params.setAsJsonContent(true);//设置为json内容(这句个本人感觉不加也没有影响)
        try {
            jsonObject=new JSONObject();
            jsonObject.put("content",Account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.setBodyContent(jsonObject.toString());//添加json内容到请求参数里
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("yjq","网络请求成功order: "+result);  //接收JSON的字符串
//               HashMap<String,String> map=Web_Json.getJson(result);
                //HashMap<String,String> map=Web_Json.getJson(result);
                Log.e("yjqresult:",result.toString());
                Log.e("yjqresult:",result.length()+"");
                try{
                    //int jsonSize = result.length();//获取数据组的长度
                    jsonArr=new JSONArray(result);
                    if(jsonArr.length()>0){
                        mData=null;
                        mData = new LinkedList<Order>();
                    }
                    for(int i=0;i<jsonArr.length();i++){

                        jsonObject = (JSONObject)jsonArr.getJSONObject(i);
                        Log.e("yjqresult:",i+":"+jsonObject.toString());
                        if(jsonObject.getString("pay").equals("0")) {
                            mData.add( new Order(jsonObject.getString("id"),jsonObject.getString("appointmenttime"),
                                    jsonObject.getString("type"),jsonObject.getString("site"),
                                    jsonObject.getString("time"),jsonObject.getString("place"),
                                    jsonObject.getString("money"),jsonObject.getString("pay")));
                        }
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
                Toast.makeText(getContext(), "连接超时，请查看网络连接", Toast.LENGTH_SHORT).show();
                swipeRefreshView.setRefreshing(false);

                swipeRefreshView.setRefreshing(false);
                refreshDialog.dismiss();
            }
            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("yjq","取消");
            }
            @Override
            public void onFinished() {
                Log.e("yjq","完成");
                refreshDialog.dismiss();

                recyclerView.setAdapter(new RecyclerAdapter((LinkedList<Order>)mData,getActivity()));
                swipeRefreshView.setRefreshing(false);
                //完成时候运行
            }
        });
    }
}
