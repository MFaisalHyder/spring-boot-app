package com.spring.project.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: S776781 - Muhammad Faisal Hyder
 * @Date: 30/10/2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Response implements Serializable {

    private boolean status;
    private String message;
    private String requestReceivedTime;
    private String responseSentTime;
    private String requestProcessingTime;
    private Object data;

}