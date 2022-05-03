package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName : jpabook.jpashop.service
 * fileName : MemberService
 * author : haedoang
 * date : 2021/11/21
 * description :
 */
@Service
@Transactional(readOnly = true) //성능 최적화
@RequiredArgsConstructor //final로 선언된 필드로 생성자를 생성한다..
public class MemberService {

    private final MemberRepository memberRepository;

//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    // 멀티 스레드 상황에서 검증이 완벽하지 않기 때문에 DB 컬럼상에 제약 조건을 주는 것이 안전하다.
    private void validateDuplicateMember(Member member) {
        //exception
        List<Member> members = memberRepository.findByName(member.getName());
        if (!members.isEmpty()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = findOne(id);
        member.setName(name);
    }
}
