package ru.dmitrybochkov.webinartesttask.usecase;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by Dmitry Bochkov on 28.10.2015.
 */
public class CameraUseCase implements UseCase{

    private OnFinishedListener onFinishedListener;

    private static Camera camera;

    private CameraHandlerThread cameraThread = null;

    public static Camera getCamera(){
        return camera;
    }

    public void getCamera(Context context, OnFinishedListener onFinishedListener){

        if (!checkCameraHardware(context)){
            onFinishedListener.onFailure(OnFinishedListener.NO_CAMERA);
            return;
        }

        openCamera();

        if (camera == null){
            onFinishedListener.onFailure(OnFinishedListener.CAMERA_IS_NOT_AVAILABLE);
            return;
        }

        onFinishedListener.onSuccess();
    }

    public void stopCamera(){
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public interface OnFinishedListener{
        int NO_CAMERA = 0;
        int UNKNOWN_ERROR = 1;
        int CAMERA_IS_NOT_AVAILABLE = 2;

        void onSuccess();
        void onFailure(int error);
    }

    //Check if this device has a camera
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        else
            return false;
    }

    // A safe way to get an instance of the Camera object.
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){

        }
        return c; // returns null if camera is unavailable
    }


    /**
     * Start camera in separated thread.
     */
    private void openCamera() {
        if (cameraThread == null) {
            cameraThread = new CameraHandlerThread();
        }

        synchronized (cameraThread) {
            cameraThread.openCamera();
        }
    }

    private static class CameraHandlerThread extends HandlerThread {
        Handler handler = null;

        CameraHandlerThread() {
            super("CameraHandlerThread");
            start();
            handler = new Handler(getLooper());
        }

        synchronized void notifyCameraOpened() {
            notify();
        }

        void openCamera() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    camera = getCameraInstance();
                    notifyCameraOpened();
                }
            });
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
    }

}
