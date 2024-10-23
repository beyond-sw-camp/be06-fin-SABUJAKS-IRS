<h1 align="center">통합 채용 시스템 프론트엔드<br>
	IRS (Integration Recruite System) </h1>
<div align="center">
  <img src="https://github.com/user-attachments/assets/a2d10980-8f3c-4d47-b015-37429120b350" style="width: 50%">
</div>
<br><br><br>



# 🛠 기술 스택
<br>
<div align=center>
<img src="https://img.shields.io/badge/HTML-E34F26?style=flat&logo=html5&logoColor=white"/></a>
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=black"/></a>
<img src="https://img.shields.io/badge/CSS-1572B6?style=flat&logo=css3&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Vue.js-%2335495e.svg?style=flat&logo=vuedotjs&logoColor=%234FC08D"/></a>
<img src="https://img.shields.io/badge/Pinia-FF6F20?style=flat&logo=vue.js&logoColor=white"/></a>
<img src="https://img.shields.io/badge/Nginx-009639?style=flat&logo=nginx&logoColor=white"/></a>
<img src="https://img.shields.io/badge/OpenVidu-FF3D00?style=flat&logo=webrtc&logoColor=white"/></a>
</div>
<br><br><br>



# 🌳 또 설명하고싶은 거 쓸까말까 기술적인 부분
<br>

<br><br><br>



# 📜 상세 기능 설명서
<br>

### 🚪 공통 🚪 

<details>
    <summary>
<span style="font-size:100%"> 회원가입 & 기업인증 </span></summary>
<p>1. 채용담당자는 기업인증 후, 인증에 입력했던 이메일로 회원가입을 해야 합니다. 이메일 인증 완료 후 계정 이용이 가능합니다.</p>
	
![채용담당자-이메일인증+기업인증+회원가입](https://github.com/user-attachments/assets/633aad28-890e-4ca1-bf1a-9d7404eaecca)

<p>2. 지원자는 이메일 인증 완료 후 계정 이용이 가능합니다.</p>

![지원자-회원가입+이메일인증](https://github.com/user-attachments/assets/c6d4a896-baca-4a50-beac-3af366594006)
</details>

<details>
    <summary>
<span style="font-size:100%"> 로그인 </span></summary>
<p>1. 채용담당자는 이메일과 비밀번호로 로그인합니다.</p>

![채용담당자-로그인+로그아웃](https://github.com/user-attachments/assets/39ce2335-7b80-40bb-8400-b6d663d1fddf)

<p>2. 지원자는 이메일과 비밀번호로 로그인합니다.</p>

![지원자-로그인+로그아웃](https://github.com/user-attachments/assets/ff70ff6b-ebd6-4b16-8ba1-dbdb6f16c989)
</details>

<br>

### 🏢 채용담당자 🏢

<details>
    <summary>
<span style="font-size:100%"> 기업 등록 </span></summary>
<p>1. 공고 등록 전에 마이페이지에서 기업을 먼저 등록합니다.</p>

![채용담당자-기업정보등록](https://github.com/user-attachments/assets/519e5e51-104c-4265-8af4-7c51aa0ceec8)
</details>

<details>
    <summary>
<span style="font-size:100%"> 공고와 지원서 폼 등록 </span></summary>
<p>1. 이미지 또는 템플릿으로 공고를 등록합니다.</p>
<p>2. 등록한 공고에서 받을 지원서 폼을 조립합니다. 자기소개서 폼은 문항까지 입력한 후 등록합니다.</p>
	
![채용담당자-공고등록](https://github.com/user-attachments/assets/b3dd125d-96a0-4d08-a1a6-53446ae06a86)
</details>

<details>
    <summary>
<span style="font-size:100%"> 지원자 서류합격 </span></summary>
<p>1. 각 공고에 지원한 지원자의 지원서를 관리 할 수 있습니다.</p>
<p>2. 서류를 합격, 불합격 처리합니다.</p>
<p>3. 서류전형 결과는 이메일과 사이트 알람으로 각 지원자에게 알림을 보냅니다.</p>

![채용담당자-서류합불처리](https://github.com/user-attachments/assets/a1a94628-a544-4183-a984-7700c71c69be)
</details>

<details>
    <summary>
<span style="font-size:100%"> 면접 일정 관리 ❓</span></summary>
<p>1. 각 공고의 서류합격 지원자들을 대상으로, 면접관 정보와 날짜를 설정한 후 면접일정을 생성합니다.</p>
<p>2. 신입은 일괄처리로 날짜를 지정하여 생성하고, 경력은 채용담당자와 지원자 간 일정을 조율 할 수 있습니다.</p>
<p>3. 면접관 메일로 면접용 임시 비밀번호와 입장 주소를 전송합니다.</p>
</details>

<details>
    <summary>
<span style="font-size:100%"> 화상 면접방 생성 ❓</span></summary>
<p>1. 정해진 면접 일정에 따라 화상 면접방을 생성합니다.</p>
<p>2. 면접방은 지정한 면접일 하루 전날에 일괄 생성됩니다.</p>
</details>

<details>
    <summary>
<span style="font-size:100%"> 면접 평가 관리 ❓</span></summary>
<p>1. 화상 면접 시에 면접관이 평가할 면접 평가서를 생성합니다.</p>
<p>2. 채용담당자는 각 면접방마다 진행되고 있는 채용 프로세스 결과를 확인 할 수 있습니다.</p>
<p>3. 모든 채용 프로세스가 끝나면 최종 결과를 이메일과 사이트 알람으로 전송합니다.</p>
</details>

<br>

### 🖥️ 면접관 🖥️

<details>
    <summary>
<span style="font-size:100%"> 면접 참여 & 평가 ❓</span></summary>
<p>1. 이메일에 있는 IRS 사이트 주소로 입장 후 로그인을 하여 면접방에 참여 할 수 있습니다.</p>
<p>2. 화상 면접을 하면서 각 지원자를 평가합니다.</p>
</details>

<br>

### 🧑‍💼 지원자 🧑‍💼

<details>
    <summary>
<span style="font-size:100%"> 공고 검색 ❓</span></summary>
<p>1. 기업, 모집직무, 지역, 키워드로 공고를 검색할 수 있습니다.</p>
<p>2. 마감임박순, </p>
</details>

<details>
    <summary>
<span style="font-size:100%"> 통합 지원서 작성 </span></summary>
<p>1. 마이페이지의 메뉴 중 통합지원서 관리를 눌러 작성된 지원서가 없으면 작성 할 수 있습니다.</p>
<p>2. 작성한 통합지원서가 있으면 조회됩니다.</p>
	
![지원자-통합지원서등록](https://github.com/user-attachments/assets/ed3df967-fb15-4845-a80a-f3a7c398d595)
</details>

<details>
    <summary>
<span style="font-size:100%"> 공고 지원 </span></summary>
<p>1. 공고를 보고 마감 전에 지원할 수 있습니다.</p>
<p>2. 통합지원서를 작성했다면, 지원하기를 클릭했을 때 작성된 폼들을 조회하여 입력 해 집니다.</p>
	
![지원자-공고지원서등록](https://github.com/user-attachments/assets/d7a67de5-1c13-4845-b0d8-6145171f8d40)
</details>

<details>
    <summary>
<span style="font-size:100%"> 공고 별 지원한 지원서 관리 ❓</span></summary>
<p>1. 마이페이지에서 공고 별 지원한 지원서와 각 프로세스 결과를 확인할 수 있습니다.</p>
</details>

<details>
    <summary>
<span style="font-size:100%"> 채용 과정 알림 </span></summary>
<p>1. 이메일과 사이트의 마이페이지에서 서류결과 알림을 확인 할 수 있습니다.</p>

![지원자-서류합불알림](https://github.com/user-attachments/assets/0d8e6f7e-fa83-41ed-8daf-c8c1bec7bc19)
</details>

<details>
    <summary>
<span style="font-size:100%"> 면접 일정 조율 ❓</span></summary>
<p>1. 경력 지원일 때, 채용담당자가 면접일정 알람을 보내면, 가능한 날짜를 선택하여 조율을 요청할 수 있습니다.</p>
</details>

<details>
    <summary>
<span style="font-size:100%"> 화상 면접 참가 ❓</span></summary>
<p>1. 면접 날짜와 시간에 맞춰 면접방에 입장할 수 있습니다.</p>
</details>

<br><br><br>
