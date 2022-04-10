package design.Controller;

import design.pojo.ImageLabel;
import design.pojo.RenameMulDialog;
import design.pojo.RenameProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.util.*;

/**
 * User: 86176
 * Date: 2022/4/8
 * Time: 15:45
 * Description:
 */
public class RenameController {

    private TextInputDialog renameSingDialog;

    private RenameMulDialog renameMulDialog;

    private ImagePreviewController imagePreviewController;

    private FlowPane imageLabelsPane;


    public RenameController(){
        renameMulDialog = new RenameMulDialog();
        renameSingDialog = new TextInputDialog();
        renameSingDialog.getEditor().setPromptText("输入新的名字");
        renameSingDialog.getDialogPane().setContentText("请输入新的名字：");
        renameSingDialog.setHeaderText("重命名图片");
        renameSingDialog.setTitle("请输入新的图片名");
    }
    public RenameController(ImagePreviewController imagePreviewController){
        this();
        this.imagePreviewController = imagePreviewController;
    }

    /**
     *  重命名功能
     */
    public void renameAction(){
        ArrayList<ImageLabel> selectedPictures = ImageLabel.getSelectedPictures();
        // 单选重命名模式
        if(selectedPictures.size()==1){
            renameSingDialog.getEditor().clear();
            Optional<String> newFileNameOptional = renameSingDialog.showAndWait();
            newFileNameOptional.ifPresent(newFileName->{
                File file = selectedPictures.get(0).getImageFile();
                String filePath = file.getPath();
                String fileName = file.getName();
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                String newFilePath = filePath.substring(0,filePath.length()-fileName.length())+newFileName+suffix;
                File newFile = new File(newFilePath);
                boolean b = file.renameTo(newFile);
                System.out.println(fileName+"---"+b);
                imagePreviewController.refreshImageViews(selectedPictures, List.of(newFile));
            });
        }else{
            // 多选重命名模式
            renameMulDialog.refreshText();
            Optional<RenameProperty> renameProperty = renameMulDialog.showAndWait();
            renameProperty.ifPresent(property -> {
//            ArrayList<File> selectedPictureFiles = ImageLabel.getSelectedPictureFiles();
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
                    newImageFiles.add(newFile);
                }
                System.out.println(property);
                imagePreviewController.refreshImageViews(selectedPictures,newImageFiles);
            });
        }
    }
}
