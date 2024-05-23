package com.boot.s1.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class RootConfig {

    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                //필드 이름이 동일한 경우 자동으로 매핑됨
                .setFieldMatchingEnabled(true)
                //필드 접근 수준 설정
                .setFieldAccessLevel(PRIVATE)
                //매칭 전략을 엄격함으로 설정, 소스 객체와 대상 객체의 필드가 동일해야 매핑이 가능
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }
}
