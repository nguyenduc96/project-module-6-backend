package com.diosa.model;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class Common {
    private String updateAt;

    private Long updateBy;

    private String createAt;

    private Long createBy;

    private String deleteAt;

    private Long deleteBy;
}
