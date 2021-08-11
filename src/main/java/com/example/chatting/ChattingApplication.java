package com.example.chatting;

import com.example.chatting.repository.StorageRepository;
import com.example.chatting.storage.StorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
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
