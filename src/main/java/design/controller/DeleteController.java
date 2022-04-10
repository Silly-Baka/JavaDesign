package design.controller;

import design.Utils.AlertUtils;
import design.viewController.ImagePreviewViewController;
import design.Main;
import design.model.ImageLabel;
import javafx.scene.control.Alert;

public class DeleteController {
    ImagePreviewViewController imagePreviewViewController;
    public DeleteController(ImagePreviewViewController imagePreviewViewController) {
        this.imagePreviewViewController = imagePreviewViewController;
    }
    public void deleteAction(){
        if(ImageLabel.getSelectedPictures().size()<=0) {
            return;
        }
        if(ImageLabel.getCutedPictures().size() > 0) {
            for(ImageLabel pNode : ImageLabel.getCutedPictures()) {
                pNode.getImageView().setEffect(null);
            }
            ImageLabel.getCutedPictures().clear();
        }

        if(AlertUtils.showAlert(Alert.AlertType.WARNING,"是否删除选中的图片？", "", Main.mainStage)) {
            for(ImageLabel pNode : ImageLabel.getSelectedPictures()) {
                imagePreviewViewController.getImageLabelsPane().getChildren().remove(pNode);
                pNode.getImageFile().delete();
            }
            ImageLabel.getSelectedPictureFiles().clear();

        }else {
            ImageLabel.getSelectedPictureFiles().clear();
        }
        ImageLabel.clearSelected();
    }
}
