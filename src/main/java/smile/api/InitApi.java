package smile.api;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import smile.api.model.TtwUrlModel;
import smile.common.util.ComparatorUtil;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * ©土土网 shop
 * qq:1413221142
 * 作者：王健(aupp)
 * 时间：2016-02-03
 */
@Component
public class InitApi implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = Logger.getLogger(InitApi.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        log.info("====================生成apihtml=====================");
        ApplicationContext applicationContext = event.getApplicationContext();
        String[] con1 = applicationContext.getBeanNamesForAnnotation(Api.class);
        String[] con2 = applicationContext.getBeanNamesForAnnotation(TtwApiResponse.class);
        String[] con3 = applicationContext.getBeanNamesForAnnotation(TtwApiMenu.class);
        Object[] con = ArrayUtils.addAll(ArrayUtils.addAll(con1, con2), con3);
//        String[] con = applicationContext.getBeanNamesForAnnotation(Controller.class);
        if (!ObjectUtils.isEmpty(con)) {
            Map<String, List<TtwUrlModel>> listMap = new HashMap();

            //遍历所有控制器
            for (Object c : con) {
                String s = String.valueOf(c);
                ArrayList list = new ArrayList();
                Object o = applicationContext.getBean(s);
                Class cla = o.getClass();
                if (AopUtils.isAopProxy(o)) {
//                    if (AopUtils.isJdkDynamicProxy(o)) {
//                        o = getJdkDynamicProxyTargetObject(o);
//                    } else { //cglib
//                        o = getCglibProxyTargetObject(o);
                    o = AopUtils.getTargetClass(o);
                    cla = (Class) o;
//                    }
                }
                String claString = cla.getName();
                /**
                 * 使用aop会导致，多一个父类
                 */
                if (!(claString.length() == claString.toUpperCase().indexOf(s.toUpperCase()) + s.length())) {
                    cla = cla.getSuperclass();
                }
                TtwUrlModel conTtwUrlModel = new TtwUrlModel();
                String clsMapping = "";
                /**
                 *取菜单标题
                 */
                if (cla.isAnnotationPresent(TtwApiMenu.class)) {
                    TtwApiMenu ttwApiMenu = (TtwApiMenu) cla.getAnnotation(TtwApiMenu.class);
                    conTtwUrlModel.className = ttwApiMenu.value();
                }

                if (cla.isAnnotationPresent(Api.class)) {
                    Api api = (Api) cla.getAnnotation(Api.class);
                    if (StringUtils.isEmpty(conTtwUrlModel.className)) {
                        conTtwUrlModel.className = api.value();
                    }
                }

                if (cla.isAnnotationPresent(TtwApiResponse.class)) {
                    TtwApiResponse ttwApiResponse = (TtwApiResponse) cla.getAnnotation(TtwApiResponse.class);
                    if (StringUtils.isEmpty(conTtwUrlModel.className)) {
                        conTtwUrlModel.className = ttwApiResponse.value();
                    }
                }
                if (StringUtils.isEmpty(conTtwUrlModel.className)) {
                    conTtwUrlModel.className = s;
                }

                /**
                 * 取url
                 */
                if (cla.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = (RequestMapping) cla.getAnnotation(RequestMapping.class);
                    clsMapping = requestMapping.value()[0];
                }

                /**
                 * 遍历方法
                 */
                for (Method method : cla.getMethods()) {
//                    method.getAnnotation(TtwApiRequest.class);
                    Api ttwApiHtml = method.getAnnotation(Api.class);
                    if (ObjectUtils.isEmpty(ttwApiHtml)) {
                        continue;
                    }

//                    if (!method.isAnnotationPresent(TtwApiRequest.class)) {
//                        continue;
//                    }
                    TtwUrlModel ttwUrlModel = new TtwUrlModel();
                    ttwUrlModel.className = conTtwUrlModel.className;
//                    TtwApiRequest ttwApiHtml = method.getAnnotation(TtwApiRequest.class);
                    ttwUrlModel.methodName = ttwApiHtml.value();
                    ttwUrlModel.params = ttwApiHtml.names();


                    if (method.isAnnotationPresent(TtwApiMenu.class)) {
                        TtwApiMenu ttwApiMenu = method.getAnnotation(TtwApiMenu.class);
                        ttwUrlModel.requestDesc = ttwApiMenu.requestDesc();
                        ttwUrlModel.responseDesc = ttwApiMenu.responseDesc();
                    }


                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    if (ObjectUtils.isEmpty(requestMapping)) {
                        ttwUrlModel.url = (clsMapping + "/" + method.getName());
                    } else {
                        ttwUrlModel.url = (clsMapping + requestMapping.value()[0]);
                    }
                    if (StringUtils.isEmpty(ttwUrlModel.methodName)) {
                        ttwUrlModel.methodName = method.getName();
                    }
                    ttwUrlModel.method = method;
                    Parameter[] parameters = method.getParameters();
                    for (Parameter parameter : parameters) {
//                        System.out.println(parameter.getType().getCanonicalName());
//                        System.out.println(parameter.getType());
                        if (parameter.isNamePresent()) {
//                            System.out.println(parameter.getName());
                        } else {
                            System.out.println("无法获取参数名称，请使用JDK8 添加 -parameters 后编译");
                        }
                    }
                    list.add(ttwUrlModel);
                }


                ComparatorUtil.sort(list, "methodName", false);
                listMap.put(conTtwUrlModel.className, list);
            }
            String webPath = ((WebApplicationContext) applicationContext).getServletContext().getRealPath("/api/");
            VelocityContext context = new VelocityContext();
            VelocityEngine ve = new VelocityEngine();
            try {
                Properties properties = new Properties();
                properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, webPath);
//                properties.setProperty(Velocity.MAX_NUMBER_LOOPS, "5");
                ve.init(properties);
                Template t = ve.getTemplate("api.vm", "utf-8");
                context.put("form", listMap);
                FileOutputStream fos = new FileOutputStream(webPath + "/api.html");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));//设置写入的文件编码,解决中文问题
                t.merge(context, writer);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        log.info("=======================生成apihtml完成=======================");
    }

    public static void main(String[] args) {
    }

}
