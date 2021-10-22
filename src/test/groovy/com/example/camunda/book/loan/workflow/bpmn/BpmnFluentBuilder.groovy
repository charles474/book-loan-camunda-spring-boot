package com.example.camunda.book.loan.workflow.bpmn

import org.camunda.bpm.model.bpmn.Bpmn
import org.camunda.bpm.model.bpmn.BpmnModelInstance

trait BpmnFluentBuilder {

    BpmnModelInstance fetchModelAsResource(String bpmnResource){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(bpmnResource);
        BpmnModelInstance model = Bpmn.readModelFromStream(inputStream);
        return model;
    }

}