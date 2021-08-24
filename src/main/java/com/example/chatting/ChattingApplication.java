package com.example.chatting;

import com.example.chatting.storage.StorageProperties;
import com.example.chatting.storage.StorageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
@EnableJpaAuditing
public class ChattingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChattingApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageRepository storageRepository) {
		return (args) -> {
            storageRepository.deleteAll();
            storageRepository.init();
		};
	}
}
