package com.example;

import com.example.model.ActivityProductDetailInfoEntity;
import com.example.model.ActivityProductItemEntity;
import com.example.model.BannerInfo;
import com.example.model.BingoRecordEntity;
import com.example.model.IndianaRecordEntity;
import com.example.model.InventoryEntity;
import com.example.model.LastestBingoEntity;
import com.example.model.PayEntity;
import com.example.model.ResponseUserInfoEntitiy;
import com.example.model.UserInfoEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

public class MyClass {

    public static void main(String[] args) throws Exception {
//        gerationListBanner();
//        reGerationListBanner();
//        gerationListActivityProduct();
//        gerationListLatest();
//        gerationActivityEntity();

//        gerationProductDetailInfo();

//        gerationProductDetailInfo();

//        gerationIndianaRecordInfo();

//        dump();

//        gerationBingoRecordInfo();

//        gerationInventroyInfo();

//        reGerationInventroyInfo();
        
//        gerationUserInfo();

        jiami();

    }

    private static void jiami() {

    }


    public static String getToken2(long time, long userId, String token) {
        StringBuilder sb = new StringBuilder(token);
        String tempToken = token;
        String strTime = String.valueOf(time);
        String s1 = strTime.substring(strTime.length() - 3, strTime.length());
        long A = Integer.parseInt(s1) * userId;
        int index = (int) (A % 29);
        sb.insert(index, tempToken);
        String token2 = DigestUtils.sha1(sb.toString()).toString();

        System.out.println(String.format("token2=%s", token2));
        return token2;
    }


    private static void gerationUserInfo() {
        List<UserInfoEntity.UserAddress> addresses = new ArrayList<>();
        addresses.add(new UserInfoEntity.UserAddress("a", "119", "province", "city", "area", "detail", 1));
        addresses.add(new UserInfoEntity.UserAddress("a", "119", "province", "shenzhen", "area", "detail", 1));
        UserInfoEntity user = new UserInfoEntity(12, "name", "token", 21, "urlPhoto", "110", addresses);
        ResponseUserInfoEntitiy response = new ResponseUserInfoEntitiy(200, "succeess", user);
        Gson gson = new Gson();
        String json = gson.toJson(response);
        System.out.println(json);

        Gson gson1 = new Gson();
        ResponseUserInfoEntitiy r1 = gson1.fromJson(json, ResponseUserInfoEntitiy.class);
        System.out.println(r1.getData().getAddress().get(1).getCity());

    }

    private static void reGerationInventroyInfo() {
        InventoryEntity item1 = new InventoryEntity(21312, "imgUrl", "titleName", "dd", 3, 2, 32);
        InventoryEntity item2 = new InventoryEntity(21312, "imgUrl", "titleName", "dd", 3, 2, 32);
        InventoryEntity item3 = new InventoryEntity(21312, "imgUrl", "titleName", "dd", 3, 2, 32);

        List<InventoryEntity> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);

        PayEntity pay = new PayEntity();
        pay.setToken("token");
        pay.setUserId(1231);
        pay.setInventoryList(list);

        Gson gson = new Gson();
        String json = gson.toJson(pay);

        gson = new Gson();

        PayEntity p = gson.fromJson(json, PayEntity.class);

        System.out.println(p.toString());
    }

    private static void gerationInventroyInfo() {
        InventoryEntity item1 = new InventoryEntity(21312, "imgUrl", "titleName", "dd", 3, 2, 32);
        InventoryEntity item2 = new InventoryEntity(21312, "imgUrl", "titleName", "dd", 3, 2, 32);
        InventoryEntity item3 = new InventoryEntity(21312, "imgUrl", "titleName", "dd", 3, 2, 32);

        List<InventoryEntity> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);

        PayEntity pay = new PayEntity();
        pay.setToken("token");
        pay.setUserId(1231);
        pay.setInventoryList(list);

        Gson gson = new Gson();
        String json = gson.toJson(pay);

        System.out.println(json);
    }


    private static void gerationBingoRecordInfo() {
        BingoRecordEntity item1 = new BingoRecordEntity(312, "imgUrl1", "title", "tilteDesc", 321, 0, 21, "123", "123", 23213123);
        BingoRecordEntity item2 = new BingoRecordEntity(312, "imgUrl1", "title", "tilteDesc", 321, 0, 21, "123", "123", 23213123);
        List<BingoRecordEntity> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);

        Gson gson = new Gson();
        String jsonData = gson.toJson(list);

        System.out.println(jsonData);
    }

    private static void dump() {
        int[] temp = {1, 2, 4};
        Gson gson = new Gson();

        System.out.println(gson.toJson(temp));
    }

    private static void gerationIndianaRecordInfo() {
        IndianaRecordEntity item1 = new IndianaRecordEntity(2213, 1, "imgUrl", "titleDesc1", "23132", 213, 21, 21, 12, "23123", "libai", 2, "32432", 21323132);
        IndianaRecordEntity item2 = new IndianaRecordEntity(2213, 1, "imgUrl", "titleDesc1", "23132", 213, 21, 21, 12, "23123", "libai", 2, "32432", 21323132);
        List<IndianaRecordEntity> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);

        Gson gson = new Gson();
        String jsonData = gson.toJson(list);

        System.out.println(jsonData);
    }


    private static void sendPostJson() {

    }


    private static void gerationProductDetailInfo() {
        ActivityProductDetailInfoEntity item1 = new ActivityProductDetailInfoEntity(23214, new String[]{"imgUrl1", "imgUrl2", "imgUrl3"}, 1, "titleDescribe1", 31211, 2121, 21, true, "21231", new String[]{"productImgUrl1", "productImgUrl2", "productImgUrl3"}, 231231, "http：//userImg", "username", "userAddress", 5, "1232143");
//        ActivityProductDetailInfoEntity item2 = new ActivityProductDetailInfoEntity(23214, new String[]{}, 1, "titleDescribe1", 31211, 2121, 21, true, "21231", new String[]{}, 231231, "http：//userImg", "username", "userAddress", 5, "1232143");
//        ActivityProductDetailInfoEntity item3 = new ActivityProductDetailInfoEntity(23214, new String[]{}, 1, "titleDescribe1", 31211, 2121, 21, true, "21231", new String[]{}, 231231, "http：//userImg", "username", "userAddress", 5, "1232143");
//        List<ActivityProductDetailInfoEntity> list = new ArrayList<>();
//
//        list.add(item1);
//        list.add(item2);
//        list.add(item3);

        Gson gson = new Gson();
        String jsonStr = gson.toJson(item1);

        System.out.println(jsonStr);

        ActivityProductDetailInfoEntity activityProductDetailInfoEntity = gson.fromJson(jsonStr, ActivityProductDetailInfoEntity.class);
        System.out.println("item=" + activityProductDetailInfoEntity.toString());

    }


    private static void gerationActivityEntity() {
//        ActivityProductItemEntity item1 = new ActivityProductItemEntity(23214, "imgUrl1", "name1", "title1", 34, "34%", 1243, 321);
        long[] activytids = {12123, 3123, 34324321};
        List<Long> lists = new ArrayList<>();
        lists.add((long) 23123);
        lists.add((long) 424234);

        Gson gson = new Gson();

        String json1 = gson.toJson(activytids);
        String json2 = gson.toJson(lists);

        System.out.println(json1);
        System.out.println(json2);

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
        List<BannerInfo> list = gson.fromJson(strJson, new TypeToken<List<BannerInfo>>() {}.getType());
        System.out.println( String.format("size=%d, info0=%s", list.size(), list.get(0).toString()));
    }


    private static void gerationListActivityProduct() {
        ActivityProductItemEntity item1 = new ActivityProductItemEntity(23214, "imgUrl1", "name1", "title1", 34, "34%", 1243, 321);
        ActivityProductItemEntity item2 = new ActivityProductItemEntity(23214, "imgUrl1", "name1", "title1", 34, "34%", 1243, 321);
        ActivityProductItemEntity item3 = new ActivityProductItemEntity(23214, "imgUrl1", "name1", "title1", 34, "34%", 1243, 321);
        List<ActivityProductItemEntity> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);

        Gson gson = new Gson();
        String json = gson.toJson(list);
        System.out.println(json);
    }

    private static void gerationListLatest() {
        LastestBingoEntity item1 = new LastestBingoEntity("imgUrl", "titleDescribe", "bingoUser", "123121", 4, 2132141232, 0);
        LastestBingoEntity item2 = new LastestBingoEntity("imgUrl", "titleDescribe", "bingoUser", "123121", 4, 2132141232, 0);
        LastestBingoEntity item3 = new LastestBingoEntity("imgUrl", "titleDescribe", "bingoUser", "123121", 4, 2132141232, 0);
        LastestBingoEntity item4 = new LastestBingoEntity("imgUrl", "titleDescribe", "bingoUser", "123121", 4, 2132141232, 0);
        List<LastestBingoEntity> list = new ArrayList<>();
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);

        Gson gson = new Gson();
        String json = gson.toJson(list);

        System.out.println(json);
    }

}
