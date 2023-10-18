package com.itwray.study.third.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class JsoupExample {
    public static void main(String[] args) {
        try {
            // 发起 HTTP GET 请求并获取页面内容
            Document doc = Jsoup.connect("https://example.com").get();

            // 从页面中选择元素并提取数据
            Element titleElement = doc.selectFirst("title");
            String title = titleElement.text();
            System.out.println("页面标题: " + title);

            Elements linkElements = doc.select("a");
            for (Element linkElement : linkElements) {
                String linkText = linkElement.text();
                String linkHref = linkElement.attr("href");
                System.out.println("链接文本: " + linkText);
                System.out.println("链接地址: " + linkHref);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}