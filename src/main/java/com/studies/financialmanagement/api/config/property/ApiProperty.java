package com.studies.financialmanagement.api.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties("api-property")
public class ApiProperty {

    private String originAllowed = "http://localhost:4200";

    private final Security security = new Security();

    @Getter
    @Setter
    public static class Security {

        private boolean enableHttps;

    }

}
