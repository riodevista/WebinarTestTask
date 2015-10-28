package ru.dmitrybochkov.webinartesttask.ui.video;
/**
 * Created by Dmitry Bochkov on 28.10.2015.
 */

public interface CameraPresenter {

    void checkIfCameraStarted();

    void startCamera();
    void stopCamera();
}
