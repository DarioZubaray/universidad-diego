package com.zubaray.appweb.universidad.models;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StandardResponseDto<T> {

    private Boolean status;
    private Integer code;
    private LocalDateTime datetime;
    private T data;

}
