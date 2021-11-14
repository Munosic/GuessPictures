import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.event.*;

////////////////////////////////////////////////
// View (V)

// Viewは，Observerをimplementsする．Modelを監視して，
// モデルが更新されたupdateする．実際には，Modelから
// update が呼び出される．

/*絵を描く*/
class ViewPanel extends JPanel implements Observer {
    protected DrawModel model;
    protected JPanel colorChoice;
    protected JPanel subChoice;
    protected JButton cb[],rb[];
    protected JButton eraser,clear;
    protected JSlider cs;
    protected JLabel label;
    protected Font font;


    public ViewPanel(DrawModel m,DrawController c,ChatModel cmodel) {
      model = m;
      this.setBackground(Color.white);
      this.setBorder(new LineBorder(Color.decode("#96B4CE"),6));
      this.setLayout(new BorderLayout());
      colorChoice = new JPanel();
      colorChoice.setBackground(Color.white);
      colorChoice.setLayout(new GridLayout(1,10));


      /*色ボタン*/
      cb = new JButton[10];
      for(int i = 0; i < 10; i++){
        cb[i] = new JButton("<html><span style='font-size:20pt; color:"+model.getCode(i)+";background-color:"+model.getCode(i)+";'>___</span></html>");
        cb[i].setMargin(new Insets(0, 0, 0, 0));
        colorChoice.add(cb[i]);
        cb[i].setActionCommand(model.getColor(i));
        cb[i].addActionListener(c);
      }

      subChoice = new JPanel();
      subChoice.setBackground(Color.white);
      subChoice.setLayout(new GridLayout(1,3));

      /*消しゴムボタン*/
      eraser = new JButton("<html><img src='file:eraser.png' width=40 height=40></html>");
      clear = new JButton("<html><img src='file:trash.png' width=40 height=40></html>");
      subChoice.add(eraser);
      eraser.setActionCommand("eraser");
      eraser.addActionListener(c);
      subChoice.add(clear);
      clear.setActionCommand("clear");
      clear.addActionListener(c);

      /*太さ設定スライダー*/
      cs = model.getSlider();
      subChoice.add(cs);
      cs.addChangeListener(c);
      cs.setBackground(Color.white);

      /*フォント*/
      font = model.getFont();
      font = model.setSize(80f);

      /*正解EFラベル*/
      label = cmodel.getCorrectLabel();
      label.setFont(font);
      label.setForeground(new Color(208,17,38));

      this.add(label,BorderLayout.CENTER);
      this.add(colorChoice,BorderLayout.NORTH);
      this.add(subChoice,BorderLayout.SOUTH);
      this.addMouseListener(c);
      this.addMouseMotionListener(c);
      model.addObserver(this);
    }
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      for(Figure f:model.getFigures()){
        f.draw(g);
      }
    }

    public void update(Observable o,Object arg){
      repaint();
    }
  }
