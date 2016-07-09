package smile.common.util;

import org.apache.commons.lang.math.RandomUtils;

/**
 * ©土土网 shop
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-06-12
 */
public class CommonUtil {
    /**
     * 补随机数
     *
     * @param count
     * @return
     */
    public static String patchRandom(int count) {
        String temp = "";
        for (int i = 0; i < count; i++) {
            temp += RandomUtils.nextInt(9);
        }
        return temp;
    }
}
