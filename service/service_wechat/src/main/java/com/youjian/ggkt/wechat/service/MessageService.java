package com.youjian.ggkt.wechat.service;

import java.util.Map;

public interface MessageService {

    //接收消息
    String receiveMessage(Map<String, String> param);

    void pushPayMessage(long orderId);
}
