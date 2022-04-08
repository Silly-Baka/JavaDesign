package design.Controller;

import design.pojo.ImageLabel;
import design.pojo.RenameDialog;
import design.pojo.RenameProperty;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

/**
 * User: 86176
 * Date: 2022/4/8
 * Time: 15:45
 * Description:
 */
public class RenameController {

    private RenameDialog renameDialog;

    private ImagePreviewController imagePreviewController;

    private File directoryFile;

    private FlowPane imageLabelsPane;


    public RenameController(){
        renameDialog = new RenameDialog();
    }
    public RenameController(ImagePreviewController imagePreviewController){
        this();
        this.imagePreviewController = imagePreviewController;
    }
    public void renameAction(){
        renameDialog.refreshText();

        Optional<RenameProperty> renameProperty = renameDialog.showAndWait();

        renameProperty.ifPresent(property -> {
//            ArrayList<File> selectedPictureFiles = ImageLabel.getSelectedPictureFiles();
            ArrayList<ImageLabel> selectedPictures = ImageLabel.getSelectedPictures();
            ArrayList<File> selectedPictureFiles = new ArrayList<>();
            ArrayList<File> newImageFiles = new ArrayList<>();
            for (ImageLabel selectedPicture : selectedPictures) {
                selectedPictureFiles.add(selectedPicture.getImageFile());
            }
            String prefix = property.getPrefix();
            int startNum = property.getStartNum();
            int numBits = property.getNumBits();
            for (File file : selectedPictureFiles) {
                String filePath = file.getPath();
                String fileName = file.getName();
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                String newFileName = prefix+String.format("%0"+numBits+"d",startNum++)+suffix;
                String newFilePath = filePath.substring(0,filePath.length()-fileName.length())+newFileName;
                File newFile = new File(newFilePath);
                boolean b = file.renameTo(newFile);
                System.out.println(fileName+"---"+b);
                System.out.println(1);

                newImageFiles.add(newFile);
            }
            System.out.println(property);
//            imagePreviewController.createImageViews(directoryFile, imageLabelsPane);
            imagePreviewController.refreshImageViews(selectedPictures,newImageFiles);

        });
    }

    public void setDirectoryFile(File directoryFile) {
        this.directoryFile = directoryFile;
    }
}
