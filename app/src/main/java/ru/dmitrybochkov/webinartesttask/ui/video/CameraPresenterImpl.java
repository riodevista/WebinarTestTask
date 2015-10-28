package ru.dmitrybochkov.webinartesttask.ui.video;

import android.hardware.Camera;

import ru.dmitrybochkov.webinartesttask.ui.video.view.CameraPreview;
import ru.dmitrybochkov.webinartesttask.usecase.CameraUseCase;

/**
 * Created by Dmitry Bochkov on 28.10.2015.
 */


public class CameraPresenterImpl implements CameraPresenter {

    private CameraView cameraView;

    public CameraPresenterImpl(CameraView cameraView) {
        this.cameraView = cameraView;
    }

    @Override
    public void checkIfCameraStarted() {
        if (CameraUseCase.getCamera() != null){
            cameraView.setCameraPreview(
                    new CameraPreview(
                            cameraView.provideContext(),
                            CameraUseCase.getCamera()
                    )
            );
            cameraView.switchToStopCameraButton();
        }
    }

    @Override
    public void startCamera() {
        cameraView.showLoader();

        new CameraUseCase().getCamera(
                cameraView.provideContext(),
                new CameraUseCase.OnFinishedListener() {
                    @Override
                    public void onSuccess() {
                        cameraView.setCameraPreview(
                                new CameraPreview(
                                        cameraView.provideContext(),
                                        CameraUseCase.getCamera()
                                )
                        );
                        cameraView.hideLoader();
                        cameraView.switchToStopCameraButton();
                    }

                    @Override
                    public void onFailure(int error) {
                        switch (error){
                            case NO_CAMERA:
                                cameraView.showNoCameraMessage();
                                break;
                            case UNKNOWN_ERROR:
                                cameraView.showUnknownErrorMessage();
                                break;
                            case CAMERA_IS_NOT_AVAILABLE:
                                cameraView.showCameraIsNotAvailableMessage();
                                break;
                            default:
                                break;
                        }
                        cameraView.hideLoader();
                    }
                }
        );


    }

    @Override
    public void stopCamera() {
        new CameraUseCase().stopCamera();
        cameraView.removeCameraPreview();
        cameraView.switchToStartCameraButton();
    }

}
