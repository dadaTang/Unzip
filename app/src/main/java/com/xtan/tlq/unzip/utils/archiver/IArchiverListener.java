package com.xtan.tlq.unzip.utils.archiver;


public interface IArchiverListener {

    void onStartArchiver();

    void onProgressArchiver(int current, int total);

    void onEndArchiver();
}
