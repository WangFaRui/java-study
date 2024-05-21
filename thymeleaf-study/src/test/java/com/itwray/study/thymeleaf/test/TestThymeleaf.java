package com.itwray.study.thymeleaf.test;

import org.junit.jupiter.api.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;

/**
 * Description
 *
 * @author wangfarui
 * @since 2024/5/21
 */
public class TestThymeleaf {

    @Test
    public void test() throws Exception {
        //thymleaf模板引擎
        TemplateEngine engine = new TemplateEngine();
        //读取磁盘中的模板文件
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        //路径
        resolver.setPrefix("thymeleaf/");
        //后缀
        resolver.setSuffix(".java");
        //设置模板模式、默认是HTML
        resolver.setTemplateMode("TEXT");
        //设置引擎使用 resolve
        engine.setTemplateResolver(resolver);
        //准备数据 使用context
        Context context = new Context();
        //添加基本类型
        String className = "Student";
        String methodName = "hello";
        String packagePath = "com/itwray/study/thymeleaf";
        String packageName = packagePath.replace("/", ".");
        context.setVariable("className", className);
        context.setVariable("methodName", methodName);
        context.setVariable("packageName", packageName);
        context.setVariable("flag", true);
        String ms = engine.process("IF_TEST", context);
        //获取输入流
        ByteArrayInputStream in = new ByteArrayInputStream(ms.getBytes());
        System.out.printf(ms);
        //项目路径
        String projectPath = System.getProperty("user.dir");
        String fiePath = projectPath + "/src/main/java/" + packagePath + "/" + className + ".java";
        FileOutputStream out = new FileOutputStream(fiePath);
        byte[] bytes = new byte[8 * 1024];
        int len = 0;
        while ((len = in.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
    }
}
