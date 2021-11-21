### 
- #### spring-boot-devtools
  - `spring-boot-devtools` 라이브러리는 `html` 파일을 컴파일만 해주면 서버 재시작 없이 View 변경이 가능하다
  - Build => Recompile

- #### h2 Database
  - `jdbc:h2:~/jpashop` (최소 한번, 세션키 유지한 상태로 실행)
  - `~/jpashop.mv.db` 파일 생성 확인
  - 이후부터는 `jdbc:h2:tcp://localhost/~/jpashop` 이렇게 접속

- #### 엔티티 설계 주의점
  - 가급적 Setter 사용금지 
  - 모든 연관관계는 지연 로딩으로 설정
  - `@ManyToOne`, `@OneToOne` 는  default 값이 `type = FetchType.EAGER` 이다.
  - 컬렉션은 필드에서 초기화할 것
    - 하이버네이트는 엔티티를 영속화할 때 컬렉션을 감싸서 내장 컬렉션으로 변경한다 
    - `org.hibernate.collection.internal.PersistentBag`
    - 임의의 메소드에서 컬렉션을 생성하는 경우 하이버네이트 메커니즘에 문제가 생길 수 있다.
    - null 문제에서도 안전하다

- #### 테이블, 컬럼명 생성 전략
  - 스프링 부트(엔티티(필드) -> 테이블(컬럼)) : `SpringPhysicalNamingStrategy`
    1. CamelCase => 언더스코어
    2. . => _(언더스코어)
    3. 대문자 => 소문자
  - 논리명 생성 : 명시적으로 테이블, 컬렴명 직접 적지 않으면 : ImplicitNamingStrategy
    - `spring.jpa.hibernate.naming.implicit-strategy`
  - 물리명 적용 : 
    - `spring.jpa.hibernate.naming.physical-strategy` : 모든 논리명에 적용
  
- #### In-Memory Database 사용법 
  - https://h2database.com/html/cheatSheet.html : `jdbc:h2:mem:test`
  - 스프링 부트의 경우 아무 설정도 하지 않으면 In-Memory 방식으로 동작한다 
  