module JavaDesign {
    requires javafx.controls;
    requires javafx.fxml;

    opens design.controller to javafx.fxml;
    exports design.viewController;
    exports design.controller;
    opens design.viewController to javafx.fxml;
    exports design.Utils;
    opens design.Utils to javafx.fxml;
    exports design;
    opens design to javafx.fxml;
}