package design.pojo;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MouseEvenHandler implements EventHandler<MouseEvent> {
    Node node;
    public MouseEvenHandler(Node node){
        this.node = node;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        if(node instanceof ImageLabel) {
            if(mouseEvent.isControlDown() == false) {//Control没有按下
                if(mouseEvent.getButton()!= MouseButton.SECONDARY || !((ImageLabel)node).selected.getValue())
                    ImageLabel.clearSelected();
                ((ImageLabel)node).setSelected(true);
            }
            if(mouseEvent.isControlDown() && mouseEvent.getButton() == MouseButton.PRIMARY) {//Control按下
                ((ImageLabel) node).setSelected( !((ImageLabel)node).selected.get() );
            }
        }
    }
}
