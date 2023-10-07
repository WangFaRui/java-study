package io;

import java.util.ResourceBundle;

/**
 * 基于 {@link ResourceBundle} 示例
 *
 * @author Wray
 * @since 2023/10/7
 */
public class ResourceBundleDemo {

    public static void main(String[] args) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config.jdbc");
        String driver = resourceBundle.getString("jdbc.driver");
        System.out.println(driver);
    }
}
