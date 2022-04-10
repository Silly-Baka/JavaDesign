package design.Controller;

import javafx.scene.control.Label;

/**
 * User: 86176
 * Date: 2022/3/19
 * Time: 19:35
 * Description: 负责提示信息区域的组件创建
 */
public class TipsController {

    private ImagePreviewController imageController;

    private Label tipsLabel;

    public void setImageController(ImagePreviewController imageController) {
        this.imageController = imageController;
    }

    private int selectedCount = 0;

    private int imageCount;

    private double imageTotalSize;

    public TipsController(){

    }
    public TipsController(ImagePreviewController imageController){
        this.imageController = imageController;
    }

    public void createTipsLabel(Label tipsLabel){
        this.tipsLabel = tipsLabel;
        this.imageCount = imageController.getImageCount();
        this.imageTotalSize = imageController.getImageTotalSize().doubleValue();
        ///

        String text = "    "+imageCount+"张图片("+imageTotalSize+"MB)---选中"+selectedCount+"张照片";
        tipsLabel.setText(text);
        tipsLabel.setStyle("-fx-background-color: pink");
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount = selectedCount;
        String text = "    "+imageCount+"张图片("+imageTotalSize+"MB)---选中"+this.selectedCount+"张照片";
        tipsLabel.setText(text);
    }
}
