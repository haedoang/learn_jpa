package io.haedoang.querydsl.dto;

import lombok.Data;

/**
 * author : haedoang
 * date : 2022/05/15
 * description :
 */
@Data
public class MemberSearchCondition {
    private String username;
    private String teamName;
    private Integer ageGoe;
    private Integer ageLoe;

}
