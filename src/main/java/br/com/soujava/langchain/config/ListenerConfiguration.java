package br.com.soujava.langchain.config;

import br.com.soujava.langchain.listener.MyChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerConfiguration {
    /*@Bean
    @Scope(SCOPE_PROTOTYPE)
    ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(10);
    }*/

    @Bean
    ChatModelListener chatModelListener() {
        return new MyChatModelListener();
    }
}