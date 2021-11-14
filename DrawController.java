import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.event.*;

////////////////////////////////////////////////
// Controller (C)

/*絵を描くコントローラ*/
class DrawController implements MouseListener,MouseMotionListener,ActionListener ,ChangeListener{
    protected DrawModel model;
    protected ChatModel cmodel;
    protected SCModel sc;
    protected int x, y, preX, preY;

    public DrawController(DrawModel a, ChatModel cmodel,SCModel sc) {
      model = a;
      this.sc = sc;
      this.cmodel = cmodel;
    }
    public void mouseClicked(MouseEvent e) { }
    public void mousePressed(MouseEvent e) {
      preX = e.getX(); preY = e.getY();
      if(sc.isDrawTurn()) {
        cmodel.setCorrectLabel(""); //正解EFラベルを白紙にする
        sc.sendLabelClear(); //相手側に正解ラベルを白紙にすることを伝える。
        model.addFigure(preX, preY, model.getRadius(), model.getCurrentColor());//取得した座標で図形を作る
        sc.sendDraw(preX, preY, preX, preY, model.getRadius(), model.getCurrentColor()); //図形の各値を相手側に送る。
      }
    }
    public void mouseDragged(MouseEvent e) {
      x = e.getX(); y = e.getY();
      if(sc.isDrawTurn()) {
        model.addLine(x, y, preX, preY, model.getRadius(), model.getCurrentColor());//取得した座標で図形を作る
        sc.sendDraw(x, y, preX, preY, model.getRadius(), model.getCurrentColor());//図形の各値を相手側に送る。
      }
      preX = x; preY = y;
    }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseMoved(MouseEvent e) { }

    public void actionPerformed(ActionEvent ev) {
      String cmd = ev.getActionCommand();
      for(int i = 0; i < 10; i++){
        if(cmd.equals(model.getColor(i))){
          model.setColor(Color.decode(model.getCode(i))); //各色を設定
        }
      }
      if(cmd.equals("eraser")){
        model.setColor(Color.white);
      }else if(cmd.equals("clear")){
        if(sc.isDrawTurn()) {
          model.reset(); //白紙にする
          sc.sendClear(); //相手に「絵を消せ」と伝える
        }
      }
    }

    public void stateChanged(ChangeEvent e) {
      if(e.getSource()==model.getSlider()){
        model.setRadius(model.getSlider().getValue()); //スライダーの値を太さとして設定
      }
    }

  }
