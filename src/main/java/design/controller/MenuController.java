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

    private ImagePreviewViewController imagePreviewViewController;

    private Node node;

    private ImagePreviewController imagePreviewController;

    private ContextMenu imageControlMenu;

    private ContextMenu treeControlMenu;

    private KeyCodeCombination copyKey;

    private KeyCodeCombination pasteKey;

    private KeyCodeCombination deleteKey;

    private CopyController copyController;

    private PasteController pasteController;

    private DeleteController deleteController;

    private RenameController renameController;

    private TipsController tipsController;

    public MenuController(){
        copyKey = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        pasteKey = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
        deleteKey = new KeyCodeCombination(KeyCode.DELETE);
    }
    public MenuController (ImagePreviewViewController imagePreviewViewController,TipsController tipsController) {
        this();
        this.imagePreviewViewController = imagePreviewViewController;
        this.tipsController = tipsController;
    }

    /**
     * @return 获取图片的右键菜单
     */
    public ContextMenu getImageControlMenu(ImagePreviewController imagePreviewController){
        if(imageControlMenu == null){
            MenuItem menuItem1 = new MenuItem("复制");
            MenuItem menuItem2 = new MenuItem("粘贴");
            MenuItem menuItem3 = new MenuItem("删除");
            MenuItem menuItem4 = new MenuItem("重命名");
            imageControlMenu = new ContextMenu(menuItem1,menuItem2,menuItem3,menuItem4);

            copyController = new CopyController();
            pasteController = new PasteController(imagePreviewController);
            deleteController = new DeleteController(imagePreviewViewController);
            renameController = new RenameController(imagePreviewController);

            menuItem1.setAccelerator(copyKey);
            menuItem2.setAccelerator(pasteKey);
            menuItem3.setAccelerator(deleteKey);

            menuItem1.setOnAction(e->{
                copyController.copyAction();
            });

            menuItem2.setOnAction(e->{
                pasteController.pasteAction();
            });

            menuItem3.setOnAction(e->{
                deleteController.deleteAction();
            });

            menuItem4.setOnAction(e->{
                renameController.renameAction();
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

            //点击空白处清空已选
            node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                Node clickNode = e.getPickResult().getIntersectedNode();
                if (clickNode instanceof FlowPane && !(clickNode instanceof ImageLabel) && !(clickNode instanceof Text)) {// 鼠标点击非图片节点
                    ImageLabel.clearSelected();// 清空已选
                    tipsController.setSelectedCount(ImageLabel.getSelectedPictures().size());
                }
            });

        }
        return imageControlMenu;
    }
    public ContextMenu getTreeControlMenu(){
        return null;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
