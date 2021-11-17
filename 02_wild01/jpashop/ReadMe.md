### 
- #### spring-boot-devtools
  - `spring-boot-devtools` 라이브러리는 `html` 파일을 컴파일만 해주면 서버 재시작 없이 View 변경이 가능하다
  - Build => Recompile

- #### h2 Database
  - `jdbc:h2:~/jpashop` (최소 한번, 세션키 유지한 상태로 실행)
  - `~/jpashop.mv.db` 파일 생성 확인
  - 이후부터는 `jdbc:h2:tcp://localhost/~/jpashop` 이렇게 접속 