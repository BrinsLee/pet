//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.brins.lib_appbase.manager;

import android.app.Activity;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.NonNull;

import com.brins.lib_appbase.utils.UIUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class ActivityManager {
    private static volatile ActivityManager sInstance;
    private Stack<Activity> mActivityStack = new Stack();

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        if (sInstance == null) {
            Class var0 = ActivityManager.class;
            synchronized(ActivityManager.class) {
                if (sInstance == null) {
                    sInstance = new ActivityManager();
                }
            }
        }

        return sInstance;
    }

    public void startActivity(Class clazz) {
        this.startActivity(clazz, (Bundle)null);
    }

    public void startActivity(Class clazz, Bundle bundle) {
        if (this.currentActivity() != null) {
            Intent intent = new Intent(this.currentActivity(), clazz);
            if (bundle != null) {
                intent.putExtras(bundle);
            }

            this.currentActivity().startActivity(intent);
        }
    }

    public void addActivity(Activity activity) {
        if (activity != null && this.mActivityStack != null) {
            this.mActivityStack.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (this.mActivityStack != null && activity != null) {
            this.mActivityStack.remove(activity);
        }

    }

    public Activity currentActivity() {
        Activity activity = null;
        if (this.mActivityStack == null) {
            return activity;
        } else {
            try {
                if (this.mActivityStack.size() > 0) {
                    activity = (Activity)this.mActivityStack.lastElement();
                }
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return activity;
        }
    }

    public Activity preActivity() {
        Activity activity = null;
        if (this.mActivityStack == null) {
            return activity;
        } else {
            try {
                if (this.mActivityStack.size() > 1) {
                    activity = (Activity)this.mActivityStack.get(this.mActivityStack.size() - 2);
                }
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            return activity;
        }
    }

    public boolean isCurrent(Activity activity) {
        if (activity != null && this.currentActivity() != null) {
            return activity == this.currentActivity();
        } else {
            return false;
        }
    }

    public boolean isCurrent(Class<?> clazz) {
        if (clazz != null && this.currentActivity() != null) {
            return this.currentActivity().getClass().equals(clazz);
        } else {
            return false;
        }
    }

    public boolean hasActivity(String clazzStr) {
        try {
            Class<?> cls = Class.forName(clazzStr);
            return this.hasActivity(cls);
        } catch (Exception var3) {
            return false;
        }
    }

    public boolean hasActivity(Class clazz) {
        if (this.mActivityStack != null && clazz != null) {
            for(int i = 0; i < this.activityCounts(); ++i) {
                Activity activity = (Activity)this.mActivityStack.get(i);
                if (activity.getClass().equals(clazz)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public boolean hasActivity(Activity activity) {
        if (this.mActivityStack != null && activity != null) {
            for(int i = 0; i < this.activityCounts(); ++i) {
                Activity activitys = (Activity)this.mActivityStack.get(i);
                if (activitys == activity) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public void finishActivity() {
        if (this.mActivityStack != null) {
            try {
                Activity activity = (Activity)this.mActivityStack.lastElement();
                this.finishActivity(activity);
            } catch (Exception var2) {
                var2.printStackTrace();
            }

        }
    }

    public void finishActivity(final Activity activity) {
        if (this.mActivityStack != null && activity != null) {
            try {
                if (activity != null) {
                    if (this.mActivityStack.size() == 1) {
                        activity.getWindow().getDecorView().postDelayed(new Runnable() {
                            public void run() {
                                ActivityManager.this.mActivityStack.remove(activity);
                                activity.finish();
                            }
                        }, 500L);
                    } else {
                        this.mActivityStack.remove(activity);
                        activity.finish();
                    }
                }
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }
    }

    public void finishActivity(Class<?> clazz) {
        if (this.mActivityStack != null && clazz != null) {
            try {
                Iterator var2 = this.mActivityStack.iterator();

                while(var2.hasNext()) {
                    Activity activity = (Activity)var2.next();
                    if (activity.getClass().equals(clazz)) {
                        this.finishActivity(activity);
                    }
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }

        }
    }

    public void finishAllActivityExceptOne(Class clazz) {
        if (this.mActivityStack != null && clazz != null) {
            for(int i = 0; i < this.activityCounts(); ++i) {
                Activity activity = (Activity)this.mActivityStack.get(i);
                if (!activity.getClass().equals(clazz)) {
                    this.finishActivity(activity);
                }
            }

        }
    }

    public void finishUtilThisActivity(Class clazz) {
        int length = this.activityCounts();
        int i;
        if (this.mActivityStack != null && clazz != null) {
            for(i = 0; i < length; ++i) {
                Activity activity = (Activity)this.mActivityStack.lastElement();
                if (activity.getClass().equals(clazz)) {
                    break;
                }

                this.finishActivity(activity);
            }

        } else {
            for(i = 0; i < length - 1; ++i) {
                this.finishActivity((Activity)this.mActivityStack.lastElement());
            }

        }
    }

    public void finishAllActivityExceptCurrent() {
        Activity currentActivity = this.currentActivity();
        if (currentActivity != null) {
            this.finishAllActivityExceptOne(currentActivity.getClass());
        }

    }

    public void finishAllActivity() {
        if (this.mActivityStack != null) {
            try {
                for(int i = 0; i < this.mActivityStack.size(); ++i) {
                    if (null != this.mActivityStack.get(i)) {
                        ((Activity)this.mActivityStack.get(i)).finish();
                    }
                }

                this.mActivityStack.clear();
            } catch (Exception var2) {
                var2.printStackTrace();
            }

        }
    }

    public Activity getActivity(Class<?> clazz) {
        if (this.mActivityStack != null && clazz != null) {
            try {
                Iterator var2 = this.mActivityStack.iterator();

                while(var2.hasNext()) {
                    Activity activity = (Activity)var2.next();
                    if (activity.getClass().equals(clazz)) {
                        return activity;
                    }
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return null;
        } else {
            return null;
        }
    }

    public Activity getActivity(String name) {
        if (this.mActivityStack != null && name != null) {
            try {
                Iterator var2 = this.mActivityStack.iterator();

                while(var2.hasNext()) {
                    Activity activity = (Activity)var2.next();
                    if (activity.getClass().getSimpleName().equals(name)) {
                        return activity;
                    }
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return null;
        } else {
            return null;
        }
    }

    public int activityCounts() {
        int counts = 0;
        if (this.mActivityStack != null && this.mActivityStack.size() > 0) {
            counts = this.mActivityStack.size();
        }

        return counts;
    }

    public void exit() {
        try {
            if (this.currentActivity() != null) {
                this.finishAllActivity();
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public boolean isForeground(Context context, String className) {
        if (context != null && !TextUtils.isEmpty(className)) {
            android.app.ActivityManager am = (android.app.ActivityManager)context.getSystemService("activity");
            List<RunningTaskInfo> list = am.getRunningTasks(1);
            if (list != null && list.size() > 0) {
                ComponentName cpn = ((RunningTaskInfo)list.get(0)).topActivity;
                if (className.contains(cpn.getClassName())) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public String getLauncherActivity(@NonNull String pkg) {
        if (UIUtils.isSpace(pkg)) {
            return "";
        } else {
            Intent intent = new Intent("android.intent.action.MAIN", (Uri)null);
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(pkg);
            PackageManager pm = UIUtils.getContext().getPackageManager();
            List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
            return info != null && info.size() != 0 ? ((ResolveInfo)info.get(0)).activityInfo.name : "";
        }
    }

    public boolean isActivityNotFinish(Activity activity) {
        return activity != null && !activity.isFinishing() && !activity.isDestroyed();
    }
}
