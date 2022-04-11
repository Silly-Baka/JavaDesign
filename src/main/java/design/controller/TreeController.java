package design.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * User: 86176
 * Date: 2022/3/16
 * Time: 21:57
 * Description: 负责目录树的创建 以及目录树的获取
 */
public class TreeController {
    private static final Image image = new Image(TreeController.class.getResource("/img/directory.jpg").toString());

    private MenuController menuController;

    private TreeItem<File> rootNode;

    public TreeController(){
        rootNode = new TreeItem<>(new File(""));
        File[] files = File.listRoots();
        for (File file : files) {
            TreeItem<File> treeNode = createTreeNode(file);
            rootNode.getChildren().add(treeNode);
        }
    }
    public TreeController(MenuController menuController){
        this();
        this.menuController = menuController;
    }
    /**
     * 创建目录树的结点
     * @param file 传入的文件
     * @return 返回一个节点
     */
    public static TreeItem<File> createTreeNode(File file){
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        TreeItem<File> treeNode = new TreeItem<>(file,imageView);

        ObservableList<TreeItem<File>> childrenNodes = createChildrenNodes(treeNode);

        treeNode.getChildren().addAll(childrenNodes);

        return treeNode;
    }
    /**
     * 创建当前节点的子节点
     * @param parentNode 当前节点
     * @return 返回子节点的数组
     */
    public static ObservableList<TreeItem<File>> createChildrenNodes(TreeItem<File> parentNode){
        File parentFile = parentNode.getValue();
        // 如果文件不存在 或 文件不为目录 都返回空的子节点数组
        if(!parentFile.exists()){
            return FXCollections.emptyObservableList();
        }
        if(parentFile.isFile()){
            return FXCollections.emptyObservableList();
        }
        // 如果该节点是个目录文件
        ObservableList<TreeItem<File>> childrenNodes = FXCollections.observableArrayList();
        File[] files = parentFile.listFiles();
        if(files != null){
            for (File file : files) {
                // 只有当文件是个目录时才会创建节点
                if(file.isDirectory()){
//                    childrenNodes.add(createTreeNode(file));
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(16);
                    imageView.setFitWidth(16);
                    TreeItem<File> treeNode = new TreeItem<>(file,imageView);
                    childrenNodes.add(treeNode);
                }
            }
        }
        return childrenNodes;
    }
    public static void selectedActon(TreeItem<File> treeNode){
        ObservableList<TreeItem<File>> childrenNodes = createChildrenNodes(treeNode);
        treeNode.getChildren().addAll(childrenNodes);
    }

    public TreeItem<File> getRootNode() {
        return rootNode;
    }
}
