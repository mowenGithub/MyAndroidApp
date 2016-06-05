package com.vincent.shaka.hybridlib;

import android.app.Activity;
import android.webkit.WebView;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mowenlong on 16-5-31.
 */
public class JSBridge {
    //  protocol://module/method?params&callback=
    private static final String JS_PROTOCAL_URI = "(\\w+)://(\\w+)/(\\w+)\\?([\\S, \\s]*)&callback=(\\w+)";
    private static Map<String, HashMap<String, Method>> exposedMethods = new HashMap<>();
    private static Pattern pattern = Pattern.compile(JS_PROTOCAL_URI);

    public static void register(String exposedName, Class<? extends IModule> clazz) {
        if (!exposedMethods.containsKey(exposedName)) {
            try {
                exposedMethods.put(exposedName, getAllMethod(clazz));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static HashMap<String, Method> getAllMethod(Class injectedCls) throws Exception {
        HashMap<String, Method> mMethodsMap = new HashMap<>();
        Method[] methods = injectedCls.getDeclaredMethods();
        for (Method method : methods) {
            String name;
            if (method.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC) || (name = method.getName()) == null) {
                continue;
            }
            Class[] parameters = method.getParameterTypes();
            if (null != parameters && parameters.length == 4) {
                if (parameters[0] == Activity.class && parameters[1] == WebView.class && parameters[2] == JSONObject.class && parameters[3] == Callback.class) {
                    mMethodsMap.put(name, method);
                }
            }
        }
        return mMethodsMap;
    }


    public static String callJava(Activity activity, WebView webView, String uriString) {

        String protocal = "";
        String moduleName = "";
        String methodName = "";
        String param = "";
        String callback = "";
        Matcher matcher = pattern.matcher(uriString.trim());
        if(matcher.find()) {
            protocal = matcher.group(1);
            moduleName = matcher.group(2);
            methodName = matcher.group(3);
            param = matcher.group(4);
            callback = matcher.group(5);
        }

        if (exposedMethods.containsKey(moduleName)) {
            HashMap<String, Method> methodHashMap = exposedMethods.get(moduleName);

            if (methodHashMap != null && methodHashMap.size() != 0 && methodHashMap.containsKey(methodName)) {
                Method method = methodHashMap.get(methodName);
                if (method != null) {
                    try {
                        if(callback.equals("0")) {  //无回调
                            if(param.equals("")) {
                                method.invoke(null, activity, webView, null, null);
                            }else{
                                method.invoke(null, activity, webView, new JSONObject(param), null);
                            }
                        } else {
                            if(param.equals("")) {
                                method.invoke(null, activity, webView, null, new Callback(webView, callback));
                            }else {
                                method.invoke(null, activity, webView, new JSONObject(param), new Callback(webView, callback));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
