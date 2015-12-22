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

    public interface IndianaMessage {
        /** 正在加载轮播图片 */
        public static final String MESSAGE_LOADING_BANNER = "message_indiannamessage_loading_banner";

        public static final String MESSAGE_LOAD_BANNER_FAIL = "message_indianamessage_loading_banner_fail";

        public static final String MESSAGE_LOAD_BANNER_SUCCESS = "message_indianamessage_loading_baner_success";

        /** 正在加载商品数据 */
        public static final String MESSAGE_LOADING_PRODUCT_DATA = "message_indianamessage_loading_product";

        public static final String MESSAGE_LOAD_PRODUCT_DATA_FAIL = "message_indiannamessage_loading_product_fail";

        public static final String MESSAGE_LOAD_PRODUCT_DATA_SUCCESS = "message_indianamessage_loading_product_successd";




    }

    public interface LatestAnnouncementMessage {

        public static final String MESSAGE_LOADING_PRODUCT_DATA = "message_latest_loading_productdata";

        public static final String MESSAGE_LOAD_PRODUCT_DATA_FAILED = "message_latest_load_failed";

        public static final String MESSAGE_LOAD_PRODUCT_DATA_SUCCEED = "message_latest_load_success";


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


    }








}
