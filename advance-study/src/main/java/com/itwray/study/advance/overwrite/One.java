package com.itwray.study.advance.overwrite;

/**
 * Description
 *
 * @author wangfarui
 * @since 2024/6/11
 */
public class One {

    protected One getNewOne(One old) {
        System.out.println("One");
        return new One();
    }
}
