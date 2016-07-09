package smile.api.model;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * ©土土网 shop
 * qq:1413221142
 * 作者：王健(aupp)
 * 时间：2016-02-18
 */
public class TtwParamModel {
    /**
     * 参数名称
     */
    public String name = "";
    /**
     * 参数类型
     */
//    public TtwParamEnum type = TtwParamEnum.STRING;
    public String type = "text";
    /**
     * 参数说明
     */
    public String desc = "";
    /**
     * 默认值
     */
    public String def = "";

    public TtwParamModel(String initStr) {
        if (!StringUtils.isEmpty(initStr)) {
            String[] temp = initStr.split("/");
            if (!ObjectUtils.isEmpty(temp)) {
                for (int i = 0; i < temp.length; i++) {
                    if (StringUtils.isEmpty(temp[i])) {
                        continue;
                    }
                    switch (i) {
                        case 0:
                            this.name = temp[i];
                            break;
                        case 1:
                            this.desc = temp[i];
                            break;
                        case 2:
                            this.type = temp[i];
                            break;
                        case 3:
                            this.def = temp[i];
//                            //如果默认值等于time 返回时间戳
//                            this.def = (temp[i].equals("time") ? System.currentTimeMillis() + "" : temp[i]);
                            break;
                    }

                }
            }
        }


    }

    private TtwParamModel() {

    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getDef() {
        return def;
    }
}


