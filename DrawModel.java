import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

////////////////////////////////////////////////
// Model (M)

// modelは java.util.Observableを継承する．Viewに監視される．
class DrawModel extends Observable{
    protected ArrayList<Figure> fig;
    protected Color currentColor;
    protected int r;
    protected String color[];
    protected String code[];
    protected JSlider cs;
    protected ImFont imFont;


    public DrawModel() {
      fig = new ArrayList<Figure>();
      currentColor = Color.black;  // 色の初期値は黒
      r = 10; //半径は10
      code = new String[10];
      color = new String[10];
      // String color[] = {"BLACK","RED","PINK","PURPLE","BLUE","LIGHTBLUE","GREEN","YELLOWGREEN","YELLOW","ORANGE"};
      color[0] = "BLACK";
      color[1] = "RED";
      color[2] = "PINK";
      color[3] = "PURPLE";
      color[4] = "BLUE";
      color[5] = "LIGHTBLUE";
      color[6] = "GREEN";
      color[7] = "YELLOWGREEN";
      color[8] = "YELLOW";
      color[9] = "ORANGE";
      // String code[] = {"#000000","#D01016","#EA6088","#5F1885","#2660AC","#00B5DE","#2A6D39","#73A82D","#EDDA15","#F8B516"};
      code[0] = "#000000";
      code[1] = "#D01016";
      code[2] = "#EA6088";
      code[3] = "#5F1885";
      code[4] = "#0064B3";
      code[5] = "#00B5DE";
      code[6] = "#2A6D39";
      code[7] = "#73A82D";
      code[8] = "#EDDA15";
      code[9] = "#F8B516";

      cs = new JSlider(JSlider.HORIZONTAL,10,40,10);
      cs.setPaintTicks(true);
      cs.setMajorTickSpacing(10);
      cs.setMinorTickSpacing(5);
      cs.setLabelTable(cs.createStandardLabels(10));
      cs.setPaintLabels(true);

      imFont = new ImFont();
    }

    public String getCode(int i){return code[i];} //色の16進数表記
    public String getColor(int i){return color[i];} //色の名前
    public JSlider getSlider(){
      return cs;
    }

    public void setColor(Color c){
      currentColor = c;
    }

    public void setRadius(int r){
      this.r = r;
    }

    public ArrayList<Figure> getFigures() {
      return fig;
    }

    public Color getCurrentColor() {
      return currentColor;
    }
    public int getRadius() {
      return r;
    }

    public void reset() {
      fig.clear();
      setChanged();
      notifyObservers(); //update呼ばれる→repaint
    }

    public void addFigure(int x, int y, int rad, Color c) {
      Figure f = new Figure(x,y,rad,c); //点生成
      fig.add(f); //Figureクラスの配列に追加
      setChanged();
      notifyObservers(); //update呼ばれる→repaint
    }

    public void addLine(int x, int y, int preX, int preY, int rad, Color c) {
      Line f = new Line(x,y,preX,preY,rad,c); //線生成
      fig.add(f); //Figureクラスの配列に追加
      setChanged();
      notifyObservers(); //update呼ばれる→repaint
    }

    /*フォント関連*/
    public Font getFont(){
      return imFont.getFont();
    }

    public Font setSize(float size){
      return imFont.setSize(size);
    }
}
