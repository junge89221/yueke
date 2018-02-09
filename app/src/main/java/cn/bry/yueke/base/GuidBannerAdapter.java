package cn.bry.yueke.base;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;

import cn.bry.yueke.util.Utils;

/**
 * <pre>
 * author：张俊
 * date： 2017/3/6
 * desc：
 * <pre>
 */

public class GuidBannerAdapter implements Holder<Integer> {
    private ImageView imageView;

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        Glide.with(Utils.getContext())
                .load(data)
                .into(imageView);
    }
}
