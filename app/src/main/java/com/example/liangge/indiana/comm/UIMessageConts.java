package com.example.liangge.indiana.comm;

/**
 * Created by baoxing on 2015/12/15.
 */
public class UIMessageConts {

    public static final String UI_MESSAGE_ACTION = "com.example.liangge.indiana.ui_message_action";

    public static final String UI_MESSAGE_KEY = "com.example.liangge.indiana.ui_message_key";

    public interface CommResponse {

        /** 没有网络，没有连接网络 */
        public static final String MESSAGE_COMM_NO_NETWORK = "message_comm_no_network";

    }


    public interface HomeActivity {

        /** 切换Fragment */
        String HOME_ACTIVITY_M_REPLACE_FLAGMENT = "HOME_ACTIVITY_M_REPLACE_FLAGMENT";

    }




    public interface IndianaMessage {

        String PREV= "IndianaMessage";

        /** 正在加载轮播图片 */
        public static final String MESSAGE_LOADING_BANNER = "message_indiannamessage_loading_banner";

        public static final String MESSAGE_LOAD_BANNER_FAIL = "message_indianamessage_loading_banner_fail";

        public static final String MESSAGE_LOAD_BANNER_SUCCESS = "message_indianamessage_loading_baner_success";

        /** 正在加载商品数据 */
        public static final String MESSAGE_LOADING_PRODUCT_DATA = "message_indianamessage_loading_product";

        public static final String MESSAGE_LOAD_PRODUCT_DATA_FAIL = "message_indiannamessage_loading_product_fail";

        public static final String MESSAGE_LOAD_PRODUCT_DATA_SUCCESS = "message_indianamessage_loading_product_successd";

        /** 开始加载子分标签的数据 */
        public static final String MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_START = "m_log_tag_activity_product_info_start";

        public static final String MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_FAIL = "m_log_tag_activity_product_info_fail";

        public static final String MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_SUCCESS = "m_log_tag_activity_product_info_success";

        /** 刷新加载 */
        String MSG_REFRESH_START = PREV + "MSG_REFRESH_START";
        String MSG_REFRESH_SUCCESS = PREV + "MSG_REFRESH_SUCCESS";
        String MSG_REFRESH_FAILED = PREV + "MSG_REFRESH_FAILED";


        /** 开始加载相应分标签<b>更多</b>的数据 */
        public static final String MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_START = "MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_START";

        public static final String MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_FAIL = "MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_FAIL";

        public static final String MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_SUCCESS = "MSG_LOAD_TAG_ACTIVITY_PRODUCT_INFO_MORE_SUCCESS";

        /** 加载消息数据成功 */
        String MSG_LOAD_NOTICATION_SUCCESS = "MSG_LOAD_NOTICATION_SUCCESS";

    }

    public interface LatestAnnouncementMessage {

        //加载
        public static final String MESSAGE_LOADING_PRODUCT_DATA = "message_latest_loading_productdata";

        public static final String MESSAGE_LOAD_PRODUCT_DATA_FAILED = "message_latest_load_failed";

        public static final String MESSAGE_LOAD_PRODUCT_DATA_SUCCEED = "message_latest_load_success";

        //加载更多
        public static final String MESSAGE_LOADING_PRODUCT_DATA_MORE = "message_latest_loading_productdata_more";

        public static final String MESSAGE_LOAD_PRODUCT_DATA_FAILED_MORE = "message_latest_load_failed_more";

        public static final String MESSAGE_LOAD_PRODUCT_DATA_SUCCEED_MORE = "message_latest_load_success_more";

        /** 更新中奖用户信息 */
        public static final String MSG_UPDATE_BINGO_INFO = "msg_update_bingo_info";

    }


    /**
     * 晒单消息
     */
    public interface ShareOrdersMessage {

        String PREV = "ShareOrdersMessage";

        String NET_START = PREV + "NET_START";
        String NET_SUCCESS = PREV + "NET_SUCCESS";
        String NET_FAILED = PREV + "NET_FAILED";

        String LOAD_MORE_NET_START = PREV + "LOAD_MORE_NET_START";
        String LOAD_MORE_NET_SUCCESS = PREV + "LOAD_MORE_NET_SUCCESS";
        String LOAD_MORE_NET_FAILED = PREV + "LOAD_MORE_NET_FAILED";


        String REFRESH_SUCCESS = PREV + "REFRESH_SUCCESS";
        String REFRESH_FAILED = PREV + "REFRESH_FAILED";
    }


    public interface AddShareOrdersMessage {
        String PREV = "AddShareOrdersMessage";
        /** 处理图片 */
        String DEAL_IMGS = PREV + "DEAL_IMGS";
    }


    public interface ShoppingCartMessage {

        /** 重置列表ui */
        public static final String M_RESET_UPDATE_LISTS = "m_reset_update_lists";

        /** 购物车为空 */
        public static final String M_EMPTY_ORDERS = "m_empty_orders";

        /** 网络查询失败 */
        public static final String M_QUERY_ORDERS_FAILED = "m_query_orders_failed";

        /** 网络查询成功 */
        public static final String M_QUERY_ORDERS_SUCCESS = "m_query_orders_success";

        /** 查询操作开始 */
        public static final String M_QUERY_ORDERS_STARTS = "m_query_orders_start";

        /** 更新购物车商品数量 */
        public static final String M_UPDATE_SHOPPINGCART_ITEM_COUNTS = "m_update_shoppingcart_item_count";

        /** 更新购物车商品数量，不要扭图标 */
        public static final String M_UPDATE_SHOPPINGCART_ITEM_COUNTS_WITHOUT_SHAKE = "m_update_shoppingcart_item_counts_without_shake";

        /** 取消显示购物车的BadgeVieww */
        public static final String M_DISMISS_SHOPPINGCART_ITEM_COUNTS_ICON = "m_dismiss_shopping_item_counts_icon";

        /** 更新结算信息 */
        public static final String M_UPDATE_PAY_INFO = "m_update_pay_info";

    }

    public interface PersonalCenterMessage {
        /** 初始化登录信息 */
        public static final String M_INIT_LOGIN_INFO = "m_init_login_info";

        //退出
        public static final String M_LOGOUT_START = "M_LOGOUT_START";
        public static final String M_LOGOUT_FAILED = "M_LOGOUT_FAILED";
        public static final String M_LOGOUT_SUCCESS = "M_LOGOUT_SUCCESS";

        /** 更新用户信息 */
        String PERSONALCENTER_M_UPDATE_USER_INFO = "PERSONALCENTER_M_UPDATE_USER_INFO";


    }


    /**
     * 商品（奖品）详情
     */
    public interface DetailInfo {

        /** 奖品信息开始请求 */
        public static final String M_DETAILINFO_PRODUCT_ACTIVITY_REQ_START = "m_detailinfo_product_activity_req_start";

        /** 奖品信息请求成功 */
        public static final String M_DETAILINFO_PRODUCT_ACTIVITY_REQ_SUCCEED = "m_detailinfo_product_activity_req_success";

        /** 奖品信息请求失败 */
        public static final String M_DETAILINFO_PRODUCT_ACTIVITY_REQ_FAILED = "m_detailinfo_product_activity_req_failed";

        /** 是否参与开始请求 */
        public static final String M_DETAILINFO_REQ_WHETHER_PLAY_REQ_START = "m_detailinfo_req_whether_play_req_start";

        /** 是否参与请求成功 */
        public static final String M_DETAILINFO_REQ_WHETHER_PLAY_REQ_SUCCESS = "m_detailinfo_req_whether_play_req_success";

        /** 是否参与请求失败 */
        public static final String M_DETAILINFO_REQ_WHETHER_PLAY_REQ_FAILED = "m_detailinfo_req_whether_play_req_failed";

        /** 开始请求参与记录 */
        public static final String M_DETAILINFO_REQ_PLAYRECORD_REQ_START = "m_detailinfo_req_playrecord_req_start";

        /** 请求参与记录成功 */
        public static final String M_DETAILINFO_REQ_PLAYRECORED_SUCCESSED = "m_detailinfo_req_playrecord_success";

        /** 请求参与记录失败 */
        public static final String M_DETAILINFO_REQ_PLAYRECORED_FAILED = "m_detailinfo_req_playrecord_failed";


        /** 开始请求参与记录，更多 */
        public static final String M_DETAILINFO_REQ_PLAYRECORD_REQ_START_MORE = "M_DETAILINFO_REQ_PLAYRECORD_REQ_START_MORE";

        /** 请求参与记录成功，更多 */
        public static final String M_DETAILINFO_REQ_PLAYRECORED_SUCCESSED_MORE = "M_DETAILINFO_REQ_PLAYRECORED_SUCCESSED_MORE";

        /** 请求参与记录失败，更多 */
        public static final String M_DETAILINFO_REQ_PLAYRECORED_FAILED_MORE = "M_DETAILINFO_REQ_PLAYRECORED_FAILED_MORE";

        /** 加入购物车提示 */
        String M_DETAIL_UPDATE_ADD_TO_SHOPPONGCART_HINT = "M_DETAIL_UPDATE_ADD_TO_SHOPPONGCART_HINT";


    }


    /**
     *  订单支付
     */
    public interface InventoryPay {

        /** 初始化订单信息 */
        public static final String M_INIT_INVENTORY_INFO = "m_init_inventory_info";

        /** 初始化支付状态 */
        String M_INIT_INVETORY_STATE = "M_INIT_INVETORY_STATE";

        //订单支付网络请求结果
        String INVENTORY_PAY_START = "INVENTORY_PAY_START";
        String INVENTORY_PAY_SUCCESS = "INVENTORY_PAY_SUCCESS";
        String INVENTORY_PAY_FAILED = "INVENTORY_PAY_FAILED";


    }

    /**
     * 夺宝记录
     */
    public interface IndianaRecord {

        /** 重新加载/初始化加载 开始 */
        public static final String M_RELOAD_START = "m_reload_IndianaRecord_start";
        public static final String M_RELOAD_SUCCESS = "m_reload_IndianaRecord_success";
        public static final String M_RELOAD_FAIL = "m_reload_IndianaRecord_fail";

        /** 加载更多 开始 */
        public static final String M_RELOAD_START_MORE = "m_reload_IndianaRecord_start_more";
        public static final String M_RELOAD_SUCCESS_MORE = "m_reload_IndianaRecord_success_more";
        public static final String M_RELOAD_FAIL_MORE = "m_reload_IndianaRecord_fail_more";

    }

    /**
     * 中奖记录
     */
    public interface BingoRecord {

        /** 重新加载/初始化加载 开始 */
        public static final String M_RELOAD_START = "m_reload_BingoRecord_start";
        public static final String M_RELOAD_SUCCESS = "m_reload_BingoRecord_success";
        public static final String M_RELOAD_FAIL = "m_reload_BingoRecord_fail";

        /** 加载更多 开始 */
        public static final String M_RELOAD_START_MORE = "m_reload_BingoRecord_start_more";
        public static final String M_RELOAD_SUCCESS_MORE = "m_reload_BingoRecord_success_more";
        public static final String M_RELOAD_FAIL_MORE = "m_reload_BingoRecord_fail_more";

    }


    /**
     * 登录或注册
     */
    public interface LogSignIn {

        /** 注册 */
        public static final String M_LOGSINGIN_SINGIN_START = "M_LOGSINGIN_SINGIN_START";
        public static final String M_LOGSINGIN_SINGIN_SUCCESS = "M_LOGSINGIN_SINGIN_SUCCESS";
        public static final String M_LOGSINGIN_SINGIN_FAILED = "M_LOGSINGIN_SINGIN_FAILED";


        /** 登录 */
        public static final String M_LOGSINGIN_LOG_START = "M_LOGSINGIN_LOG_START";
        public static final String M_LOGSINGIN_LOG_SUCCESS = "M_LOGSINGIN_LOG_SUCCESS";
        public static final String M_LOGSINGIN_LOG_FAILED = "M_LOGSINGIN_LOG_FAILED";


        /** 验证码 */
        public static final String M_LOGSINGIN_VETICATIONCODE_START = "M_LOGSINGIN_VETICATIONCODE_START";
        public static final String M_LOGSINGIN_VETICATIONCODE_SUCCESS = "M_LOGSINGIN_VETICATIONCODE_SUCCESS";
        public static final String M_LOGSINGIN_VETICATIONCODE_FAILED = "M_LOGSINGIN_VETICATIONCODE_FAILED";


    }


    public interface CategoryDetailMessage {
        String PREV = "CategoryDetailMessage";
        //下拉刷新
        String REFRESH_SUCCESS = PREV + "REFRESH_SUCCESS";
        String REFRESH_FAILED = PREV + "REFRESH_FAILED";
        //进入刷新
        String ENTERY_REFRESH_START = PREV + "ENTERY_REFRESH_START";
        String ENTERY_REFRESH_SUCCESS = PREV + "ENTERY_REFRESH_SUCCESS";
        String ENTERY_REFRESH_FAILED = PREV + "ENTERY_REFRESH_FAILED";

        //加载更多
        String LOAD_MORE_START = PREV + "LOAD_MORE_START";
        String LOAD_MORE_SUCCESS = PREV + "LOAD_MORE_SUCCESS";
        String LOAD_MORE_FAILED = PREV + "LOAD_MORE_FAILED";

        String LOAD_START = PREV + "LOAD_START";
        String LOAD_FAILED = PREV + "LOAD_FAILED";
        String LOAD_SUCCESS = PREV + "LOAD_SUCCESS";

    }



    public interface Comm_Activity {
        String COMM_NET_START = "COMM_NET_START";
        String COMM_NET_FAILED = "COMM_NET_FAILED";
        String COMM_NET_SUCCESS = "COMM_NET_SUCCESS";

        /** 检查app更新 */
        String COMM_CHECK_APP_UPDATE_SUCCESS = "COMM_CHECK_APP_UPDATE_SUCCESS";
    }




}
