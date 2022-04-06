package design.Controller;

import design.ViewController.ImagePreviewViewController;
import design.ViewController.fxmlTest;
import design.pojo.ImageLabel;

public class DeleteAction {
    ImagePreviewViewController imagePreviewViewController;
    public DeleteAction (ImagePreviewViewController imagePreviewViewController) {
        this.imagePreviewViewController = imagePreviewViewController;

        if(ImageLabel.getSelectedPictures().size()<=0) {
            return;
        }
        if(ImageLabel.getCutedPictures().size() > 0) {
            for(ImageLabel pNode : ImageLabel.getCutedPictures()) {
                pNode.getImageView().setEffect(null);
            }
            ImageLabel.getCutedPictures().clear();
        }

        if(MyAlert.showAlert("是否删除选中的图片？", "", fxmlTest.mainStage)) {
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
