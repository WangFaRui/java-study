package com.itwray.study.advance.overwrite;

/**
 * Description
 *
 * @author wangfarui
 * @since 2024/6/11
 */
public class Three extends Two {

    @Override
    public Two getNewOne(One old) {
        if (old instanceof Two) {
            System.out.println("from two");
        }
        System.out.println("three");
        return new Three();
    }
}
