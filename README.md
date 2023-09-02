## 목차
1. [서비스 소개](#1-서비스-소개)
2. [주요 기능](#2-주요-기능)
3. [기술 스택](#3-기술-스택)
4. [시스템 구성도](#4-시스템-구성도)
5. [실행 방법](#5-실행-방법)
6. [팀원 소개](#6-팀원-소개)


# 1. 서비스 소개

<div align="center">
  <img width="200" src="https://github.com/iinnu/SSAFY-iljungitjung/assets/55791128/0e79ec1a-84b0-4ea8-8972-68ac9edf5d29" />
</div>
<br />

**일정있정**은 개인과 소상공인을 위한 웹 스케줄링 서비스 입니다.  

네이버, 카카오 미용실과 같은 예약 시스템은 등록된 업체만 이용 가능하며 예약이나 약속을 잡을 때 전화나 SNS를 이용한 커뮤니케이션에 의존적이라는 문제점에 착안하여 쉽고 편하게 이용할 수 있는 웹 서비스를 개발하였습니다.  

‘매우 가벼운’ 일정 예약 툴을 찾고 있는 프로젝트 팀이나 일정 조율, 관리가 중요한 직업이지만 이를 위한 복잡한 절차를 거치기 힘든 소상공인들이 사용할 수 있습니다.

📆 **프로젝트 규모**: 2022.10.11 - 2022.11.21, 6인(프론트엔드 3명, 백엔드 3명)  

🔗 [노션 바로가기](https://fierce-alpaca-126.notion.site/3329ecd7f4c54f13979f2edce36bd810)

# 2. 주요 기능

### ✅ 일정 예약 기능
<img width="600" src="https://github.com/iinnu/SSAFY-iljungitjung/assets/55791128/2b00d354-8a39-4c01-b83c-b7094fac71d6" />
<img width="600" src="https://github.com/iinnu/SSAFY-iljungitjung/assets/55791128/2abaace4-4692-44b4-8bbc-0a81542d9462" />

고객은 예약 시간, 요청 사항으로 일정을 예약할 수 있습니다.  
고객이 업체의 일정을 확인한 후 빈 시간대에 요청 사항을 입력하면 업체 측에서는 확인 후 수락 및 거절 시 고객에게 메시지를 전송할 수 있습니다.

### ✅ 일정 알림 기능
<img width="250" src="https://github.com/iinnu/SSAFY-iljungitjung/assets/55791128/ebc5e040-cf84-496d-b210-0b4e49401814" />

매일 오전 10시 오늘 일정 알림을 고객에게 전송합니다.  
고객이 예약을 신청하거나 취소하면 업체에게 알림 문자를 전송해주고, 업체가 예약을 승인하거나 취소하면 고객에게 알림 문자를 전송해줍니다.

### ✅ 검색 기능
<img width="600" src="https://github.com/iinnu/SSAFY-iljungitjung/assets/55791128/9b99a872-563f-45bc-a93c-70a6d62e023a" />

사용자 이름을 기반으로 검색할 수 있습니다.  
검색 결과를 클릭하면 상세 설명이 나오고 바로가기 버튼을 클릭해서 예약 페이지로 이동할 수 있습니다.

### ✅ 예약 조회 기능
<img width="600" src="https://github.com/iinnu/SSAFY-iljungitjung/assets/55791128/067465ab-9797-44f9-a088-85f011ee17e7" />
<img width="600" src="https://github.com/iinnu/SSAFY-iljungitjung/assets/55791128/0d8a65cb-bbb0-4437-8cfd-d8e477c17f72" />

고객은 본인이 기간을 선택해서 예약한 목록을 확인하거나 예약을 취소할 수 있습니다.

### ✅ 일정 관리 기능
<img width="600" src="https://github.com/iinnu/SSAFY-iljungitjung/assets/55791128/87dbac9e-4378-4f92-9ff2-376025483889" />

업체는 카테고리 등록, 수정, 삭제를 통해 카테고리를 관리할 수 있습니다.  
업체는 일정 차단 기능을 통해 해당 시간대에 예약이 불가하도록 차단할 수 있습니다.

# 3. 기술 스택
## backend
### local
|Intellij|JDK 11|Spring Boot|Docker|
|:---:|:---:|:---:|:---:|
|<img src="https://user-images.githubusercontent.com/57346428/201485479-1afe6c3b-f4c6-445e-9571-dfa69fe05ed5.png" height="70px" />|<img src="https://user-images.githubusercontent.com/57346428/201485473-df4c3051-d002-4ebe-bb76-9f40877354a6.png" height="70px" />|<img src="https://user-images.githubusercontent.com/57346428/201485489-c736460b-4972-4a25-ae6d-3ffe1fc03364.png" height="70px" />|<img src="https://user-images.githubusercontent.com/57346428/201485465-fd9a1093-624f-4ec1-b90d-041b53db4abf.png" height="70px" />|
### server (ec2)
|Docker|Jenkins|Nginx|MariaDB|Redis|
|:---:|:---:|:---:|:---:|:---:|
|<img src="https://user-images.githubusercontent.com/57346428/201485465-fd9a1093-624f-4ec1-b90d-041b53db4abf.png" height="70px" />|<img src="https://user-images.githubusercontent.com/57346428/201485481-a64eef1c-5b2c-477b-928b-07167c7ceddb.png" height="70px" />|<img src="https://user-images.githubusercontent.com/57346428/201485478-c010bb71-e543-4d70-88f8-b54029e9db94.png" height="70px" />|<img src="https://user-images.githubusercontent.com/57346428/201485474-a6525625-bb89-4353-90e1-605d935941a4.png" height="70px" />|<img src="https://user-images.githubusercontent.com/57346428/205480235-660e544b-95b8-4f57-8920-d768b73ae8cd.png" height="70px" />|

## frontend
|HTML|CSS|JavaScript|TypeScript|
|:---:|:---:|:---:|:---:|
|<img src="https://user-images.githubusercontent.com/57346428/201485466-acdca075-fda0-4270-a624-13f65b68eb73.svg" height="70px" />|<img src="https://user-images.githubusercontent.com/57346428/201485464-d9acf4e1-bc99-4c53-9ee1-e0ea5208151f.svg" height="70px" />|<img src="https://user-images.githubusercontent.com/57346428/201485471-b2af26e8-a01f-4fca-99df-ba55197e7221.svg" height="70px" />|<img src="https://user-images.githubusercontent.com/57346428/205480236-c07a874f-7f21-401d-a5ab-e4eaf2c50248.png" height="70px" />|


|React|Redux|Sass|Mui|
|:---:|:---:|:---:|:---:|
|<img src="https://user-images.githubusercontent.com/57346428/201485482-4271f58e-eff9-4210-a501-226c37fe63cd.svg" height="70px" />|<img src="https://user-images.githubusercontent.com/57346428/201485485-39e73f7a-0b45-4745-9992-1a4b2cc20c22.svg" height="70px" />|<img src="https://user-images.githubusercontent.com/57346428/201485486-bc1ff015-0878-4b91-a669-26474ef15d46.svg" height="70px" />|<img src="https://user-images.githubusercontent.com/57346428/201485476-067081ef-b33d-4897-95f5-4698cae36c9d.png" height="70px" />|

# 4. 시스템 구성도

![제목_없는_다이어그램 drawio](https://user-images.githubusercontent.com/57346428/205480304-0a0b4420-04f7-437d-bee9-c31cca834cb1.png)

# 5. 실행 방법
## backend
### CI/CD  
gitlab(back branch) push or merge -> jenkins webhook -> auto build  

### SSH
```
ssh -i K7D106T.pem ubuntu@k7d106.p.ssafy.io
```

![image](https://user-images.githubusercontent.com/52438368/211197392-8860b850-19ee-4bcf-99d8-c04c73efc29c.png)
```
docker logs --tail 300 iljungitjung_server_green
```

## frontend
1. 레포지토리를 clone 받는다.
```bash
git clone https://lab.ssafy.com/s07-final/S07P31D106.git
```
2. `front` 폴더에서 `package.json`에 정의된 모듈을 설치한다.
```shell
npm install
```
3. 프로그램을 실행시킨다.
```shell
npm start
```


# 6. 팀원 소개
|조인후|김다은|김주영|이기종|이상민|이재영|
|:---:|:---:|:---:|:---:|:---:|:---:|
|front(팀장)|back|front|back|back|front|
