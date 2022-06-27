package com.ecut;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhouwei
 */
@SpringBootApplication
@MapperScan("com.ecut.mapper")
public class EcutStudentMangermentApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcutStudentMangermentApplication.class, args);
    }
}
