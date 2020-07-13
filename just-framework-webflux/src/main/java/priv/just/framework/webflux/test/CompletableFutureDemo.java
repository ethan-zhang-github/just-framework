package priv.just.framework.webflux.test;

import org.apache.commons.lang3.RandomUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureDemo {

    public static void main(String[] args) {
        CompletableFutureDemo demo = new CompletableFutureDemo();
        Instant start = Instant.now();
        List<String> res = demo.invokeAsync();
        Instant end = Instant.now();
        System.out.println(Arrays.toString(res.toArray()));
        System.out.println("耗时：" + Duration.between(start, end).toMillis());
    }

    private List<String> invokeSync() {
        List<String> res = new ArrayList<>();
        List<Long> userIds = getUserIds();
        for (Long userId : userIds) {
            String userRole = getUserRole(userId);
            String userCompany = getUserCompany(userId);
            res.add(userId + "-" + userRole + "-" + userCompany);
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    private List<String> invokeAsync() {
        CompletableFuture<List<String>> res = CompletableFuture.supplyAsync(this::getUserIds)
                .thenComposeAsync(userIds -> {
                    CompletableFuture<String>[] tasks = userIds.stream().map(userId -> {
                        CompletableFuture<String> getUserRoleTask = CompletableFuture.supplyAsync(() -> getUserRole(userId));
                        CompletableFuture<String> getUserCompanyTask = CompletableFuture.supplyAsync(() -> getUserCompany(userId));
                        return getUserRoleTask.thenCombineAsync(getUserCompanyTask, (userRole, userCompany) -> userId + "-" + userRole + "-" + userCompany);
                    }).toArray(CompletableFuture[]::new);
                    return CompletableFuture.allOf(tasks).thenApply(v -> Arrays.stream(tasks).map(CompletableFuture::join).collect(Collectors.toList()));
                });
        return res.join();
    }

    private List<Long> getUserIds() {
        sleep(10 * 10);
        return Stream.iterate(1L, i -> i + 1L).limit(10).collect(Collectors.toList());
    }

    private String getUserRole(Long userId) {
        sleep(100);
        return RandomUtils.nextBoolean() ? "worker" : RandomUtils.nextBoolean() ? "leader" : "admin";
    }

    private String getUserCompany(Long userId) {
        sleep(80);
        return RandomUtils.nextBoolean() ? "A公司" : RandomUtils.nextBoolean() ? "B公司" : "C公司";
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
