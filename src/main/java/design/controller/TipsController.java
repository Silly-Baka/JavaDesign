package design.controller;

import javafx.scene.control.Label;

/**
 * User: 86176
 * Date: 2022/3/19
 * Time: 19:35
 * Description: 负责提示信息区域的组件创建
 */
public class TipsController {

    private static Label tipsLabel;

    private static int selectedCount = 0;

    private static int imageCount;

    private static double imageTotalSize;

    public TipsController(){

    }

    public static void createTipsLabel(Label tipsLabel,ImagePreviewController imageController){
        TipsController.tipsLabel = tipsLabel;
        TipsController.imageCount = imageController.getImageCount();
        if(imageController.getImageTotalSize()!=null){
            TipsController.imageTotalSize = imageController.getImageTotalSize().doubleValue();
        }
        ///

        String text = "    "+imageCount+"张图片("+imageTotalSize+"MB)---选中"+selectedCount+"张照片";
        tipsLabel.setText(text);
        tipsLabel.setStyle("-fx-background-color: pink");
    }

    public static void setSelectedCount(int selectedCount) {
        TipsController.selectedCount = selectedCount;
        String text = "    "+imageCount+"张图片("+imageTotalSize+"MB)---选中"+selectedCount+"张照片";
        TipsController.tipsLabel.setText(text);
    }
}
