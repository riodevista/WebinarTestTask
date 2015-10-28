package ru.dmitrybochkov.webinartesttask.ui.video;

import android.content.Context;

import ru.dmitrybochkov.webinartesttask.ui.video.view.CameraPreview;

/**
 * Created by Dmitry Bochkov on 28.10.2015.
 */
public interface CameraView {

    Context provideContext();

    void switchToStartCameraButton();
    void switchToStopCameraButton();

    void showNoCameraMessage();
    void showUnknownErrorMessage();
    void showCameraIsNotAvailableMessage();

    void showLoader();
    void hideLoader();

    void setCameraPreview(CameraPreview cameraPreview);
    void removeCameraPreview();

}
