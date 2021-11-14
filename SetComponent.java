import javax.swing.*;
import java.awt.*;
import java.util.*;

class SetComponent extends JPanel{
  GridBagConstraints gbc;
  GridBagLayout gbl;
  JPanel p;

  public SetComponent(GridBagConstraints gbc,GridBagLayout gbl,JPanel p){
    this.gbc = gbc;
    this.gbl = gbl;
    this.p = p;
  }

  public void setComponent(Component a,int x,int y,int w,int h,double tx,double ty,Insets insets){
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = insets;
    gbc.gridx = x;
    gbc.gridy = y;
    gbc.gridwidth = w;
    gbc.gridheight = h;
    gbc.weightx = tx;
    gbc.weighty = ty;
    gbl.setConstraints(a, gbc);
    p.add(a);
  }
}
