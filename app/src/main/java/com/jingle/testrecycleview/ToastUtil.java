package com.jingle.testrecycleview;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/7/18.
 */

public class ToastUtil {
    private static Toast toast;

    public static void showToast(Context contex, String message){
       if (null == toast){
          toast = Toast.makeText(contex,message, Toast.LENGTH_SHORT);
       }
       else{
           toast.setText(message);
       }
       toast.show();
    }
}
