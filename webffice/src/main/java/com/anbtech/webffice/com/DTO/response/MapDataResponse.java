package com.anbtech.webffice.com.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MapDataResponse<T> extends BaseResponse{
private Map<String, T> data; 
}