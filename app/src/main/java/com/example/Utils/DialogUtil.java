package com.example.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.example.fravell.R;

public class DialogUtil {

    private static Dialog simpleProgressDialog = null;
    public static void showSimpleProgressDialog(Context context) {
        if (simpleProgressDialog != null) {
            closeProgressDialog();
        }

        if (context != null) {
            simpleProgressDialog = new Dialog(context);
            simpleProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            simpleProgressDialog.setContentView(R.layout.dialog_progress_simple);
            setDialogOpacity(simpleProgressDialog, Color.WHITE, 0);
            simpleProgressDialog.setCancelable(false);
            simpleProgressDialog.show();
        }
    }

    public static void closeProgressDialog() {

        if (simpleProgressDialog != null) {
            try {
                simpleProgressDialog.cancel();
                simpleProgressDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isProgressShowing() {
        return (simpleProgressDialog != null && simpleProgressDialog.isShowing());
    }

    public static void setDialogOpacity(Dialog dialog, int bgColor, int alpha) {
        ColorDrawable bgDrawable = new ColorDrawable(bgColor);
        bgDrawable.setAlpha(alpha);
        dialog.getWindow().setBackgroundDrawable(bgDrawable);
    }

}
