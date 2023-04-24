# 식당 예약시스템
### ProjectDescription
    사용자(User)가 식당을 미리 예약.  
    점주(Owner)는 이를 승인(Accept) 및 거절(Reject)을 통해 예약을 확정.    
    예약된 사용자는 예약 10분 전에 키오스크를 통한 인증

    점주가 예약을 승인해야 사용 가능한 예약으로 변경이 됨.
   
    기본적으로 현재시간이 예약시간 10분 전을 넘을 시, 자동 취소.
     
    리뷰 기능을 통해 만들어진 평점을 0.5점 단위의 별점으로 구현.
    해당 점수는 매일 한 번 값을 갱신해, 식당의 점수를 바꿔 주는 스케쥴러 작성.
    
    버전정보 : Spring 2.7.10. Gradle 7.6.1.
   
    USE : Lombok, Spring Web, OpenFeign, pringdoc(Swagger)
          Validation, jjwt(JWT 토큰)
          Spring Data JDBC, Spring Data JPA, H2, MariaDB.
### ProjectImage
![img_1.png](img_1.png)
## 공통
- [x] 예외 메시지 처리
- [x] 둘러보기(전체 음식점 리스트가 보임) (기본과제)
- [x] 매장검색(이름으로 검색 가능) (기본과제)
- [x] 상세 페이지 이동 및 확인(레스토랑의 id로 해당 상세 정보 유저입장에서 접근(제한된 정보)) (기본과제)

## 회원
### 공통
- [x] 회원가입 
- [x] 로그인 토큰 발행(Jwt) 
- [x] 예약 만들기
- [x] 리뷰 만들기
### 사용자
- [x] 예약 내역 상세 확인
- [x] 예약 내역 목록 확인
- [x] 내가 작성한 리뷰 전체 조회
- [x] 내가 작성한 리뷰 상세 확인
### 점주
- [x] 점주 인증
- [x] 점포 등록
- [x] 점포 상세 내용 등록
- [x] 점포 정보 업데이트 구현
- [x] 예약 승인/거절
## 예약 시스템
- [x] 예약 등록시 예약 자동 종료 시간 구현
- [x] 예약 내역 수정 구현.
- [x] 키오스크를 통한 예약 사용 구현.
## 리뷰 시스템
- [x] 리뷰 작성 가능 기간 구현.
- [x] 별점 구현. 
- [x] 별점 식당 정보 업데이트 구현.
## 코드 확인
- [x] scratch 파일을 통한 controller 확인
- [x] Swagger 를 통한 확인

## ERD 이미지(연관관계매핑)
![img_2.png](img_2.png)
