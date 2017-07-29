package com.example.sikefeng.testproject.f.pickerphoto.clipimagedemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.sikefeng.testproject.R;
import com.example.sikefeng.testproject.f.pickerphoto.CustomProgressDialog;
import com.example.sikefeng.testproject.f.pickerphoto.PhotoSelectwidget;

import static com.example.sikefeng.testproject.f.pickerphoto.ImageUtils.FILE_DIR;

/**
 * 裁剪图片的Activity
 * 
 * @ClassName: CropImageActivity
 * @Description:
 * @author xiechengfa2000@163.com
 * @date 2015-5-8 下午3:39:22
 */
public class ClipImageActivity extends Activity implements OnClickListener {
	public static final String RESULT_PATH = "crop_image";
	private static final String KEY = "path";
	private ClipImageLayout mClipImageLayout = null;
	private ImageButton btn_back,btn_sure;

	public static void startActivity(Activity activity, String path, int code) {
		Intent intent = new Intent(activity, ClipImageActivity.class);
		intent.putExtra(KEY, path);
		activity.startActivityForResult(intent, code);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crop_image_layout);

		mClipImageLayout = (ClipImageLayout) findViewById(R.id.clipImageLayout);
		String path = getIntent().getStringExtra(KEY);

		// 有的系统返回的图片是旋转了，有的没有旋转，所以处理
		int degreee = readBitmapDegree(path);
		Bitmap bitmap = createBitmap(path);
		if (bitmap != null) {
			if (degreee == 0) {
				mClipImageLayout.setImageBitmap(bitmap);
			} else {
				mClipImageLayout.setImageBitmap(rotateBitmap(degreee, bitmap));
			}
		} else {
			finish();
		}
		findViewById(R.id.btn_back).setOnClickListener(this);
		findViewById(R.id.btn_sure).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btn_sure) {
			new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = mClipImageLayout.clip();
                    String time = new SimpleDateFormat("yyMMddhhmmss").format(System.currentTimeMillis()) + ".JPG";
                    String path = FILE_DIR + time;
                    saveBitmap(bitmap, path);
                    Message message=new Message();
                    message.what=1;
                    message.obj=path;
                    handler.sendMessage(message);
                }
            }).start();
		}
	}
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
				//文字即为显示的内容
				Dialog mDialog = CustomProgressDialog.createLoadingDialog(ClipImageActivity.this, "");
				mDialog.setCancelable(true);//允许返回
				mDialog.show();//显示
				try {
					String path= (String) msg.obj;
					PhotoSelectwidget photoSelectwidge=new PhotoSelectwidget();
					File file=photoSelectwidge.getSmallImage(Uri.fromFile(new File(path)));
					Intent intent = new Intent();
					intent.putExtra(RESULT_PATH, file.getAbsolutePath());
					setResult(RESULT_OK, intent);
				}finally {
					mDialog.cancel();
					ClipImageActivity.this.finish();
				}
            }
        }
    };
	private void saveBitmap(Bitmap bitmap, String path) {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}

		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (fOut != null)
					fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建图片
	 * 
	 * @param path
	 * @return
	 */
	private Bitmap createBitmap(String path) {
		if (path == null) {
			return null;
		}

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 1;
		opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		opts.inDither = false;
		opts.inPurgeable = true;
		FileInputStream is = null;
		Bitmap bitmap = null;
		try {
			is = new FileInputStream(path);
			bitmap = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return bitmap;
	}

	// 读取图像的旋转度
	private int readBitmapDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
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

	// 旋转图片
	private Bitmap rotateBitmap(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, false);
		return resizedBitmap;
	}
	private void  test(){
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
		dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
		dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
		dialog.setIcon(R.mipmap.ic_launcher);//
		// 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
		dialog.setTitle("提示");

		/**设置透明度*/
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.0f;// 透明度
		lp.dimAmount = 0.8f;// 黑暗度
		window.setAttributes(lp);
		//设置可点击的按钮，最多有三个(默认情况下)
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		dialog.show();
	}
}
