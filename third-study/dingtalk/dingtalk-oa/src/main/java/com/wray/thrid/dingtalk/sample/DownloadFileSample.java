package com.wray.thrid.dingtalk.sample;

import okhttp3.*;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description
 *
 * @author Wray
 * @since 2023/8/28
 */
public class DownloadFileSample {

    public static void main(String[] args) {
        String url = ""; // 调用获取下载信息接口获取的internalResourceUrls。
        String path = "/Users/xxxx/Downloads/20230828-123.jpg"; // 文件要下载的目标路径。
        // 调用获取下载信息接口得到的headers信息。
        Map<String, String> headers = new HashMap<>(); //调用下载信息接口获取的headers。
        headers.put("Authorization", "token");
        headers.put("x-oss-date", "Mon, 28 Aug 2023 02:31:14 GMT");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Sink sink = null;
                BufferedSink bufferedSink = null;
                try {
                    File dest = new File(path);
                    sink = Okio.sink(dest);
                    bufferedSink = Okio.buffer(sink);
                    bufferedSink.writeAll(response.body().source());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedSink != null) {
                        bufferedSink.close();
                    }
                    if (sink != null) {
                        sink.close();
                    }
                    System.out.println("下载完成");
                }
            }
        });

        System.out.println("执行完成");
    }
}
