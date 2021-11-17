## JPQL - 기본 문법과 기능

### JPQL 소개
- JPQL은 객체지향 쿼리 언어. 엔티티 객체를 대상으로 쿼리
- JPQL은 SQL을 추상화해서 특정 데이터베이스 SQL에 의존하지 않는다.
- JPQL은 결국 SQL로 변환된다

### 문법 
 - select m from Member as m where m.age > 18 
 - 엔티티와 속성은 대소문자 구분함
 - JPQL 키워드는 대소문자 구분X (SELECT, FROM, where)
 - 엔티티 이름 사용, 테이블 이름이 아님 주의..
 - ```Alias 필수(as는 생략 가능)```
 - 집합과 정렬 지원 
 - #### TypeQuery, Query 
   - TypeQuery: 반환 타입이 명확할 때 사용
   - Query: 반환 타입이 명확하지 않을 때 사용
 - #### query.getResultList(): 결과가 하나 이상일 떄 리스트 반환, 결과가 없으면 빈 리스트 반환
 - #### query.getSingleResult(): 결과가 정확히 하나, 단일 객체 반환 
   - 결과 없을 시  :  javax.persistence.NoResultException
   - 결과 둘 이상 : javax.persistence.NonUniqueResultException
   
 - ### 파라미터 바인딩 
   - 이름 기준 
   - 위치 기준(비추천)
   
 - ### 프로젝션 
   - SELECT 절에 조회할 대상을 지정하는 것
   - 프로젝션 대상 : 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자등 기본 데이터 타입)
   - SELECT m FROM Member m -> 엔티티
   - SELECT m.team FROM Member m -> 엔티티
   - SELECT m.address FROM Member m -> 임베디드
   - SELECT m.username, m.age FROM Member m -> 스칼라 타입 
   - DISTINCT로 중복 제거
   
 - ### 프로젝션 여러 값 조회 
   1) 쿼리 타입으로 조회
   2) Object[] 타입으로 조회
   3) new 명령어로 조회
      - 순서와 타입이 일치하는 생성자 필요

 - ### 페이징 
   - setFirstResult(int startPosition): 조회 시작 위치(0부터 시작)
   - setMaxResult(int maxResult): 조회할 데이터 수

 - ### 조인 
   - 내부 조인 : SELECT m FROM Member m [INNER] JOIN m.team t
   - 외부 조인 : SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
   - 세타 조인 : select count(m) from Member m, Team t where m.username = t.name
   - ON절을 활용한 조인(JPA 2.1부터)
     1. 조인 대상 필터링 ex) 회원과 팀을 조인하면서 팀 이름이 A인 팀만 조인
        - select m, t from Member m left join m.team t on t.name = 'A'
     2. 연관관계 없는 엔티티 외부 조인(하이버네이트 5.1부터) ex) 회원의 이름과 팀의 이름이 같은 대상 외부 조인
        - select m, t from Member m left join Team t on m.username = t.name

 - ### 서브 쿼리
   - JPA는 WHERE, HAVING 절에서만 서브 쿼리 가능
   - SELECT 절도 가능(하이버네이트에서 지원)
   - FROM 절의 서브쿼리는 JPQL에서 불가능함
     - 조인으로 할 것 
 
 - ### JPQL 타입 표현 
   - 문자 : 'hi'
   - 숫자 : 10L, 10D
   - Boolean : TRUE, FALSE (대소문자 구별 X)
   - ENUM :  jpql.MemberType.GUEST (패키지명 포함), setParameter 사용하자
   - 엔티티 타입 : TYPE(m) = Member(상속 관계에서 사용)
     - ex) select i from Item i where type(i) = Book
   
 - ### 조건식 
   - 기본 CASE 식(연산자 사용)
   - 단순 CASE 식(값 일치)
   - COALESCE : 하나씩 조회해서 null이 아니면 반환
   