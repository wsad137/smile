package smile.common.util;


import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ©土土网 shop
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-05-31
 */
public class SortUtil {
    /**
     * 按拼音首字母排序
     *
     * @param list 需要排序的集合
     * @param name 排序依据
     */
    public static void abcAsc(List list, String name) {
        List def = new ArrayList();
        if (ObjectUtils.isEmpty(list, name)) return;

        List<String> temp = new ArrayList();

        for (int i = 0; i < list.size(); i++) {
            try {
                String property = BeanUtils.getSimpleProperty(list.get(i), name);

                property = Pinyin4jUtil.toPinying(property);
                temp.add(property + "_" + i);
                System.out.println(property);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(temp, Collator.getInstance(java.util.Locale.CHINA));
//        Arrays.sort(temp.toArray(),Collator.getInstance(Locale.CHINA));

        for (String s : temp) {
            int i = Integer.parseInt(s.split("_")[1]);
            def.add(list.get(i));
        }

        list.clear();
        list.addAll(def);
    }

    public static void main(String[] args) {
//        System.out.println(StringUtils.isEmpty(""));
//        System.out.println(ObjectUtils.isEmpty(""));
    }
}
