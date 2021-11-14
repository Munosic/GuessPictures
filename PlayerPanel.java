import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

class PlayerPanel extends JPanel{
    private JLabel c1; //"あと"ラベル
    private JLabel c2; //残り時間を表示させるラベル
    private JLabel sec; //"秒"ラベル
    private JLabel p1; //"Player1: "ラベル
    private JLabel p2; //"Player2: "ラベル
    private JLabel s1; //Player1のスコアを表示させるラベル
    private JLabel s2; //Player2のスコアを表示させるラベル
    protected JLabel s; //"スコア"という機能名を表示させるラベル
    protected JPanel timer; //タイマー機能のパネル
    protected JPanel score,scorePanel; //スコア機能のパネル
    protected Font font; //外部フォント

    public PlayerPanel(ThemeModel tmodel, DrawModel dmodel,Clock clock){
      this.setLayout(new GridLayout(2,1));

      /*フォント*/
      font = dmodel.getFont();
      font = dmodel.setSize(30f);

      /*クロック表示 */
      timer = new JPanel();
      timer.setBackground(Color.white);
      timer.setLayout(new GridLayout(1,3));
      timer.setBorder(new LineBorder(Color.decode("#96B4CE"),6));
      c1 = new JLabel("あと",JLabel.RIGHT);
      c1.setFont(font);
      c2 = clock.getClock0(); //clockで生成したラベルを代入
      sec = new JLabel("秒",JLabel.LEFT);
      sec.setFont(font);
      font = dmodel.setSize(40f);
      c2.setFont(font);
      c2.setForeground(new Color(143, 195, 31));
      timer.add(c1); //"あと"
      timer.add(c2); //残り時間
      timer.add(sec); //"秒"

      /*プレイヤー＆スコア表示 */
      scorePanel = new JPanel();
      score = new JPanel();
      scorePanel.setBackground(Color.white);
      scorePanel.setBorder(new LineBorder(Color.decode("#96B4CE"),6));
      score.setBackground(Color.white);
      scorePanel.setLayout(new BorderLayout());
      score.setLayout(new GridLayout(2,2));

      font = dmodel.setSize(40f);
      s = new JLabel("スコア",JLabel.CENTER);
      s.setBorder(new LineBorder(Color.decode("#96B4CE"),1));
      score.setBorder(new LineBorder(Color.decode("#96B4CE"),1));
      p1 = new JLabel("Player1: ",JLabel.RIGHT);
      s1 = tmodel.setLabel_score0(); //ThemeModelで生成したラベル代入
      p2 = new JLabel("Player2: ",JLabel.RIGHT);
      s2 = tmodel.setLabel_score1(); //ThemeModelで生成したラベル代入
      font = dmodel.setSize(20f);
      p1.setFont(font);
      p2.setFont(font);
      s1.setFont(font);
      s2.setFont(font);
      s.setFont(font);
      score.add(p1); score.add(s1);
      score.add(p2); score.add(s2);
      scorePanel.add(s,BorderLayout.NORTH); //機能名の表示
      scorePanel.add(score,BorderLayout.CENTER); //機能の表示

      this.add(timer); //タイマー機能配置
      this.add(scorePanel); //スコア機能配置
  }
}
