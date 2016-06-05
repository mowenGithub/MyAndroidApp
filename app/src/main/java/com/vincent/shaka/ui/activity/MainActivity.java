package com.vincent.shaka.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.vincent.shaka.R;
import com.vincent.shaka.model.ServerMap;
import com.vincent.shaka.model.param.TestParam;
import com.vincent.shaka.model.response.GithubResponse;
import com.vincent.shaka.model.response.TestResponse;
import com.vincent.shaka.network.NetworkManager;
import com.vincent.shaka.network.NetworkResult;
import com.vincent.shaka.util.ToastUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.imge_test);
        Picasso.with(this).load("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1402/12/c1/31189058_1392186616852.jpg").into(imageView);
    }

    @Override
    public void onResponse(NetworkResult netWorkResult) {
        switch (netWorkResult.getRequestKey()) {
            case TEST_HTTP:
                TestResponse testResponse = (TestResponse) netWorkResult.getResponse();
                ToastUtils.showLong(testResponse.weatherinfo.city);
                break;
            case TEST_HTTPS_GITHUB:
                GithubResponse githubResponse = (GithubResponse) netWorkResult.getResponse();
                ToastUtils.showLong(githubResponse.home);
                break;
//            case TEST_HTTPS:
//                break;
        }
    }

    public void netTestGet(View v) {
        NetworkManager.getNetworkManager().addGetRequest(ServerMap.TEST_HTTPS_GITHUB, null, this);
    }

    public void netTestPost(View v) {
        NetworkManager.getNetworkManager().addPostRequest(ServerMap.TEST_HTTP, null, this);
    }

    public void onCrash(View v) {
        Integer integer = null;
        integer.byteValue();
    }

    public void toOtherActivity(View v) {
        openActivity(HybridActivity.class);
    }
    public void toHomeActivity(View v) {
//        openActivity(HomeActivity.class);
    }
}
