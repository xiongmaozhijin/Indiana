/*
package com.example.liangge.indiana.ui.test;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


*/
/**
 * @author xinspace 自定义菜单栏
 *//*

public class CustomizeMenu extends LinearLayout {
	private static final String TAG = "CustomizeMenu";
	private MenuItem mPreviousPressedItem;// 保存前一个被按下的菜单按钮
	// 四个菜单按钮
	private MenuItem mFeaturedItem;
	private MenuItem mThemeItem;
	private MenuItem mWallpaperItem;
    private MenuItem mFontItem;
    // 监听器
    private OnMenuClickListener mMenuClickListener;

	public CustomizeMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.layout_styleshop_menu, this, true);

		loadViews();
		initStatus();
		setupViews();
	}

	*/
/**
	 * 变化菜单按钮的按下状态
     *
     * @param menuItem
	 *            菜单按钮
	 * *//*

	public void switchPressed(MenuItem menuItem) {
		if (mPreviousPressedItem == menuItem) {
			// 如果点击的是已经按下的菜单，则不做任何操作
			return;
		}

		if (mPreviousPressedItem != null) {
			// 先把之前按下的菜单恢复为正常布局
			mPreviousPressedItem.switchStatus();
		}
		// 再把当前被按下的菜单转换为按下布局
		menuItem.switchStatus();
		// 保存被按下的菜单，在转换时会恢复这个菜单
		mPreviousPressedItem = menuItem;
	}

	*/
/**
	 * 设置菜单点击事件监听器
     *
     * @param listener
	 *            监听器
	 * *//*

	public void setOnMenuClickListener(OnMenuClickListener listener) {
		mMenuClickListener = listener;
	}

    */
/**
     * @author xinspace
     * created at 12/04/15 14:45
     * 设置按下菜单项的背景颜色
     *//*

    public void setPressedItemBackground(MenuItem menuItem, int color) {
        if (menuItem != null) {
            menuItem.setBackgroundColor(color);
        }
    }

    */
/**
     * @author xinspace
     * created at 12/10/15 16:41
     * 获取按下子项的背景颜色
     *//*

    public int getPressedItemBackground(MenuItem menuItem) {
        if (menuItem != null) {
            return menuItem.getBackgroundColor();
        }
        return -1;
    }

    */
/**
     * @author xinspace
     * created at 12/04/15 15:23
     * 获取推荐页面菜单项
     *//*

    public MenuItem getFeaturedMenuItem() {
        return mFeaturedItem;
    }

    */
/**
     * @author xinspace
     * created at 12/04/15 15:24
     * 获取主题页面菜单项
     *//*

    public MenuItem getThemeMenuItem() {
        return mThemeItem;
    }

    */
/**
     * @author xinspace
     * created at 12/04/15 15:24
     * 获取主题页面菜单项
     *//*

    public MenuItem getWallpaperMenuItem() {
        return mWallpaperItem;
    }

    */
/**
     * @author xinspace
     * created at 12/04/15 15:24
     * 获取主题页面菜单项
     *//*

    public MenuItem getFontMenuItem() {
        return mFontItem;
    }

	*/
/**
	 * 从布局文件中加载view
	 * *//*

	private void loadViews() {
		mFeaturedItem = new MenuItem();
		mFeaturedItem.mParentLayout = (FrameLayout) findViewById(R.id.menu_featured);
		mFeaturedItem.mNormalLayout = (LinearLayout) findViewById(R.id.normal_featured);
		mFeaturedItem.mPressedLayout = (LinearLayout) findViewById(R.id.pressed_featured);

		mThemeItem = new MenuItem();
		mThemeItem.mParentLayout = (FrameLayout) findViewById(R.id.menu_theme);
		mThemeItem.mNormalLayout = (LinearLayout) findViewById(R.id.normal_theme);
		mThemeItem.mPressedLayout = (LinearLayout) findViewById(R.id.pressed_theme);

		mWallpaperItem = new MenuItem();
		mWallpaperItem.mParentLayout = (FrameLayout) findViewById(R.id.menu_wallpaper);
		mWallpaperItem.mNormalLayout = (LinearLayout) findViewById(R.id.normal_wallpaper);
		mWallpaperItem.mPressedLayout = (LinearLayout) findViewById(R.id.pressed_wallpaper);

        mFontItem = new MenuItem();
        mFontItem.mParentLayout = (FrameLayout) findViewById(R.id.menu_font);
        mFontItem.mNormalLayout = (LinearLayout) findViewById(R.id.normal_font);
        mFontItem.mPressedLayout = (LinearLayout) findViewById(R.id.pressed_font);
    }

    // 监听器接口

	*/
/**
	 * 初始化状态,loadViews后调用
	 * *//*

	private void initStatus() {
		// 默认Featured菜单按下
		mFeaturedItem.switchStatus();
		// 保存被按下的菜单，在转换时会恢复这个菜单
		mPreviousPressedItem = mFeaturedItem;
	}

	*/
/**
	 * 设置view事件，initStatus后调用
	 * *//*

	private void setupViews() {
		mFeaturedItem.mParentLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switchPressed(mFeaturedItem);
				if (mMenuClickListener != null) {
					mMenuClickListener.onFeaturedClicked();
				}
			}
		});

		mThemeItem.mParentLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switchPressed(mThemeItem);
				if (mMenuClickListener != null) {
					mMenuClickListener.onThemeClicked();
                }
            }
		});

		mWallpaperItem.mParentLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                switchPressed(mWallpaperItem);
                if (mMenuClickListener != null) {
                    mMenuClickListener.onWallpaperClicked();
                }
            }
        });

        mFontItem.mParentLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                switchPressed(mFontItem);
                if (mMenuClickListener != null) {
                    mMenuClickListener.onFontClicked();
                }
            }
        });
	}

    */
/**
     * 监听菜单被点击事件接口
     *//*

    public interface OnMenuClickListener {
        void onFeaturedClicked();

        void onThemeClicked();

        void onWallpaperClicked();

        void onFontClicked();
    }

    */
/**
     * 自定义菜单按钮类
     *//*

    public class MenuItem {
        public boolean mNormalStatus = true;// 当前菜单是否在正常布局
        public LinearLayout mNormalLayout;// 正常布局
        public LinearLayout mPressedLayout;// 按下布局
        public FrameLayout mParentLayout;// 两种布局的父布局，用于管理按下事件

        */
/**
         * 转换菜单按钮的布局，从正常布局转换为按下布局，或相反
         *//*

        public void switchStatus() {
            // 转换状态标识
            mNormalStatus = !mNormalStatus;
            if (mNormalStatus) {
                // 显式正常布局，隐藏按下布局
                mNormalLayout.setVisibility(VISIBLE);
                mPressedLayout.setVisibility(GONE);
            } else {
                // 相反
                mNormalLayout.setVisibility(GONE);
                mPressedLayout.setVisibility(VISIBLE);
            }
        }

        */
/**
         * @author xinspace
         * created at 12/10/15 16:41
         * 获取背景颜色
         *//*

        public int getBackgroundColor() {
            if (!mNormalStatus) {
                Drawable background = mPressedLayout.getBackground();
                if (background instanceof ColorDrawable) {
                    return ((ColorDrawable) background).getColor();
                }
            } else {
                Drawable background = mNormalLayout.getBackground();
                if (background instanceof ColorDrawable) {
                    return ((ColorDrawable) background).getColor();
                }
            }
            return -1;
        }

        */
/**
         * @author xinspace
         * created at 12/04/15 14:48
         * 设置背景颜色
         * @param color 颜色值
         *//*

        public void setBackgroundColor(int color) {
            if (!mNormalStatus) {
                //当菜单被按下时才会改变颜色
                mPressedLayout.setBackgroundColor(color);
            }
        }
    }
}
*/
