package design.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.File;

/**
 * User: 86176
 * Date: 2022/3/18
 * Time: 23:02
 * Description: 总控制器
 */
public class MainController {
    private TreeController treeController;
    private ImageController imageController;
    private MenuController menuController;
    private TipsController tipsController;
    private ImageShowController imageShowController;

    private TreeView<File> treeView;
    private AnchorPane presentPane;
    private FlowPane oldImageBox;
    private Stage primaryStage;

    public MainController(Stage stage){
        menuController = new MenuController();
        imageShowController = new ImageShowController();
        imageController = new ImageController(menuController,imageShowController);
        treeController = new TreeController(menuController);
        tipsController = new TipsController(imageController);
        presentPane = new AnchorPane();
        this.primaryStage = stage;

        treeView = treeController.getTreeView();

        // 当选择目录时 就会获取目录的图片 并加载在视图中
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            imageController.createImageViews(observable.getValue().getValue());
            tipsController.createTipsLabel();
            setPresentPane(presentPane);
        });
    }

    /**
     * 获取当前布局
     * @return 当前布局对象
     */
    public AnchorPane getPresentPane(){
        FlowPane imageViewsBox = imageController.getImageViewsPane();
        oldImageBox = imageViewsBox;
        AnchorPane.setLeftAnchor(imageViewsBox,300.0);
        presentPane.getChildren().addAll(treeView,imageViewsBox);

        return presentPane;
    }

    /**
     * 每当目录树被点击时 就调用此方法 动态改变布局
     * @param presentPane 当前的布局
     */
    public void setPresentPane(AnchorPane presentPane) {
        boolean remove = presentPane.getChildren().remove(oldImageBox);
        FlowPane imageViewsBox = imageController.getImageViewsPane();
        oldImageBox = imageViewsBox;
        AnchorPane.setLeftAnchor(imageViewsBox,300.0);
        imageViewsBox.setPrefWidth(primaryStage.getWidth()-AnchorPane.getLeftAnchor(imageViewsBox));
        imageViewsBox.setHgap(10);
        imageViewsBox.setVgap(10);
        imageViewsBox.setStyle("-fx-background-color: yellow");

        Label tipsLabel = tipsController.getTipsLabel();
        tipsLabel.setPrefHeight(30);
        tipsLabel.prefWidthProperty().bind(primaryStage.widthProperty());
        AnchorPane.setTopAnchor(tipsLabel,600.0);

        presentPane.getChildren().addAll(imageViewsBox,tipsLabel);

        // 添加监听器 动态改变图片层的宽度 自适应
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(!newValue.equals(oldValue)){
                    imageViewsBox.setPrefWidth((Double)newValue-AnchorPane.getLeftAnchor(imageViewsBox));
                }
            }
        });

    }
}
