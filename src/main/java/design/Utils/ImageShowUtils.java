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
