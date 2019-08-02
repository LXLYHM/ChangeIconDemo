package com.keytop.changeicon;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private PackageManager mPm;
    private ComponentName mDefault;
    private ComponentName mAfter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDefault = getComponentName();
        mAfter = new ComponentName(
                getBaseContext(),
                "com.keytop.changeicon.Main2Activity");
        mPm = getApplicationContext().getPackageManager();
    }

    /**
     * 替换图标名称
     */
    public void btnChange(View v){
        changeIcon65(mDefault, mAfter);
    }

    /**
     * 换回默认图标名称
     */
    public void btnBack(View v){
        changeIcon65(mAfter, mDefault);
    }

    /**
     * 隐藏桌面图标名称
     * todo 记得测试前先安装hiddenapp
     */
    public void btnHidden(View v){
        Intent intent = new Intent();
        ComponentName cn = new ComponentName("com.keytop.hiddenapp",//即将启动app的包名
                "com.keytop.hiddenapp.MainActivity");//即将启动app的隐式意图 scheme + "." + host
        intent.setComponent(cn);
        Uri uri = Uri.parse("com.keytop.hiddenapp.MainActivity");//即将启动app的隐式意图scheme + "." + host
        intent.setData(uri);
        startActivity(intent);
    }

    public void changeIcon65(ComponentName disable, ComponentName enable) {
        disableComponent(this, disable);
        enableComponent(this, enable);
    }

    /**
     * 启用组件 *
     * @param componentName
     * 重要方法
     */
    private void enableComponent(Activity activity, ComponentName componentName) {
        PackageManager pm = activity.getPackageManager();
        int state = pm.getComponentEnabledSetting(componentName);
        if (PackageManager.COMPONENT_ENABLED_STATE_ENABLED == state) {
            //已经启用
            return;
        }
        pm.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用组件 *
     * @param componentName
     * 重要方法
     */
    private void disableComponent(Activity activity, ComponentName componentName) {
        PackageManager pm = activity.getPackageManager();
        int state = pm.getComponentEnabledSetting(componentName);
        if (PackageManager.COMPONENT_ENABLED_STATE_DISABLED == state) {
            //已经禁用
            return;
        }
        pm.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
