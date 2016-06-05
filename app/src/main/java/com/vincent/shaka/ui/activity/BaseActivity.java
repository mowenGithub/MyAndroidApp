package com.vincent.shaka.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.vincent.shaka.R;
import com.vincent.shaka.network.NetworkCallback;
import com.vincent.shaka.network.NetworkManager;
import com.vincent.shaka.network.NetworkResult;
import com.vincent.shaka.ui.fragment.BaseFragment;
import com.vincent.shaka.util.EasyPermissions;
import com.vincent.shaka.util.LogUtils;
import com.vincent.shaka.util.ToastUtils;

import java.util.List;

public class BaseActivity extends AppCompatActivity implements NetworkCallback, BaseFragment.OnFragmentInteractionListener, EasyPermissions.PermissionCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getNetworkManager().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }
    /**
     * 通过Action启动Activity
     *
     * @param pAction
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     *
     * @param pAction
     * @param pBundle
     */
    protected void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error instanceof NoConnectionError) {
            ToastUtils.showLong("网络连接错误,服务器异常！");
        } else if (error instanceof NetworkError) {
            ToastUtils.showLong("Socket关闭，服务器宕机！");
        } else if (error instanceof ParseError) {
//                    ToastUtils.showLong("接收到的JSON异常！");
        } else if (error instanceof AuthFailureError) {
            ToastUtils.showLong("HTTP的身份验证错误！");
        } else if (error instanceof TimeoutError) {
            ToastUtils.showLong("服务器太忙超时！");
        } else if (error instanceof ServerError) {
            ToastUtils.showLong("服务器响应错误！");
        } else {
            ToastUtils.showLong("服务发生异常！");
        }
        //AuthFailureError：如果在做一个HTTP的身份验证，可能会发生这个错误。
        //NetworkError：Socket关闭，服务器宕机，DNS错误都会产生这个错误。
        //NoConnectionError：和NetworkError类似，这个是客户端没有网络连接。
        //ParseError：在使用JsonObjectRequest或JsonArrayRequest时，如果接收到的JSON是畸形，会产生异常。
        //SERVERERROR：服务器的响应的一个错误，最有可能的4xx或5xx HTTP状态代码。
        //TimeoutError：Socket超时，服务器太忙或网络延迟会产生这个异常。默认情况下，Volley的超时时间为2.5秒。如果得到这个错误可以使用RetryPolicy
    }

    @Override
    public void onResponse(NetworkResult netWorkResult) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        LogUtils.d("onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        LogUtils.d("onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied permissions and checked NEVER ASK AGAIN.
        // This will display a dialog directing them to enable the permission in app settings.
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.rationale_ask_again),
                R.string.setting, R.string.cancel, perms);
    }
}
