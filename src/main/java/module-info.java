module JavaDesign {
    requires javafx.controls;
    requires javafx.fxml;

    opens design.Controller to javafx.fxml;
    exports design.ViewTest;
    exports design.Controller;
}