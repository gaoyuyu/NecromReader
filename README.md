# NecromReader Dev Log


## 2017.3.12

***[update]***
 1. 初步封装BaseActivity，BasePresenter，BaseView
 2. 增加DrawerLayout，NavigationView



## 2017.3.12

***[update]***
 1. 封装BaseFragment，LazyFragment
 2. 封装Okhttp3，Retrofit
 3. 使用MVP
 4. 增加“新闻”模块

***[fix]***
 1. 懒加载下首次加载HomeFragment需手动设置setUserVisibleHint为true
 2. Fragment在使用getActivity()获取Activity的引用去获取资源时，需通过isAdded()来判断当前Fragment是否Attached到Activity，否则会报异常，Fragment not attached to Activity

```Java
    if (homeFragment == null)
    {
        homeFragment = HomeFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putInt("titleId", R.string.app_name);
        bundle.putIntArray("newsType", newsType);
        homeFragment.setArguments(bundle);
        /**
         * 由于是BaseFragment是懒加载，add HomeFragment之前isVisibleToUser为false
         * 所以HomeFragment的UI没有渲染出来，这里需要手动设置为true，
         * 告诉LazyFragment HomeFragment是可见的
         */
        homeFragment.setUserVisibleHint(true);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), homeFragment, R.id.main_content);
    }
    //初始化MainPresenter
    new MainPresenter(homeFragment);
```

```Java
    if(isAdded())
    {
        map.put("type",activity.getResources().getString(getArguments().getInt("type")));
        map.put("key", Constant.APPKEY);
        mNewsPresenter.loadNewsData(map);
    }
```

## 2017.3.18

***[update]***
 1. 新闻详情页
 1. 新闻列表item设置属性动画


***[fix]***
 1. 由于BaseFragment继承自LazyFragment，正常在add之后需要setUserVisibleHint(true)，否则lazyload的if判断过不去，所以在ActivityUtils中做统一设置。


## 2017.3.19

***[update]***
 1. 新增GankFragment


***[fix]***
 1. style-v21中设置`<item name="android:windowDrawsSystemBarBackgrounds">true</item>`和`<item name="android:statusBarColor">@android:color/transparent</item>`，NavigationView上状态栏效果为透明且不被状态栏截断。
 2. NavigationView中点击item通过`switchContent()`来回切换Fragment，出现`FragmentManager is already executing transactions`异常，由于HomeFragment中存在Viewpager，且Viewpager的Item同样是Fragemnt，所以FragmentManager要使用`getChildFragmentManager`获取
```Java
    /**
     * 当fragment进行切换时，采用隐藏与显示的方法加载fragment以防止数据的重复加载
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to)
    {
        if (currentFragment != to)
        {
            currentFragment = to;
            FragmentManager fm = getSupportFragmentManager();
            //添加渐隐渐现的动画
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            Log.i("MainAty","===>"+to.isAdded());
            if (!to.isAdded())
            {    // 先判断是否被add过
                ft.hide(from).add(R.id.main_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            }
            else
            {
                ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
```
```Java
        newsPagerAdapter = new NewsPagerAdapter(activity, getChildFragmentManager(), tabType, fragmentList);
        homeViewpager.setAdapter(newsPagerAdapter);
```

## 2017.3.24

***[update]***
 1. 新增PhotoFragment


## 2017.3.25

***[update]***
 1. 新增BigPhotoActivity,大图查看

***[fix]***
 1. 设置统一Theme资源
 2. 大图查看`BigPhotoActivity`布局文件下的toolbar，为了适配statusbar，设置marginTop为25dp


 ## 2017.7.23

 ***[update]***
  1.增加PhotoView，图片放大缩小手势操作效果

 ***[fix]***
  1.PhotoActivity 图片列表上拉加载更多数据填充错乱，增加pageNum=1判断
  2.增加loading


## 2017.7.25

***[update]***
 1. 图片下载功能
 2. 动态获取`WRITE_EXTERNAL_STORAGE`权限

***[fix]***
 1. 新建BaseLazyFragment



## 2017.7.26

***[update]***
 1. 增加WaveView显示下载进度



## 2017.7.27

***[update]***
 1. 增加下载时动画效果
 2. 设置壁纸


 ## 2017.10.10

 ***[update]***
  1. 增加`我的下载`，滑动删除已下载的图片


 ## 2017.10.11

 ***[update]***
  1. `干活集中营`增加`Androd`，`iOS`，`前端`标签


 ## 2017.10.12

 ***[update]***
  1. `干活集中营`列表详情
  2. tag排序/删除（未完成）


 ## 2017.10.15

 ***[update]***
  1. tag排序/删除
  2. 在处理拖拽Grid排列的列表时，要保证list的排列和显示的位置一样

```Java
        //保证list的排列和显示的位置一样
        synchronized (this)
        {
            if (fromPosition > toPosition)
            {
                int count = fromPosition - toPosition;
                for (int i = 0; i < count; i++)
                {
                    Collections.swap(data, fromPosition - i, fromPosition - i - 1);
                }
            }
            if (fromPosition < toPosition)
            {
                int count = toPosition - fromPosition;
                for (int i = 0; i < count; i++)
                {
                    Collections.swap(data, fromPosition + i, fromPosition + i + 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }
```
2021-08-30 17:56:17.605 22038-22038/? E/AndroidRuntime: FATAL EXCEPTION: main
    Process: au.com.hsbc.hsbcaustralia, PID: 22038
    java.lang.IllegalArgumentException: Invalid format: ""
        at org.joda.time.format.DateTimeFormatter.parseDateTime(DateTimeFormatter.java:945)
        at org.joda.time.DateTime.parse(DateTime.java:160)
        at org.joda.time.DateTime.parse(DateTime.java:149)
        at com.hsbc.mobilebanking.openbanking.g.e$a.a(OpenBankingDateTimeFormatter.kt:9)
        at com.hsbc.mobilebanking.openbanking.dashboardV2.c.a(ConsentDashboardTppListAdapter.kt:224)
        at com.hsbc.mobilebanking.openbanking.dashboardV2.c.a(ConsentDashboardTppListAdapter.kt:123)
        at com.hsbc.mobilebanking.openbanking.dashboardV2.c.getChildView(ConsentDashboardTppListAdapter.kt:92)
        at android.widget.ExpandableListConnector.getView(ExpandableListConnector.java:451)
        at android.widget.AbsListView.obtainView(AbsListView.java:2472)
        at android.widget.ListView.makeAndAddView(ListView.java:2052)
        at android.widget.ListView.fillDown(ListView.java:786)
        at android.widget.ListView.fillSpecific(ListView.java:1494)
        at android.widget.ListView.layoutChildren(ListView.java:1790)
        at android.widget.AbsListView.onLayout(AbsListView.java:2261)
        at android.view.View.layout(View.java:19946)
        at android.view.ViewGroup.layout(ViewGroup.java:6310)
        at androidx.constraintlayout.widget.ConstraintLayout.onLayout(ConstraintLayout.java:1855)
        at android.view.View.layout(View.java:19946)
        at android.view.ViewGroup.layout(ViewGroup.java:6310)
        at androidx.viewpager.widget.ViewPager.onLayout(ViewPager.java:1775)
        at android.view.View.layout(View.java:19946)
        at android.view.ViewGroup.layout(ViewGroup.java:6310)
        at androidx.constraintlayout.widget.ConstraintLayout.onLayout(ConstraintLayout.java:1855)
        at android.view.View.layout(View.java:19946)
        at android.view.ViewGroup.layout(ViewGroup.java:6310)
        at android.widget.FrameLayout.layoutChildren(FrameLayout.java:323)
        at android.widget.FrameLayout.onLayout(FrameLayout.java:261)
        at android.view.View.layout(View.java:19946)
        at android.view.ViewGroup.layout(ViewGroup.java:6310)
        at androidx.coordinatorlayout.widget.CoordinatorLayout.d(CoordinatorLayout.java:1213)
        at androidx.coordinatorlayout.widget.CoordinatorLayout.b(CoordinatorLayout.java:899)
        at androidx.coordinatorlayout.widget.CoordinatorLayout.onLayout(CoordinatorLayout.java:919)
        at android.view.View.layout(View.java:19946)
        at android.view.ViewGroup.layout(ViewGroup.java:6310)
        at androidx.constraintlayout.widget.ConstraintLayout.onLayout(ConstraintLayout.java:1855)
        at android.view.View.layout(View.java:19946)
        at android.view.ViewGroup.layout(ViewGroup.java:6310)
        at android.widget.FrameLayout.layoutChildren(FrameLayout.java:323)
        at android.widget.FrameLayout.onLayout(FrameLayout.java:261)
        at android.view.View.layout(View.java:19946)
        at android.view.ViewGroup.layout(ViewGroup.java:6310)
        at android.widget.FrameLayout.layoutChildren(FrameLayout.java:323)
        at android.widget.FrameLayout.onLayout(FrameLayout.java:261)
        at android.view.View.layout(View.java:19946)
        at android.view.ViewGroup.layout(ViewGroup.java:6310)
        at android.widget.LinearLayout.setChildFrame(LinearLayout.java:1791)
        at android.widget.LinearLayout.layoutVertical(LinearLayout.java:1635)
        at android.widget.LinearLayout.onLayout(LinearLayout.java:1544)
        at android.view.View.layout(View.java:19946)
        at android.view.ViewGroup.layout(ViewGroup.java:6310)
        at android.widget.FrameLayout.layoutChildren(FrameLayout.java:323)
        at android.widget.FrameLayout.onLayout(FrameLayout.java:261)
        at android.view.View.layout(View.java:19946)
        at android.view.ViewGroup.layout(ViewGroup.java:6310)
        at android.widget.LinearLayout.setChildFrame(LinearLayout.java:1791)
        at android.widget.LinearLayout.layoutVertical(LinearLayout.java:1635)
        at android.widget.LinearLayout.onLayout(LinearLayout.java:1544)
        at android.view.View.layout(View.java:19946)
        at android.view.ViewGroup.layout(ViewGroup.java:6310)
        at android.widget.FrameLayout.layoutChildren(FrameLayout.java:323)
2021-08-30 17:56:17.605 22038-22038/? E/AndroidRuntime:     at android.widget.FrameLayout.onLayout(FrameLayout.java:261)
        at com.android.internal.policy.DecorView.onLayout(DecorView.java:950)
        at android.view.View.layout(View.java:19946)
        at android.view.ViewGroup.layout(ViewGroup.java:6310)
        at android.view.ViewRootImpl.performLayout(ViewRootImpl.java:2668)
        at android.view.ViewRootImpl.performTraversals(ViewRootImpl.java:2373)
        at android.view.ViewRootImpl.doTraversal(ViewRootImpl.java:1485)
        at android.view.ViewRootImpl$TraversalRunnable.run(ViewRootImpl.java:7179)
        at android.view.Choreographer$CallbackRecord.run(Choreographer.java:935)
        at android.view.Choreographer.doCallbacks(Choreographer.java:747)
        at android.view.Choreographer.doFrame(Choreographer.java:677)
        at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:921)
        at android.os.Handler.handleCallback(Handler.java:790)
        at android.os.Handler.dispatchMessage(Handler.java:99)
        at android.os.Looper.loop(Looper.java:192)
        at android.app.ActivityThread.main(ActivityThread.java:6896)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:556)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:875)



