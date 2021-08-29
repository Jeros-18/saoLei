package jhSaoLei;


import javax.swing.*;


public class SaoLei {
    JFrame frame = new JFrame();

    public SaoLei(){
        frame.setSize(600,700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SaoLei();
    }



}
