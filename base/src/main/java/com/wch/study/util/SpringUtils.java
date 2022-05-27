package com.wch.study.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/5/27 16:08
 */
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }

    /**
     * 获取request header
     * */
    public static String getReqHeader(String headerName) {
        HttpServletRequest request = getRequest();
        return request.getHeader(headerName);
    }

    /**
     * 获取response header
     * */
    public static String getResHeader(String headerName) {
        HttpServletResponse response = getResponse();
        return response.getHeader(headerName);
    }

    /**
     * 设置response header
     * */
    public static void setResHeader(String headerName, String headerVal) {
        HttpServletResponse response = getResponse();
        response.setHeader(headerName, headerVal);
    }

    /**
     * 设置request attribute
     * */
    public static void setReqAttr(String key, Object val) {
        HttpServletRequest request = getRequest();
        request.setAttribute(key, val);
    }

    /**
     * 获取request attribute
     * */
    public static <T> T getReqAttr(String key, Class<T> val) {
        HttpServletRequest request = getRequest();
        return (T) request.getAttribute(key);
    }

    /**
     * 获取request
     * */
    public static HttpServletRequest getRequest(){
        HttpServletRequest request = null;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes != null){
            request = requestAttributes.getRequest();
        }
        return request;
    }

    /**
     * 获取response
     * */
    public static HttpServletResponse getResponse(){
        HttpServletResponse response = null;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes != null){
            response = requestAttributes.getResponse();
        }
        return response;
    }

    /**
     * 获取session
     * */
    public static HttpSession getSession(){
        HttpServletRequest request = getRequest();
        if(request != null){
            return request.getSession();
        }
        return null;
    }

    /**
     * 获取session
     * */
    public static HttpSession getSession(boolean flag){
        HttpServletRequest request = getRequest();
        if(request != null){
            return request.getSession(flag);
        }
        return null;
    }

    /**
     * 获取客户端地址
     * */
    @SuppressWarnings("AlibabaUndefineMagicConstant")
    public static String getIpAddr() {
        HttpServletRequest request = getRequest();
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if ("127.0.0.1".equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }

        return ipAddress;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return SpringUtils.applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> tClass) throws BeansException {
        return (T) applicationContext.getBean(tClass);
    }
}
