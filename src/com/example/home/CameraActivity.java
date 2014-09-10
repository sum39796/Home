package com.example.home;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class CameraActivity extends Activity {
	//メールが送られたかどうか false->送られてない　true->送られた
	private boolean isSending = false;
	
	private static final String PREF_KEY = "preferenceHome"; 
	private static final String KEY_TEXT = "addres";
	
	SharedPreferences pref;  
	SharedPreferences.Editor editor; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 画面をフルスクリーンに設定
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// カメラ用ビューの設定
		setContentView(new CameraView(this));
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		if(isSending){
			finish();
		}
	}
	
	public void sendCameraImage(String path,String name){
		Log.d("CameraAcivity","sendCameraImage");
		isSending = true;
		File sendFile = new File(path+name); 
		
		pref = getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE); 
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		String address = (pref.getString(KEY_TEXT, ""));
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] {address});
		intent.putExtra(Intent.EXTRA_SUBJECT, "今日の私です");
		intent.putExtra(Intent.EXTRA_TEXT, "今日も元気です。");
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(sendFile)); 
		startActivity(intent);
	}
	
	public class CameraView extends SurfaceView implements SurfaceHolder.Callback, PictureCallback {
		private SurfaceHolder holder = null;
		private Camera camera = null;
		private static final String SDCARD_FOLDER = "/sdcard/CameraHome/";
		
		PictureCallback callBackJpeg;
		
		public CameraView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			holder = getHolder();
			holder.addCallback(this);
			holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			// 保存用フォルダ作成
			File dirs = new File(SDCARD_FOLDER);
			if(!dirs.exists()) {
				dirs.mkdir();
			}
			callBackJpeg = this;
		}
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd_kkmmss");
			String datName = "P" + date.format(new Date()) + ".jpg";
			try {
				// データ保存
				savePhotoData(datName, data);
				sendCameraImage(SDCARD_FOLDER, datName);
			} catch (Exception e) {
				if(camera != null) {
					camera.release();
					camera = null;
				}
			}
			// プレビュー再開
			camera.startPreview();
		}
		
		
		
		private void savePhotoData(String datName, byte[] data) throws Exception {
			// TODO Auto-generated method stub
			FileOutputStream outStream = null;
			
			try {
				outStream = new FileOutputStream(SDCARD_FOLDER + datName);
				outStream.write(data);
				outStream.close();
			} catch (Exception e) {
				if(outStream != null) {
					outStream.close();
				}
				throw e;
			} 
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN) {
				camera.takePicture(null, null, callBackJpeg);
			}
			return true;
		}
		
		@SuppressLint("NewApi")
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			if(camera != null) {
				return;
			}
			camera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
			Camera.Parameters parameters = camera.getParameters();  
			List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
			Camera.Size cs = sizes.get(0);
			parameters.setPreviewSize(cs.width, cs.height);
			try {
				camera.setParameters(parameters);
				camera.setPreviewDisplay(holder);
				camera.startPreview();
			} catch (IOException e) {
			}
		}
		
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			try{
				camera.stopPreview();
			}catch(Exception e){
			}
			try{
				camera.setPreviewDisplay(holder);
				Camera.Parameters params = camera.getParameters();
				params.setPreviewSize(width, height);
				camera.setParameters(params);
				Log.v("Camera", "format=" + format + ", width=" + width + ", height=" + height);
			}catch(Exception e){
			}
			try{
				camera.startPreview();
			}catch(Exception e){
				
			}
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}
	
}

    
    

