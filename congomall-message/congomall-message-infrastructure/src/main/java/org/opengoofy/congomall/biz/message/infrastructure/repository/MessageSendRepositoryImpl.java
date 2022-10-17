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

package org.opengoofy.congomall.biz.message.infrastructure.repository;

import org.opengoofy.congomall.biz.message.domain.entity.MessageSend;
import org.opengoofy.congomall.biz.message.domain.repository.MessageSendRepository;
import org.opengoofy.congomall.biz.message.infrastructure.dao.entity.MailSendRecordDO;
import org.opengoofy.congomall.biz.message.infrastructure.dao.mapper.MailSendRecordMapper;
import org.opengoofy.congomall.biz.message.infrastructure.converter.MailSendMessageConverter;
import org.opengoofy.congomall.springboot.starter.common.enums.StatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 消息发送 Repository
 *
 * @author chen.ma
 * @github https://github.com/itmachen
 */
@Repository
@AllArgsConstructor
public class MessageSendRepositoryImpl implements MessageSendRepository {
    
    private final MailSendRecordMapper mailSendRecordMapper;
    
    private final MailSendMessageConverter mailSendMessageConverter;
    
    @Override
    public void mailMessageSave(MessageSend messageSend) {
        MailSendRecordDO mailSendRecordDO = mailSendMessageConverter.customerUserToDO(messageSend);
        mailSendRecordDO.setStatus(messageSend.getSendResult() ? StatusEnum.SUCCESS.code() : StatusEnum.FAIL.code());
        mailSendRecordDO.setSendTime(new Date());
        mailSendRecordMapper.insert(mailSendRecordDO);
    }
}