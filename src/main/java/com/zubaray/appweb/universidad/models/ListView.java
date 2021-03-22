package com.zubaray.appweb.universidad.models;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ListView<T> {

    private Long totalPages;
    private Integer currentPage;
    private Integer sizeShowed;
    private Boolean firstPage;
    private Boolean lastPage;
    private List<T> data;
}
