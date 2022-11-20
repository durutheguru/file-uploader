package com.julianduru.fileuploader;


/**
 * created by julian
 */

import com.julianduru.fileuploader.config.TestConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest(
    classes = {
        TestConfig.class,
        FileUploaderConfig.class
    }
)
@ExtendWith({SpringExtension.class})
public class BaseServiceIntegrationTest {





}
