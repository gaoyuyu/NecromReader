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



