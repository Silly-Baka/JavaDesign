package design.viewController;

import design.controller.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * User: 86176
 * Date: 2022/3/28
 * Time: 11:12
 * Description: 图片预览界面的视图控制了
 */
public class ImagePreviewViewController {
    private TreeController treeController;
    private ImagePreviewController imageController;
    private TipsController tipsController;
    private MenuController menuController;
    private ShowImageController showImageController;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TreeView<File> treeView;
    @FXML
    private FlowPane imageLabelsPane;
    @FXML
    private Label tipsLabel;
    @FXML
    private VBox renameWindow;

    private Stage primaryStage;


    public ImagePreviewViewController() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        tipsController = new TipsController();
        menuController = new MenuController(this,tipsController);
        treeController = new TreeController(menuController);
        showImageController = new ShowImageController();
        imageController = new ImagePreviewController(menuController, showImageController, tipsController);
        tipsController.setImageController(imageController);
    }
    public void initialize(){

        treeView.setRoot(treeController.getRootNode());
//        imageLabelsPane.setPrefWidth(primaryStage.getWidth()-AnchorPane.getLeftAnchor(imageLabelsPane));

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            menuController.setNode(imageLabelsPane);
            imageController.createImageViews(observable.getValue().getValue(),imageLabelsPane);
            tipsController.createTipsLabel(tipsLabel);
            setListener();
            try {
                TreeController.createAndSetChildrenNodes(newValue);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void setListener(){
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(!newValue.equals(oldValue)){
                    imageLabelsPane.setPrefWidth((Double)newValue-AnchorPane.getLeftAnchor(imageLabelsPane));
                    tipsLabel.setPrefWidth((Double)newValue);
                }
            }
        });

    }

    public FlowPane getImageLabelsPane() {
        return imageLabelsPane;
    }
}
