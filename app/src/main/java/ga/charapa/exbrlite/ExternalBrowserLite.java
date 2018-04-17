package ga.charapa.exbrlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static android.content.Intent.ACTION_VIEW;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class ExternalBrowserLite implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.facebook.lite")) return;

        findAndHookMethod(
            "com.facebook.browser.lite.BrowserLiteActivity", lpparam.classLoader, "onCreate",
            Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Activity obj = (Activity) param.thisObject;
                    obj.startActivity(new Intent(ACTION_VIEW, obj.getIntent().getData()));
                    obj.finish();
                }
            }
        );
    }
}
