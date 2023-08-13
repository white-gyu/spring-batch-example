package com.example.springbatchexample.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep1())
                .build();
    }

    /**
     * Tasklet = Step 안에서 단일로 수행될 커스텀 기능 선언 목적
     * Job = Multiple Steps
     * Step = Tasklet or Reader + Processor + Writer
     * Tasklet = Reader + Processor + Writer -> 같은 묶음 => Reader, Processor 종료 후 Tasklet 마무리 불가능
     * @return
     */
    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">>>> This is Step1");
                    return RepeatStatus.FINISHED;
                }))
                .build();
    }
}
