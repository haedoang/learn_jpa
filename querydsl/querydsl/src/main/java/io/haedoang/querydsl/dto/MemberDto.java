package io.haedoang.querydsl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author : haedoang
 * date : 2022/05/05
 * description :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private String username;
    private int age;
}
