package design.pojo;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.Optional;

/**
 * User: 86176
 * Date: 2022/4/8
 * Time: 17:24
 * Description:
 */
public class RenameDialog extends Dialog<RenameProperty> {
    private TextField prefix;

    private TextField startNum;

    private TextField numBits;

    public RenameDialog(){
        super();
        this.getDialogPane().getButtonTypes().addAll(ButtonType.YES,ButtonType.NO);

        prefix = new TextField();
        prefix.setPromptText("新名字的前缀");

        startNum = new TextField();
        startNum.setPromptText("输入编号起始");

        numBits = new TextField();
        numBits.setPromptText("编号的位数");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(new Label("名字前缀："),0,0);
        gridPane.add(prefix,0,1);
        gridPane.add(new Label("编号起始："),1,0);
        gridPane.add(startNum,1,1);
        gridPane.add(new Label("编号位数："),2,0);
        gridPane.add(numBits,2,1);


        this.getDialogPane().setContent(gridPane);

        this.setResultConverter(new Callback<ButtonType, RenameProperty>() {
            @Override
            public RenameProperty call(ButtonType param) {
                if(param == ButtonType.YES){
                    return new RenameProperty(prefix.getText(),Integer.valueOf(startNum.getText()),Integer.valueOf(numBits.getText()));
                }
                return null;
            }
        });
    }
    public void refreshText(){
        prefix.clear();
        startNum.clear();
        numBits.clear();
    }
}
