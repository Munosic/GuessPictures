import java.awt.event.*;

////////////////////////////////////////////////
// Controller (C)

/*スタート画面のコントローラ*/
class SceneController implements ActionListener{
  protected ThemeModel tmodel;
  protected SCModel sc;
  protected Clock clock;

  public SceneController(ThemeModel tmodel,SCModel sc,Clock clock) {
    this.tmodel = tmodel;
    this.sc = sc;
    this.clock = clock;
  }

  public void actionPerformed(ActionEvent ev) {
    String cmd = ev.getActionCommand();
    if(cmd == "Level1"){
      tmodel.setThemeset("Level1.txt", sc); //レベル1のお題ファイルをセット
    }else if(cmd == "Level2"){
      tmodel.setThemeset("Level2.txt", sc); //レベル2のお題ファイルをセット

    }else if(cmd == "Level3"){
      tmodel.setThemeset("Level3.txt", sc); //レベル3のお題ファイルをセット

    }else if(cmd == "startButton"){
      tmodel.setCurrentScene("countdown"); //currentSceneをcontdownに変更
      sc.sendSceneChange("countdown"); //相手側に次のシーン名countdownを送る
      tmodel.clockStart(5); //カウントダウンタイマーセット
      clock.setClockLabel(5); //カウントダウンタイマーのラベルの初期値5をセット
    }else if(cmd == "replay"){
      tmodel.setCurrentScene("start"); //currentSceneをstartに変更
      sc.sendSceneChange("start"); //相手側に次のシーン名startを送る
    }

  }
}
