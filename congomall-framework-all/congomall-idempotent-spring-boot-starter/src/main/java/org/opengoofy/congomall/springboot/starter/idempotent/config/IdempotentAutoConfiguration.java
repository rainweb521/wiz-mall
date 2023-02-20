/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opengoofy.congomall.springboot.starter.idempotent.config;

import org.opengoofy.congomall.springboot.starter.cache.DistributedCache;
import org.opengoofy.congomall.springboot.starter.idempotent.core.IdempotentAspect;
import org.opengoofy.congomall.springboot.starter.idempotent.core.param.IdempotentParamExecuteHandler;
import org.opengoofy.congomall.springboot.starter.idempotent.core.param.IdempotentParamService;
import org.opengoofy.congomall.springboot.starter.idempotent.core.spel.IdempotentSPELExecuteHandler;
import org.opengoofy.congomall.springboot.starter.idempotent.core.spel.IdempotentSPELService;
import org.opengoofy.congomall.springboot.starter.idempotent.core.token.IdempotentTokenController;
import org.opengoofy.congomall.springboot.starter.idempotent.core.token.IdempotentTokenExecuteHandler;
import org.opengoofy.congomall.springboot.starter.idempotent.core.token.IdempotentTokenService;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 幂等自动装配
 *
 * @author chen.ma
 * @github https://github.com/opengoofy
 */
@EnableConfigurationProperties(IdempotentProperties.class)
public class IdempotentAutoConfiguration {
    
    /**
     * 幂等切面
     */
    @Bean
    public IdempotentAspect idempotentAspect() {
        return new IdempotentAspect();
    }
    
    /**
     * 参数方式幂等实现
     */
    @Bean
    @ConditionalOnMissingBean
    public IdempotentParamService idempotentParamExecuteHandler(RedissonClient redissonClient) {
        return new IdempotentParamExecuteHandler(redissonClient);
    }
    
    /**
     * Token方式幂等实现
     */
    @Bean
    @ConditionalOnMissingBean
    public IdempotentTokenService idempotentTokenExecuteHandler(DistributedCache distributedCache,
                                                                IdempotentProperties idempotentProperties) {
        return new IdempotentTokenExecuteHandler(distributedCache, idempotentProperties);
    }
    
    /**
     * 申请幂等Token控制器
     */
    @Bean
    public IdempotentTokenController idempotentTokenController(IdempotentTokenService idempotentTokenService) {
        return new IdempotentTokenController(idempotentTokenService);
    }
    
    /**
     * SPEL方式幂等实现
     */
    @Bean
    @ConditionalOnMissingBean
    public IdempotentSPELService idempotentSPELExecuteHandler() {
        return new IdempotentSPELExecuteHandler();
    }
}