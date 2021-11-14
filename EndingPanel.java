import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

////////////////////////////////////////////////
// View (V)

// Viewは，Observerをimplementsする．Modelを監視して，
// モデルが更新されたupdateする．実際には，Modelから
// update が呼び出される．

/*エンディング画面のview*/

class EndingPanel extends JPanel{
  protected ThemeModel tmodel;
  protected DrawModel dmodel;
  protected JLabel score1,score2,player1,player2,winner,loser;
  protected JPanel scorePanel;
  protected JButton replay;
  protected Font font;
  protected Image image;
  public EndingPanel(ThemeModel tmodel,DrawModel dmodel,SceneController c) {
    this.tmodel = tmodel;
    this.setBackground(Color.white);

    /*背景*/
    image = Toolkit.getDefaultToolkit().createImage("ending.jpg");

    font = dmodel.getFont();
    font = dmodel.setSize(40f);

    score1 = tmodel.setLabel_score2(); //Player1のスコアラベル(ThemeModelで生成)
    score2 = tmodel.setLabel_score3(); //Player2のスコアラベル(ThemeModelで生成)
    player1 = new JLabel("Player1",JLabel.CENTER);
    player2 = new JLabel("Player2",JLabel.CENTER);
    score1.setFont(font);
    score2.setFont(font);
    player1.setFont(font);
    player2.setFont(font);

    replay = new JButton("REPLAY");
    replay.setFont(font);
    replay.setPreferredSize(new Dimension(200, 100)); //ボタンのサイズを設定
    replay.setActionCommand("replay");
    replay.addActionListener(c);

    this.setLayout(new BorderLayout());
    scorePanel = new JPanel();
    scorePanel.setBackground(Color.white);
    scorePanel.setLayout(new GridLayout(2,2));
    scorePanel.setOpaque(false); //背景画像見えるようにするためにfalseにする
    scorePanel.add(player1);
    scorePanel.add(player2);
    scorePanel.add(score1);
    scorePanel.add(score2);
    this.add(scorePanel,BorderLayout.CENTER);
    this.add(replay,BorderLayout.SOUTH);
  }

  public void paintComponent(Graphics g){
    g.drawImage(image, 0, 0, this);
  }
}
