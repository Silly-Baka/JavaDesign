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
    public static boolean isImageFile(File file){
        String fileName = file.getName().toLowerCase(Locale.ROOT);
        if(fileName.endsWith(".jpg")||fileName.endsWith("jpeg")||fileName.endsWith(".gif")
                ||fileName.endsWith("png")||fileName.endsWith(".bmp")){
            return true;
        }
        return false;
    }
}
