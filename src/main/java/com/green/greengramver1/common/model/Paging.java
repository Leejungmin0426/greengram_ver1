package com.green.greengramver1.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Getter
@ToString
public class Paging {

    @JsonIgnore
    private final static int DEFAULT_PAGE_SIZE = 20;
    @Schema(example = "1", description = "Selected Page")
    private int page;
    @Schema(example = "30", description = "item count per page")
    private int size;
    @JsonIgnore
    private int startIdx;

    public Paging(Integer page, Integer size) {
        this.size = (size == null || size <= 0) ? DEFAULT_PAGE_SIZE : size;
        log.info("size = {}", this.size);
//        this.page = (size == null|| size<= 0) ? 1: page;
        this.page =(page==null||page<=0)? 1:page;
        this.startIdx = (this.page - 1) * this.size;
    }

}
