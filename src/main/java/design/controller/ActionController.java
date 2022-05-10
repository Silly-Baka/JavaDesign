package design.controller;

import design.Main;
import design.Utils.AlertUtils;
import design.model.ImageLabel;
import design.model.RenameMulDialog;
import design.model.RenameProperty;
import design.viewController.ImagePreviewViewController;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * User: 86176
 * Date: 2022/4/15
 * Time: 8:52
 * Description:
 */
public class ActionController {
    private final TextInputDialog renameSingDialog;

    private final RenameMulDialog renameMulDialog;

    public ActionController(){
        renameMulDialog = new RenameMulDialog();
        renameSingDialog = new TextInputDialog();
        renameSingDialog.getEditor().setPromptText("输入新的名字");
        renameSingDialog.getDialogPane().setContentText("请输入新的名字：");
        renameSingDialog.setHeaderText("重命名图片");
        renameSingDialog.setTitle("请输入新的图片名");
    }

    /**
     * 复制功能
     */
    public void copyAction(){
        if(ImageLabel.getSelectedPictures().size()<=0) {
            return;
        }

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboard.clear();
        ImageLabel.getSelectedPictureFiles().clear();
        for(ImageLabel pNode : ImageLabel.getSelectedPictures()) {
            ImageLabel.getSelectedPictureFiles().add(pNode.getImageFile());
        }
        clipboardContent.putFiles(ImageLabel.getSelectedPictureFiles());
        clipboard.setContent(clipboardContent);
        clipboard = null;
        clipboardContent = null;
    }

    /**
     * 粘贴功能
     */
    public void pasteAction(ImagePreviewController imagePreviewController){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        List<File> files = (List<File>) (clipboard.getContent(DataFormat.FILES));
        if (files.size() <= 0) {
            return;
        }

        for(File oldFile : files) {
            String newName = Pasterename(imagePreviewController.getOldPath(),oldFile.getName());
            File newFile = new File(imagePreviewController.getOldPath()+File.separator+newName);
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(newFile.exists()) {
                try {
                    copyFile(oldFile,newFile);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            //该段代码应该把刚刚粘贴的图片加载到图片数组中，这样界面才能刷新显示出新加的图片
//            FileInputStream fis = new FileInputStream(newFile);
//            Image image = new Image(fis,100,100,true,true);
//            ImageView imageView = new ImageView(image);
//            ImageLabel I1 = new ImageLabel(newFile.getName(),imageView);
//            I1.setImageFileProperty(newFile);
//            mainUI.getPictures().add(I1);

            imagePreviewController.refreshImageViews(newFile);




//            imagePreviewViewController.showPicture();
        }
        clipboard.clear();
    }
    private void copyFile(File fromFile, File toFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(fromFile);
        FileOutputStream outputStream = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int byteRead;
        while ((byteRead = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, byteRead);
        }
        inputStream.close();
        outputStream.close();

    }
    private String Pasterename(String theFilePath, String name) {
        String newName = name;
        File fatherPathFile = new File(theFilePath);
        File[] filesInFatherPath = fatherPathFile.listFiles();
        for (File fileInFatherPath : filesInFatherPath) {
            String fileName = fileInFatherPath.getName();
            int cmp = newName.compareTo(fileName);
            if (cmp == 0) {
                String str = null;
                int end = newName.lastIndexOf("."), start = newName.lastIndexOf("_副本");
                if (start != -1) {
                    str = newName.substring(start, end);
                    int num = 1;
                    try {
                        num = Integer.parseInt(str.substring(str.lastIndexOf("_副本") + 3)) + 1;
                        int cnt = 0, d = num - 1;
                        while (d != 0) {
                            d /= 10;
                            cnt++;
                        }
                        newName = newName.substring(0, end - cnt) + num + newName.substring(end);
                    } catch (Exception e) {
                        newName = newName.substring(0, end) + "_副本1" + newName.substring(end);
                    }

                } else {
                    newName = newName.substring(0, end) + "_副本1" + newName.substring(end);
                }
            }
        }
        return newName;
    }

    /**
     * 删除功能
     */
    public void deleteAction(ImagePreviewViewController imagePreviewViewController){
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
    /**
     *  重命名功能
     */
    public void renameAction(ImagePreviewController imagePreviewController){
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
