package priv.just.framework.cache.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class CacheValue {

    private String uuid = UUID.randomUUID().toString();

    private Integer number = (int) (Math.random() * 1000);

}
