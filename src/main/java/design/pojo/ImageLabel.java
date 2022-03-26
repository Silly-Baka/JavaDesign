package design.pojo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.io.File;

/**
 * User: 86176
 * Date: 2022/3/19
 * Time: 16:03
 * Description:
 */
public class ImageLabel extends Label {
    //  该图片的文件属性
    private ObjectProperty<File> imageFileProperty;
    //  是否被选中 默认没被选中
    private boolean isSelected;

    public ImageLabel(){

    }
    public ImageLabel(String text, Node graphic,File file){
        super(text,graphic);
        if(imageFileProperty == null){
            imageFileProperty = new SimpleObjectProperty<>();
        }
        this.imageFileProperty.set(file);
        this.isSelected = false;
    }
    public File getImageFileProperty() {
        return imageFileProperty.get();
    }

    public ObjectProperty<File> imageFilePropertyProperty() {
        return imageFileProperty;
    }

    public void setImageFileProperty(File imageFile) {
        if(imageFileProperty == null){
            imageFileProperty = new SimpleObjectProperty<File>();
        }
        imageFileProperty.set(imageFile);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
