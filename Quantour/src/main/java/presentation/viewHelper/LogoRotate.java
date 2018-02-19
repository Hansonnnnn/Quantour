package presentation.viewHelper;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by LENOVO on 2017/4/19.
 */
public class LogoRotate {
    private static  LogoRotate logoRotate;
    private int rotateNum;
    private Timeline timeline;
    private ImageView logoImageView;

    private LogoRotate(){
        rotateNum = 1;
        timeline = new Timeline();
    }

    public static LogoRotate getLogoRotate(){
        if(logoRotate == null){
            logoRotate = new LogoRotate();
            return logoRotate;
        }else{
            return logoRotate;
        }
    }

    /**
     * 旋转logoImageView
     */
    public void imageviewRotate(){
//        timeline.setAutoReverse(false);
//        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.stop();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        KeyValue newY = new KeyValue(logoImageView.rotateProperty(),360 * rotateNum);
//        rotateNum++;
        logoImageView.setRotate(0);
        KeyFrame kf = new KeyFrame(Duration.millis(700), newY);
        timeline.getKeyFrames().add(kf);
        timeline.play();


    }

    /**
     * 不停转动logoImageView
     */
    public void noStopRotate(){
        timeline.stop();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        KeyValue newY = new KeyValue(logoImageView.rotateProperty(),360 * rotateNum);
//        rotateNum++;
        logoImageView.setRotate(0);
        KeyFrame kf = new KeyFrame(Duration.millis(700), newY);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    /**
     * 停止logoImageView的转动
     */
    public void rotateStop(){
        logoImageView.setRotate(0);
        timeline.stop();
    }

    public ImageView getLogoImageView() {
        return logoImageView;
    }

    public int getRotateNum() {
        return rotateNum;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setLogoImageView(ImageView logoImageView) {
        this.logoImageView = logoImageView;
    }
}
