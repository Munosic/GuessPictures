import javax.swing.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

////////////////////////////////////////////////
// Model (M)

class ThemeModel extends Observable{
    protected JTextField t = new JTextField();
    protected JLabel l;
    protected JLabel s0 = new JLabel("",JLabel.CENTER);
    protected JLabel s1 = new JLabel("",JLabel.CENTER);//Score表示用
    protected JLabel s2 = new JLabel("スコア: ", JLabel.CENTER);
    protected JLabel s3 = new JLabel("スコア: ", JLabel.CENTER);//エンディングのScore表示用
    protected JLabel c;//Clock表示用
    protected Score score = new Score(2);//今は適当
    protected Clock clock;
    protected int size=1;//今は適当

    public ThemeModel(Clock clock) {
      this.clock = clock;
    }

    public JTextField getTextField(){return t;} //入力テキストのgetter
    public JLabel setLabel(int position){ //お題表示ラベルのsetter
      l = new JLabel(theme,position);
      return l;
    }
    public JLabel setLabel_score0(){ //スコア表示ラベルのsetter
      s0.setText(""+score.getScore(0));
      return s0;
    }

    public JLabel setLabel_score1(){ //スコア表示ラベルのsetter
      s1.setText(""+score.getScore(1));
      return s1;
    }

    public JLabel setLabel_score2(){ //エンディングのスコア表示ラベルのsetter
      s2.setText("スコア: "+score.getScore(0));
      return s2;
    }

    public JLabel setLabel_score3(){ //エンディングのスコア表示ラベルのsetter
      s3.setText("スコア: "+score.getScore(1));
      return s3;
    }


    public void countdownStart(){
      setChanged();
      notifyObservers(); //update呼ばれる→start
    }

    List<String> list = new ArrayList<String>();//Arrays.asList("start"));
    SelectTheme selectTheme = new SelectTheme(list);
    protected int num=0;
    protected String theme;// = selectTheme.getTheme(num);
    protected int themenum = 5;

    boolean ifLast = true;

    public void setThemeset(String filename,SCModel sc){
      ifLast = true;
      num = 0;
      list.clear();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"));
            String str = br.readLine();
            while(str!=null){
                list.add(str);
                str = br.readLine();
            }
            size = list.size();
            br.close();
        }catch(IOException e){
            System.out.println("IO error");
        }
        selectTheme.newThemeSet(list);
        theme = selectTheme.getTheme(num);
        if(sc.isDrawTurn()){
          l.setText(theme);
        }
        //clock.start(10);
    }

    public void clockStart(int count){
      clock.start(count);
    }

    /*新たなお題をセット*/
    public void setOdai(){
      l.setText(theme);
    }

    /*デバッグ用なので最終的には使わないです*/
    public void setOdai(String s){
      l.setText(s);
    }

    public void setThemeNum(int themenum) {
      this.themenum = themenum;
    }

    /*現在出題されているお題のgetter */
    public String getCurrentTheme() {
      return theme;
    }

    boolean result;

    /*お題が正しいかどうか判定して返す*/
    public boolean check(String s,int player){
      result = s.equals(theme);
      System.out.println(result);
      if(result){
        num++;
        score.plus(0,clock.getCount());//今は適当(プレイヤー0に残り時間と等しい点数を加算)
        score.plus(1,15);//今は適当(プレイヤー1に固定値を加算)
        clock.start(30);//正解したら再度30秒数え始める
        if(num<size && num<themenum){
          theme = selectTheme.getTheme(num);
        }else{
          ifLast = false;//終了判定
          System.out.println(score.rank());//今は適当
        }
      }
      // else{//このelseは本番はなくてもいいかもしれない
      //   score.minus(0);
      // }
      renewScoreLabel();
      return result;
    }




     /*お題が正しいかどうか判定して返す*/
    public void next(boolean result){
      if(result){
        num++;
        clock.start(30);//時間切れになったら再度30秒数え始める
        if(num<size && num<themenum){
          theme = selectTheme.getTheme(num);
        }else{
          ifLast = false;//終了判定
          System.out.println(score.rank());//今は適当
        }
      }
    }

    /*Enterを押したときにTextFieldを白紙にする*/
    public void reset(){
      t.setText("");
    }

    public boolean checkLast(){
      return ifLast;
    }

    public int getScore(int player){
      return score.getScore(player);
    }

    public void setScore(int player, int s) {
      score.setScore(player, s);
    }

    public void renewScoreLabel() {
      s0.setText(""+score.getScore(0));
      s1.setText(""+score.getScore(1));
      s2.setText("スコア: "+score.getScore(0));
      s3.setText("スコア: "+score.getScore(1));
    }

    public void resetScore(){
      score.resetScore(); //プレイヤー人数(1～)
      s0.setText(""+score.getScore(0));
      s1.setText(""+score.getScore(1));
    }

    protected String currentScene = "start";

    public String getCurrentScene() {return currentScene;}

    public void setCurrentScene(String scene) {
      currentScene = scene;
    }
}
