/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproj;

import teamproj.utils.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author chenjiaxin
 */
public class RecoController {

    private Button cameraButton;

    // the FXML area for showing the current frame
    private ImageView originalFrame;

    // checkboxes for enabling/disabling a classifier
    private CheckBox haarClassifier;
    private CheckBox lbpClassifier;
    
    private Scene scene;
    private Pane pane;

    // a timer for acquiring the video stream
    private ScheduledExecutorService timer;
    // the OpenCV object that performs the video capture
    private final VideoCapture capture;
    // a flag to change the button behavior
    private boolean cameraActive;

    // face cascade classifier
    private final CascadeClassifier faceCascade;
    private int absoluteFaceSize;

    public RecoController(Button btn, CheckBox haarClassifier, CheckBox lbpClassifier, ImageView originalFrame) {

        this.cameraButton = btn;
        this.haarClassifier = haarClassifier;
        this.lbpClassifier = lbpClassifier;
        //this.originalFrame = originalFrame;

        this.capture = new VideoCapture();
        this.faceCascade = new CascadeClassifier();
        this.absoluteFaceSize = 0;
        // set a fixed width for the frame
        originalFrame.setFitWidth(600);
//        // preserve image ratio
        originalFrame.setPreserveRatio(true);
    }
    
    public void setScene(Scene s){
        this.scene = s;
    }
    
    public void setPane(Pane p){
        this.pane = p;
    }

    public void startCamera() {

        if (!this.cameraActive) {
            // disable setting checkboxes
            this.haarClassifier.setDisable(true);
            this.lbpClassifier.setDisable(true);

            // start the video capture
            this.capture.open(0);

            // is the video stream available?
            if (this.capture.isOpened()) {
                this.cameraActive = true;
                System.out.println("open camara success!!");

                // grab a frame every 33 ms (30 frames/sec)
                Runnable frameGrabber = new Runnable() {

                    @Override
                    public void run() {
                        // effectively grab and process a single frame
                        Mat frame = grabFrame();
                        // convert and show the frame
                        Image imageToShow = Utils.mat2Image(frame);
                        System.out.println(imageToShow.getHeight());
                        originalFrame = updateImageView(originalFrame, imageToShow);
                        //System.out.println("back");
                        System.out.println(originalFrame.getImage().getHeight());
                        Platform.runLater(new Runnable(){
                            public void run(){
                                pane.getChildren().setAll(originalFrame);
                            }
                        });
                        System.out.println("refrash ok");
                    }
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

                // update the button content
                this.cameraButton.setText("Stop Camera");
            } else {
                // log the error
                System.err.println("Failed to open the camera connection...");
            }
        } else {
            // the camera is not active at this point
            this.cameraActive = false;
            // update again the button content
            this.cameraButton.setText("Start Camera");
            // enable classifiers checkboxes
            this.haarClassifier.setDisable(false);
            this.lbpClassifier.setDisable(false);

            // stop the timer
            this.stopAcquisition();
        }
    }

    /**
     * Get a frame from the opened video stream (if any)
     *
     * @return the {@link Image} to show
     */
    private Mat grabFrame() {
        Mat frame = new Mat();

        // check if the capture is open
        if (this.capture.isOpened()) {
            try {
                // read the current frame
                this.capture.read(frame);
                System.out.println("read frame success!");

                // if the frame is not empty, process it
                if (!frame.empty()) {
                    // face detection
                    this.detectAndDisplay(frame);
                    //System.out.println("detect success!");
                }

            } catch (Exception e) {
                // log the (full) error
                System.err.println("Exception during the image elaboration: " + e);
            }
        }

        return frame;
    }

    /**
     * Method for face detection and tracking
     *
     * @param frame it looks for faces in this frame
     */
    private void detectAndDisplay(Mat frame) {
        MatOfRect faces = new MatOfRect();
        Mat grayFrame = new Mat();

        // convert the frame in gray scale
        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
        // equalize the frame histogram to improve the result
        Imgproc.equalizeHist(grayFrame, grayFrame);

        // compute minimum face size (20% of the frame height, in our case)
        if (this.absoluteFaceSize == 0) {
            int height = grayFrame.rows();
            if (Math.round(height * 0.2f) > 0) {
                this.absoluteFaceSize = Math.round(height * 0.2f);
            }
        }

        // detect faces
        this.faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
                new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size());

        // each rectangle in faces is a face: draw them!
        Rect[] facesArray = faces.toArray();
        for (int i = 0; i < facesArray.length; i++) {
            Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 3);
        }

    }

    /**
     * The action triggered by selecting the Haar Classifier checkbox. It loads
     * the trained set to be used for frontal face detection.
     */
    public void haarSelected(Event event) {
        // check whether the lpb checkbox is selected and deselect it
        if (this.lbpClassifier.isSelected()) {
            this.lbpClassifier.setSelected(false);
        }

        this.checkboxSelection("/Users/chenjiaxin/Downloads/opencv-3.3.0/data/haarcascades/haarcascade_frontalface_alt.xml");
    }

    /**
     * Method for loading a classifier trained set from disk
     *
     * @param classifierPath the path on disk where a classifier trained set is
     * located
     */
    private void checkboxSelection(String classifierPath) {
        // load the classifier(s)
        this.faceCascade.load(classifierPath);

        // now the video capture can start
        this.cameraButton.setDisable(false);
    }

    /**
     * Update the {@link ImageView} in the JavaFX main thread
     *
     * @param view the {@link ImageView} to update
     * @param image the {@link Image} to show
     */
    private ImageView updateImageView(ImageView view, Image image) {
        //Utils.onFXThread(view.imageProperty(), image);
        //System.out.println("enter update");
        view = new ImageView(image);
        //view.setImage(image);
        //System.out.println(view.getImage().getHeight());
        return view;
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    private void stopAcquisition() {
        if (this.timer != null && !this.timer.isShutdown()) {
            try {
                // stop the timer
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                // log any exception
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (this.capture.isOpened()) {
            // release the camera
            this.capture.release();
        }
    }

}
