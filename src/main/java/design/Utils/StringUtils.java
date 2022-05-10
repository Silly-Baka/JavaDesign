package design.Utils;

import java.io.File;
import java.util.Locale;

/**
 * User: 86176
 * Date: 2022/3/18
 * Time: 20:42
 * Description:
 */
public class StringUtils {
    /**
     * 判断传入的文件是否是图片文件
     * @param fileName 需要判断的文件名字
     * @return 返回是否是图片文件
     */
    public static boolean isImageFile(String fileName){
        String lowerName = fileName.toLowerCase(Locale.ROOT);
        if(lowerName.endsWith(".jpg")||lowerName.endsWith("jpeg")||lowerName.endsWith(".gif")
                ||lowerName.endsWith("png")||lowerName.endsWith(".bmp")){
            return true;
        }
        return false;
    }
}
