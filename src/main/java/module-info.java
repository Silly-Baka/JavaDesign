module JavaDesign {
    requires javafx.controls;
    requires javafx.fxml;

    opens design.Controller to javafx.fxml;
    exports design.ViewController;
    exports design.Controller;
    opens design.ViewController to javafx.fxml;
    exports design.Utils;
    opens design.Utils to javafx.fxml;
}