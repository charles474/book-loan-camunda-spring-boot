package com.example.camunda.book.loan;

import com.example.camunda.book.loan.bpmn.BpmnProcessor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.context.event.EventListener;

@Slf4j
public class BookLoanProcess {

    private final BpmnProcessor bpmnProcessor;

    public BookLoanProcess(BpmnProcessor bpmnProcessor) {
        this.bpmnProcessor = bpmnProcessor;
    }

    @EventListener
    public void onPostDeploy(PostDeployEvent event){
        log.info(event.toString());
    }

}
