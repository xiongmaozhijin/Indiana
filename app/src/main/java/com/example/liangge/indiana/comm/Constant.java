package com.example.liangge.indiana.comm;

/**
 * Created by baoxing on 2015/12/23.
 */
public class Constant {

    public interface SharedPerfer {

        /** 是否登录了 */
        String KEY_IS_LOGIN = "KEY_IS_LOGIN";

        String KEY_TOKEN = "KEY_TOKEN";

        String KEY_USERID = "KEY_USERID";

        /** 下载的apk id */
        String KEY_APK_DOWNLOAD_ID = "KEY_APK_DOWNLOAD_ID";

    }




    public interface Comm {
        int MODE_REFRESH = 1;
        int MODE_ENTER = 2;
        int MODE_LOAD_MORE = 3;

        int LOAD_MORE_SUCCESS = 100;
        int LOAD_MORE_START = 101;
        int LOAD_MORE_FAILED = 102;

        int NET_FAILED_NO_NET = -1;
        int NET_LOADING = 1;
        int NET_SUCCESS = 2;


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

    /**
     * 最新揭晓
     */
    public interface LatestFragment {
        /** 已揭晓 */
        int CODE_ALREADY_RUN = 0;
        /** 等待揭晓(揭晓中) */
        int CODE_RUNNING = 2;
        /** 计算中 */
        int CODE_CLAC_ING = 3;

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


    /**
     * 个人中心
     */
    public interface PersonalCenter {

        //请求用户信息返回状态码
        int REQUEST_USER_INFO_RESULT_CODE_SUCCESS = 200;
        int REQUEST_USER_INFO_RESULT_CODE_FAILED = 400;


    }





    public interface WebServiceAPI {

        public static final String WEBHOST = "http://121.201.63.76";

        /** 夺宝页活动详情 */
        public static final String INDIANA_GOODS_LIST_API = WEBHOST + "/index.php/Admin/Api/goodslist";

        /** 产品活动详情 */
        public static final String INDIANA_ACTIVITY_DETAIL_INFO = WEBHOST + "/index.php/Admin/Api/goodsdetail";

        /** 最新揭晓详情 */
        public static final String LATEST_PRODUCT_INFO = WEBHOST + "/index.php/Admin/Api/goodstoreveal";

        /** 参与记录 */
        public static final String PLAY_INDIANA_RECORD =  WEBHOST + "/index.php/Admin/Api/participation";

        /** 中奖记录 */
        public static final String BINGO_RECORED = WEBHOST +"/index.php/Admin/Api/prize";

        /** Banner 轮播图片*/
        public static final String BANNER_INFO = WEBHOST + "/index.php/Admin/Api/banner";

        /** 获取验证码 */
        public static final String REQUEST_VETICATION_CODE = WEBHOST + "/index.php/Admin/Api/getVerificationCode";

        /** 注册接口 */
        public static final String REQUEST_SIGININ = WEBHOST + "/index.php/Admin/Api/register";

        /** 登录接口 */
        public static final String REQUEST_LOG = WEBHOST + "/index.php/Admin/Api/login";

        /** 订单支付接口 */
        public static final String REQUEST_PAY_ORDERS = WEBHOST + "/index.php/Admin/Api/shopping";

        /** 请求用户信息接口 */
        String REQUEST_USER_INFO = WEBHOST + "/index.php/Admin/Api/personalData";

        /** 一期活动用户参与记录 */
        String REQUEST_ACTIVITY_PLAY_RECORDS = WEBHOST + "/index.php/Admin/Api/issueRecord";

        /** 计算详细 */
        String REQUEST_CALC_DETAIL_INFO = WEBHOST + "/index.php/Admin/Api/calculation";

        /** 请求有什么类别 */
        String REQUEST_CATEGORY = WEBHOST + "/index.php/Admin/Api/category";

        /**请求类别详细*/
        String REQUEST_CATEGORY_DETAIL =  WEBHOST + "/index.php/Admin/Api/categoryGoods";

        /** 十元专区接口 */
        String REQUEST_CATEGORY_TEN_YUAN_DETAIL = WEBHOST + "/index.php/Admin/Api/tenUnit";


        /** 常见问题接口 */
        String REQUEST_QAQ = WEBHOST + "/index.php/Admin/Api/question";

        /** 更新个人信息 */
        String REQUEST_UPDATE_USER_INFO = WEBHOST + "/index.php/Admin/Api/saveUserData";

        /** 往期揭晓 */
        String REQUEST_HISTORY_RECORD = WEBHOST + "/index.php/Admin/Api/previousActivity";

        /** 热门搜索请求 */
        String REQUEST_HOT_SEARCH = WEBHOST + "/index.php/Admin/Api/keyWords";

        /** 商品搜索请求 */
        String REQUEST_SEARCH_PRODUCT = WEBHOST + "/index.php/Admin/Api/search";

        /** 获取消息通知接口 */
        String REQUEST_NOTIFICATION = WEBHOST + "/index.php/Admin/Api/newestResult";

        /** 检查更新 */
        String REQUEST_CHECK_UPDATE = WEBHOST + "/index.php/check.php";

        /** 关于一元夺宝 */
        String REQUEST_ABOUT_INDAINA = WEBHOST + "/index.php/about_indiana";

        /** 相关通知信息 */
        String REQUEST_NOTIFY_INFO = WEBHOST + "/index.php/notify_info";

        /** 检查更新 */
        String REQUEST_CHECK_UPDATE_APP = WEBHOST + "/index.php/Admin/Api/checkAppUpdate";

        /** 添加晒单 */
        String REQUEST_ADD_SHARE_ORDERS = WEBHOST + "/index.php/Admin/Api/prizeShowSave";

        /** 晒单接口 */
        String REQUEST_SHARE_ORDERS = WEBHOST + "/index.php/shaidan";

        String ERROR_404 = WEBHOST + "/index.php/404.php";


    }


}
