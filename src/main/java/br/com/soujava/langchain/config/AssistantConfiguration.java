package br.com.soujava.langchain.config;

import br.com.soujava.langchain.listener.MyChatModelListener;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration
public class AssistantConfiguration {
    @Bean
    @Scope(SCOPE_PROTOTYPE)
    ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(10);
    }

    @Bean
    ChatModelListener chatModelListener() {
        return new MyChatModelListener();
    }
}