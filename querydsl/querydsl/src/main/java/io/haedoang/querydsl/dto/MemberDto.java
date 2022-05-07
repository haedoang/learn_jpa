package io.haedoang.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

/**
 * author : haedoang
 * date : 2022/05/05
 * description :
 */
@Data
@NoArgsConstructor
public class MemberDto {
    private String username;
    private int age;


    @QueryProjection
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
