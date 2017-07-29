package com.example.sikefeng.testproject.f.pickerphoto;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 图片选择器
 * use:圈子上传、活动上传
 * Created by duke on 2017/3/3.
 */

public class PhotoSelectwidget {

    private Activity context;
    private String lastImageName="";

    public String getLastImageName() {
        return lastImageName;
    }



    public static String getFolderPath() {
        return ImageUtils.FILE_DIR;
    }

    @SuppressLint("SimpleDateFormat")
    private String getNowTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
        return dateFormat.format(date);
    }

    @SuppressLint("SdCardPath")
    public File getTwoImage(Uri uri) {
        if (uri == null) {
            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
            return null;
        }
        String imageUrl = uri.getPath();
        String imageName_temp = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        imageName_temp = imageName_temp.replace(" ", "");
        // 生成大图
        save(imageUrl, 200, "big_" + imageName_temp);
//        save(imageUrl, 60, "small_" + imageName_temp);
        Log.e("imageUrl---->>>>", imageUrl);
        Log.e("imageName_temp---->>>>", imageName_temp);


        File uploadimg = new File(getFolderPath() + "big_" + imageName_temp);
        if (uploadimg.exists()) {
            return uploadimg;
        } else {
            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    @SuppressLint("SdCardPath")
    public  File getSmallImage(Uri uri) {
        if (uri == null) {
            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
            return null;
        }
        String imageUrl = uri.getPath();
        String imageName_temp = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        imageName_temp = imageName_temp.replace(" ", "");
        // 生成小图
        save(imageUrl, 60, "small_" + imageName_temp);
        Log.e("imageUrl---->>>>", imageUrl);
        Log.e("imageName_temp---->>>>", imageName_temp);
        File smallimg = new File(getFolderPath() + "small_" + imageName_temp);
        if (smallimg.exists()) {
            return smallimg;
        } else {
            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    @SuppressLint("SdCardPath")
    public File getSmallImage(Uri uri,String fileName) {
        if (uri == null) {
            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
            return null;
        }
        String imageUrl = uri.getPath();
        String imageName_temp = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        imageName_temp = imageName_temp.replace(" ", "");
        // 生成小图
        saveImage(imageUrl, 60, fileName);
        Log.e("imageUrl---->>>>", imageUrl);
        Log.e("imageName_temp---->>>>", imageName_temp);
        File smallimg = new File(getFolderPath() + fileName);
        if (smallimg.exists()) {
            return smallimg;
        } else {
            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    /**
     * 保存图片
     *
     * @param path
     * @param size
     * @param saveName
     */
    private void save(String path, int size, String saveName) {

        try {
            // File f = new File(path);
            Bitmap bm = getSmallBitmap(path);
                int degree = readPictureDegree(path);
                if (degree != 0) {// 旋转照片角度
                    bm = rotateBitmap(bm, degree);
                }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 75, baos);

            FileOutputStream fos = new FileOutputStream(new File(
                    getAlbumDir(context), saveName));

            int options = 100;
            // 如果大于80kb则再次压缩,最多压缩三次
            while (baos.toByteArray().length / 1024 > size && options > 10) {
                // 清空baos
                baos.reset();
                // 这里压缩options%，把压缩后的数据存放到baos中
                bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
                options -= 30;
            }

            fos.write(baos.toByteArray());
            fos.close();
            baos.close();

        } catch (Exception e) {

        }
    }


    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);

            if(exifInterface == null)
                return degree;
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }
    private void saveImage(String path, int size, String saveName) {

        try {
            // File f = new File(path);
            Bitmap bm = getSmallBitmap(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 75, baos);

            FileOutputStream fos = new FileOutputStream(new File(
                    getAlbumDir(context), saveName));

            int options = 100;
            // 如果大于80kb则再次压缩,最多压缩三次
            while (baos.toByteArray().length / 1024 > size && options > 10) {
                // 清空baos
                baos.reset();
                // 这里压缩options%，把压缩后的数据存放到baos中
                bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
                options -= 30;
            }

            fos.write(baos.toByteArray());
            fos.close();
            baos.close();

        } catch (Exception e) {

        }
    }
    /**
     * 获取保存图片的目录
     *
     * @return
     */
    @SuppressLint("SdCardPath")
    public static File getAlbumDir(Activity context) {
        File dir = new File(getFolderPath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    /**
     * 根据路径获得图片并压缩返回bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }
    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}
