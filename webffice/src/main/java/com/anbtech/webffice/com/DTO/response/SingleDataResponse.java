package com.anbtech.webffice.com.DTO.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SingleDataResponse<T> extends BaseResponse {
    private T data; 
}