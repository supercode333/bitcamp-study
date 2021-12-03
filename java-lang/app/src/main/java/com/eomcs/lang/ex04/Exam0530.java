package com.eomcs.lang.ex04;

//# 배열 - 배열 레퍼런스와 배열 인스턴스
//
public class Exam0530 {
  public static void main(String[] args) {

    int[] arr1 = new int[5];

    // 배열 선언문 분석:
    // arr1
    //   - 배열 메모리의 주소를 담는 변수이다.
    //   - 이렇게 메모리의 주소를 보관하는 변수를 '레퍼런스'라 부른다.
    //
    // new int[5]
    //   - new 명령은 사용할 메모리를 확보하는 명령이다.
    //   - 즉 연속된 5 개의 int 타입 메모리를 준비하라는 명령이다.
    //   - 사용할 메모리를 확보한 후 그 메모리의 찾아 갈 수 있도록 시작 주소를 리턴한다.
    //   - 이렇게 값을 저장하기 위해 확보된 메모리를 "인스턴스"라 부른다.
    //
    // new 명령은 메모리를 확보하는 명령이다.
    // - 리턴 값은 확보된 메모리의 시작 주소이다.

    // 
    arr1 = new int[5];

    arr1[0] = 100; // arr1에 저장된 주소로 찾아가서 0번째 항목에 값을 넣어라!
    arr1[1] = 200; // arr1에 저장된 추소로 찾아가서 1번째 항목에 값을 넣어라!

    System.out.println(arr1[0]);
    System.out.println(arr1[1]);
  }
}

// [정리]
// 레퍼런스(reference)란?
// - 값이 아닌 메모리의 주소를 담는 변수.
//
// 인스턴스(instance)란?
// - 값을 저장하는 메모리.
//