package ru.sibdigital.egrul.scheduling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


@Configuration
@ComponentScan(basePackages = "ru.sibdigital.egrul.scheduling", basePackageClasses = { ScheduleTasks.class })
public class SchedulerConfig {

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        return threadPoolTaskScheduler;
    }

    @Bean
    public ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor() {
        ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor = new ScheduledAnnotationBeanPostProcessor();
        return scheduledAnnotationBeanPostProcessor;
    }

}
