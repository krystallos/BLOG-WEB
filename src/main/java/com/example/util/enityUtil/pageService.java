package com.example.util.enityUtil;

//import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

@Service
public class pageService<T> {

    /**
     * 泛型方法，声明分页功能
     * @param obj 实体
     * @param nowTab 起始页
     * @param hasTab 单页分页数
     * @param c 注入服务
     * @param <T> 持有方法
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <T> T pageEnityService(Object obj , int nowTab , int hasTab , Class<T> c) throws IllegalAccessException, InstantiationException {
        pageEnity page = new pageEnity();
        page.setNowTab(nowTab);
        page.setHasTab(hasTab);
        //PageHelper.offsetPage(page.getStartTab(), page.getHasTab());
        T t = c.newInstance();
        return t;
    }

}
