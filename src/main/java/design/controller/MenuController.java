package design.controller;

import design.viewController.ImagePreviewViewController;
import design.model.ImageLabel;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

/**
 * User: 86176
 * Date: 2022/3/19
 * Time: 18:53
 * Description: 负责菜单组件的创建以及获取
 */
public class MenuController {
    public static ContextMenu imageControlMenu;

    public static ContextMenu treeControlMenu;

    public static final KeyCodeCombination copyKey = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);

    public static final KeyCodeCombination pasteKey = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);

    public static final KeyCodeCombination deleteKey = new KeyCodeCombination(KeyCode.DELETE);

    public static final KeyCodeCombination renameKey = new KeyCodeCombination(KeyCode.R,KeyCombination.CONTROL_DOWN);

    public static final ActionController actionController = new ActionController();

    public MenuController(){

    }

    /**
     * @return 获取图片的右键菜单
     */
    public static ContextMenu getImageControlMenu(ImagePreviewController imagePreviewController,ImagePreviewViewController imagePreviewViewController){
        if(imageControlMenu == null){
            MenuItem menuItem1 = new MenuItem("复制");
            MenuItem menuItem2 = new MenuItem("粘贴");
            MenuItem menuItem3 = new MenuItem("删除");
            MenuItem menuItem4 = new MenuItem("重命名");
            imageControlMenu = new ContextMenu(menuItem1,menuItem2,menuItem3,menuItem4);

            menuItem1.setAccelerator(copyKey);
            menuItem2.setAccelerator(pasteKey);
            menuItem3.setAccelerator(deleteKey);
            menuItem4.setAccelerator(renameKey);

            menuItem1.setOnAction(e->{
                actionController.copyAction();
            });

            menuItem2.setOnAction(e->{
                actionController.pasteAction(imagePreviewController);
            });

            menuItem3.setOnAction(e->{
                actionController.deleteAction(imagePreviewViewController);
            });

            menuItem4.setOnAction(e->{
                actionController.renameAction(imagePreviewController);
            });

            // ContextMenu自带的 不用写这个
//            node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
//                if (e.getButton() == MouseButton.SECONDARY)
//                    imageControlMenu.show(node, e.getScreenX(), e.getScreenY());
//                else {
//                    if (imageControlMenu.isShowing())
//                        imageControlMenu.hide();
//                }
//            });

        }
        return imageControlMenu;
    }
    public ContextMenu getTreeControlMenu(){
        return null;
    }
}
