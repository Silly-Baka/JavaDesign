package design.Controller;

import design.pojo.ImageLabel;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class CopyController {
    public CopyController() {

    }
    public void copyAction(){
        if(ImageLabel.getSelectedPictures().size()<=0) {
            return;
        }
        if(ImageLabel.getCutedPictures().size() > 0) {
            for(ImageLabel pNode : ImageLabel.getCutedPictures()) {
                pNode.getImageView().setEffect(null);
            }
            ImageLabel.getCutedPictures().clear();
        }
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboard.clear();
        for(ImageLabel pNode : ImageLabel.getSelectedPictures()) {
            ImageLabel.getSelectedPictureFiles().add(pNode.getImageFile());
        }
        clipboardContent.putFiles(ImageLabel.getSelectedPictureFiles());
        clipboard.setContent(clipboardContent);
        clipboard = null;
        clipboardContent = null;
    }

}
