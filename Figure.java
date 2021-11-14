import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

// 描画した図形を記録する Figure クラス (継承して利用する)
class Figure {
  protected int x,y,r;
  protected Color color;

  public Figure(int x,int y,int r,Color c) {
    this.x = x; this.y = y;  // this.x, this.y はフィールド変数を指します．
    this.r = r;;   // ローカル変数で同名の変数がある場合は，this
    color = c;               // を付けると，フィールド変数を指すことになります．
  }
  

  public void draw(Graphics g) {
    g.setColor(color);
    g.fillOval(x,y,r,r);
  }
}

class Line extends Figure {
  protected int preX, preY;
  public Line(int x, int y, int preX, int preY, int r, Color c) {
    super(x,y,r,c);
    this.preX = preX; this.preY = preY;
  }

  public void setPreLocation(int preX, int preY) {
    this.preX = preX; this.preY = preY;
  }

  @Override
  public void draw(Graphics g) {
    int i, x1, x2, y1, y2;
    g.setColor(color);
    if(preX == x) {
      if(preY < y) {
        y1 = preY; y2 = y;
      } else {
        y1 = y; y2 = preY;
      }
      for(i=y1; i<=y2; i++) {
        g.fillOval(x,i,r,r);
      }
    } else {
      if(preX < x) {
        x1 = preX; x2 = x;
      } else {
        x1 = x; x2 = preX;
      }
      for(i=x1; i<=x2; i++) {
        y1 = (int)((double)(y-preY)/(x-preX) * (i-preX) + preY);
        g.fillOval(i,y1,r,r);
      }
    }
  }
}
