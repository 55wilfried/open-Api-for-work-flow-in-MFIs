/*
package com.microfinance.client_services.config;

import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.distributed.proxy.RemoteBucketBuilder;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Supplier;

@Configuration
public class RateLimiterConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // Adjust the Redis address if needed.
        config.useSingleServer().setAddress("redis://redis:6379");
        return Redisson.create(config);
    }

    @Bean
    public ProxyManager<String> bucket4jProxyManager(RedissonClient redissonClient) {
        // Use Redisson's RMap for storing bucket states
        RMap<String, byte[]> map = redissonClient.getMap("buckets");

        return new RemoteProxyManager<>(map);
    }

    @Bean
    public RemoteBucketBuilder<String> bucketBuilder(ProxyManager<String> proxyManager) {
        return proxyManager.builder()
                .withExpirationStrategy(ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofMinutes(5)))
                .withInitialTokens(10)
                .withCapacity(10);
    }
}
*/
