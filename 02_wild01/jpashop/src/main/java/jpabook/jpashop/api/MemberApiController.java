package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName : jpabook.jpashop.api
 * fileName : MemberAPIController
 * author : haedoang
 * date : 2021-12-02
 * description :
 */
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /***
     * 문제점 : entity가 변경될 시 api 스펙 자체가 변경되버린다.
     *         presentation계층의  검증(@Valid를 통한 Entity 검증)을 entity에서 구현
     *          => dto를 활용하자
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@RequestBody @Valid UpdateMemberRequest request,
                                               @PathVariable("id") Long id) {

        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    /**
     * 문제점 : entity가 변경될 시 api 스펙 자체가 변경되버린다.
     *        응답 스펙을 맞추기 위해 로직이 추가됨 @JsonIgnore
     *        엔티티가 변경되면 API 스펙이 변함
     * */
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream().map(m -> new MemberDto(m.getName())).collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @Data
    static private class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static private class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static private class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static private class UpdateMemberRequest {
        private String name;
    }
}
