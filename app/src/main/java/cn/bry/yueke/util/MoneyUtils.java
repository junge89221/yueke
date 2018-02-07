package cn.bry.yueke.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/8/11
 *     desc  : 文件相关工具类
 * </pre>
 */
public class MoneyUtils {

    private MoneyUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 根据传入
     * @param  money 文件路径
     * @return 转化后的金额 保留两位小数
     */
    public static String getMoney(float money) {
        String format = new DecimalFormat(".00").format(money);
        if(format.startsWith(".")) format="0"+format;
        return format;
    }

    public static String getMoney(double money) {
        String format = new DecimalFormat(".00").format(money);
        if(format.startsWith(".")) format="0"+format;
        return format;
    }

    /**
     * 限制输入小数点后两位
     */
    public static void keppTwoDecimals(EditText editText){
        if (editText == null) return;
        editText.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if((source.equals(".") || source.equals("0")) && dest.toString().length() == 0){
                    return "";
                }
                if(dest.toString().contains(".")){
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index).length();
                    if(mlength == 3){
                        return "";
                    }
                }
                return null;
            }
        }});
    }




    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static double mul(double v1, int v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
}