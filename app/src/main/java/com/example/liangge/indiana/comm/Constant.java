package com.example.liangge.indiana.comm;

/**
 * Created by baoxing on 2015/12/23.
 */
public class Constant {

    public interface SharedPerfer {

        /** 是否登录了 */
        public static final String KEY_IS_LOGIN = "key_is_login";


    }


    public interface IndianaFragment {

        /** 人气 */
        public static final String TAG_HOTS = "hot";

        /** 最新 */
        public static final String TAG_NEWS = "new";

        /** 总需 */
        public static final String TAG_SHARE = "share";

        /** 进度 */
        public static final String TAG_PROGRESS = "progress";



    }

    public interface IndianaRecord {
        /** 全部 */
        public static final String TAG_ALL = "all";
        /** 进行中 */
        public static final String TAG_ING = "progress";
        /** 已揭晓 */
        public static final String TAG_DONE = "revealed";
    }


    public interface WebServiceAPI {

        /** 夺宝页活动详情 */
        public static final String INDIANA_GOODS_LIST_API = "http://192.168.1.199/index.php/Admin/Api/goodslist";

        /** 产品活动详情 */
        public static final String INDIANA_ACTIVITY_DETAIL_INFO = "http://192.168.1.199/index.php/Admin/Api/goodsdetail";

        /** 最新揭晓详情 */
        public static final String LATEST_PRODUCT_INFO = "http://192.168.1.199/index.php/Admin/Api/goodstoreveal";

        /** 参与记录 */
        public static final String PLAY_INDIANA_RECORD = "http://192.168.1.199/index.php/Admin/Api/participation";

    }


}
