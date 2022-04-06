package design.Controller;

import design.ViewController.ImagePreviewViewController;
import design.pojo.ImageLabel;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

/**
 * User: 86176
 * Date: 2022/3/19
 * Time: 18:53
 * Description: 负责菜单组件的创建以及获取
 */
public class MenuController {

    private ImagePreviewViewController imagePreviewViewController;

    private Node node;

    private ImagePreviewController imagePreviewController;

    private ContextMenu imageControlMenu;

    private ContextMenu treeControlMenu;

    public MenuController (Node node, ImagePreviewViewController imagePreviewViewController) {
        this.imagePreviewViewController = imagePreviewViewController;
        this.node = node;
    }

    /**
     *
     * @return 获取图片的右键菜单
     */
    public ContextMenu getImageControlMenu(ImagePreviewController imagePreviewController){
        if(imageControlMenu == null){
            MenuItem menuItem1 = new MenuItem("复制");
            MenuItem menuItem2 = new MenuItem("粘贴");
            MenuItem menuItem3 = new MenuItem("删除");
            MenuItem menuItem4 = new MenuItem("重命名");
            imageControlMenu = new ContextMenu(menuItem1,menuItem2,menuItem3,menuItem4);

            menuItem1.setOnAction(e->{
                new CopyAction();
            });

            menuItem2.setOnAction(e->{
                new PasteAction(imagePreviewController);
            });

            menuItem3.setOnAction(e->{
                new DeleteAction(imagePreviewViewController);
            });

            menuItem4.setOnAction(e->{

            });

            node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                if (e.getButton() == MouseButton.SECONDARY)
                    imageControlMenu.show(node, e.getScreenX(), e.getScreenY());
                else {
                    if (imageControlMenu.isShowing())
                        imageControlMenu.hide();
                }
            });

            //点击空白处清空已选
            node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                Node clickNode = e.getPickResult().getIntersectedNode();
                if (clickNode instanceof FlowPane && !(clickNode instanceof ImageLabel) && !(clickNode instanceof Text)) {// 鼠标点击非图片节点
                    ImageLabel.clearSelected();// 清空已选
                }
            });

        }
        return imageControlMenu;
    }
    public ContextMenu getTreeControlMenu(){
        return null;
    }
}
