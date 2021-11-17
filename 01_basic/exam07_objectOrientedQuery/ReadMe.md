## 객체지향 쿼리 언어

### JPA는 다양한 쿼리 방법을 지원한다
 - ```JPQL```
 - JPA Criteria 
 - ```QueryDSL```
 - 네이티브 SQL
 - JDBC API 직접사용, MyBatis, SrpingJdbcTemplate 

### JPQL 
 - JPA를 사용하면 엔티티 객체를 중심으로 개발
 - 검색 쿼리 시 문제가 됨
 - 검색할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색
 - 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능함
 - 에플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요함
 - JPA는 엔티티 객체를 대상으로 쿼리
 - SQL은 데이터베이스 테이블을 대상으로 쿼리
 - 테이블이 아닌 ```객체```를 대상으로 검색하는 객체 지향 쿼리
 - 동적 쿼리 작성이 어렵다

### Criteria
 - em.getCriteriaBuilder();
 - 컴파일 시점에 오류를 확인 가능
 - 동적 쿼리 작성 용이
 - 유지보수가 어려워 사용안하는 걸 추천함
 - QueryDSL을 추천함

### QueryDSL 
 - 직관적. 유지보수성 높음
 - 컴파일 시점에 오류를 확인 가능
 - 단순하고 쉬움

### 네이티브 SQL 
 - JPA가 제공하는 SQL을 직접 사용
 - JPQL로 해결할 수 없는 특정 데이터베이스에 의존적인 기능
 - 오라클의 CONNECT BY

### JDBC 직접사용, SpringJdbcTemplate
 - JPA를 사용하면서 JDBC 커넥션을 직접사용하거나, 스프링 jdbcTemplate, Mybatis와 함께 사용 가능
 - 단 영속성 컨텍스트를 적절한 시점에 강제로 플러시 필요
 - JPA를 우회해서 SQL을 실행하기 직전에 영속성 컨텍스트 수동 플러시