import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

class JTextFieldLimit extends PlainDocument {
   private int limit;
   JTextFieldLimit(int limit) {
      super();
      this.limit = limit;
   }
   JTextFieldLimit(int limit, boolean upper) {
      super();
      this.limit = limit;
   }
   public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
      if (str == null)
         return;
      if ((getLength() + str.length()) <= limit) {
         super.insertString(offset, str, attr);
      }
   }
}
public class JTextFieldLimitTest extends JFrame {
   JTextField textfield;
   JLabel label;
   public static void main(String[]args){
      new JTextFieldLimitTest().GUI();
   }
   public void GUI() {
      setLayout(new FlowLayout());
      label = new JLabel("max 10 chars");
      textfield = new JTextField(15);
      add(label);
      add(textfield);
      textfield.setDocument(new JTextFieldLimit(1));
      setSize(350,300);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }
}
