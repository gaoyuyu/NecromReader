# NecromReader Dev Log
---

##2017.3.12

***[update]***
 1. 初步封装BaseActivity，BasePresenter，BaseView
 2. 增加DrawerLayout，NavigationView


----------


##2017.3.12

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





