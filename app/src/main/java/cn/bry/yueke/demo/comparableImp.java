package cn.bry.yueke.demo;

/**
 * <pre>
 * author：张俊
 * date： 2018/2/26
 * desc：
 * <pre>
 */

public class comparableImp {
    public   <T extends Comparable<T>> int countGreaterThan(T[] anArray, T elem) {
        int count = 0;
        for (T e : anArray)
            if (e.compareTo(elem) > 0)
                ++count;
        return count;
    }

   public void Test(){
       testBean[] tt = {};
        countGreaterThan(tt,new testBean());
   }

   public  class  testBean implements Comparable<testBean> {
        private testBean text1;
       @Override
       public int compareTo(testBean o) {
           return o.compareTo(text1);
       }
   }
}
