package com.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author admin
 * @Date 2019/12/10 21:41
 * @Description
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FareDto implements Serializable {

    private String phone;
}
