package smile.common.util;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.ObjectUtils;
import smile.common.model.Child;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ©土土网 shop
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-05-17
 */
public class ConvertUtil {
    /**
     * 需要先转成tree，才能使用这个方法
     * 把tree转列表
     *
     * @param models
     * @return
     */
    public static List toOption(List<? extends Child> models, String levelFormatStr) {
        List temp = new ArrayList();
        _toOption(models, 0, temp, levelFormatStr);
        models.clear();
        models.addAll(temp);
        return temp;
    }

    /**
     * 递归分类
     *
     * @param models
     * @param level
     * @param container
     */
    private static void _toOption(List<? extends Child> models, int level, List container, String isLevelFormat) {
        for (Child child : models) {
            String nbsp = "";
            for (int i = 0; i < level; i++) {
                nbsp += isLevelFormat;
            }
            if (!org.springframework.util.StringUtils.isEmpty(isLevelFormat)) {
                child.setOptionName(nbsp + child.getOptionName());
            }
            container.add(child);
            _toOption(child.getChild(), level + 1, container, isLevelFormat);
            child.getChild().clear();
        }
    }

    /**
     * 集合转成树结构
     *
     * @param models
     */
    public static void toTree(List<? extends Child> models) {
        _toTree(models, null);
    }

    /**
     * 转成树结构
     *
     * @param models
     * @param temp
     */
    private static void _toTree(List<? extends Child> models, List temp) {
        if (ObjectUtils.isEmpty(temp)) {
            temp = models;
        }
        for (Child model : models) {
            List<Child> child = findChild(temp, model.getId(), model.getLevel() + 1);
            model.setChild(child);
            _toTree(child, temp);
        }
        for (int i = 0; i < models.size(); i++) {
            if (models.get(i).getLevel() == 0 && models.get(i).getParentId() > 0) {
                models.remove(i);
                i--;
            }
        }
    }

    /**
     * 查找子类
     *
     * @param list
     * @param parentId
     * @param level
     * @return
     */
    private static List findChild(List<? extends Child> list, int parentId, int level) {
        List<Child> temp = new ArrayList();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getParentId() == parentId) {
//                try {
                Child child = null;
                try {
                    child = (Child) BeanUtils.cloneBean(list.get(i));
                } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                child.setLevel(level);
                temp.add(child);
//                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
//                    e.printStackTrace();
//                }
            }
        }
        return temp;
    }

//    private void addChild(List<ArticleCategoryModel> models, Integer level) {
//        for (ArticleCategoryModel model : models) {
//            List<ArticleCategoryModel> child = categoryModelMapper.selectAllByParentId(model.getId());
//            model.setLevel(level);
//            model.setChild(child);
//            if (!ObjectUtils.isEmpty(child)) {
//                addChild(child, level+1);
//            }
//
//        }
//    }


    /**
     * 格式成表格字段样式
     *
     * @param field
     * @return
     */
    public static String toTableStyle(String field) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < field.length(); i++) {
            if (field.charAt(i) >= 'A' && field.charAt(i) <= 'Z') {
                buffer.append("_");
            }
            buffer.append(field.charAt(i));
        }
        return buffer.toString().toLowerCase();
    }

    public static void main(String[] args) {
        BigDecimal volumn = new BigDecimal(700);
        System.out.println(volumn.multiply(new BigDecimal("0.7")));
    }
}
