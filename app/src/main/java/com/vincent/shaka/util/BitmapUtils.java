package com.vincent.shaka.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import com.vincent.shaka.App;

/**
 * Created by mowen on 5/25/16.
 */
public class BitmapUtils {

    public static RoundedBitmapDrawable roundedBitmap(int imageId, int width_dp) {
        int width_px = DensityUtils.dip2px(width_dp);
        Bitmap src = BitmapFactory.decodeResource(App.getAppContext().getResources(), imageId);
        Bitmap dst;
        //将长方形图片裁剪成正方形图片
        if(src.getWidth()>=src.getHeight()) {
            if(src.getHeight() < width_px) {
                width_px = src.getHeight();
            }
            dst = Bitmap.createBitmap(src, src.getWidth() / 2 - src.getHeight() / 2, 0, width_px, width_px);
        } else {
            if(src.getWidth() < width_px) {
                width_px = src.getWidth();
            }
            dst = Bitmap.createBitmap(src, 0, src.getHeight() / 2 - src.getWidth() / 2, width_px, width_px);
        }

        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(App.getAppContext().getResources(), dst);
        roundedBitmapDrawable.setCornerRadius(dst.getWidth()/2); //设置圆角半径为正方形边长的一半
        roundedBitmapDrawable.setAntiAlias(true);
        return roundedBitmapDrawable;
    }
}
