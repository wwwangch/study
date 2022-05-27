package com.wch.study.es8.interceptor;

import cn.hutool.core.util.ReflectUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @Author wch
 * @Version 1.0
 * @Date 2022/5/27 14:45
 */
public class ElasticsearchInterceptor implements HttpRequestInterceptor {

    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {

        StringBuilder sb = new StringBuilder();

        sb.append(request.getRequestLine().getMethod());
        sb.append(" ");
        sb.append(request.getRequestLine().getUri());
        HttpEntity entity = (HttpEntity) ReflectUtil.getFieldValue(request, "entity");
        if (null != entity && entity.getContent().available() > 0) {
            sb.append("\n");
            String s = new String(entity.getContent().readAllBytes());
            sb.append(s);
        }
        System.out.println(sb);
        // 记录一下审计语句，当前只记录了成功的
        ThreadLocal<String> auditStatement = getAuditStatement();
        if (auditStatement != null) {
            String audit = auditStatement.get();
            if (StringUtils.isNotEmpty(audit)) {
                audit += ";";
            } else {
                audit = "";
            }
            audit += sb;
            auditStatement.set(audit);
        }

    }


    private ThreadLocal<String> getAuditStatement() {
        try {
            Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass("cn.ac.iscas.dmo.db.invoke.InvokeHelper");
            Field field = aClass.getDeclaredField("AUDIT_STATEMENT");
            field.setAccessible(true);
            return (ThreadLocal<String>) field.get(null);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }
}
