package design.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * User: 86176
 * Date: 2022/3/16
 * Time: 21:57
 * Description: 负责目录树的创建 以及目录树的获取
 */
public class TreeController {
    private static final Image image = new Image(TreeController.class.getResource("/img/directory.jpg").toString());
    private static final Image diskImage = new Image(TreeController.class.getResource("/img/disk.png").toString());
    private static final Image computerImage = new Image(TreeController.class.getResource("/img/computer.png").toString());

    private TreeItem<File> rootNode;

    private static Method setLeaf;

    public TreeController() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        //利用反射来访问setLeaf方法  用来调TreeItem的默认样式
        setLeaf = TreeItem.class.getDeclaredMethod("setLeaf", boolean.class);
        setLeaf.setAccessible(true);

        ImageView computerImageView = new ImageView(computerImage);
        computerImageView.setFitHeight(20);
        computerImageView.setFitWidth(20);
        rootNode = new TreeItem<>(new File("磁盘根目录"));
        rootNode.setGraphic(computerImageView);
        File[] files = File.listRoots();
        for (File file : files) {
            ImageView diskImageView = new ImageView(diskImage);
            diskImageView.setFitHeight(20);
            diskImageView.setFitWidth(20);
            TreeItem<File> treeNode = createTreeNode(file);
            treeNode.setGraphic(diskImageView);
            rootNode.getChildren().add(treeNode);
        }
    }
    /**
     * 创建目录树的结点
     * @param file 传入的文件
     * @return 返回一个节点
     */
    public static TreeItem<File> createTreeNode(File file) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
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
    public static ObservableList<TreeItem<File>> createChildrenNodes(TreeItem<File> parentNode) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
        // 获取目录文件
        File[] files = parentFile.listFiles(File::isDirectory);
        if(files != null){
            for (File file : files) {
                // 只有当文件是个目录时才会创建节点
//                if(file.isDirectory()){
//                }
//                childrenNodes.add(createTreeNode(file));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(20);
                imageView.setFitWidth(20);
                TreeItem<File> treeNode = new TreeItem<>(file,imageView);
                File[] childFiles = file.listFiles(File::isDirectory);
                if(childFiles!=null && childFiles.length > 0){
                    setLeaf.invoke(treeNode, false);
                }
                childrenNodes.add(treeNode);
            }
        }
        return childrenNodes;
    }

    /**
     * 当节点被点击时执行该方法  创建被点击节点的子节点 并设置
     * @param treeNode 被点击的结点
     */
    public static void createAndSetChildrenNodes(TreeItem<File> treeNode) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ObservableList<TreeItem<File>> childrenNodes = createChildrenNodes(treeNode);
        treeNode.getChildren().addAll(childrenNodes);
    }

    public TreeItem<File> getRootNode() {
        return rootNode;
    }
}
