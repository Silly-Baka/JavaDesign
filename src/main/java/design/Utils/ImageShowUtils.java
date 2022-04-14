package design.Utils;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

/**
 * User: 86176
 * Date: 2022/3/21
 * Time: 20:56
 * Description: 用于实现图片的幻灯片以及轮播图功能的工具类
 */
public class ImageShowUtils {

    private static ArrayList<ImageView> tempImageViewList;

    /**
     * 幻灯片向左移动
     * @param imageViews
     * @param presentIndex
     * @param trans_x1
     * @param trans_x2
     * @param trans_x3
     * @param trans_z
     */
    public static void right_to_left(ArrayList<ImageView> imageViews,Integer presentIndex,double trans_x1,double trans_x2,double trans_x3,double trans_z){
        ImageView img1;
        ImageView img2;
        ImageView img3;
        if(tempImageViewList == null){
            tempImageViewList = new ArrayList<>();
            img1 = imageViews.get(presentIndex-1);
            img2 = imageViews.get(presentIndex);
            img3 = imageViews.get(presentIndex+1);
            tempImageViewList.add(img1);
            tempImageViewList.add(img2);
            tempImageViewList.add(img3);
        }else {
            img1 = tempImageViewList.get(0);
            img2 = tempImageViewList.get(1);
            img3 = tempImageViewList.get(2);
        }
        presentIndex = presentIndex-1;

        middle_to_right_animation(img2,trans_x2,trans_x3,trans_z);
        left_to_middle_animation(img1,trans_x1,trans_x2,trans_z);
        right_to_left_animation(img3,trans_x3,trans_x1,trans_z);

        ImageView newImageView = imageViews.get(presentIndex - 1);
        newImageView.setVisible(true);
        img3.setVisible(false);

        tempImageViewList.clear();
        tempImageViewList.add(newImageView);
        tempImageViewList.add(img1);
        tempImageViewList.add(img2);
    }
    private static void middle_to_right_animation(ImageView imageView,double trans_x2,double trans_x3,double trans_z){
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.seconds(1));
        tt.setNode(imageView);
        tt.setFromX(trans_x2);
        tt.setFromZ(0);

        tt.setToX(trans_x3);
        tt.setToZ(trans_z);

        tt.play();
    }
    private static void left_to_middle_animation(ImageView imageView,double trans_x1,double trans_x2,double trans_z){
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.seconds(1));
        tt.setNode(imageView);
        tt.setFromX(trans_x1);
        tt.setFromZ(trans_z);

        tt.setToX(trans_x2);
        tt.setToZ(0);

        tt.play();
    }
    private static void right_to_left_animation(ImageView imageView,double trans_x3,double trans_x1,double trans_z){
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.seconds(1));
        tt.setNode(imageView);
        tt.setFromX(trans_x3);
        tt.setFromZ(trans_z);

        tt.setToX(trans_x1);
        tt.setToZ(trans_z);

        tt.play();
    }

    /**
     * 将图片文件数组包装成图片数组
     * @param imageFileList 图片文件数组
     * @return 图片数组
     */
    public static ArrayList<Image> createImageList(ArrayList<File> imageFileList){
        ArrayList<Image> imageList = new ArrayList<>();
        for (File file : imageFileList) {
            Image image = new Image("file:"+file.getPath());
            imageList.add(image);
        }
        return imageList;
    }

    /**
     * 设置 ImageView 的大小 根据窗口的长宽比 等比例缩放
     * @param imageView 传入的图片组件
     * @param imgWidth 图片宽度
     * @param imgHeight 图片高度
     * @param containWidth 窗口宽度
     * @param containHeight 窗口高度
     */
    public static void setImageSize(ImageView imageView,double imgWidth,double imgHeight,double containWidth,double containHeight){
        double finalWidth = containWidth;
        double finalHeight = containHeight;
        if (imgWidth/imgHeight > containWidth/containHeight){
            finalHeight = containWidth/imgWidth * imgHeight;
        }else {
            finalWidth = containHeight/imgHeight * imgWidth;
        }
        imageView.setFitHeight(finalHeight);
        imageView.setFitWidth(finalWidth);
    }

}
