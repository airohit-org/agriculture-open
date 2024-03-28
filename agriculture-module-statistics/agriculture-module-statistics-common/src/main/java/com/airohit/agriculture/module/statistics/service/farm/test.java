package com.airohit.agriculture.module.statistics.service.farm;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IDEA
 *
 * @author :shiminghao
 * @date :2023/10/17 10:22
 */
public class test {
    public static void main(String[] args) {
//        System.out.println(test.translate("你好"));
//        System.out.println(test.picture("/Users/minghaoshi/Desktop/1/123.png"));
        System.out.println(test.voicetrans("/Users/minghaoshi/Desktop/1/iat_mp3_8k.m4a"));
    }

    public static String translate(String q) {
        String appid = "20190613000307038";
        String secret = "c3zBlSYdMswy30rOsX0Y";
        String salt = String.valueOf(System.currentTimeMillis());
        String url = "https://fanyi-api.baidu.com/api/trans/vip/translate";
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("q", q);
        map.put("from", "auto");
        map.put("to", "ru");
        map.put("appid", appid);
        map.put("salt", salt);
        map.put("sign", SecureUtil.md5(appid + q + salt + secret));
        return JSONObject.parseObject(HttpUtil.get(url, map)).toJSONString();
    }

    public static String picture(String url) {
        String appid = "20190613000307038";
        String secret = "c3zBlSYdMswy30rOsX0Y";
        String salt = String.valueOf(System.currentTimeMillis());
        String apiUrl = "https://fanyi-api.baidu.com/api/trans/sdk/picture";
        HttpRequest post = HttpUtil.createPost(apiUrl);
        post.form("image", new File(url));
        post.form("from", "auto");
        post.form("to", "ru");
        post.form("appid", appid);
        post.form("salt", salt);
        post.form("cuid", "APICUID");
        post.form("mac", "mac");
        post.form("version", "3");
        post.form("paste", 1);
        post.form("erase", 0);
        post.form("sign", SecureUtil.md5(appid + SecureUtil.md5(new File(url)).toLowerCase() + salt + "APICUID" + "mac" + secret).toLowerCase());
        JSONObject jsonObject = JSONObject.parseObject(post.execute().body());
        BufferedImage image = ImgUtil.toImage(jsonObject.getJSONObject("data").getString("pasteImg"));
        File outputFile = new File("/Users/minghaoshi/Desktop/1/123_out.png");
        try {
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String voicetrans(String url) {
        String appid = "20190613000307038";
        String secret = "c3zBlSYdMswy30rOsX0Y";
        String salt = String.valueOf(System.currentTimeMillis() / 1000);
        String apiUrl = "https://fanyi-api.baidu.com/api/trans/v2/voicetrans";
        File file = new File(url);
        HMac hMac = SecureUtil.hmacSha256(secret);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("from", "zh");
        jsonObject.put("to", "ru");
        jsonObject.put("format", "m4a");
        jsonObject.put("voice", SecureUtil.md5(file));

        HttpRequest post = HttpUtil.createPost(apiUrl);
        post.header("Content-Type", "application/json");
        post.header("X-Appid", appid);
        post.header("X-Timestamp", salt);
        post.header("X-Sign", hMac.digestBase64(appid + salt + SecureUtil.md5(file), false));
        post.body(jsonObject.toJSONString());
        return JSONObject.parseObject(post.execute().body()).toJSONString();
    }
}
