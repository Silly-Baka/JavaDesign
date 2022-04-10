package design.controller;

import design.model.ImageLabel;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PasteController {
    ImagePreviewController imagePreviewController;
    public PasteController(ImagePreviewController imagePreviewController) {
        this.imagePreviewController = imagePreviewController;
    }
    public void pasteAction(){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        List<File> files = (List<File>) (clipboard.getContent(DataFormat.FILES));
        if (files.size() <= 0) {
            return;
        }
        if (ImageLabel.getCutedPictures().size() > 0) {
            File first = files.get(0);
            if(first.getParentFile().getAbsolutePath().compareTo(imagePreviewController.getOldPath()) == 0){
                for(ImageLabel pNode : ImageLabel.getCutedPictures()) {
                    pNode.getImageView().setEffect(null);
                }
                ImageLabel.clearSelected();
                ImageLabel.getCutedPictures().clear();
                ImageLabel.getSelectedPictureFiles().clear();
                clipboard.clear();
                return;
            }
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


            if(ImageLabel.getCutedPictures().size()>0) {
                oldFile.delete();
            }

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
}
