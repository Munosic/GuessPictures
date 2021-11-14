import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

/*お題のコントローラ*/
class OdaiController implements ActionListener{
    protected ThemeModel tmodel;
    protected ChatModel cmodel;
    protected SCModel sc;
    public OdaiController(ThemeModel tmodel,ChatModel cmodel,SCModel sc) {
      this.tmodel = tmodel;
      this.cmodel = cmodel;
      this.sc = sc;
    }

    public void actionPerformed(ActionEvent ev) {
      if(ev.getSource()==tmodel.getTextField()){
        sc.sendChat(tmodel.getTextField().getText()); //相手側で判定するために入力された文字列を送る
        cmodel.renewChat(tmodel.getTextField().getText()); //チャットログに入力された文字列を追加
        tmodel.reset(); //テキストフィールドを白紙
      }
   }
  }
