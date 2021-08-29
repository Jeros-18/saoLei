package jhSaoLei;


import javax.swing.*;
import java.awt.*;


public class SaoLei {
    JFrame frame = new JFrame();

    // 头部按钮
    ImageIcon bannerIcon = new ImageIcon("banner.png");
    // 内部按钮
    ImageIcon guessIcon = new ImageIcon("guess.png");
    ImageIcon bombIcon = new ImageIcon("bomb.png");
    ImageIcon failIcon = new ImageIcon("fail.png");
    ImageIcon winIcon = new ImageIcon("win.png");
    ImageIcon winFlagIcon = new ImageIcon("win_flag.png");

    JButton bannerBtn = new JButton(bannerIcon);

    //数据结构
    int ROW = 20; //行数
    int COL = 20; //列数
    int[][] data = new int[ROW][COL];//存放数据
    JButton[][] btns = new JButton[ROW][COL];
    int LEICOUNT = 3; //雷的总数
    int LEICODE = -1; //表示是雷

    int unopened = ROW * COL; //未开格子数
    int opened = 0; //已开格子数
    int seconds = 0; //时钟计数
    JLabel label1 = new JLabel("待开：" + unopened);
    JLabel label2 = new JLabel("已开：" + opened);
    JLabel label3 = new JLabel("用时：" + seconds + "s");



    public SaoLei(){
        frame.setSize(600,700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setHeader();
        setButtons();


        frame.setVisible(true);
    }

    private void setButtons() {
        Container con = new Container();
        con.setLayout(new GridLayout(ROW, COL));

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                JButton btn = new JButton(guessIcon);
                con.add(btn);
                btns[i][j]=btn;
            }
        }

        frame.add(con, BorderLayout.CENTER);

    }

    private void setHeader(){
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c1 = new GridBagConstraints(0,0,3,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
        panel.add(bannerBtn, c1); // 放置头部按钮

        label1.setOpaque(true);
        label1.setBackground(Color.white);
        label1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        label2.setOpaque(true);
        label2.setBackground(Color.white);
        label2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        label3.setOpaque(true);
        label3.setBackground(Color.white);
        label3.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        bannerBtn.setOpaque(true);
        bannerBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        bannerBtn.setBackground(Color.white);

        // 放置三个组件
        GridBagConstraints c2 = new GridBagConstraints(0,1,1,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
        panel.add(label1, c2);
        GridBagConstraints c3 = new GridBagConstraints(1,1,1,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
        panel.add(label2, c3);
        GridBagConstraints c4 = new GridBagConstraints(2,1,1,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0,0);
        panel.add(label3, c4);


        frame.add(panel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        new SaoLei();
    }



}
