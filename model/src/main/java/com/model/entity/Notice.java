package com.model.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@Data
@ToString
public class Notice implements Serializable{
    private Integer id;

    @NotBlank(message = "通告标题必填")
    private String title;

    @NotBlank(message = "通告内容必填")
    private String content;

    private Byte isActive=1;
}