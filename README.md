<h1 align="center">통합 채용 시스템 <br> 
	IRS (Integration Recruite System) </h1>
<div align="center">
  <img src="https://github.com/user-attachments/assets/a2d10980-8f3c-4d47-b015-37429120b350" style="width: 50%">
</div>
<br>

<div align=center>
	<h3>
	    🌐 IRS 사이트
	    <a href="https://www.sabujaks-irs.kro.kr/">https://www.sabujaks-irs.kro.kr</a>
	</h3>
</div>
<br><br><br><br>




## 👨‍💻 팀원
<div align=center>
<table>
  <tr>
    <td>
      <a href="https://github.com/eunjooNine">
        <img src="https://avatars.githubusercontent.com/u/167549513?v=4" width="100" style="max-width: 100%;">
      </a>
    </td>
    <td>
      <a href="https://github.com/mpqm">
        <img src="https://avatars.githubusercontent.com/u/79093184?v=4" width="100" style="max-width: 100%;">
      </a>
    </td>
    <td>
      <a href="https://github.com/SihyunSeo">
        <img src="https://avatars.githubusercontent.com/u/63051137?v=4" width="100" style="max-width: 100%;">
      </a>
    </td>
    <td>
      <a href="https://github.com/seo-jae-eun">
        <img src="https://avatars.githubusercontent.com/u/82444759?v=4" width="100" style="max-width: 100%;">
      </a>
    </td>
  </tr>
  <tr>
    <td align="center">
      <a href="https://github.com/eunjooNine">구은주</a>
    </td>
    <td align="center">
      <a href="https://github.com/mpqm">박종성</a>
    </td>
    <td align="center">
      <a href="https://github.com/SihyunSeo">서시현</a>
    </td>
    <td align="center">
      <a href="https://github.com/seo-jae-eun">서재은</a>
    </td>
  </tr>
</table>
</div>
<br><br><br><br>




## 📋 프로젝트 소개
![sabujak-중간발표ppt](https://github.com/user-attachments/assets/33e5a20d-82cc-4c0b-9472-d8563561663d)
### 프로젝트 배경
- 기업별로 각기 다른 채용 절차와 복잡한 지원 방식으로 인해 구직자들은 적합한 공고를 찾고 지원하는 데에 많은 시간과 노력을 투자해야 한다.
- 각 기업의 채용담당자들도 여러 채용 구인구직 사이트에 구인을 등록한 후의 과정은, 각 회사에서 관리를 해야하는 번거로움이 있다.
### 프로젝트 목표
- 구직자는 지원부터 채용 일정 조율, 화상 면접까지 하나의 서비스를 통해 진행할 수 있다.
- 채용담당자는 공고 등록부터 서류/면접 결과 발표까지 하나의 서비스를 통해 관리할 수 있다.
### 주요 기능
1. 공고
	- 공고 이미지를 직접 업로드하거나 제공된 공고 템플릿을 활용하여 빠르고 쉽게 공고를 생성할 수 있습니다.
 	- **모듈형 구조로 제공되는 10여 가지 지원서 항목**에서 필요한 항목만 선택하고, **자기소개서 문항과 글자 수를 설정**하여 해당 공고에 맞는 지원서를 구성할 수 있습니다.
3. 지원서
   	- 각 공고에 지원하기 위해 **모듈형 지원서 작성 기능**을 제공합니다. 미리 작성된 통합 지원서를 불러와 간편하게 새로운 지원서를 작성할 수 있습니다.

5. 면접 일정 조율
   	- (신입) 채용 담당자가 확정한 면접 일정을 이메일 또는 사이트 내 알림 관리를 통해 안내받습니다.
   	- (경력) 채용 담당자가 제안한 면접 일정을 이메일 또는 사이트 내 알림 관리를 통해 안내받고, 해당 **일정을 다시 조율**할 수 있습니다.
7. 화상 면접
   	- 공고 작성 시 선택한 채용 절차에 따라 1차, 2차 화상 면접을 진행할 수 있습니다.
   	- 면접일 하루 전 **자동으로 Openvidu를 통해 면접방이 생성**되고, 면접 참여자들에게 **이메일을 통해 면접방 입장 안내**를 합니다.
   	- 면접에 참여하는 면접관은 면접 평가표를 작성하여 채용 담당자에게 전달할 수 있습니다.
9. 결과 알림
	- 공고 작성 시 선택한 **결과 발표일 오후 4시에 Spring Batch를 통해 자동**으로 전송되는 결과 안내 이메일 또는 사이트 내 알림 관리를 통해 결과를 확인 할 수 있습니다.
<br><br><br><br>




## 📋 프로젝트 설계
### 화면 설계서
➡ [바로가기](https://www.figma.com/design/7hhJk8GBwXHjP18kA4C3jb/sabujaks-project?node-id=0-1&node-type=canvas&t=dWLv1NizvLMFMug6-0)
### API 명세서
➡ [바로가기](https://instinctive-dry-e2c.notion.site/SABUJAKS-API-30a8ddd3514a40839da7cd858b11c261?pvs=4)
### ERD
![IRS ERD](https://github.com/user-attachments/assets/45d21f65-49e1-40c7-a25e-a4bdb5973cae)

<br><br><br><br>




## 📖 기술 스택

#### Frontend
![HTML](https://img.shields.io/badge/HTML-E34F26?style=flat&logo=html5&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=javascript&logoColor=black)
![CSS](https://img.shields.io/badge/CSS-1572B6?style=flat&logo=css3&logoColor=white)
![Vue.js](https://img.shields.io/badge/Vue.js-%2335495e.svg?style=flat&logo=vuedotjs&logoColor=%234FC08D)
![Pinia](https://img.shields.io/badge/Pinia-FF6F20?style=flat&logo=vue.js&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?style=flat&logo=nginx&logoColor=white)
![OpenVidu](https://img.shields.io/badge/OpenVidu-FF3D00?style=flat&logo=webrtc&logoColor=white)

#### Backend
![SpringBoot](https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=SpringBoot&logoColor=white)
![SpringSecurity](https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat&logo=springsecurity&logoColor=white)
![SpringJPA](https://img.shields.io/badge/SpringJPA-6DB33F?style=flat&logo=spring&logoColor=white)
![SpringBatch](https://img.shields.io/badge/SpringBatch-6DB33F?style=flat&logo=spring&logoColor=white)
![Tomcat](https://img.shields.io/badge/Tomcat-F8DC75?style=flat&logo=apachetomcat&logoColor=black)
![AmazonS3](https://img.shields.io/badge/AmazonS3-569A31?style=flat&logo=amazons3&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=flat&logo=jsonwebtokens&logoColor=white)
![OpenVidu](https://img.shields.io/badge/OpenVidu-FF3D00?style=flat&logo=webrtc&logoColor=white)

#### Database
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=flat&logo=mariadb&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat&logo=redis&logoColor=white)

#### CI/CD
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=flat&logo=jenkins&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=black&color=blue)
![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?style=flat&logo=Kubernetes&logoColor=blue&color=skyblue)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=flat&logo=Git&logoColor=white&color=ffa500)
![Discord](https://img.shields.io/badge/Discord-7289DA?style=flat&logo=discord&logoColor=white)
![Webhooks](https://img.shields.io/badge/Webhooks-black?style=flat&logo=webhooks&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat&logo=gradle&logoColor=white)
![npm](https://img.shields.io/badge/npm-CB3837?style=flat&logo=npm&logoColor=white)

#### Monitoring & Infra
![Linux](https://img.shields.io/badge/linux-FCC624?style=flat&logo=linux&logoColor=white)
![Ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=flat&logo=ubuntu&logoColor=white)
![Grafana](https://img.shields.io/badge/Grafana-F46800?style=flat&logo=grafana&logoColor=white)
![Prometheus](https://img.shields.io/badge/Prometheus-E6522C?style=flat&logo=prometheus&logoColor=white)

#### Tools
![Notion](https://img.shields.io/badge/Notion-000000?style=flat&logo=notion&logoColor=white)
![Longhorn](https://img.shields.io/badge/Longhorn-FF6D00?style=flat&logo=longhorn&logoColor=white)
<br><br><br><br>




## 🎡 아키텍처
![시스템 아키텍처 -  최종](https://github.com/user-attachments/assets/d6b2d2e1-3421-4224-bf09-56d24d634b77)


<br><br><br><br>




## 💻 개발 폴더 바로가기
#### ➡ [FRONTEND](https://github.com/beyond-sw-camp/be06-fin-SABUJAKS-IRS/tree/main/frontend)
#### ➡ [BACKEND](https://github.com/beyond-sw-camp/be06-fin-SABUJAKS-IRS/tree/main/backend)
#### ➡ [CI/CD](https://github.com/beyond-sw-camp/be06-fin-SABUJAKS-IRS/tree/main/cicd)
<br><br><br><br>
