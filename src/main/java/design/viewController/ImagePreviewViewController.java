package design.viewController;

import design.controller.*;
import design.model.ImageLabel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
    private final TreeController treeController;
    private final ImagePreviewController imageController;
    private ShowImageController showImageController;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TreeView<File> treeView;
    @FXML
    private ScrollPane imageLabelsScrollPane;
    @FXML
    private FlowPane imageLabelsPane;
    @FXML
    private Label tipsLabel;
    @FXML
    private GridPane actionPane;
    @FXML
    private Button slideShowButton;

    private Stage primaryStage;


    public ImagePreviewViewController() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        treeController = new TreeController();
        showImageController = new ShowImageController();
        imageController = new ImagePreviewController(showImageController);
    }
    public void initialize(){

        treeView.setRoot(treeController.getRootNode());
//        imageLabelsPane.setPrefWidth(primaryStage.getWidth()-AnchorPane.getLeftAnchor(imageLabelsPane));

        imageController.setSlideShowButton(slideShowButton);
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            imageController.createImageViews(observable.getValue().getValue(),imageLabelsPane);
            TipsController.createTipsLabel(tipsLabel,imageController);
            setListener();
            try {
                TreeController.createAndSetChildrenNodes(newValue);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        imageLabelsPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Node clickNode = e.getPickResult().getIntersectedNode();
            if (clickNode instanceof FlowPane && !(clickNode instanceof ImageLabel) && !(clickNode instanceof Text)) {// 鼠标点击非图片节点
                ImageLabel.clearSelected();// 清空已选
                TipsController.setSelectedCount(ImageLabel.getSelectedPictures().size());
            }
        });
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        imageController.setPrimaryStage(primaryStage);
    }
    public void setListener(){
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                 imageLabelsPane.setPrefWidth((Double)newValue-treeView.prefWidth(-1));
                 imageLabelsScrollPane.setPrefWidth(newValue.doubleValue()-treeView.prefWidth(-1)-15);
                 tipsLabel.setPrefWidth((Double)newValue);
                 actionPane.setPrefWidth(newValue.doubleValue()-treeView.prefWidth(-1));
            }
        });
        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                 treeView.setPrefHeight(newValue.doubleValue()-70);
                 imageLabelsScrollPane.setPrefHeight(newValue.doubleValue()-70-40);
                 imageLabelsPane.setPrefHeight(newValue.doubleValue()-70-40);
                 AnchorPane.setTopAnchor(tipsLabel,treeView.prefHeight(-1));
                 AnchorPane.setTopAnchor(actionPane,imageLabelsScrollPane.prefHeight(-1));
            }
        });

    }

    public FlowPane getImageLabelsPane() {
        return imageLabelsPane;
    }

    public ImagePreviewController getImageController() {
        return imageController;
    }
}
