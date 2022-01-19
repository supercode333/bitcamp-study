package com.eomcs.mylist.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("boards.data")));

    while (true) {
      try { // 이 작업을 수행하다가
        Board board = new Board();
        board.setTitle(in.readUTF());
        board.setContent(in.readUTF());
        board.setViewCount(in.readInt());
        board.setCreatedDate(Date.valueOf(in.readUTF()));

        boardList.add(board);
      } catch(Exception e) {// 만약 예외(오류)가 발생한다면 더 이상 데이터를 읽지말고 반복문을 나가라
        break;
      }
    }

    in.close();
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
    DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("boards.data")));

    Object[] arr = boardList.toArray();
    for (Object obj : arr) {
      Board board = (Board) obj;
      out.writeUTF(board.getTitle());  // 제목은 문자열이니 writeUTF로!
      out.writeUTF(board.getContent());
      out.writeInt(board.getViewCount());  // 조회수는 숫자이니 int로!
      out.writeUTF(board.getCreatedDate().toString());
    }

    out.close();  // 얘 close하면 얘가 포함하는 out1, out이 같이 close된다! 따라서 따로 out 안해줘도 된다
    return arr.length;
  }
}
