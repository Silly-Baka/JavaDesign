package design.Controller;

import javafx.scene.control.Label;

/**
 * User: 86176
 * Date: 2022/3/19
 * Time: 19:35
 * Description: 负责提示信息区域的组件创建
 */
public class TipsController {

    private ImageController imageController;

    private Label tipsLabel;

    public TipsController(){

    }
    public TipsController(ImageController imageController){
        this.imageController = imageController;
    }

    public void createTipsLabel(){
        int imageCount = imageController.getImageCount();
        double imageTotalSize = imageController.getImageTotalSize().doubleValue();
        ///
        int selectedCount = 0;

        String text = "    "+imageCount+"张图片("+imageTotalSize+"MB)---选中"+selectedCount+"张照片";
        tipsLabel = new Label(text);
        tipsLabel.setStyle("-fx-background-color: pink");
    }

    public Label getTipsLabel() {
        return tipsLabel;
    }

    public void setTipsLabel(Label tipsLabel) {
        this.tipsLabel = tipsLabel;
    }
}
