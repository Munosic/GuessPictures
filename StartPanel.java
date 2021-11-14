import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import java.io.*;

////////////////////////////////////////////////
// View (V)

// Viewは，Observerをimplementsする．Modelを監視して，
// モデルが更新されたupdateする．実際には，Modelから
// update が呼び出される．

/*スタート画面のview*/

class StartPanel extends JPanel{
  protected DrawModel dmodel;
  protected JButton start;
  protected JLabel title,announce,level;
  protected JPanel levelPanel;
  private SCModel sc;
  protected GridBagConstraints gbc;
  protected GridBagLayout gbl;
  protected SetComponent setC;
  protected Font font;
  protected JRadioButton theme1,theme2,theme3;
  protected ButtonGroup bgroup;
  protected Image image;
  protected Graphics g;

  public StartPanel(DrawModel dmodel,SceneController c ,SCModel sc) {
    this.sc = sc;

    this.setLayout(new GridLayout(2,1));
    this.setBackground(Color.white);

    image = Toolkit.getDefaultToolkit().createImage("start.jpg");

    font = dmodel.getFont();
    font = dmodel.setSize(100f);

    /*タイトル*/
    title = new JLabel("<html><body><center></center></body></html>",JLabel.CENTER);
    this.add(title);

    if(!sc.isServer()){ //Player1(client)


      levelPanel = new JPanel();
      levelPanel.setOpaque(false);
      gbc = new GridBagConstraints();
      gbl = new GridBagLayout();
      setC = new SetComponent(gbc,gbl,levelPanel);

      /*レベル選択*/
      levelPanel.setLayout(gbl);
      level = new JLabel("〜レベル〜",JLabel.CENTER);
      theme1 = new JRadioButton("かんたん　");
      theme2 = new JRadioButton("ふつう　　");
      theme3 = new JRadioButton("むずかしい");
      theme1.setHorizontalAlignment(JRadioButton.CENTER);
      theme2.setHorizontalAlignment(JRadioButton.CENTER);
      theme3.setHorizontalAlignment(JRadioButton.CENTER);
      bgroup = new ButtonGroup();
      bgroup.add(theme1);
      bgroup.add(theme2);
      bgroup.add(theme3);

      /*スタートボタン*/
      start = new JButton("PLAY");

      /*フォント*/
      font = dmodel.setSize(40f);
      level.setFont(font);
      theme1.setFont(font);
      theme2.setFont(font);
      theme3.setFont(font);
      start.setFont(font);

      /*レイアウト*/
      setC.setComponent(level,0,0,1,1,1.0,1.0,new Insets(0,50,5,20));
      setC.setComponent(theme1,0,1,1,1,1.0,1.0,new Insets(0,50,5,20));
      setC.setComponent(theme2,0,2,1,1,1.0,1.0,new Insets(0,50,5,20));
      setC.setComponent(theme3,0,3,1,1,1.0,1.0,new Insets(0,50,5,20));
      setC.setComponent(start,1,0,1,4,1.0,1.0,new Insets(50,150,50,50));

      this.add(levelPanel);
      theme1.setActionCommand("Level1");
      theme2.setActionCommand("Level2");
      theme3.setActionCommand("Level3");
      start.setActionCommand("startButton");
      theme1.addActionListener(c);
      theme2.addActionListener(c);
      theme3.addActionListener(c);
      start.addActionListener(c);
    }else{ //Player2(server)
      announce = new JLabel("<html><body><center>Player1がじゅんび中です。<br />お待ちください・・・</center></body></html>",JLabel.CENTER);
      font = dmodel.setSize(40f);
      announce.setFont(font);
      this.add(announce);
    }
  }

  public void paintComponent(Graphics g){
    g.drawImage(image, 0, 0, this);
  }
}
