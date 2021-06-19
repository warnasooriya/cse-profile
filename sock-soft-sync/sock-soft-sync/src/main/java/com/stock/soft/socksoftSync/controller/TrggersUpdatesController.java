package com.stock.soft.socksoftSync.controller;


import com.stock.soft.socksoftSync.Dto.cse.Greeting;
import com.stock.soft.socksoftSync.Dto.cse.IndexUpdates;
import com.stock.soft.socksoftSync.Dto.cse.SocketPricesDto;
import com.stock.soft.socksoftSync.model.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class TrggersUpdatesController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/indexupdate")
    @SendTo("/topic/indexes")
    public IndexUpdates indexes(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new IndexUpdates();
    }

    @MessageMapping("/trade")
    @SendTo("/topic/trade")
    public SocketPricesDto trade(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new SocketPricesDto();
    }

}
