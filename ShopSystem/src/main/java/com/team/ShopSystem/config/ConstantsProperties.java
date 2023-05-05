package com.team.ShopSystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = ConstantsProperties.PREFIX)
@Getter
@Setter
public class ConstantsProperties {
    public static final String PREFIX = "constants";
    public int unapproved;
    public int approved;
    public int rejected;
    public int applyDeleted;
    public int deleted;
    public int rejectDeleted;
}
