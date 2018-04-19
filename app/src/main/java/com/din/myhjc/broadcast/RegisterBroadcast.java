package com.din.myhjc.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

/**
 * Created by dinzhenyan on 2018/3/1.
 */

public class RegisterBroadcast {

    private BroadcastReceiver receiver;


    public void register(Context context) {
        receiver = new NetStatusBroadcast();
        //  创建过滤器
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, filter);
    }

    public void unregister(Context context) {
        if (receiver != null) {
            context.unregisterReceiver(receiver);
            receiver = null;
        }
    }
}
