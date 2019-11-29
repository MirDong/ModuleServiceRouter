package com.dzk.shell.moduleservice.core;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;


/**
 * 模块服务者，利用动态代理实现解耦
 * ModuleServicer.getDefault().create(ModuleStub.class).testMethod("oh this from mainActivity!",getApplicationContext(), textView);
 */
public class ModuleServicer {
    private static final String TAG = "ModuleServicer";
    private HashMap<String,String>mProtocals;
    private HashMap<Class<?>,Object>mShadowBeanMap = new HashMap<>();
    private HashMap<Class<?>, InvocationHandler>mInvocationHandlers = new HashMap<>();
    static class Holder{
        static ModuleServicer instance = new ModuleServicer();
    }

   public ModuleServicer(){

    }

    public static ModuleServicer getDefault(){
        return Holder.instance;
    }

    /**加载协议表
     * @param context
     * @param assetFile
     */
    public void init(Context context, String assetFile){
        try {
            mProtocals = ProtocolParser.parse(context,assetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**入口
     * @param stub interface
     * @param <T> clazz
     * @return  clazz obj
     */
    public <T> T create(Class<T> stub){
        if (mShadowBeanMap.get(stub) != null){
            return (T) mShadowBeanMap.get(stub);
        }

        InvocationHandler handler = null;
        try {
            handler = findHander(stub);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        T result = (T) Proxy.newProxyInstance(stub.getClassLoader(),new Class[]{stub},handler);
        mShadowBeanMap.put(stub,result);
        return result;
    }

    private  InvocationHandler findHander(Class stub) throws ClassNotFoundException,RuntimeException{
        if (mInvocationHandlers.keySet().contains(stub)){
            return mInvocationHandlers.get(stub);
        }

        //获取目标类的全路径
        String targetClassName;
        if (stub.isAnnotationPresent(ServiceTarget.class)){
            ServiceTarget target = (ServiceTarget) stub.getAnnotation(ServiceTarget.class);
            targetClassName = target.value();
        }else {
            targetClassName = mProtocals.get(stub.getName());
        }
        if (targetClassName == null || targetClassName.isEmpty() || targetClassName.equals("null")){
            throw new RuntimeException("error! targetClassName is null");
        }

        Log.d(TAG, "findHander->targetClassName="+targetClassName);

        final Class<?> clazz = Class.forName(targetClassName);
        Object targetObj = null;
        try {
            targetObj = clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        if (targetObj == null){
            throw new RuntimeException("targetObj is null");
        }

        final Object action = targetObj;
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Method realMethod = clazz.getDeclaredMethod(method.getName(),method.getParameterTypes());
                realMethod.setAccessible(true);
                return realMethod.invoke(action,args);
            }
        };
        mInvocationHandlers.put(stub,handler);
        return handler;
    }
}
