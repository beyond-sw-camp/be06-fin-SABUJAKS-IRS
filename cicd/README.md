

### :sunny: **[플레이 데이터] 한화시스템 BEYOND SW캠프 6기 / SABUJAKS-IRS** :sunny:

<br><br>



## 🤼‍♂️팀원 소개
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
      <a href="https://github.com/syy0O">구은주</a>
    </td>
    <td align="center">
      <a href="https://github.com/xeunnie">박종성</a>
    </td>
    <td align="center">
      <a href="https://github.com/SihyunSeo">서시현</a>
    </td>
    <td align="center">
      <a href="https://github.com/subi930">서재은</a>
    </td>
  </tr>
</table>
</div>
<br>


## 📌 기술 스택

<div align="center">
<img src="https://img.shields.io/badge/linux-FCC624?style=for-the-badge&logo=linux&logoColor=black" style="border-radius: 5px;">
<!-- <img src="https://img.shields.io/badge/centos-262577?style=for-the-badge&logo=centos&logoColor=white" style="border-radius: 5px;"> -->
<img src="https://img.shields.io/badge/kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white" style="border-radius: 5px;"></br>
<img src="https://img.shields.io/badge/grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white" style="border-radius: 5px;">
<img src="https://img.shields.io/badge/prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white" style="border-radius: 5px;">
<!-- <img src="https://img.shields.io/badge/discord-4A154B?style=for-the-badge&logo=discord&logoColor=white" style="border-radius: 5px;"> -->
<img src="https://img.shields.io/badge/webhook-2088FF?style=for-the-badge&logo=webhook&logoColor=white" style="border-radius: 5px;">
</div>
<br>
<details>
    <summary>
<span style="font-size:150%"> 상세 기술 스택 </span></summary>

- OS : Linux
- Container : Docker v26.0.0
- Orchestration : Kubernetes v1.29.9
- CNI : Calico v3.27.0
- CI/CD : Jenkins v2.473
- Monitoring : Prometheus, Grafana(dashboard_ID - )
- Notification : Discord plugin
</details>

<br>

## 🖥️ IRS 운영 환경
<p align="center">
<img width="80%" src="./img/k8s_system.png"></p>


<br>

### ✔️ Calico CNI
<p align="center">




### 🖥️ 모니터링 시스템


<br>

## ✨젠킨스 파이프라인(배포 시나리오)
<details>
    <summary>
<span style="font-size:150%"> 백엔드 배포 시나리오 </span></summary>
<p align="center">

</br>
</details>

<details>
    <summary>
<span style="font-size:150%"> 프론트엔드 배포 시나리오 </span></summary>
<p align="center">


</details>

<details>
    <summary>
<span style="font-size:150%"> Discord 알림 </span></summary>
<p align="center">

- 성공과 실패 메시지에 포함되는 내용
  - 슬렉 알림 색깔 ex) Green, Red
  - 성공 및 실패 메시지 ex) SUCCESSFUL, FAILED
  - 어떤 파이프라인 stage에서 발생한 이벤트인지 ex) stage - 'Git Clone'
  - 현재 작업의 이름, 빌드 번호, 빌드 url ex) Job Info - 'frontend-pipe[100]'(http://[Jenkins_URL]) 

- ✅ 성공 메시지 <br>
  - 파이프라인이 성공적으로 완료되었을 때 발생하는 메시지<br>
  <img width="50%" src="img/discord_success.png">

- ❌ 실패 메시지 <br>
  - 파이프라인이 실패했을 때 발생하는 메시지<br>
  <img width="50%" src="img/discord_fail.png">

</details>
