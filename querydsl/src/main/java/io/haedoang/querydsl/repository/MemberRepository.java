package io.haedoang.querydsl.repository;

import io.haedoang.querydsl.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

/**
 * packageName : io.haedoang.querydsl.repository
 * fileName : MemberRepository
 * author : haedoang
 * date : 2022-05-19
 * description :
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, QuerydslPredicateExecutor<Member> {
    List<Member> findByUsername(String username);
}
