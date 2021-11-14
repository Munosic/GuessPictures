import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class TransDrawFrame extends JFrame{
  DrawModel model;
  ThemeModel tmodel;
  ChatModel cmodel;
  SCModel sc;
  Clock clock;
  ViewPanel view1;
  OdaiPanel view2;
  PlayerPanel view3;
  ChatPanel view4;
  StartPanel stview;
  EndingPanel enview;
  CountdownPanel cdview;
  DrawController cont;
  OdaiController cont2;
  SceneController scont;

  public static JPanel cardPanel; //各シーンをまとめるパネル
  public static CardLayout layout; //遷移用のレイアウト
  public SetComponent setC; //GridLayoutの設定

  public TransDrawFrame(DrawModel model, ThemeModel tmodel, ChatModel cmodel, SCModel sc, Clock clock, String user){

    this.setTitle(user);
    this.setSize(1000,500);
    this.setLocationRelativeTo(null); //画面が真ん中に配置
    this.setResizable(false); //ウインドウサイズ固定


    this.sc = sc;
    this.model = model;
    this.tmodel = tmodel;
    this.cmodel = cmodel;
    this.clock = clock;

    /*シーンパネル生成*/
    // startScene
    JPanel startScene = new JPanel();
    scont = new SceneController(tmodel,sc,clock);
    stview = new StartPanel(model,scont,sc);
    startScene.setLayout(new BorderLayout());
    startScene.add(stview,BorderLayout.CENTER);

    //countdownScene
    JPanel countdownScene = new JPanel();
    cdview = new CountdownPanel(model, sc,clock);
    countdownScene.setLayout(new BorderLayout());
    countdownScene.add(cdview,BorderLayout.CENTER);

    //mainScene
    JPanel mainScene = new JPanel();
    mainScene.setBackground(Color.decode("#96B4CE"));
    cont = new DrawController(model, cmodel,sc);
    view1 = new ViewPanel(model,cont,cmodel);
    cont2 = new OdaiController(tmodel,cmodel,sc);
    view2 = new OdaiPanel(tmodel,model,cont2,sc);
    view3 = new PlayerPanel(tmodel, model,clock);
    view4 = new ChatPanel(cmodel,model);
    JPanel subPanel = new JPanel();
    subPanel.setLayout(new GridLayout(2,1));
    subPanel.add(view3);
    subPanel.add(view4);
    GridBagConstraints gbc = new GridBagConstraints();
    GridBagLayout gbl = new GridBagLayout();
    mainScene.setLayout(gbl);
    setC = new SetComponent(gbc,gbl,mainScene);
    setC.setComponent(view1,0,1,2,1,1.0,0.9,new Insets(0,0,0,0));
    setC.setComponent(view2,0,0,2,1,1.0,0.1,new Insets(0,0,0,0));
    setC.setComponent(subPanel,2,0,1,2,1.0,1.0,new Insets(0,0,0,0));

    //endingScene
    JPanel endingScene = new JPanel();
    enview = new EndingPanel(tmodel,model,scont);
    endingScene.setLayout(new BorderLayout());
    endingScene.add(enview,BorderLayout.CENTER);

    // CardLayout用パネル
    cardPanel = new JPanel();
    layout = new CardLayout();
    cardPanel.setLayout(layout);

    /*カードパネルに各パネルを登録*/
    cardPanel.add(startScene, "start");
    cardPanel.add(countdownScene,"countdown");
    cardPanel.add(mainScene, "main");
    cardPanel.add(endingScene, "ending");

    this.add(cardPanel, BorderLayout.CENTER);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }


  public static void main(String argv[]) {
    DrawModel model = new DrawModel();
    SCModel sc;
    String player;
    ChatModel cmodel = new ChatModel();
    if(argv[0].equals("server")) {
      sc = new SCModel(true, false, 10010);
      player = "Player2";
    } else {
      sc = new SCModel(false, true, 10010);
      player = "Player1";
    }
    Clock clock = new Clock(sc);
    ThemeModel tmodel = new ThemeModel(clock);
    new TransDrawFrame(model, tmodel, cmodel, sc, clock, player);

    Color c;
    String scState;

    //tmodel.setCurrentScene("main");

    while(true) {
      layout.show(cardPanel, tmodel.getCurrentScene());
      scState = sc.recvSort(); // 何らかの情報を受け取り更新する。
      System.out.println(scState);
      // scStateには何の命令を受け取ったかが格納される。
      // 相手から何かしらが送信されない限り、このwhile文はここで止まる。

      if(scState.equals("SCENE")) { //シーンチェンジ処理
        if(sc.getScene().equals("changed")) { //while文を回すための処理
          //sc.setScene(tmodel.getCurrentScene());
          System.out.println("changed to "+tmodel.getCurrentScene());
        } else {
          tmodel.setCurrentScene(sc.getScene());
          sc.sendSceneChange("changed"); //while文を回すための処理
        }
      }

      System.out.println("scene: "+ tmodel.getCurrentScene());

      if(tmodel.getCurrentScene().equals("start")){ //スタートシーン
        System.out.println("do start");


      } else if(tmodel.getCurrentScene().equals("countdown")){
        System.out.println("do countdown");
         if(scState.equals("TIME")) {
           clock.setClockLabel(sc.getTime());
           if(sc.getTime() == 0){
             sc.sendChat("Time Out");

           }
         }
         if(scState.equals("CHAT")){
           if(sc.getChat().equals("Time Out")){
               tmodel.setCurrentScene("main");
               sc.sendSceneChange("main");
               tmodel.clockStart(30);
           }
         }


      }else if(tmodel.getCurrentScene().equals("main")){ //メインシーン
        System.out.println("do main");


        if(scState.equals("SCORE")) {
          tmodel.setScore(0, sc.getScore(0));
          tmodel.setScore(1, sc.getScore(1));
          tmodel.renewScoreLabel();
          System.out.println(sc.getScore(0)+" "+sc.getScore(1));
        }

        if(!sc.isDrawTurn()) {/* 自分がお題を当てる側の時 */
          /*描く側から受け取った絵の情報を当てる側に反映する*/
          if(scState.equals("TIME")) {
            clock.setClockLabel(sc.getTime());
            if(sc.getTime()<=0){
              sc.sendTime(-1);
              cmodel.renewChat("時間切れ…");
              cmodel.setCorrectLabel("時間切れ…");
            }
          }
          if(scState.equals("CHAT")) {
            cmodel.renewChat(sc.getChat());
            cmodel.setCorrectLabel(sc.getChat());
          }
          if(scState.equals("CLEAR")) {
            System.out.println("clear");
            model.reset();
          }
          if(scState.equals("DRAW")) {
            model.addLine(sc.getX(), sc.getY(), sc.getPreX(), sc.getPreY(), sc.getR(), sc.getColor());
          }
          if(scState.equals("LABEL")){
            cmodel.setCorrectLabel("");
          }
        } else {/* 自分が絵を描く側の時 */
          if(scState.equals("CHAT")){
            cmodel.renewChat(sc.getChat());

            if(tmodel.check(sc.getChat(), 0)) {
              sc.sendChat("せいかい！");
              cmodel.renewChat("せいかい！");
              cmodel.setCorrectLabel("せいかい！");
              sc.sendClear();
              tmodel.setOdai(); //お題変更
              model.reset(); //白紙にする
              // sc.changeDrawTurn();
              // sc.sendTurnChange();
              if(!tmodel.checkLast()) {
                System.out.println("end");
                tmodel.setCurrentScene("ending");
                sc.sendSceneChange("ending");
              }
            }
            sc.sendScore(0, tmodel.getScore(0));
            sc.sendScore(1, tmodel.getScore(1));

          }
          if(scState.equals("TIME")) {
            cmodel.renewChat("時間切れ…");
            sc.sendChat("A.「"+tmodel.getCurrentTheme()+"」");
            cmodel.setCorrectLabel("時間切れ…");
            tmodel.next(true);
            sc.sendClear();
            tmodel.setOdai(); //お題変更
            model.reset(); //白紙にする
            // sc.changeDrawTurn();
            // sc.sendTurnChange();
            if(!tmodel.checkLast()) {
              System.out.println("end");
              tmodel.setCurrentScene("ending");
              sc.sendSceneChange("ending");
            }
            sc.sendScore(0, tmodel.getScore(0));
            sc.sendScore(1, tmodel.getScore(1));
          }
        }
      }else if(tmodel.getCurrentScene().equals("ending")){
        System.out.println("do ending");
        cmodel.clearChat();
        cmodel.setCorrectLabel("");
        tmodel.resetScore();
        clock.setClockLabel(5);


        if(scState.equals("SCORE")) {
          tmodel.setScore(0, sc.getScore(0));
          tmodel.setScore(1, sc.getScore(1));
          tmodel.renewScoreLabel();
          System.out.println(sc.getScore(0)+" "+sc.getScore(1));
        }

      }
    }
  }
}
