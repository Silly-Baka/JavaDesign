package design.Controller;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * User: 86176
 * Date: 2022/3/19
 * Time: 18:53
 * Description: 负责菜单组件的创建以及获取
 */
public class MenuController {

    private ContextMenu imageControlMenu;

    private ContextMenu treeControlMenu;

    /**
     *
     * @return 获取图片的右键菜单
     */
    public ContextMenu getImageControlMenu(){
        if(imageControlMenu == null){
            MenuItem menuItem1 = new MenuItem("复制");
            MenuItem menuItem2 = new MenuItem("粘贴");
            MenuItem menuItem3 = new MenuItem("删除");
            MenuItem menuItem4 = new MenuItem("重命名");
            imageControlMenu = new ContextMenu(menuItem1,menuItem2,menuItem3,menuItem4);
        }
        return imageControlMenu;
    }
    public ContextMenu getTreeControlMenu(){
        return null;
    }
}
