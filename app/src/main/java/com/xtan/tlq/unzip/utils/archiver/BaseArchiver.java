package com.xtan.tlq.unzip.utils.archiver;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.io.File;

public abstract class BaseArchiver {

    private static final String TAG = "BaseArchiver";


    @SuppressLint("HandlerLeak")
    protected Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    /**
     * 压缩文件
     * @param files
     * @param destpath
     */
    public abstract void doArchiver(File[] files, String destpath);

    /**
     * 解压文件
     * @param srcfile
     * @param unrarPath
     */
    public abstract void doUnArchiver(String srcfile, String unrarPath, String password, IArchiverListener listener);
}
