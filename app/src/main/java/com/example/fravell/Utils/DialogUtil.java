package com.example.fravell.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.TextView;

import com.example.fravell.R;
import com.example.fravell.databinding.DialogProgressWithTextBinding;

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

    public static void showSimpleProgressWithTextDialog(Context context, String message) {
        if (simpleProgressDialog != null) {
            closeProgressDialog();
        }
        if (context != null) {
            DialogProgressWithTextBinding binding = DialogProgressWithTextBinding.inflate(LayoutInflater.from(context));
            if (message != null) {
                binding.tvText.setText(message);
            }

            simpleProgressDialog = new Dialog(context);
            simpleProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            simpleProgressDialog.setContentView(binding.getRoot());
            setDialogOpacity(simpleProgressDialog, Color.WHITE, 0);
            simpleProgressDialog.setCancelable(false);
            simpleProgressDialog.show();
        }
    }

    public static void setTextForTextDialog(String message) {
        if (simpleProgressDialog != null) {
            TextView textView = simpleProgressDialog.findViewById(R.id.tvText);
            if (textView != null) {
                textView.setText(message);
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
