package com.smartalk.gank;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 显示圆角图片
 * Created by panl on 15/12/21.
 */
public class GlideRoundTransform extends BitmapTransformation {
    private float radius;
    public GlideRoundTransform(Context context,float radius) {
        super(context);
        this.radius = Resources.getSystem().getDisplayMetrics().density * radius;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap result = pool.get(toTransform.getWidth(),toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0,0,toTransform.getWidth(),toTransform.getHeight());
        canvas.drawRoundRect(rectF,radius,radius,paint);
        return result;
    }


    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}
