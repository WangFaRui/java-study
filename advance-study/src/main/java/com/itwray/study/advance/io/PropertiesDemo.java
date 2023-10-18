package com.itwray.study.advance.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * 基于 {@link java.util.Properties} 示例
 *
 * @author Wray
 * @since 2023/10/7
 */
public class PropertiesDemo {

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        String propertiesName = "jdbc.properties";

        // 获取文件流
        InputStream inputStream = getInputStreamByClassLoader(propertiesName);
        // InputStream inputStream = getInputStreamByClass();
        // InputStream inputStream = getFileInputStream();

        properties.load(inputStream);
        String property = properties.getProperty("jdbc.driver");
        System.out.println(property);
    }

    private static InputStream getInputStreamByThread(String name) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }

    private static InputStream getSystemResourceAsStream(String name) {
        return ClassLoader.getSystemResourceAsStream(name);
    }

    private static InputStream getInputStreamBySystemClassLoader(String name) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(name);
    }

    private static InputStream getInputStreamByClassLoader(String name) {
        return PropertiesDemo.class.getClassLoader().getResourceAsStream(name);
    }

    private static InputStream getInputStreamByClass() {
        return PropertiesDemo.class.getResourceAsStream("./../jdbc.properties");
    }

    private static InputStream getFileInputStream() throws IOException {
        return Files.newInputStream(Paths.get("/Users/wangfarui/workspaces/wfr/java-study/advance-study/src/main/resources/jdbc.properties"));
    }
}
