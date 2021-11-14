import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

// ////////////////////////////////////////////////
// // View (V)

// // Viewは，Observerをimplementsする．Modelを監視して，
// // モデルが更新されたupdateする．実際には，Modelから
// // update が呼び出される．

/*お題を入力・表示*/
class OdaiPanel extends JPanel{
    protected ThemeModel model;
    private JLabel theme,l;
    private JTextField t;
    private SCModel sc;
    protected Font font;
    public OdaiPanel(ThemeModel m,DrawModel dmodel,OdaiController c ,SCModel sc) {
      model = m;
      this.sc = sc;
      this.setBackground(Color.white);
      this.setBorder(new LineBorder(Color.decode("#96B4CE"),6));

      /*フォント*/
      font = dmodel.getFont();
      font = dmodel.setSize(30f);

      if(sc.isDrawTurn()){ //絵を描く側
        this.setLayout(new BorderLayout());
        l = new JLabel("お題：　");
        theme = model.setLabel(JLabel.CENTER);
        theme.setFont(font);
        l.setFont(font);
        this.add(l,BorderLayout.WEST);
        this.add(theme,BorderLayout.CENTER);
      }else{ //絵を当てる側
        this.setLayout(new GridLayout(1,1));
        this.setLayout(new BorderLayout());
        l = new JLabel("答え：　");
        l.setFont(font);
        t = model.getTextField();
        t.setFont(font);
        this.add(l,BorderLayout.WEST);
        this.add(t,BorderLayout.CENTER);

        t.addActionListener(c);
      }
    }
  }
