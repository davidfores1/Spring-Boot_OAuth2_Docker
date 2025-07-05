/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.customer.common;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author David
 */

@NoArgsConstructor
@Data
public class StandarizedApiExceptionResponse {
    
    private String type;

    private String title;

    private String code;

    private String detail;

    private String instance;            

    public StandarizedApiExceptionResponse(String type, String title, String code, String detail) {
        super();
        this.type = type;
        this.title = title;
        this.code = code;
        this.detail = detail;
    }
            
}
