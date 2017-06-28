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

1. ViewPager, TabLayout으로 3개의 화면(플레이어, 재생목록, 즐겨찾는 재생목록)으로 구성하였습니다. 
동시에, 하단의 '뮤직컨트롤러'는 세 화면 중 어디에서나 보이게끔 고정하였습니다.
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui1.png)
    
1. 우선, 앱을 실행하면 가장 먼저 뜨는 재생목록 화면에서 '곡추가' 버튼을 눌러 기기에 저장된 음악파일을 불러올 수 있게 하였습니다. 
1. 곡명 혹은 아티스트명으로 구분하여 기기에 저장된 음악 파일을 보여줍니다. 
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui2.png)

1. 원하는 음악들을 선택하고 '플레이리스트에 추가하기' 버튼을 눌러 재생목록에 추가합니다.
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui3.png)

1. 재생목록에서 음악을 선택하여 재생합니다. '플레이어'와 하단의 '뮤직컨트롤러'에도 해당 음악 정보가 업데이트 됩니다.
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui4.png)

1. 플레이어 화면에서 하트 버튼을 클릭하여, 현재 재생중인 곡을 즐겨찾기에 추가할 수 있습니다. 즐겨찾기에 추가된 곡들을 모아 기존의 '재생목록'과는 별개로 '즐겨찾는 재생목록'을 제공합니다.
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui5.png)

1. 재생목록 편집기능을 제공합니다.
1. 롤리팝 이상에서는 알림바가 표시됩니다. 이전/다음 곡 재생, 재생/일시정지, 앱 종료 기능이 있습니다. 
1. 잠금화면에서도 알림이 표시됩니다. 
    ![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/ui6.png)


## 4. Sutructure(Xmind)
![screensh](https://github.com/HyunjuLee521/MusicPlayerProject/blob/master/structure.png)

## 5. Features
1. 메인 화면 
<pre>
1. 하단 우측의 버튼(연필 모양 아이콘) 클릭하여 새 메모 작성
2. 작성되어 있는 폴라로이드를 꾹 눌러 편집 또는 삭제하기
3. 오른쪽 상단의 버튼(네모 모양 아이콘) 클릭하여, 작성된 폴라로이드 크기를 크게(한 줄에 한 장씩)/ 작게(한 줄에 세 장씩) 볼 수 있게끔 모드 전환
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
1. 작성되어 있는 각 폴라로이드 짧게 클릭해, 앞장(사진)/뒷장(텍스트) 뒤집기
2. 어플 종료하고 다시 실행해도, 각 폴라로이드 앞장 혹은 뒷장으로 뒤집어 놓은 상태 그대로 복원
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




