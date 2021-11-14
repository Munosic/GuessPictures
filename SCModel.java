import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class SCModel {
  private boolean isServer; //trueでサーバー、falseでクライアントを生成
  private boolean isDrawTurn; //描く側ならtrue、当てる側ならfalse
  private CommServer sv = null;
  private CommClient cl = null;
  private int recvX, recvY, recvPreX, recvPreY, recvRad; //recvDraw()で受け取ったx,y,半径の値を格納する
  private Color recvC; //recvDraw()で受け取った色を格納する
  private String recvChat; //recvChat()で受け取ったチャット内容を格納する
  private String recvScene;
  private int recvTime;
  private int recvScore[] = new int[2];

  public SCModel(boolean isServer, boolean isDrawTurn, int port) {
    this.isServer = isServer;
    this.isDrawTurn = isDrawTurn;
    if(isServer) { //サーバー生成
      sv = new CommServer(port);
    } else { //クライアント生成
      cl = new CommClient("localhost",port);
    }
  }

//x,y座標の値、色を取り出す
  public int getX() {
    return recvX;
  }
  public int getY() {
    return recvY;
  }
  public int getPreX() {
    return recvPreX;
  }
  public int getPreY() {
    return recvPreY;
  }
  public int getR() {
    return recvRad;
  }
  public Color getColor() {
    return recvC;
  }
  public String getChat() {
    return recvChat;
  }
  public String getScene() {
    return recvScene;
  }
  public int getTime() {
    return recvTime;
  }
  public int getScore(int player) {
    return recvScore[player];
  }

  //サーバーかクライアントかの判定
  public boolean isServer() {
    return isServer;
  }
  public boolean isDrawTurn() {
    return isDrawTurn;
  }

  public void setDrawTurn(boolean turn) {
    isDrawTurn = turn;
  }

  public void changeDrawTurn() {
    if(isDrawTurn == true) {
      isDrawTurn = false;
    } else {
      isDrawTurn = true;
    }
  }

  public void setScene(String s) {
    recvScene = s;
  }

//送信
  public void sendDraw(int x, int y, int preX, int preY, int rad, Color c) {
    int r,g,b;
    r = c.getRed();
    g = c.getGreen();
    b = c.getBlue();
    if(isServer) {
      sv.send("DRAW:"+x+" "+y+" "+preX+" "+preY+" "+rad+" "+r+" "+g+" "+b);
    } else {
      cl.send("DRAW:"+x+" "+y+" "+preX+" "+preY+" "+rad+" "+r+" "+g+" "+b);
    }
  }

  public void sendClear() {
    if(isServer) {
      sv.send("CLEAR:");
    } else {
      cl.send("CLEAR:");
    }
  }

  public void sendChat(String s) {
    if(!s.equals("")) {
      if(isServer) {
        sv.send("CHAT:"+s);
      } else {
        cl.send("CHAT:"+s);
      }
    }
  }

  // public void sendSceneChange() {
  //   if(isServer) {
  //     sv.send("SCENE:");
  //   } else {
  //     cl.send("SCENE:");
  //   }
  // }
  public void sendSceneChange(String s) {
    if(isServer) {
      sv.send("SCENE:"+s);
    } else {
      cl.send("SCENE:"+s);
    }
  }

  public void sendTurnChange() {
    if(isServer) {
      sv.send("TURN:");
    } else {
      cl.send("TURN:");
    }
  }

  public void sendTime(int t) {
    if(isServer) {
      sv.send("TIME:"+t);
    } else {
      cl.send("TIME:"+t);
    }
  }

  public void sendScore(int player, int s) {
    if(isServer) {
      sv.send("SCORE:"+player+" "+s);
    } else {
      cl.send("SCORE:"+player+" "+s);
    }
  }

  public void sendLabelClear(){
    if(isServer) {
      sv.send("LABEL:");
    } else {
      cl.send("LABEL:");
    }
  }


//受信
  public String recvSort() {
    String msg;
    if(isServer) {
      msg = sv.recv();
    } else {
      msg = cl.recv();
    }
    if(msg==null) return null;
    String[] cmd=msg.split(":");
    switch(cmd[0]) {
      case "DRAW":
        renewDraw(cmd[1]);
        break;
      case "CLEAR":
        break;
      case "CHAT":
        renewChat(cmd[1]);
        break;
      case "SCENE":
        renewScene(cmd[1]);
        break;
      case "TIME":
        renewTime(cmd[1]);
        break;
      case "SCORE":
        renewScore(cmd[1]);
        break;
      case "TURN":
        changeDrawTurn();
        break;
      case "LABEL":
        break;
    }
    return cmd[0];
  }

  private void renewDraw(String msg) {
    int r, g, b;
    String[] xyc = msg.split(" ");
    recvX = Integer.parseInt(xyc[0]); recvY = Integer.parseInt(xyc[1]);
    recvPreX = Integer.parseInt(xyc[2]); recvPreY = Integer.parseInt(xyc[3]);
    recvRad = Integer.parseInt(xyc[4]);
    r = Integer.parseInt(xyc[5]); g = Integer.parseInt(xyc[6]); b = Integer.parseInt(xyc[7]);
    recvC = new Color(r, g, b);
    System.out.println(recvX+" "+recvY+" "+recvPreX+" "+recvPreY+" "+recvRad+"("+r+","+g+","+b+")");
  }

  private void renewChat(String msg) {
    recvChat = msg;
    System.out.println("Chat: "+recvChat);
  }

  private void renewScene(String msg) {
    recvScene = msg;
  }

  private void renewTime(String msg) {
    int t = Integer.parseInt(msg);
    if(t >= 0) recvTime = t;
  }

  private void renewScore(String msg) {
    int player, score;
    String[] ps = msg.split(" ");
    player = Integer.parseInt(ps[0]); score = Integer.parseInt(ps[1]);
    recvScore[player] = score;
  }
}
