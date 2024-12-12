import javax.swing.*;
import java.awt.*;

public class CheckBalanceUI extends JFrame {
    private Font customFont;
    public CheckBalanceUI(){
        super("Balance Information");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(CommonConstants.PRIMARY_COLOR);
        customFont = CustomTools.createFont(CommonConstants.FONT_PATH);
        setVisible(true);
    }
}
