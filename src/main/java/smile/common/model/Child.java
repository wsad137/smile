package smile.common.model;


import java.util.List;

/**
 * ©土土网 shop
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-05-16
 */
public interface Child {


    List getChild();

    void setChild(List child);

    int getLevel();

    void setLevel(int level);

    Integer getParentId();

    void setParentId(Integer parentId);

    Integer getId();

    void setOptionName(String optionName);

    /**
     *
     * 事例 StringUtils.isEmpty(this.optionName) ? this.name : this.optionName;
     *
     * @return
     */
    String getOptionName();
}
