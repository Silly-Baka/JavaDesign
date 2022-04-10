package design.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

import java.util.Optional;

public class AlertUtils {
    public static boolean showAlert(Alert.AlertType alertType,String p_header, String p_message, Window stage){

        Alert alert = new Alert(alertType,p_message,new ButtonType("取消", ButtonBar.ButtonData.NO),
                new ButtonType("确定", ButtonBar.ButtonData.YES));

        alert.setTitle("注意窗口");
        alert.setHeaderText(p_header);

        alert.initOwner(stage);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if(buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)){
            return true;
        }
        else {
            return false;
        }
    }
}
