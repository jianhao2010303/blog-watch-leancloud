/**
 * Copyright (C), 2012-2021, 263
 * FileName: NoticeValineTask
 * Author:   Administrator
 * Date:     2021/1/28 9:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * xutao           修改时间           版本号              描述
 */
package cn.com.xuct.watchleancloud.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Administrator
 * @create 2021/1/28
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class NoticeValineTask {

    @Value("${notice.url}")
    private String URL;

    private final RestTemplate restTemplate;

    @Scheduled(cron = "0 0/20 7-21 * * ?")
    public void notice() {
        log.info("task:: now notice valine....");
        restTemplate.getForEntity(URL, null);
    }
}