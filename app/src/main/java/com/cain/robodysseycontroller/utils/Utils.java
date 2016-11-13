package com.cain.robodysseycontroller.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.cain.robodysseycontroller.R;

/**
 * Created by cain on 12/11/16.
 */

public class Utils {

    private static final String PREFERENCES_FILE = "robodyssey_settings";

    /*
    public static int getToolbarHeight(Context context){
        int height = (int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        return height;
    }

    public static int getStatusBarHeight(Context context){
        int height = (int) context.getResources().getDimension(R.dimen.statusbar_size);
        return height;
    }

    public static Drawable tintMyDrawable(Drawable drawable, int colour){
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, colour);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }
    */

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

}
