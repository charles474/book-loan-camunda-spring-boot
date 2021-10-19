package com.example.camunda.book.loan;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.context.event.EventListener;

@Slf4j
public class BookLoanProcess {

    @EventListener
    public void onPostDeploy(PostDeployEvent event){
        log.info(event.toString());
    }

}
