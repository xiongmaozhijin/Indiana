package com.example.liangge.indiana.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.liangge.indiana.R;
import com.example.liangge.indiana.biz.HomeBiz;
import com.example.liangge.indiana.biz.MessageManager;
import com.example.liangge.indiana.comm.LogUtils;
import com.example.liangge.indiana.comm.UIMessageConts;
import com.example.liangge.indiana.fragments.BaseFragment;
import com.example.liangge.indiana.fragments.IndianaFragment;
import com.example.liangge.indiana.fragments.LatestAnnouncementFragment;
import com.example.liangge.indiana.fragments.PersonalCenterFragment;
import com.example.liangge.indiana.fragments.ShoppingCartFragment;
import com.example.liangge.indiana.ui.widget.RotateImageView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends UIBaseActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private ViewPager mViewPager;

    //各个fragment对应viewpager的标签
    public static final int I_TAG_FRAGMENT_INDIANA = 0;
    public static final int I_TAG_FRAGMENT_LASTEST = 1;
    public static final int I_TAG_FRAGMENT_SHOPPING_CART = 2;
    public static final int I_TAG_FRAGMENT_PERSONAL_CENTER = 3;
    public static final int I_TAG_FRAGMENT_INVALID = -1;

    private static final int I_TAG_FRAGMENT_COUNTS = 4;

    private TextView mTxvTitlebarTitle;

    /** 夺宝界面Fragment中的搜索 **/
    private ImageView mIconSearch;

    /** 购物车界面的刷新加载icon */
    private RotateImageView mIconRefresh;

    /** 夺宝的Fragment */
    private IndianaFragment mIndianaFragment;

    /** 最新揭晓的Fragment */
    private LatestAnnouncementFragment mLastestAnnouncementFragment;

    /** 购物车的Fragment */
    private ShoppingCartFragment mShoppingCartFragment;

    /** 个人中心的Fragment */
    private PersonalCenterFragment mPersonalCenterFragment;

    /** Fragment 列表*/
    private List<Fragment> mListFragments = new ArrayList<>();

    private boolean[] mAlreadyEntry = new boolean[I_TAG_FRAGMENT_COUNTS];

    /** 夺宝菜单 */
    private RadioButton mRbIndaina;

    /** 最新揭晓菜单 */
    private RadioButton mRbLastest;

    /** 购物车菜单 */
    private RadioButton mRbShoppingCart;

    /** 个人中心菜单 */
    private RadioButton mRbPersonalCenter;

    /** 菜单RadioGroup */
    private RadioGroup mMenuRadioGroup;

    private MessageManager mMessageManager;

    private HomeBiz mHomeBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initHomeActivityManager();
        initRes();
    }

    private void initHomeActivityManager() {
        mHomeBiz = HomeBiz.getInstance(this);
    }

    @Override
    protected void handleUIMessage(String uiAction) {
        LogUtils.i(TAG, "handleUIMessage().uiAction=%s", uiAction);

        if (uiAction.equals(UIMessageConts.HomeActivity.HOME_ACTIVITY_M_REPLACE_FLAGMENT)) {
            handleReplaceFragment();
        }
    }

    private void handleReplaceFragment() {
        onGoToFragment(mHomeBiz.getReplaceFragmentFlag());
    }

    private void initView() {
        initBottomMenu();
        initViewPager();
        initOtherWidget();

    }

    private void initRes() {
        mMessageManager = MessageManager.getInstance(this);

    }

    private void initOtherWidget() {
        mTxvTitlebarTitle = (TextView) findViewById(R.id.titlebar_title);
        mIconSearch = (ImageView) findViewById(R.id.main_btn_search);
        mIconRefresh = (RotateImageView) findViewById(R.id.main_btn_refresh_loading);

    }

    private void initBottomMenu() {
        mMenuRadioGroup = (RadioGroup) findViewById(R.id.main_menu_rg);
        mRbIndaina = (RadioButton) findViewById(R.id.main_rb_indiana);
        mRbLastest = (RadioButton) findViewById(R.id.main_rb_latest_announcement);
        mRbShoppingCart = (RadioButton) findViewById(R.id.main_rb_shopping_card);
        mRbPersonalCenter = (RadioButton) findViewById(R.id.main_rb_personal_center);

    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mViewPager.setOffscreenPageLimit(4);    //TODO 暂时的简单解决方案
        mIndianaFragment = new IndianaFragment();
        mLastestAnnouncementFragment = new LatestAnnouncementFragment();
        mShoppingCartFragment = new ShoppingCartFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();
        mListFragments.add(mIndianaFragment);
        mListFragments.add(mLastestAnnouncementFragment);
        mListFragments.add(mShoppingCartFragment);
        mListFragments.add(mPersonalCenterFragment);

        mViewPager.setAdapter(new HomeFragmentAdapter(getSupportFragmentManager()));
    }


    public RotateImageView getShoppingCartRefrshIconView() {
        return this.mIconRefresh;
    }

    public RadioButton getShoppingCartBtn() {
        return this.mRbShoppingCart;
    }


    /**
     * @deprecated <br/>
     * use {@link #onGoToFragment }
     * 马上去夺宝，ShoppingCart click which btn
     */
    public void onFragmentBtnGoIndiana() {
        changeItemFragmentByButton(I_TAG_FRAGMENT_INDIANA, false);
    }


    /**
     * 跳转到指定的Fragment
     * @param fragmentFlag
     */
    private void onGoToFragment(int fragmentFlag) {
        changeItemFragmentByButton(fragmentFlag, false);
    }


    private void changeTitlebar(int iCurItem) {
        switch (iCurItem) {
            case I_TAG_FRAGMENT_INDIANA:
                mTxvTitlebarTitle.setText(R.string.main_titlebar_title_indiana);
                mIconSearch.setVisibility(View.VISIBLE);
                mIconRefresh.setVisibility(View.GONE);
                break;
            case I_TAG_FRAGMENT_LASTEST:
                mTxvTitlebarTitle.setText(R.string.main_titlebar_title_lastest_anno);
                mIconSearch.setVisibility(View.INVISIBLE);
                mIconRefresh.setVisibility(View.GONE);
                break;
            case I_TAG_FRAGMENT_SHOPPING_CART:
                mTxvTitlebarTitle.setText(R.string.main_titlebar_title_shopping_cart);
                mIconSearch.setVisibility(View.INVISIBLE);
                mIconRefresh.setVisibility(View.VISIBLE);
                break;
            case I_TAG_FRAGMENT_PERSONAL_CENTER:
                mTxvTitlebarTitle.setText(R.string.main_titlebar_title_personal_center);
                mIconSearch.setVisibility(View.INVISIBLE);
                mIconRefresh.setVisibility(View.GONE);
                break;
        }
    }


    public void onBtnIndiana(View view) {
        changeItemFragmentByButton(I_TAG_FRAGMENT_INDIANA, false);
        changeTitlebar(I_TAG_FRAGMENT_INDIANA);

    }

    public void onBtnLatestAnnouncement(View view) {
        changeItemFragmentByButton(I_TAG_FRAGMENT_LASTEST, false);
        changeTitlebar(I_TAG_FRAGMENT_LASTEST);

    }

    public void onBtnShoppingCart(View view) {
        changeItemFragmentByButton(I_TAG_FRAGMENT_SHOPPING_CART, false);
        changeTitlebar(I_TAG_FRAGMENT_SHOPPING_CART);

    }

    public void onBtnPersonalCenter(View view) {
        changeItemFragmentByButton(I_TAG_FRAGMENT_PERSONAL_CENTER, false);
        changeTitlebar(I_TAG_FRAGMENT_PERSONAL_CENTER);

    }


    private void changeItemFragmentByButton(int item, boolean scrollSmooth) {
        mViewPager.setCurrentItem(item, scrollSmooth);
    }


    public class HomeFragmentAdapter extends FragmentPagerAdapter {

        private int iPrevItem = I_TAG_FRAGMENT_INVALID;

        public HomeFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mListFragments.get(position);
        }

        @Override
        public int getCount() {
            if (mListFragments != null) {
                return mListFragments.size();
            }

            return 0;
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
            int iCurItem = mViewPager.getCurrentItem();

            if (iPrevItem != iCurItem) {
                handleFragmentReplace(iPrevItem, iCurItem);
                changeItemFragmentByVPScroll(iCurItem);
                iPrevItem = iCurItem;
            }
        }

        private void changeItemFragmentByVPScroll(int iCurItem) {
            LogUtils.e(TAG, "changeItemFragment.iCurItem = %d", iCurItem);

            switch (iCurItem) {
                case I_TAG_FRAGMENT_INDIANA:
                    mMenuRadioGroup.check(mRbIndaina.getId());
                    changeTitlebar(I_TAG_FRAGMENT_INDIANA);

                    break;
                case I_TAG_FRAGMENT_LASTEST:
                    mMenuRadioGroup.check(mRbLastest.getId());
                    changeTitlebar(I_TAG_FRAGMENT_LASTEST);

                    break;
                case I_TAG_FRAGMENT_SHOPPING_CART:
                    mMenuRadioGroup.check(mRbShoppingCart.getId());
                    changeTitlebar(I_TAG_FRAGMENT_SHOPPING_CART);

                    break;
                case I_TAG_FRAGMENT_PERSONAL_CENTER:
                    mMenuRadioGroup.check(mRbPersonalCenter.getId());
                    changeTitlebar(I_TAG_FRAGMENT_PERSONAL_CENTER);

                    break;
            }
        }
    }

    /**
     * 处理各个Fragment之间的切换回调
     * @param iPrevItem
     * @param iCurItem
     */
    private void handleFragmentReplace(int iPrevItem, int iCurItem) {
        LogUtils.w(TAG, "prevItem=%d, curItem=%d", iPrevItem, iCurItem);
        if (iPrevItem != I_TAG_FRAGMENT_INVALID) {
            BaseFragment prevFragment = (BaseFragment) mListFragments.get(iPrevItem);
            prevFragment.onLeft();

        }

        if (iCurItem != I_TAG_FRAGMENT_INVALID) {
            BaseFragment curFragment = (BaseFragment) mListFragments.get(iCurItem);
            if (mAlreadyEntry[iCurItem]) {
                curFragment.onEnter();

            } else {
                mAlreadyEntry[iCurItem] = true;
                curFragment.onFirstEnter();

            }

        }

    }

}
