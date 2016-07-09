package smile.api.model;

import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * ©土土网 shop
 * qq:1413221142
 * 作者：王健(aupp)
 * 时间：2016-02-03
 */
public class TtwUrlModel {
    public String className;
    public String url;
    public String methodName;
    public Method method;

    public String requestDesc = "";
    public String responseDesc = "";

    public String getRequestDesc() {
        return requestDesc;
    }

    public void setRequestDesc(String requestDesc) {
        this.requestDesc = requestDesc;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public String[] getResponseNames() {
        return responseNames;
    }

    public void setResponseNames(String[] responseNames) {
        this.responseNames = responseNames;
    }

    public String[] responseNames;


    public String getClassName() {
        return className;
    }

    public String getUrl() {
        return url;
    }

    public String getMethodName() {
        return methodName;
    }

    public Method getMethod() {
        return method;
    }

    public String[] getParams() {
        return params;
    }

    public String[] params;


    private List<TtwParamModel> responseParamModels = new ArrayList<TtwParamModel>();

    private List<TtwParamModel> paramModels = new ArrayList<TtwParamModel>();

    public List<TtwParamModel> getResponseParamModels() {
        return toParamModel(responseNames, responseParamModels, method);
    }

    public List<TtwParamModel> getParamModels() {
        return toParamModel(params, paramModels, method);
    }

    private List<TtwParamModel> toParamModel(String[] responseNames, List<TtwParamModel> paramModels, Method method) {
        if (!ObjectUtils.isEmpty(responseNames)) {
            for (String s : responseNames) {
                paramModels.add(new TtwParamModel(s));
            }
        } else {
            for (Parameter parameter : method.getParameters()) {
                paramModels.add(new TtwParamModel(parameter.getName()));
            }
        }

        return paramModels;
    }
}
