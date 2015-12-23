package com.example;

import com.example.model.BannerInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MyClass {

    public static void main(String[] args) throws Exception {
//        gerationListBanner();
//        reGerationListBanner();


    }


    private static void gerationListBanner() {
        BannerInfo item1 = new BannerInfo("www.baidu.com/1img.png", "www.baidu.com/seemore", "title1", "search word", 2102192, 1);
        BannerInfo item2 = new BannerInfo("www.baidu.com/1img.png", "www.baidu.com/seemore", "title1", "search word", 2102192, 2);
        BannerInfo item3 = new BannerInfo("www.baidu.com/1img.png", "www.baidu.com/seemore", "title1", "search word", 2102192, 3);
        List<BannerInfo> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);

        Gson gson = new Gson();
        String jsonGson = gson.toJson(list);

        System.out.println(jsonGson);
    }

    private static void reGerationListBanner() {
        String strJson = "[{\"imgUrl\":\"www.baidu.com/1img.png\",\"linkUrl\":\"www.baidu.com/seemore\",\"title\":\"title1\",\"keyword\":\"search word\",\"activityId\":2102192,\"action\":1},{\"imgUrl\":\"www.baidu.com/1img.png\",\"linkUrl\":\"www.baidu.com/seemore\",\"title\":\"title1\",\"keyword\":\"search word\",\"activityId\":2102192,\"action\":2},{\"imgUrl\":\"www.baidu.com/1img.png\",\"linkUrl\":\"www.baidu.com/seemore\",\"title\":\"title1\",\"keyword\":\"search word\",\"activityId\":2102192,\"action\":3}]";
        Gson gson = new Gson();
        List<BannerInfo> list = gson.fromJson(strJson, new TypeToken<List<BannerInfo>>(){}.getType());
        System.out.println( String.format("size=%d, info0=%s", list.size(), list.get(0).toString()));
    }









}
