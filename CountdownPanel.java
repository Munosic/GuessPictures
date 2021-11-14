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

/*カウントダウン画面のview*/

class CountdownPanel extends JPanel{
  protected DrawModel dmodel;
  protected SCModel sc;
  protected JLabel count,order;
  protected Font font;
  protected Image image;
  public CountdownPanel(DrawModel dmodel,SCModel sc,Clock clock) {
    this.sc = sc;

    this.setBackground(Color.white);
    this.setLayout(new BorderLayout());

    /*背景*/
    image = Toolkit.getDefaultToolkit().createImage("start_back.jpg");

    count = clock.getClock1();
    font = dmodel.getFont();
    font = dmodel.setSize(50f);
    count.setFont(font);
    count.setForeground(new Color(208,17,38));
    this.add(count,BorderLayout.SOUTH);

    if(sc.isDrawTurn()){ //お題をかく側
      order = new JLabel("<html><body><center>お題の絵を<br />30秒で<br />かいてください</center></body></html>",JLabel.CENTER);
      font = dmodel.setSize(80f);
      order.setFont(font);


    }else{ //お題を当てる側
      order = new JLabel("<html><body><center>相手がかいた絵を<br />30秒で<br />当ててください</center></body></html>",JLabel.CENTER);
      font = dmodel.setSize(80f);
      order.setFont(font);
    }
    this.add(order,BorderLayout.CENTER);
  }

  public void paintComponent(Graphics g){
    g.drawImage(image, 0, 0, this);
  }
}
