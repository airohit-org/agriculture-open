package com.airohit.agriculture.module.system.mq.consumer.mail;

import com.airohit.agriculture.module.system.mq.message.mail.MailSendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

// TODO shiminghao：这个暂未实现
@Component
@Slf4j
public class MailSendConsumer implements Consumer<MailSendMessage> {

    @Override
    public void accept(MailSendMessage message) {
        log.info("[accept][消息内容({})]", message);
    }

}
