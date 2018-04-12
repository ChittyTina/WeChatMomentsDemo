package com.chitty.wechatmomentsdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

import com.chitty.wechatmomentsdemo.R;

/**
 * Created by chitty on 2018/4/12.
 */

public class Tools {

    /**
     * 判断网络是否连接
     * <p>需添加权限 android.permission.ACCESS_NETWORK_STATE</p>
     *
     * @param context 上下文
     * @return true: 是<br>false: 否
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isConnected();
    }

    /**
     * 获取活动网络信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * 判断网络是否可用
     * <p>需添加权限 android.permission.ACCESS_NETWORK_STATE</p>
     */
    public static boolean isAvailable(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isAvailable();
    }

    /**
     * 检查有无网
     * @param mContext 上下文
     * @return true - 有网，false - 无网
     */
    public static boolean checkConnectStatus(Context mContext) {
        if ( !Tools.isAvailable(mContext) ){
            myToast(mContext,mContext.getResources().getString(R.string.network_unable));
            return false;
        }else {
            if ( !Tools.isConnected(mContext) ){
                myToast(mContext,mContext.getResources().getString(R.string.network_unable));
                return false;
            }else {
                return true;
            }
        }
    }

    public static void myToast(Context mContext,String prompt) {
        Toast toast = Toast.makeText(mContext, prompt, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
