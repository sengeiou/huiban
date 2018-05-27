package com.bshuiban.baselibrary.view.customer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bshuiban.baselibrary.R;
import com.bshuiban.baselibrary.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zx315476228 on 17-9-20.
 */

public class CameraPreview extends FrameLayout implements SurfaceHolder.Callback, View.OnClickListener, Camera.PictureCallback, Camera.AutoFocusCallback, View.OnTouchListener {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private OnCameraCallBack onCameraCallBack;
    private SurfaceView surface_camera;
    private Button button_takephoto;
    private ImageView imageview_camera_show;
    private Button button_enter;
    private Button button_cancle;
    private Bitmap bitmap;
    private CameraOrientation cameraOrientation;
    private int camerarotate;
    private int tuoluoyi_default = 0;

    @Override
    public void onClick(View v) {
        if (mCamera == null) {
            return;
        }
        int i = v.getId();
        if (i == R.id.button_takephoto) {
            try {
                mCamera.takePicture(null, null, this);
            } catch (Exception e) {
            }

        } else if (i == R.id.button_cancle) {
            surface_camera.setVisibility(VISIBLE);

        } else if (i == R.id.button_enter) {//                button_cancle.setVisibility(GONE);
//                button_enter.setVisibility(GONE);
//                button_takephoto.setVisibility(VISIBLE);
            if (onCameraCallBack != null) {

                String path= Environment.getExternalStorageDirectory().getAbsolutePath() + "/Hbag/"+"photo/";
                onCameraCallBack.takePhoto(FileUtils.saveBitmap(bitmap, path, createFileName()));
            }

        }
    }

    public void setOnCameraCallBack(OnCameraCallBack onCameraCallBack) {
        this.onCameraCallBack = onCameraCallBack;
    }

    /**
     * 为图片创建不同的名称用于保存，避免覆盖
     **/

    public static String createFileName() {

        String fileName = "";

        Date date = new Date(System.currentTimeMillis()); // 系统当前时间

        SimpleDateFormat dateFormat = new SimpleDateFormat(

                "'IMG'_yyyyMMdd_HHmmss");// 设置日期格式

        fileName = dateFormat.format(date) + ".jpeg";// 设置照片的名称

        return fileName;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Matrix matrix = new Matrix();
        matrix.preRotate(camerarotate);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        imageview_camera_show.setImageBitmap(bitmap);
        imageview_camera_show.setVisibility(VISIBLE);
        surface_camera.setVisibility(GONE);
        button_cancle.setVisibility(VISIBLE);
        button_enter.setVisibility(VISIBLE);
        button_takephoto.setVisibility(GONE);
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            camera.cancelAutoFocus();// 只有加上了这一句，才会自动对焦
//            doAutoFocus();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                focusOnTouch((int) event.getX(), (int) event.getY());
                break;
        }
        return true;
    }

    public interface OnCameraCallBack {
        void takePhoto(File file);

    }

    public CameraPreview(Context context) {
        this(context, null);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public OnCameraCallBack getOnCameraCallBack() {
        return onCameraCallBack;
    }

    public void init(Context context) {
        View parentView = LayoutInflater.from(context).inflate(R.layout.view_camera, this);
        surface_camera = ((SurfaceView) parentView.findViewById(R.id.surface_camera));
        surface_camera.setOnTouchListener(this);
        //tuoluoyi_default = context.getResources().getInteger(R.integer.tuoluoyi_land_default);
        tuoluoyi_default = 0;
        button_takephoto = ((Button) parentView.findViewById(R.id.button_takephoto));
        imageview_camera_show = ((ImageView) parentView.findViewById(R.id.imageview_camera_show));
        button_enter = ((Button) parentView.findViewById(R.id.button_enter));
        button_cancle = ((Button) parentView.findViewById(R.id.button_cancle));
        button_takephoto.setOnClickListener(this);
        button_enter.setOnClickListener(this);
        button_cancle.setOnClickListener(this);
        cameraOrientation = new CameraOrientation(context, SensorManager.SENSOR_DELAY_NORMAL);

        try {
            mCamera = Camera.open();
            mHolder = surface_camera.getHolder();
            mHolder.addCallback(CameraPreview.this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "打开相机失败");
        }


    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            button_cancle.setVisibility(GONE);
            button_enter.setVisibility(GONE);
            button_takephoto.setVisibility(VISIBLE);
            imageview_camera_show.setVisibility(GONE);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            cameraOrientation.enable();
            start();
            Log.e("---", "surface创建完成");
        } catch (IOException e) {
            e.printStackTrace();
           // Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    private SensorManager manager;
    private Sensor sensor;
    private SensorEventListener listener = new SensorEventListener() {
        //个坐标轴重力
        private float current_x;
        private float current_y;
        private float current_z;
        private long currentTime;//进入静止状态时间
        private boolean isStart = false;//开始记录静止状态
        private boolean isRest = false;//首次开始静止状态

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[SensorManager.DATA_X];
            float y = event.values[SensorManager.DATA_Y];
            float z = event.values[SensorManager.DATA_Z];
            if (Math.abs(x - current_x) < 1 && Math.abs(y - current_y) < 1 && Math.abs(z - current_z) < 1) {
                if (isStart) {
                    isStart = false;
                    currentTime = System.currentTimeMillis();
                    return;
                }
                if (System.currentTimeMillis() - currentTime > 1000) {
                    if (!isRest) {
                        isRest = true;
                        focusOnRect();//自动对焦
                    }
                }
            } else {
                isStart = true;
                isRest = false;
            }
            current_x = x;
            current_y = y;
            current_z = z;
//                LogUtil.e("---", "重力:"+x+"----"+y+"---"+z);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public void start() {
        manager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);//启动重力感应
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        cameraOrientation.disable();
        mCamera.stopPreview();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        //LogUtil.e("---", "改变");
        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();

//            mCamera.release();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            e.printStackTrace();
            //Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }


    private void focusOnTouch(int x, int y) {
        Rect rect = new Rect(x - 100, y - 100, x + 100, y + 100);
        int left = rect.left * 2000 / surface_camera.getWidth() - 1000;
        int top = rect.top * 2000 / surface_camera.getHeight() - 1000;
        int right = rect.right * 2000 / surface_camera.getWidth() - 1000;
        int bottom = rect.bottom * 2000 / surface_camera.getHeight() - 1000;
        // 如果超出了(-1000,1000)到(1000, 1000)的范围，则会导致相机崩溃
        left = left < -1000 ? -1000 : left;
        top = top < -1000 ? -1000 : top;
        right = right > 1000 ? 1000 : right;
        bottom = bottom > 1000 ? 1000 : bottom;
        focusOnRect(new Rect(left, top, right, bottom));
    }

    protected void focusOnRect(Rect rect) {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters(); // 先获取当前相机的参数配置对象
            parameters.setPictureSize(1600, 1200);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO); // 设置聚焦模式
            //Log.d(TAG, "parameters.getMaxNumFocusAreas() : " + parameters.getMaxNumFocusAreas());
            if (parameters.getMaxNumFocusAreas() > 0) {
                List<Camera.Area> focusAreas = new ArrayList<Camera.Area>();
                focusAreas.add(new Camera.Area(rect, 1000));
                parameters.setFocusAreas(focusAreas);
            }
            mCamera.cancelAutoFocus(); // 先要取消掉进程中所有的聚焦功能
            mCamera.setParameters(parameters); // 一定要记得把相应参数设置给相机
            mCamera.autoFocus(this);
        }
    }

    protected void focusOnRect() {
        if (mCamera != null) {
            Camera.Parameters parameters = mCamera.getParameters(); // 先获取当前相机的参数配置对象
            parameters.setPictureSize(1600, 1200);
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO); // 设置聚焦模式
            mCamera.cancelAutoFocus(); // 先要取消掉进程中所有的聚焦功能
            mCamera.setParameters(parameters); // 一定要记得把相应参数设置给相机
            try {
                mCamera.autoFocus(this);
            }catch (Exception e){

            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            imageview_camera_show = null;
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            bitmap.recycle();
        } catch (Exception e) {

        }

    }

    public void onResume() {
        Log.e("---", "onResume");
        if (manager != null) {
            manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void onPause() {
        if (manager != null) {
            manager.unregisterListener(listener);
        }
    }

    private class CameraOrientation extends OrientationEventListener {

        public CameraOrientation(Context context) {
            super(context);
        }

        public CameraOrientation(Context context, int rate) {
            super(context, rate);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            switch (tuoluoyi_default) {
                case 0:
                    if (orientation > 350 || orientation < 10) { //0度
                        camerarotate = 0;
                        mCamera.setDisplayOrientation(camerarotate);
                    } else if (orientation > 170 && orientation < 190) { //180度
                        camerarotate = 180;
                        mCamera.setDisplayOrientation(camerarotate);
                    }
                    break;
                case 90:
                    if (orientation > 260 && orientation < 280) { //270度
                        camerarotate = 0;
                        mCamera.setDisplayOrientation(camerarotate);
                    } else if (orientation > 80 && orientation < 100) { //90度
                        camerarotate = 180;
                        mCamera.setDisplayOrientation(camerarotate);
                    }
                    break;
            }
        }
    }
}