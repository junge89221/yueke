package cn.bry.yueke.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
@SuppressWarnings("unused")
public class AppManager {

	private static final String TAG = "AppManager";
	
	private static Stack<Activity> activityStack;
	private static AppManager instance;
	
	private AppManager(){}
	/**
	 * 单一实例
	 */
	public static AppManager getAppManager(){
		if(instance==null){
			instance=new AppManager();
		}
		return instance;
	}
	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity){
		if (activity == null) return;
		Log.e(TAG,"--add activity stack--:"+activity.getClass().getSimpleName());
		if(activityStack==null){
			activityStack=new Stack<>();
		}
		activityStack.add(activity);

		for (Activity a : activityStack) {
			Log.e(TAG,"["+a.getClass().getSimpleName()+"]");
		}
	}
	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity(){
		return activityStack.lastElement();
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity(){
		Activity activity=activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity){
		if(activity != null){
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}

	/*删除包含指定开头的Activity*/
	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivityStartsWith(String str){

		for (int i = activityStack.size() - 1; i >= 0; i--) {
			Activity activity = activityStack.get(i);
			if (null != activity &&activity.getClass().getSimpleName().startsWith(str)) {
				activityStack.remove(i);
				activity.finish();
				activity=null;
 			}
		}
	}


	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity(){
		int size = activityStack.size();
		for (int i = size - 1; i >= 0; i--) {
            if (null != activityStack.get(i)) {
            	activityStack.get(i).finish();
            }
	    }

		activityStack.clear();
	}

	public void finishNotSpecifiedActivity(Class<?> cls){
		int size = activityStack.size();
		for (int i = size - 1; i >= 0; i--) {
			if (null != activityStack.get(i)&& activityStack.get(i).getClass()!=cls){
				activityStack.get(i).finish();
				activityStack.remove(i);
			}
		}
	}

	/**
	 * 判断指定activity是否存在
     */
	public boolean isExistSpecifiedActivity(Class<?> cls) {
		for (int i = 0, size = activityStack.size(); i < size; i++){
			if (null != activityStack.get(i) && activityStack.get(i).getClass() == cls){
				return true;
			}
		}

		return false;
	}

	/**
	 * 退出应用程序
	 */
	@SuppressWarnings("deprecation")
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

}