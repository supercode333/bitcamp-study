package com.eomcs.mylist.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.eomcs.mylist.domain.Board;
import com.eomcs.util.ArrayList;

@RestController
public class BoardController {

  ArrayList boardList = new ArrayList();

  public BoardController() throws Exception {
    System.out.println("BoardController() 호출됨!");

    try {
      ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("boards.ser2")));

      // 1) 객체가 각각 따로 serialize 되었을 경우, 다음과 같이 객체 단위로 읽으면 되고,
      //    while (true) {
      //      try { // 이 작업을 수행하다가
      //        Board board = (Board) in.readObject(); // 아 in.readObject() 얘가 내부적으로 객체를 만들어서 객체 주소(board 인스턴스라고 알려줘야 함)를 리턴하는구나
      //        boardList.add(board);
      //      } catch(Exception e) { // 만약 예외(오류)가 발생한다면 더 이상 데이터를 읽지말고 반복문을 나가라
      //        break;
      //      }
      //    }

      // 2) 목록이 통째로 serialize 되었을 경우, 한 번에 목록을 읽으면 된다.
      boardList = (ArrayList) in.readObject(); // 단 기존의 생성한 ArrayList 객체는 버린다.

      in.close();
    } catch (Exception e) {
      System.out.println("게시글 데이터 로딩 중 오류 발생!");
    }
  }

  @RequestMapping("/board/list")
  public Object list() {
    return boardList.toArray();
  }

  @RequestMapping("/board/add")
  public Object add(Board board) {

    board.setCreatedDate(new Date(System.currentTimeMillis()));
    boardList.add(board);
    return boardList.size();
  }


  @RequestMapping("/board/get")
  public Object get(int index) {
    if (index < 0 || index >= boardList.size()) {
      return "";
    }
    Board board = (Board) boardList.get(index);
    board.setViewCount(board.getViewCount() + 1);

    return board;
  }

  @RequestMapping("/board/update")
  public Object update(int index, Board board) {
    if (index < 0 || index >= boardList.size()) {
      return 0;
    }

    Board old = (Board) boardList.get(index);
    board.setViewCount(old.getViewCount());
    board.setCreatedDate(old.getCreatedDate());

    return boardList.set(index, board) == null ? 0 : 1;
  }

  @RequestMapping("/board/delete")
  public Object delete(int index) {
    if (index < 0 || index >= boardList.size()) {
      return 0;
    }
    return boardList.remove(index) == null ? 0 : 1;
  }

  @RequestMapping("/board/save")
  public Object save() throws Exception {
    ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("boards.ser2"))); // serialize의 ser을 따와서 파일 이름 바꿈

    // 1) 다음과 같이 목록에 들어있는 객체를 한 개씩 순차적으로 serialize 할 수도 있고,
    //    Object[] arr = boardList.toArray();
    //    for (Object obj : arr) {
    //      out.writeObject(obj);
    //    }

    // 2) 다음과 같이 목록 자체를 serialize 할 수도 있다. 
    out.writeObject(boardList);

    out.close();  // 얘 close하면 얘가 포함하는 out1, out이 같이 close된다! 따라서 따로 out 안해줘도 된다
    return boardList.size();
  }
}
