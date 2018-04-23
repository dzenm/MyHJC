package com.din.myhjc.utils;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by dinzhenyan on 2018/4/21.
 */

public class LocationUtils {

    private String city = null, direction = null;

    //  开始定位
    public void startLocation(Activity activity, TextView location) {
        //  动态申请定位权限
        PermissionUtils.verifyPermissions(activity, new int[]{6, 7});

        AMapLocationClient client = new AMapLocationClient(activity.getApplicationContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        option.setNeedAddress(true);
        option.setMockEnable(true);
        option.setHttpTimeOut(10000);
        option.setLocationCacheEnable(false);
        if (client != null) {
            client.setLocationOption(option);
            client.setLocationListener(setListener(location));
            client.startLocation();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                client.onDestroy();
            }
        }, 5000);
    }

    //  定位监听事件
    public AMapLocationListener setListener(TextView location) {
        AMapLocationListener listener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        city = aMapLocation.getCity();
                        direction = aMapLocation.getDistrict();
                        location.setText(direction);
                        for (int i = 0; i < city.length(); i++) {
                            if (city.substring(i, i + 1).equals("市")) {
                                city = city.substring(0, i);
//                                url = "https://api.seniverse.com/v3/weather/now.json?key=zbzp8471lvyfrz1e&location=" + city + "&language=zh-Hans&unit=c";
//                                showWeather();
                            }
                        }
                    } else {
                        direction = "定位失败\n" + aMapLocation.getErrorInfo();
                        location.setText(direction);
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        };
        return listener;
    }

    //  获取天气
//    private void showWeather(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder().url(url).build();
//                    Response response = client.newCall(request).execute();
//                    String result = response.body().string();
//                    getWeather(result);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    private void getWeather(final String data){
//             try {
//                    //  将所有数据放在一个jsonObject中
//                    JSONObject jsonObject = new JSONObject(data);
//                    //  取出为results的key
//                    String results = jsonObject.getString("results");
//                    //  该key对应的值是一个数组,因此使用数组存储
//                    JSONArray jsonArray = new JSONArray(results);
//                    //  取出json数组中json对象为0的下标
//                    JSONObject second = jsonArray.getJSONObject(0);
//                    //  取出key为now的值
//                    JSONObject now = second.getJSONObject("now");
//                    //  取出key为text的值
//                    String text = now.getString("text");
//                    bind.weather.setText(text);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//            }
//        });
//
}
