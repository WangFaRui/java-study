package com.itwray.study.advance.structure;

import java.io.IOException;
import java.util.Properties;

/**
 * 项目结构
 *
 * @author Wray
 * @since 2023/10/7
 */
public class ProjectStructure {

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        // 注意路径的写法
        props.load(ProjectStructure.class.getClassLoader().getResourceAsStream("jdbc.properties"));
        String driver = props.getProperty("jdbc.driver");
        System.out.println(driver);

        // 查询项目根目录
        System.out.println(System.getProperty("user.dir"));
    }
}
