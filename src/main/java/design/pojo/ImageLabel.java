package design.pojo;

import design.Controller.TipsController;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.ArrayList;

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

    private ImageLabel imageLabel = this;

    public BooleanProperty selected = new SimpleBooleanProperty();

    protected static ArrayList<ImageLabel> selectedPictures = new ArrayList<>();
    protected static ArrayList<ImageLabel> cutedPictures = new ArrayList<>();
    protected static ArrayList<File> selectedPictureFiles = new  ArrayList<>();
    //
    private TipsController tipsController;

    public ImageLabel(TipsController tipsController) {
        this.tipsController = tipsController;
    }

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

//    public void setSelected(boolean selected) {
//        isSelected = selected;
//    }

    public void setSelected(boolean value) {
        boolean istrue = selected.get();
        selected.set(value);
        if (selected.get() && !istrue)
            selectedPictures.add(this);
        else if (istrue && !selected.get())
            selectedPictures.remove(this);
        System.out.println(selectedPictures.size());
        tipsController.setSelectedCount(selectedPictures.size());
    }

    public static void clearSelected() {
        for (ImageLabel pNode : selectedPictures) {
            pNode.selected.set(false);
        }
        selectedPictures.removeAll(selectedPictures);
    }

    public void addPictureNodeListener() {
        selected.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if(selected.get()) {
                    imageLabel.setStyle("-fx-background-color:#a7a7a7;");
                }else {
                    imageLabel.setStyle("-fx-background-color:transparent;");
                }
            }
        });
        this.setOnMouseEntered((MouseEvent e) -> {
            if (!selected.get())
                this.setStyle("-fx-background-color:linear-gradient(to bottom,#3e4147 1%,  #a7a7a7 98%);");

        });
        this.setOnMouseExited((MouseEvent e) -> {
            if (!selected.get())
                this.setStyle("-fx-background-color:transparent;");

        });
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new MouseEvenHandler(this));
    }

    public static ArrayList<ImageLabel> getSelectedPictures() {return selectedPictures;}

    public static ArrayList<ImageLabel> getCutedPictures() {return cutedPictures;}

    public ImageView getImageView (){
        return (ImageView) this.getGraphic();
    }

    public static ArrayList<File> getSelectedPictureFiles() {
        return selectedPictureFiles;
    }

    public File getImageFile() {
        return this.imageFileProperty.get();
    }

}
