package design.Controller;

import design.Utils.AlertUtils;
import design.ViewController.ImagePreviewViewController;
import design.ViewController.MainTest;
import design.pojo.ImageLabel;

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

        if(AlertUtils.showAlert("是否删除选中的图片？", "", MainTest.mainStage)) {
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
