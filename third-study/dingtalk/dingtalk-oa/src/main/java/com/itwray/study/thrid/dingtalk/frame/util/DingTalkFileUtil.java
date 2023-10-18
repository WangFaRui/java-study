package com.itwray.study.thrid.dingtalk.frame.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 钉钉FileUtil
 *
 * @author wangfarui
 * @since 2023/8/17
 */
public abstract class DingTalkFileUtil {

    private static final String HTTP_PREFIX = "http";

    private static final String fileUrlPrefix = "www.itwray.com";

    /**
     * 获取文件流
     * <p>需要自己关闭流</p>
     */
    public static InputStream getInputStream(String fileUrl) {
        fileUrl = appendUrlPrefix(fileUrl);
        try {
            URL urlObj = new URL(fileUrl);
            URLConnection urlConnection = urlObj.openConnection();
            urlConnection.setConnectTimeout(30 * 1000);
            urlConnection.setReadTimeout(60 * 1000);
            urlConnection.setDoInput(true);
            return urlConnection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String appendUrlPrefix(String fileUrl) {
        if (StringUtils.isBlank(fileUrl) || fileUrl.contains(HTTP_PREFIX)) {
            return fileUrl;
        }
        return fileUrlPrefix.concat(fileUrl);
    }
}
