# 폴라로이드 다이어리 프로젝트
## 1. 개발환경
1. 개발 툴 
    * Android Studio 2.3
2. SDK 버전
    * minSdkVersion 21
    * targetSdkVersion 25
    * compileSdkVersion 25
3. 버전관리 
    * Git

## 2. 개발 기간
    2017년 2월 21일 ~ 2017년 4월 24일
    
## 3. UI

1. 앱 실행 시 가장 먼저 뜨는 메인화면에서 우측 하단 버튼(연필 모양 아이콘)을 클릭하여 새 폴라로이드 다이어리를 작성한다.
1. 폴라로이드 다이어리의 앞장엔 사진을 넣고, 뒷장엔 텍스트를 작성한다. 다이어리 면적을 짧게 클릭하여 앞/뒤를 뒤집을 수 있다.
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui1.png)

1. 다이어리 앞장의 사진이 들어갈 위치를 꾹 길게 눌러 기기에 저장된 사진을 가져온다.
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui1.png)
    
1. 다이어리 뒷장 역시 꾹 길게 눌러 텍스트를 작성한다. 상단 우측의 라디오 버튼으로 쓰기/읽기 모드를 전환할 수 있다.
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui1.png)
    
1. 뒤로가기 또는 상단 바 우측의 저장 버튼을 누르면 메인화면에 새 폴라로이드 다이어리가 추가된다. 삭제 버튼을 누르면 작성이 취소된다.
1. 작성된 폴라로이드 다이어리 역시 각 장을 짧게 클릭하여 앞장/뒷장을 뒤집을 수 있다.
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui1.png)

1. 저장되어 있는 폴라로이드 다이어리 각 장을 길게 꾹 누르면 해당 다이어리의 편집화면으로 이동한다. 삭제 혹은 기존 내용의 편집이 가능하다.
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui1.png)
    
1. 메인화면 상단 바 오른쪽에 있는 버튼(네모 모양 아이콘)을 클릭하여, 저장된 폴라로이드 다이어리의 크기를 크게(한 줄에 한 장씩) 또는 작게(한 줄에 세 장씩) 볼 수 있게끔 뷰 모드를 전환한다.
  ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui1.png)


1. 하단 중앙의 버튼(화살표 모양 아이콘) 클릭시 가장 아래(작성된 지 가장 오래 된 다이어리 위치)로 이동
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui1.png)

1. 상단 바 클릭 시 가장 위(가장 최신에 작성된 다이어리 위치)로 이동
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui1.png)

1. 가로모드를 지원한다.
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui1.png)



## 4. Sutructure(Xmind)
![screensh](https://github.com/HyunjuLee521/NewDiaryProject/blob/master/structure.png)

## 5. Features
1. 메인 화면 
<pre>
1. 하단 우측의 버튼(연필 모양 아이콘) 클릭하여 새 메모 작성
2. 작성되어 있는 폴라로이드 다이어리를 꾹 눌러 편집 또는 삭제하기
3. 오른쪽 상단의 버튼(네모 모양 아이콘) 클릭하여, 
   작성된 폴라로이드 다이어리의 크기를 크게(한 줄에 한 장씩)/ 작게(한 줄에 세 장씩) 볼 수 있게끔 모드 전환
4. 하단 중앙의 버튼(화살표 모양 아이콘) 클릭시 가장 아래(작성된 지 가장 오래 된 다이어리 위치)로 이동
5. 상단 바 클릭 시 가장 위(가장 최신에 작성된 다이어리 위치)로 이동
</pre>

2. 편집 화면
<pre>
1. 앞 장 길게 꾹 눌러 사진 추가하기. 기기에 저장된 사진 불러와서 한 장 선택
2. 뒷 장 길게 꾹 눌러 텍스트 작성하기
</pre>

3. 메인, 편집 화면
<pre>
1. 작성되어 있는 각 폴라로이드 다이어리를 짧게 클릭해, 앞장(사진)/뒷장(텍스트) 뒤집기
2. 어플 종료하고 다시 실행해도, 각 폴라로이드 다이어리의 앞장 혹은 뒷장으로 뒤집어 놓은 상태 그대로 복원
3. 가로모드 지원
</pre>

## 6. Credits

1. 안드로이드 이벤트 버스
    * greenrobot / EventBus : https://github.com/greenrobot/EventBus

    


## 7. License
Copyright 2017. Hyunju Lee

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.




