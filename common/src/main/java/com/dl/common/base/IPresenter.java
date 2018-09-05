package com.dl.common.base;



/**
 * Created by dalang on 2018/8/30.
 * IPresenter 顶级接口
 */
public interface IPresenter<V extends IView>{
   /**
    * 创建view时调用  调用在initView 之后
    */
   void attachView(V view);

   /**
    * view销毁时调用释放资源
    */
   void detachView();
}
