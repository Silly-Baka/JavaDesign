import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * User: 86176
 * Date: 2022/3/20
 * Time: 11:12
 * Description:
 */
public class test01 {
    @Test
    public void test011(){
        long size = 39035;
        BigDecimal totalSize = new BigDecimal(size);
        BigDecimal divi = new BigDecimal(1024*1024);

        BigDecimal imageTotalSize = totalSize.divide(divi,4,RoundingMode.HALF_EVEN);

        System.out.println(size);
        System.out.println(imageTotalSize);
        System.out.println(imageTotalSize.doubleValue());

        BigDecimal bigDecimal = imageTotalSize.setScale(4, RoundingMode.HALF_EVEN);
        System.out.println(bigDecimal);
    }
}
