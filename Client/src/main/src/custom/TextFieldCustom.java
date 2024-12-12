package custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextFieldCustom  extends JTextField {
    private String placeHolderText;
    private boolean hasPlaceHolder;

    public boolean isHasPlaceHolder() {
        return hasPlaceHolder;
    }

    public TextFieldCustom(String placeHolderText, int charLimit){
        super();
        this.placeHolderText = placeHolderText;
        hasPlaceHolder = true;

        //limit char input
        setDocument(new LimitText(charLimit));
        setText(this.placeHolderText);

        //adds margin to  pad text
        setMargin(new Insets(0,10,0,0));

        addListerners();
    }

    private void addListerners() {
        //check for mouse click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(hasPlaceHolder){
                    hasPlaceHolder = false;
                    setText("");
                }
            }
        });

        //check for key presses
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (hasPlaceHolder){
                    hasPlaceHolder = false;
                    setText("");
                }
            }
        });

        //check to see if the user has removed focus on this field
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                //check if it is empty
                if (getText().toString().length() <= 0) {
                    hasPlaceHolder = true;
                    setText(placeHolderText);
                }
            }
        });
    }
}
