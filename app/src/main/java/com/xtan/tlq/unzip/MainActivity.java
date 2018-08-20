package com.xtan.tlq.unzip;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xtan.tlq.unzip.config.Constants;
import com.xtan.tlq.unzip.utils.archiver.ArchiverManager;
import com.xtan.tlq.unzip.utils.archiver.IArchiverListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TextView tv_unzip;
    private Button btn_unzip;
    private String mAimPath;
    private String mChoosePath;


    //解压Dialog控件
    private Dialog mDialog;
    private TextView dialog_tv_title;
    private ProgressBar dialog_iv_progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化sd卡操作权限
        initpermission();
        //初始化控件
        initView();
        //初始化数据
        initData();
    }

    private void initpermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        }
    }

    private void initData() {
        /**
         * 选择路径
         * */

        mChoosePath = Constants.FILE_ROOT_FOLDER + "/Download/RecyclerViewDemo2.zip";
        mAimPath = Constants.FILE_UNZIP_AIM_FOLDER + "RecyclerViewDemo2";


        /**
         * 解压*/
        btn_unzip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //初始化dialog
                showInitDataDialog();
                unzipFile(mChoosePath, mAimPath);


            }
        });


    }


    /**
     * 解压文件
     */
    private void unzipFile(String zipFile, String unZipPath) {//
        ArchiverManager.getInstance().doUnArchiver(zipFile, unZipPath, "", new IArchiverListener() {
            @Override
            public void onStartArchiver() {
                dialog_tv_title.setText(getString(R.string.init_data));
            }

            @Override
            public void onProgressArchiver(int current, int total) {
                showProgress(current, total);
            }

            @Override
            public void onEndArchiver() {

                dismissDialog();

            }
        });
    }


    private void initView() {
        btn_unzip = findViewById(R.id.btn_unzip);
        tv_unzip = findViewById(R.id.tv_unzip);
        //初始化dialog
       // showInitDataDialog();
    }


    /**
     * 显示进度
     *
     * @param current
     * @param total
     */
    private void showProgress(int current, int total) {
        if (current > 0 && total > 0) {
            final float percent = current / (float) total;
            dialog_iv_progress_bar.setMax(100);
            dialog_iv_progress_bar.setProgress((int) (percent * 100));
            if (current == total) {
                dialog_tv_title.setText(getString(R.string.check_data));
            }
        } else {
            dialog_iv_progress_bar.setMax(1);
            dialog_iv_progress_bar.setProgress(0);
        }
    }


    /**
     * 初始化Dialog
     */
    public void showInitDataDialog() {
        try {
            dismissDialog();
            mDialog = new Dialog(this, R.style.Translucent_NoTitle);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setContentView(R.layout.dialog_init_data_tips);
            Window window = mDialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            window.setGravity(Gravity.CENTER);// 位置中心
            lp.x = 0; // 新位置X坐标
            lp.y = 0; // 新位置Y坐标
            lp.width = 800; // dialog宽度
            lp.height = 500; // dialog高度
            lp.alpha = 1f; // 透明度
            window.setAttributes(lp);
            mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode,
                                     KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                    return false;
                }
            });
            dialog_tv_title = mDialog.findViewById(R.id.dialog_tv_title);
            dialog_tv_title.setText(R.string.init_data_waiting);
            dialog_iv_progress_bar = mDialog.findViewById(R.id.dialog_iv_progress_bar);

              mDialog.show();
        } catch (Exception e) {
        }
    }

    /**
     * 清除Dialog
     */
    private void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

}
