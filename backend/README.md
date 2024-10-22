<h1 align="center">통합 채용 시스템 백엔드<br>
	IRS (Integration Recruite System) </h1>
<div align="center">
  <img src="https://github.com/user-attachments/assets/a2d10980-8f3c-4d47-b015-37429120b350" style="width: 50%">
</div>
<br><br><br>



# 🛠 기술 스택
<br>
<div align=center>
  
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white)
![SpringSecurity](https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat&logo=springsecurity&logoColor=white)
![SpringJPA](https://img.shields.io/badge/SpringJPA-6DB33F?style=flat&logo=spring&logoColor=white)
![SpringBatch](https://img.shields.io/badge/SpringBatch-6DB33F?style=flat&logo=spring&logoColor=white)
![Tomcat](https://img.shields.io/badge/Tomcat-F8DC75?style=flat&logo=apachetomcat&logoColor=black)
![AmazonS3](https://img.shields.io/badge/AmazonS3-569A31?style=flat&logo=amazons3&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=flat&logo=jsonwebtokens&logoColor=white)

</div>
<br><br><br>



# 🚗 주요 기능
## 인증 및 인가
<details>
	<summary><b>Refresh Token + Redis</b></summary>
</details>
<br><br><br><br>

## 검색
<details>
<summary><b>QueryDSL + 페이징 처리</b></summary>
<br>
	
> 메인 페이지에서 공고 조회 시간을 줄이고, 채용 형태, 모집 직무, 근무 지역, 키워드를 활용하여 효율적인 검색을 구현하기 위해 적용하였습니다.

### QueryDSL
JPQL을 Java 코드로 작성할 수 있도록 하는 라이브러리
- 자바 코드로 쿼리를 작성함으로 컴파일 시점에 에러를 잡을 수 있다.
- 복잡한 동적 쿼리를 쉽게 다룰 수 있다.

### 페이징 처리
데이터를 일정한 크기로 나누어 출력하는 방법
- 대량의 데이터를 효율적으로 처리
- 사용자 경험 개선

### QueryDSL + 페이징 처리 전, 후 비교
<details>
<summary>🎒 QueryDSL + 페이징 적용 전 → 37.26 s</summary>
<br>
	
![적용 전](https://github.com/user-attachments/assets/18d14fa1-3eb6-4584-8634-f7de41350569)

</details>
<details>
<summary>🔅 QueryDSL + 페이징 적 후 → 1.534 s</summary>

![적용 후](https://github.com/user-attachments/assets/c75d1a91-975e-4dd9-99e8-2c8e32aa2c28)


</details>
</details>
<br><br><br><br>

## 알림
<details>
	<summary><b>Spring Batch</b></summary>
</details>
<br><br><br><br>

## 화상 면접
<details>
	<summary><b>Openvidu</b></summary>
</details>

