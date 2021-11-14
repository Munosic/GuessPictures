import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

////////////////////////////////////////////////
// View (V)．

/*チャット画面のview*/
class ChatPanel extends JPanel{
    protected JTextArea textArea;
    protected JScrollPane scrollpane;
    private JLabel chat;
    protected Font font;

    public ChatPanel(ChatModel cmodel,DrawModel dmodel) {
        this.setBackground(Color.white);
        this.setBorder(new LineBorder(Color.decode("#96B4CE"),6));
        this.setLayout(new BorderLayout());

        /*フォント*/
        font = dmodel.getFont();
        font = dmodel.setSize(20f);

        chat = new JLabel("チャットログ",JLabel.CENTER);
        //chat.setFont(new Font("Tsukushi A Round Gothic", Font.BOLD, 20));
        chat.setFont(font);
        chat.setBorder(new LineBorder(Color.decode("#96B4CE"),1));
        textArea = cmodel.initializeTextArea();
        font = dmodel.setSize(16f);
        textArea.setFont(font);
        //textArea.setFont(new Font("Tsukushi A Round Gothic", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        scrollpane = new JScrollPane(textArea);
        scrollpane.setBorder(new LineBorder(Color.decode("#96B4CE"),1));


        this.add(chat,BorderLayout.NORTH);
        this.add(scrollpane, BorderLayout.CENTER);
    }
}

////////////////////////////////////////////////
// Model (M)

/*チャット画面のmodel*/
class ChatModel {
    protected JTextArea textArea;
    protected JLabel label = new JLabel("",JLabel.CENTER);

    public JTextArea initializeTextArea() {
        textArea = new JTextArea();
        return textArea;
    }

    public void clearChat() {
        textArea.setText("");
    }

    public void renewChat(String t){
        textArea.insert(t+"\n", 0);
    }

    public JLabel setCorrectLabel(String t){
      label.setText(""+t);
      return label;
    }

    public JLabel getCorrectLabel(){
      return label;
    }
}
