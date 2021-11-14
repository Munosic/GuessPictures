import javax.swing.*;
import java.awt.*;
import java.io.*;

class ImFont{
  protected Font font;
  public ImFont(){
    /*外部フォント*/
    try{
      font = Font.createFont(Font.TRUETYPE_FONT, new File("KodomoRounded.otf"));
    }catch(FontFormatException e){
      System.out.println("形式がフォントではありません。");
    }catch(IOException e){
      System.out.println("入出力エラーでフォントを読み込むことができませんでした。");
    }

    GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
    font = new Font("こども丸ゴシック", Font.PLAIN, 30);
  }

  /*フォント関連*/
  public Font getFont(){
    return font;
  }

  public Font setSize(float size){
    return font.deriveFont(size);
  }
}
