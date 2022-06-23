package cn.mall4j.springboot.starter.base.safa;

import org.springframework.beans.factory.InitializingBean;

/**
 * FastJson 安全模式
 *
 * @author chen.ma
 * @github https://github.com/mabaiwan
 */
public class FastJsonSafeMode implements InitializingBean {
    
    @Override
    public void afterPropertiesSet() throws Exception {
        System.setProperty("fastjson2.parser.safeMode", "true");
    }
}