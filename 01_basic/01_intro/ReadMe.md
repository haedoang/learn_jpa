# JPA 

#### JPA에서 제공하는 테이블 생성 기능 hibernate.hbm2ddl.auto 
- create : DROP + CREATE
- create-drop : create와 같으나 종료 시점에 DROP
- update : 변경분만 반영(운영DB에서 사용하면 안됨)
- validate : 엔티티와 테이블 매핑용되었는지만 확인
- none : 

#### 식별자 매핑 방법
- @Id 직접 매핑
- IDENTITY : 데이터베이스에 위임
- SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용, ORACLE (@SequenceGenerator 필요)
- TABLE : 키생성용 테이블 사용, 모든 DB에서 사용 (@TableGenerator 필요)
- AUTO : 방언에 따라 자동 지정, 기본값 

#### 권장하는 식별자 전략
- 기본키 제약 조건 : null 아님, 유일, 변하면 안된다.
- 미래까지 조건을 만족하는 자연키를 찾기 어렵다. 대리키(대체키)를 사용하자
- 권장 : Long + 대체키 + 키 생성전략 사용

## 양방향 매핑

### 연관관계의 주인(Owner)

#### 양방향 매핑 규칙
 - 객체의 두 관계 중 하나를 연관관계의 주인으로 지정
 - 연관관계의 주인만이 외래 키를 관리(등록, 수정)
 - 주인이 아닌쪽은 읽기만 가능
 - 주인은 mappedBy 속성 사용 X
 - 주인이 아니면 mappedBy속성으로 주인을 지정

 #### 누구를 주인으로?
 - 외래키가 있는 곳을 주인으로 정해라

#### 양방향 매핑의 장점
 - 단방향 매핑만으로도 이미 연관관계 매핑은 완료
 - 양방향 매핑은 반대 방향으로 조회(객체 그래프 탐색) 기능이 추가된 것
 - JPQL에서 역방향으로 탐색할 일이 많다
 - 단방향 앰핑을 잘 하고 양방향은 필요할 때 추가하자

#### 연관관계 매핑 어노테이션
 - @ManyToOne
 - @OneToMAny
 - @OneToOne
 - @ManyToMany
 - @JoinColumn, @JoinTable

#### 상속관계 매핑 어노테이션
 - @Inheritance
 - @DiscriminatorColumn
 - @DiscriminatorValue
 - @MappedSuperclass(매핑 속성만 상속)

#### 복합키 어노테이션
 - @IdClass
 - @EmbeddedId
 - @Embeddable
 - @Mapsld

## JPA 내부 구조

#### 영속성 컨텍스트
 - 엔티티를 저장하는 영구적인 환경
 - EntityManager.persist(entity);
 - 논리적인 개념이다.
 - 눈에 보이지 않는다.
 - 엔티티 매니저를 통해서 영속성 컨텍스트에 접근한다.

#### 엔티티의 생명 주기
 - 비영속(new/transient) : 영속성 컨텍스트와 관계가 없는 상태
 - 영속(managed) : 영속성 컨텍스트에 저장된 상태
 - 준영속(detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태 
 - 삭제(removed) : 삭제된 상태

#### 영속성 컨텍스트의 이점
 - 1차 캐시
 - 동일성(identity) 보장
 - 트랜잭션을 지원하는 쓰기 지연(transactional write-behind) : transaction.commit() =>  flush => commit 
 - 변경감지(Dirty Checking) : flush() => 엔티티와 스냅샷 비교 => update sql 생성 => flush => commit 
 - 지연로딩(Lazy Loading)

#### 플러시 
 - 영속성 컨텍스트의 변경내용을 DB에 반영
 - 영속성 컨텍스트를 비우지 않음
 - 영속성 컨텍스트의 변경 내용을 DB에 동기화
 - 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화하면 된다

#### 플러시 발생
 - 변경 감지
 - 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
 - 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송(등록, 수정, 삭제쿼리)

#### 영속성 컨텍스트를 플러시 하는 방법
 - em.flush() - 직접 호출
 - 트랜잭션 커밋 - 플러시 자동 호출
 - JPQL 쿼리 실행 - 플러시 자동 호출#

#### 준영속 상태 
 - 영속 -> 준영속
 - 영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)
 - 영속성 컨텍스트가 제공하는 기능 사용할 수 없음

#### 준영속 상태로 만드는 방법
 - em.detach(entity) : 특정 엔티티만 준영속 상태로 전환
 - em.clear() : 영속성 컨텍스트를 완전히 초기화
 - em.close() : 영속성 컨텍스트를 종료

#### 프록시와 즉시로딩 주의
 - 가급적 지연 로딩을 사용
 - 즉시 로딩을 적용하면 예상하지 못한 SQL이 발생한다.
 - 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다
 - @ManyToOne, @OneToOne은 기본이 즉시로딩 => LAZY로 변경할 것
 - @OneToMany, @ManyToMany는 기본이 지연로딩



## 객체지향 쿼리

### JPQL(Java Persistence Query Language) - 객체 지향 SQL
 - 가장 단순한 조회 방법
 - EntityManager.find()
 - 객체 그래프 탐색({a.getB().getC())
 - JPA를 사용하면 엔티티 객체를 중심으로 개발
 - 검색 쿼리 시 엔티티 객체를 대상으로 검색함 
 - 모든 DB 데이터를 객체로 변환하여 검색하는 것은 불가능
 - 애플리케이션이 필요한 데이터만 DB에서 불러오려면 검색 조건의 SQL이 필요
 - JPA는 SQL을 추상화한 JPQL이라는 쿼리를 제공함
 - 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리, SQL 의존하지 않음
 - from 절에 들어가는게 객체, 엔티티 이름을 사용(테이블 이름이 아님)
 - 별칭(alias)는 필수
 - query.getResultList() : 결과가 하나 이상
 - query.getSingleResult() : 결과가 하나
  
 - ###프로젝션 
    - Select m.team From member m -> 엔티티 프로젝션(Team 객체를 반환)
    - new 명령어 : 단순 값을 DTO로 바로 조회 
      - Select new common.UserDto(m.username, m.age) from Member m
    - Distinct는 중복 제거
   
 - ### 페이징API
   - setFirstResult()
   - setMaxResults()
   - 데이터 베이스 별 방언으로 변환

 - ### 조인
   - 내부 조인 : Select m From Member m Join m.team t
   - 외부 조인 : Select m From Member m LEFT Join m.team t
   - 세타 조인 : select count(m) from Member m, Team t where m.username = t.name
   - 페치 조인 : 엔티티 객체 그래프를 한번에 조회하는 방법
      - 별칭을 사용할 수 없다.
      - JQPL : select m from Member m join fetch m.team
      - SQL : SELECT M.*,T.* FROM MEMBER M INNER JOIN TEAM T ON M.TEAM_ID = T.ID

 - ### Named 쿼리 - 어노테이션 
   - 어플리케이션 로딩 시점에 쿼리를 작성함
   - 문법의 오류를 컴파일 시 확인할 수 있음
 
## Spring DataJPA & QueryDSL

### 스프링 데이터 JPA 
 - 반복되는 CRUD 문제를 세련된 방법으로 해결
 - 개발자는 인터페이스만 작성
 - 스프링 데이터 JPA가 구현 객체를 동적으로 생성해서 주입
 - JpaRepository interface 구현 (스프링 데이터 JPA) 
   - save(), findOne(), findAll()....
 - 메서드 이름으로 JPQL 쿼리를 생성
 - 이름으로 검색 + 정렬 + 페이징
 - @Query 

 - ### 반환 타입 
   - 컬렉션, 단건
   
 - ### 도메인 클래스 컨버터
   - 컨트롤러에서 식별자로 도메인 클래스 찾음
   
### QueryDSL
 - 문자가 아닌 코드로 작성
 - 컴파일 시점에 문법 오류 발견
 - 코드 자동완성(IDE)
 - 단순하고 쉬움 
 - 동적 쿼리

 - ### 동작원리 쿼리타입 생성
   - @Entity Member.java => APT => QMember.java