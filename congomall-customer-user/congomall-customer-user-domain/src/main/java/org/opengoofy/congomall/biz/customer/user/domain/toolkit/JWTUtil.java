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

package org.opengoofy.congomall.biz.customer.user.domain.toolkit;

import org.opengoofy.congomall.biz.customer.user.domain.aggregate.CustomerUser;
import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Maps;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author chen.ma
 * @github https://github.com/itmachen
 */
public class JWTUtil {
    
    public static final String TOKEN_HEADER = "Authorization";
    
    public static final String TOKEN_PREFIX = "Bearer ";
    
    private static final long EXPIRATION = 86400L;
    
    public static final String ISS = "mall4j";
    
    public static final String SECRET = "SecretKey039245678901232039487623456783092349288901402967890140939827";
    
    /**
     * 生成 C 端用户 Token
     *
     * @param customerUser
     * @return
     */
    public static String generateAccessToken(CustomerUser customerUser) {
        Map<String, Object> customerUserMap = Maps.newHashMap();
        customerUserMap.put("username", customerUser.getUsername());
        customerUserMap.put("accountNumber", customerUser.getAccountNumber());
        customerUserMap.put("customerUserId", customerUser.getCustomerUserId());
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuedAt(new Date())
                .setIssuer(ISS)
                .setSubject(JSON.toJSONString(customerUserMap))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
    }
}