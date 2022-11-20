package com.julianduru.fileuploader;

import com.julianduru.fileuploader.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(
    classes = {
        TestConfig.class,
        FileUploaderConfig.class
    }
)
class FileUploaderApplicationTests {

    @Test
    void contextLoads() {
    }

}
