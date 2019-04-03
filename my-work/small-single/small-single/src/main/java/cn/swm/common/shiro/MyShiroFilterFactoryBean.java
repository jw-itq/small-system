package cn.swm.common.shiro;

import cn.swm.pojo.TbShiroFilter;
import cn.swm.service.SystemService;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    private static final Logger log= LoggerFactory.getLogger(MyShiroFilterFactoryBean.class);

    /**
     * 配置中的过滤链
     */
    public static String definitions;

    //权限service
    @Autowired
    private SystemService systemService;

    /**
     * 从数据库动态读取权限
     * @param definitions
     */
    @Override
    public void setFilterChainDefinitions(String definitions) {


        MyShiroFilterFactoryBean.definitions = definitions;

        //数据库动态权限
        List<TbShiroFilter> list = systemService.getShiroFilter();
        for(TbShiroFilter tbShiroFilter : list){
            //字符串拼接权限
            definitions += tbShiroFilter.getName()+"="+tbShiroFilter.getPerms()+"\n";
        }

        System.out.println("从数据库动态获取权限---"+definitions);

        //从配置文件加载权限配置
        Ini ini = new Ini();
        ini.load(definitions);
        Ini.Section section = ini.getSection("urls");
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection("");
        }

        this.setFilterChainDefinitionMap(section);
    }
}
