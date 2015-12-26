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

    /**
     * 登录注册
     */
    public interface LogSignIn {
        /** 验证码错误 */
        int SIGNIN_VERTIATIONCODE_ERROR = 412;

        /** 手机号已存在 */
        int SIGNIN_HAVE_BEEN_SIGNIN = 406;

        /** 注册成功 */
        int SIGNIN_SUCCESS = 200;

        /** 登录失败 */
        int LOG_FAILED = 412;

        /** 登录成功 */
        int LOG_SUCCESS = 200;


    }

    /**
     * 订单支付
     */
    public interface InventoryPay {

        int ORDER_PAY_RESULT_CODE_SUCCESS = 200;

    }



    public interface WebServiceAPI {

        public static final String WEBHOST = "http://192.168.1.199";

        /** 夺宝页活动详情 */
        public static final String INDIANA_GOODS_LIST_API = "http://192.168.1.199/index.php/Admin/Api/goodslist";

        /** 产品活动详情 */
        public static final String INDIANA_ACTIVITY_DETAIL_INFO = "http://192.168.1.199/index.php/Admin/Api/goodsdetail";

        /** 最新揭晓详情 */
        public static final String LATEST_PRODUCT_INFO = "http://192.168.1.199/index.php/Admin/Api/goodstoreveal";

        /** 参与记录 */
        public static final String PLAY_INDIANA_RECORD = "http://192.168.1.199/index.php/Admin/Api/participation";

        /** 中奖记录 */
        public static final String BINGO_RECORED = "http://192.168.1.199/index.php/Admin/Api/prize";

        /** Banner 轮播图片*/
        public static final String BANNER_INFO = "http://192.168.1.199/index.php/Admin/Api/banner";

        /** 获取验证码 */
        public static final String REQUEST_VETICATION_CODE = WEBHOST + "/index.php/Admin/Api/getVerificationCode";

        /** 注册接口 */
        public static final String REQUEST_SIGININ = WEBHOST + "/index.php/Admin/Api/register";

        /** 登录接口 */
        public static final String REQUEST_LOG = WEBHOST + "/index.php/Admin/Api/login";




    }


}
