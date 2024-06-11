package com.itwray.study.advance.overwrite;

/**
 * Description
 *
 * @author wangfarui
 * @since 2024/6/11
 */
public class Two extends One {

    @Override
    public Two getNewOne(One old) {
        System.out.println("two");
        return new Two();
    }
}
