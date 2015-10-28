package ru.dmitrybochkov.webinartesttask.ui.video.activity;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ProgressBar;

import ru.dmitrybochkov.webinartesttask.R;
import ru.dmitrybochkov.webinartesttask.ui.video.CameraPresenter;
import ru.dmitrybochkov.webinartesttask.ui.video.CameraPresenterImpl;
import ru.dmitrybochkov.webinartesttask.ui.video.CameraView;
import ru.dmitrybochkov.webinartesttask.ui.video.view.CameraPreview;

/**
 * As Launcher.
 */

public class CameraActivity extends AppCompatActivity implements CameraView {

    private CameraPresenter cameraPresenter;

    private FloatingActionButton startCameraButton;
    private FloatingActionButton stopCameraButton;
    private ProgressBar loader;
    FrameLayout cameraPreviewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initViews();

        cameraPresenter = new CameraPresenterImpl(this);

    }

    private void initViews(){
        startCameraButton = (FloatingActionButton) findViewById(R.id.start_camera_button);
        stopCameraButton = (FloatingActionButton) findViewById(R.id.stop_camera_button);
        loader = (ProgressBar) findViewById(R.id.loader);
        cameraPreviewLayout = (FrameLayout) findViewById(R.id.camera_preview_layout);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //If camera started then continue to show camera preview.
        cameraPresenter.checkIfCameraStarted();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        cameraPresenter.stopCamera();
        finish();
    }

    public void startCamera(View view) {
        cameraPresenter.startCamera();
    }


    public void stopCamera(View view) {
        cameraPresenter.stopCamera();
    }

    @Override
    public Context provideContext() {
        return this;
    }

    @Override
    public void switchToStartCameraButton() {
        stopCameraButton.setVisibility(View.GONE);
        startCameraButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void switchToStopCameraButton() {
        startCameraButton.setVisibility(View.GONE);
        stopCameraButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoCameraMessage() {
        Toast.makeText(CameraActivity.this, R.string.no_camera_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUnknownErrorMessage() {
        Toast.makeText(CameraActivity.this, R.string.unknown_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCameraIsNotAvailableMessage() {
        Toast.makeText(CameraActivity.this, R.string.camera_is_not_available_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoader() {
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        loader.setVisibility(View.GONE);
    }

    @Override
    public void setCameraPreview(CameraPreview cameraPreview) {
        cameraPreviewLayout.addView(cameraPreview);
    }

    @Override
    public void removeCameraPreview() {
        cameraPreviewLayout.removeAllViews();
    }
}
