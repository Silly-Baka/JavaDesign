package design.ViewTest;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * User: 86176
 * Date: 2022/3/15
 * Time: 14:51
 * Description:
 */
public class MenuTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane an = new AnchorPane();
        an.setStyle("-fx-background-color: pink");

        MenuBar menuBar = new MenuBar();

        Menu menu1 = new Menu("menu1");
        Menu menu2 = new Menu("menu2");
        Menu menu3 = new Menu("menu3");
        Menu menu4 = new Menu("menu4");

        ImageView imageView = new ImageView("file:demo1/src/main/resources/img/test2.png");
//        File file = new File("demo1/src/main/resources/img/test2.png");
//        System.out.println(System.getProperty("user.dir"));
//        System.out.println(file.exists());

        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        MenuItem menuItem2 = new MenuItem("menuItem2");
        MenuItem menuItem3 = new MenuItem("menuItem3");
        MenuItem menuItem4 = new MenuItem("menuItem4");

        MenuItem menuItem1 = new MenuItem("menuItem1",imageView);
        KeyCombination keyCombination = KeyCombination.valueOf("ctrl+i");
        menuItem1.setAccelerator(keyCombination);

        menu1.getItems().addAll(menuItem1,menuItem2,menuItem3,menuItem4);

        menuBar.getMenus().addAll(menu1,menu2,menu3,menu4);


        menu1.setVisible(false);
        an.getChildren().add(menuBar);

        AnchorPane.setTopAnchor(imageView,60.0);

        Scene scene = new Scene(an);
        stage.setScene(scene);
        stage.setHeight(800);
        stage.setWidth(800);
        stage.show();

        menuBar.setPrefWidth(an.getWidth());

        an.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                menuBar.setPrefWidth(newValue.doubleValue());
            }
        });
        menuItem1.setOnAction(event -> {
            System.out.println("Item1.setonaction");
        });


    }

    public static void main(String[] args) {
        launch(args);
    }
}
