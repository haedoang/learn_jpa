## JPQL - 중급 문법

### 경로 표현식
 - 점을 찍어 객체 그래프를 탐색하는 것
  ```
      select m.username -> 상태 필드
        from Member m
        join m.team t -> 단일 값 연관 필드
        join m.orders o -> 컬렉션 값 연관 필드
       where t.name = 'teamA'
  ```
 - 용어 정리
   - 상태 필드(state field): 단순히 값을 저장하기 위한 필드(m.username)
   - 연관 필드(association field): 연관관계를 위한 필드
     - 단일 값 연관 필드: @ManyToOne, @OneToOne, 대상의 엔티티(ex) m.team)
     - 컬렉션 값 연관 필드: @OneToMany, @ManyToMany, 대상이 컬렉션(ex) m.orders)

 - 경로 표현식 특징
   - 상태 필드(state field): 경로 탐색의 끝, 탐색X
   - 단일 값 연관 경로: ```묵시적 내부 조인(inner join)발생```, 탐색O
   - 컬렉션 값 연관 경로: 묵시적 내부 조인 발생, 탐색X
     - From 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 

 - 단일 값 연관 경로 탐색(주의!!)
   - JPQL : select o.member from Order o
   - SQL : select m.* from Orders o inner join Member m on o.member_id = m.id

 - 명시적 조인, 묵시적 조인
   - 명시적 조인: join 키워드 직접 사용
     - select m from Member m join m.team t
   - 묵시적 조인: 경로 표현식에 의해 묵시적으로 SQL조인 발생(내부 조인)
     - select m.team from Member m
     
 - 경로 탐색을 사용한 묵시적 조인 시 주의 사항
   - 항상 내부 조인
   - 컬렉션은 경로 탐색의 끝, 명시적 조인을 통해 별칭을 얻어야 함
   - 경로 탐색은 주로 SELECT,WHERE 절에서 사용하지만 묵시적 조인으로 인해 SQL의 FROM(JOIN) 절에 영향을 준다.

 - 실무 조언
   - 묵시적 조인 X, 명시적 조인 사용
   - 조인은 SQL 튜닝에 중요 포인트
   - 묵시적 조인은 조인이 일어나는 상황을 파악하기가 어려움

### 페치 조인(fetch join) 중요!
 - SQL 조인 종류X
 - JPQL에서 성능 최적화를 위해 제공
 - 연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회
 - join fetch 명령어 사용
 - ex) join FETCH 조인경로

 - #### 엔티티 페치 조인
   - 회원을 조회하면서 연관된 팀도 함께 조인(SQL 한번에)
   - JPQL: select m from Member m join fetch m.team
   - SQL: select m.*, t.* from member m inner join team t on m.team_id = t.id
 
 - #### 컬렉션 패치 조인(중복이 발생할 수 있음)
   - 일대다 관계, 컬렉션 페치 조인
   - JPQL: select t from Team t join fetch t.members where t.name = 'teamA'
   - SQL: select t.*, m.* from team t inner join member m ont.id = m.team_id where t.name = 'teamA'
 
 - #### 페치조인과 DISTINCT 
   - SQL의 DISTINCT는 중복된 결과를 제거하는 명령
   - JPQL의 DISTINCT 2가지 기능 제공
     1. SQL에 DISTINCT를 추가
     2. 애플리케이션에서 엔티티 중복 제거 
   
 - #### 페치조인과 일반 조인의 차이
   - 일반 조인 실행 시 연관된 엔티티를 함께 조회하지 않음 
   - JPQL은 결과를 반환할 때 연관관계 고려X
   - 단지 SELECT 절에 지정한 엔티티만 조회
   - 페치 조인을 사용할 때만 연관된 엔티티도 함께 조회(즉시 로딩)
   - 페치 조인은 객체 그래프를 SQL 한번에 조회하는 개념
   
 - #### 페치조인의 특징과 한계
   - 페치 조인 대상에는 별칭을 줄 수 없다 ex) select t From Team t join fetch t.members m
     - 하이버네이트는 가능, 가급적 사용X
   - 둘 이상의 컬렉션은 페치 조인할 수 없다.
   - 컬렉션을 페치 조인하면 페이징API(setFirstResult, setMaxResults)를 사용할 수 없다.
     - 일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능
     - 하이버네이트는 경고 로그를 남기고 메모리에서 페이징(매우 위험함..)
     - BatchSize() 사용 또는 ```hibernate.default_batch_fetch_size``` 옵션을 사용하자
   - 연관된 엔티티들을 SQL 한번에 조회 -성능 최적화
   - 엔티티에 직접 적용하는 글로벌 로딩 전략보다 우선함
     - @OneToMany(fetch = FetchType.LAZY) //글로벌 로딩 전략
   - 실무에서 글로벌 로딩 전략은 모두 지연 로딩을 사용하자
   - 최적화가 필요한 곳은 페치 조인 적용
   - 모든 것은 페치 조인으로 해결할 수는 없다
   - 페치 조인은 객체 그래프를 유지할 때 사용하면 효과적
   - 엔티티가 가진 모양이 아닌 다른 결과를 내야 하는 경우에는 일반조인을 사용하고 DTO로 반환하는게 효과적임

 - #### @BatchSize 
   - 