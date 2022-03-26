package design.ViewTest;

import design.Controller.TreeController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

/**
 * User: 86176
 * Date: 2022/3/17
 * Time: 11:02
 * Description:
 */
public class treeTest01 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TreeItem<File> treeNode = TreeController.createTreeNode(new File("C:\\Program Files (x86)"));

        TreeView<File> treeView = new TreeView<>(treeNode);

//        System.out.println(System.getProperty("user.dir"));
        VBox vBox = new VBox(treeView);

        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();

        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<File>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<File>> observable, TreeItem<File> oldValue, TreeItem<File> newValue) {
                System.out.println(observable.getValue().getValue().getName());
            }
        });
        treeView.getFocusModel().focus(0);
        treeView.requestFocus();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
