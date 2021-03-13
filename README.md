#

MVVM 아키텍처 패턴으로 작성한 드라마앤컴퍼니 과제입니다.

작업 환경
- Android Studio 4.1.2
- Kotlin 1.4.31
- Min SDK Version 17

사용한 라이브러리
- Dagger-Hilt
- Androd KTX
- Room
- RxKotlin, RxAndroid
- Retrofit, OkHttp
- Gson
- Coil

## 핵심 기능

- Github 유저 검색시 검색 결과와 Local DB 데이터 비교하여 즐겨찾기 표시 여부 체크 (HashMap 자료구조 이용)
- 유저 리스트 이름 첫글자 Header 표시 (like 연락처 앱)
- 즐겨찾기 추가시 첫 글자에 해당하는 Group내 정렬 순서에 알맞게 추가
```kotlin
// ex) 기존 list에 "A-2"를 추가

val list = ["A-1", "A-3"]

val result = put("A-2")

// result : ["A-1", "A-2", "A-3"]
```