package ru.mike.serviceB.listner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.mike.serviceB.config.RabbitConfig;
import ru.mike.serviceB.dto.MsgB;

@Component
public class OrderMessageListener {
    static final Logger logger = LoggerFactory.getLogger(OrderMessageListener.class);

    @RabbitListener(queues = RabbitConfig.QUEUE_MSG_B)
    public void processOrder(MsgB msgB) {
        logger.info("Message Received: " + msgB);
    }
}
