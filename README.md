## Algorithm-Study : 알고하자

---

## 🤷‍♂️ What is ALGO-HAZA?

#### 알고하자는 정기적 알고리즘 풀이를 목적으로 하는 모임입니다. 

디스코드에서 운영중이며 모각코, 문제풀이 공유 활동을 진행하고 있습니다.

- [![Discord](https://img.shields.io/discord/1228932539868250162?style=flat&logo=discord&logoColor=white&label=%EC%95%8C%EA%B3%A0%ED%95%98%EC%9E%90&labelColor=%235865F2&color=gray)](https://discord.gg/MdM8VsSASW)
- 활동 시기 : `2024/04 ~ `

---

## 🖍 Activity

- 매주 2 회 `PR 기여` 를 진행합니다.
- 매주 1 회 `모각코/문제풀이` 를 진행합니다.
- `PR` 미제출, `모각코/문제풀이` 미참시 경고가 1 회 누적됩니다.
- 경고는 매월 1 일에 초기화되며, 경고 3 회 초과 시 통보 후 퇴출됩니다.
- `PR` 미제출은 어떠한 사유도 허용되지 않으며, `모각코/문제풀이` 사전에 허락받은 사유만 인정됩니다.

## 🍳 `PR 기여`

- `PR 기여` 의 마감일은 매주 `수요일`, `토요일` 의 자정입니다. `(수 -> 목, 토 -> 일 밤)`
- 풀이하는 문제는 각자 자유롭게 선택하며, `PR` 당 최소 1 문제를 풀이해야 합니다.
- 풀이한 문제마다 다음을 포함한 파일들이 존재해야 합니다.

    - a. 문제의 간략한 설명
    - b. 문제 풀이 방법 정리
    - c. 문제 해결 인증 스크린샷
    - d. 정답 소스코드

- 각 파일은 `markdown` `(.md)` 으로 제작하는 것을 권장합니다.
- `PR` 제출 후, 관리자의 리뷰에 따라 `PR` 이 승인됩니다.

## 💻 `모각코/문제풀이`

- `모각코/문제풀이` 활동은 매주 `일요일` 에 이루어집니다.
- 해당 활동에 총 2 문제 `(풀이 + 공유 및 발표)` 를 진행합니다.
- `모각코/문제풀이` 활동 중 `웹캠` 은 필수사항이 아니지만, `마이크` 는 필수사항입니다.
- 사전 알림 없이 활동에 미참시 경고 1 회가 누적됩니다.
- 개인 사정으로 참여가 불가할 시, `토요일 23:00` 이전에 공지해야 합니다. 이후의 공지는 받지 않습니다.

---

## 💡 참여 방법

1. 이 저장소를 `fork` 한 후, 각 멤버별 `{Github ID}` 폴더를 만듭니다.
2. 폴더 내 풀이한 문제를 `정리` 합니다.
3. `PR` 템플릿에 맞춰 이 저장소에 `PR` 을 보냅니다.
4. 관리자의 `PR` 리뷰에 따라 `PR` 이 승인되거나 보충됩니다.


## 📁 문제 정리 구조

문서 구조는 크게 `M_{활동연도}_{월}`, `{주차수}`, `{풀이 문제}` 로 나뉩니다.

```
jbw9964/                    # 멤버 별 활동 폴더
├── M_2024_03
└── M_2024_04                   # 활동 연도/월
    ├── Week_2
    └── Week_3                  # 활동 주차 수
        ├── P_2609              # 풀이 문제
        │   ├── README.md               # 문제 설명 + 풀이 정리
        │   ├── Solution_2609.java      # 정답 소스코드
        │   └── approval_2609.PNG       # 인증 스크린샷
        └── P_10950
            ├── README.md
            ├── Solution_10950.java
            └── approval_10950.PNG

6 directories, 6 files
```

각 멤버별 활동 폴더 `(예시 : jbw9964)` 내 위 구조처럼 조직합니다.

- `M_{활동연도}_{월}` 폴더는 해당 `연도/월` 에 활동한 내역을 저장합니다.
- `{주차수}` 폴더는 활동한 주차별로 제작합니다.
- `{풀이 문제}` 폴더는 `풀이한 문제` 를 기록하는 폴더입니다.

---

## 🔖 `Commit 예시` & `PR` 규칙

#### - [`Commit 예시`](./docs/rules/github_repository/commit_and_organization.md#🔖-commits)

#### - [`PR 규칙`](./docs/rules/github_repository/pr_rules.md)
#### - [`PR 템플릿`](./docs/pull_request_template.md)

---

## 📑 Repository Description

|`Directory`|`Description`|
|:---:|---|
|`/assets`|  저장소의 예시, 이미지 등이 저장된 폴더입니다. |
|`/docs`|  저장소와 관련된 문서 등이 저장된 폴더입니다.  |
|`{Github ID}`|  멤버별 활동이 저장된 폴더입니다.  |
|[`경고조회 페이지`](https://github.com/ALGOHAZA-Study/Algorithm-Study/wiki/%EA%B2%BD%EA%B3%A0-%EC%A1%B0%ED%9A%8C-%ED%8E%98%EC%9D%B4%EC%A7%80)|  멤버별 누적된 경고를 조회하는 페이지입니다.  |



---

## 👋 Members

<table>
    <tr height="140x">
    <!-- Image & Github nickname : paste block down below -->
        <td align="center" width="130px">
            <a href="https://github.com/jbw9964"><img height="100px" width="100px" src="https://avatars.githubusercontent.com/jbw9964"/></a>    <!-- github link, github profile image -->
            <br />
            <a href="https://github.com/jbw9964"> jbw9964 </a>    <!-- github link & nickname -->
        </td>
        <td align="center" width="130px">
            <a href="https://github.com/uuccu"><img height="100px" width="100px" src="https://avatars.githubusercontent.com/uuccu "/></a>
            <br />
            <a href="https://github.com/uuccu"> TwoCastle9 </a>    
        </td>
        <td align="center" width="130px">
            <a href="https://github.com/Mungjin01"><img height="100px" width="100px" src="https://avatars.githubusercontent.com/Mungjin01"/></a>
            <br />
            <a href="https://github.com/Mungjin01"> Mungjin01 </a>    
        </td>
        <td align="center" width="130px">
            <a href="https://github.com/phs7646"><img height="100px" width="100px" src="https://avatars.githubusercontent.com/phs7646"/></a>
            <br />
            <a href="https://github.com/phs7646"> phs7646 </a>
        </td>
        <td align="center" width="130px">
            <a href="https://github.com/Sikca"><img height="100px" width="100px" src="https://avatars.githubusercontent.com/Sikca "/></a>
            <br />
            <a href="https://github.com/Sikca"> Sikca </a>    
        </td>
        <td align="center" width="130px">
            <a href="https://github.com/keltion"><img height="100px" width="100px" src="https://avatars.githubusercontent.com/keltion "/></a>
            <br />
            <a href="https://github.com/keltion"> keltion </a>    
        </td>
    </tr>
    <tr height="50px">
    <!-- solve.ac profile & tier : paste block down below -->
        <td align="center">
            <img src="http://mazassumnida.wtf/api/mini/generate_badge?boj=jbw9964" />   <!-- solved.ac badge : put solved.ac ID -->
            <br />
            <a href="https://www.acmicpc.net/user/jbw9964">Baekjoon</a>     <!-- BAJ profile -->
            <br />
            <a href="https://solved.ac/profile/jbw9964">solved.ac</a>       <!-- solved.ac profile -->
        </td>
        <td align="center">
            <img src="http://mazassumnida.wtf/api/mini/generate_badge?boj=msmsms0804" />
            <br />
            <a href="https://www.acmicpc.net/user/msmsms0804">Baekjoon</a>
            <br />
            <a href="https://solved.ac/profile/msmsms0804">solved.ac</a>
        </td>
        <td align="center">
            <img src="http://mazassumnida.wtf/api/mini/generate_badge?boj=l_min402" />
            <br />
            <a href="https://www.acmicpc.net/user/l_min402">Baekjoon</a>
            <br />
            <a href="https://solved.ac/profile/l_min402">solved.ac</a>
        </td>
        <td align="center">
            <img src="http://mazassumnida.wtf/api/mini/generate_badge?boj=phs7646" />
            <br />
            <a href="https://www.acmicpc.net/user/phs7646">Baekjoon</a>
            <br />
            <a href="https://solved.ac/profile/phs7646">solved.ac</a>
        </td>
        <td align="center">
            <img src="http://mazassumnida.wtf/api/mini/generate_badge?boj=we12223" />
            <br />
            <a href="https://www.acmicpc.net/user/we12223">Baekjoon</a>
            <br />
            <a href="https://solved.ac/profile/we12223">solved.ac</a>
        </td>
        <td align="center">
            <img src="http://mazassumnida.wtf/api/mini/generate_badge?boj=keltion" />
            <br />
            <a href="https://www.acmicpc.net/user/keltion">Baekjoon</a>
            <br />
            <a href="https://solved.ac/profile/keltion">solved.ac</a>
        </td>
    </tr>
</table>

<!-- Image & Github nickname : paste block down below 

<tr height="140x">
</tr>

<td align="center" width="130px">
    <a href=" Github Profile Link "><img height="100px" width="100px" src="https://avatars.githubusercontent.com/ {Github ID} "/></a>
    <br />
    <a href=" Github Profile Link "> {Github ID} </a>    
</td>

-->

<!-- solve.ac profile & tier : paste block down below

<tr height="50px">
</tr>

<td align="center">
    <img src="http://mazassumnida.wtf/api/mini/generate_badge?boj= {BAJ ID} " />
    <br />
    <a href=" {BAJ Profile Link} ">Baekjoon</a>
    <br />
    <a href=" {solved.ac Profile Link} ">solved.ac</a>
</td>

-->

---
