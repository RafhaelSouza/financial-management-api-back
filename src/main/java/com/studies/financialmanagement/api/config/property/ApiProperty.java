package com.studies.financialmanagement.api.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("api-property")
public class ApiProperty {

    private String originAllowed = "http://localhost:4200";

    private final Security security = new Security();

    private final Mail mail = new Mail();

    private final S3 s3 = new S3();

    @Getter
    @Setter
    public static class Security {

        private boolean enableHttps;

    }

    @Getter
    @Setter
    public static class Mail {

        private String host;

        private Integer port;

        private String username;

        private String password;

    }

    @Getter
    @Setter
    public static class S3 {

        private String accessKeyId;

        private String secretAccessKey;

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getSecretAccessKey() {
            return secretAccessKey;
        }

        public void setSecretAccessKey(String secretAccessKey) {
            this.secretAccessKey = secretAccessKey;
        }
    }

}
