package com.example.goodluck.a4u.utils;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.goodluck.a4u.R;

import org.json.JSONObject;

import timber.log.Timber;

public class MsgUtils {

    public static final int TOAST_TYPE_MESSAGE = 1;
    public static final int TOAST_TYPE_INTERNAL_ERROR = 2;
    public static final int TOAST_TYPE_NO_NETWORK = 3;


    public static void logAndShowErrorMessage(Activity activity, VolleyError error) {
        try {
            String data = new String(error.networkResponse.data);
            showMessage(activity, new JSONObject(data));
        } catch (Exception e) {
            if (error.getMessage() != null && !error.getMessage().isEmpty()) {
                Timber.e(e, error.getMessage());
            } else {
                Timber.e(e, "Cannot parse error message");
                showToast(activity, TOAST_TYPE_INTERNAL_ERROR, null, ToastLength.SHORT);
            }
        }
    }

    public static void showMessage(Activity activity, JSONObject object) {
        /*
          TODO: 06/10/2017  Implement the actual fetching of individual errors
          The following is just a place holder don't get too comfortable
          */
        showToast(activity, TOAST_TYPE_MESSAGE, object.toString(), ToastLength.LONG);
    }

    public static void showToast(Activity activity, int toastType, String message, ToastLength toastLength) {
        if (activity == null) {
            Timber.e(new RuntimeException(), "Called showToast with null activity");
            return;
        }

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_custom, (ViewGroup) activity.findViewById(R.id.toast_layout_root));
        TextView textView = view.findViewById(R.id.toast_text);
        ImageView imageView = view.findViewById(R.id.toast_image);
        String str = "";
        int icon = 0;
        Toast toast = new Toast(activity);
        switch (toastLength) {
            case SHORT:
                toast.setDuration(Toast.LENGTH_SHORT);
                break;
            case LONG:
                toast.setDuration(Toast.LENGTH_LONG);
                break;
            default:
                Timber.e("Not implemented");
        }

        switch (toastType) {
            case TOAST_TYPE_MESSAGE:
                str = message;
                break;
            case TOAST_TYPE_INTERNAL_ERROR:
                str = activity.getString(R.string.internal_error);
                break;
            case TOAST_TYPE_NO_NETWORK:
                str = activity.getString(R.string.no_network_connection);
                break;
        }

        textView.setText(str);
        if (icon != 0) {
            imageView.setImageResource(icon);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

        toast.setView(view);
        toast.show();
    }

    public enum ToastLength {
        SHORT, LONG
    }
}
