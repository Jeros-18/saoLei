package jhSaoLei;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class SaoLei implements ActionListener {
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
        frame.setSize(700,800);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setHeader();

        addLei();

        setButtons();


        frame.setVisible(true);
    }

    /**
     * 埋雷
     */
    private void addLei() {
        Random rand = new Random();
        for (int i=0; i<LEICOUNT; ){
            int r = rand.nextInt(ROW);
            int c = rand.nextInt(COL);
            if (data[r][c] != LEICODE){
                data[r][c] = LEICODE;
                i++;
            }
        }

        //计算周边雷的数量
        for (int i=0;i<ROW;i++){
            for(int j=0; j<COL; j++){
                if(data[i][j] == LEICODE) continue;
                int tempCount = 0;
                if (i>0 && j>0 && data[i-1][j-1] == LEICODE) tempCount++;
                if (i>0 && data[i-1][j] == LEICODE) tempCount++;
                if (i>0 && j<19 && data[i-1][j+1] == LEICODE) tempCount++;
                if (j>0 && data[i][j-1] ==  LEICODE) tempCount++;
                if (j<19 && data[i][j+1] == LEICODE) tempCount++;
                if (i<19 && j>0 && data[i+1][j-1] == LEICODE) tempCount++;
                if (i<19 && data[i+1][j] == LEICODE) tempCount++;
                if (i<19 && j<19 && data[i+1][j+1] == LEICODE) tempCount++;
                data[i][j] = tempCount;
            }
        }
    }

    /**
     * 在游戏界面添加按钮
     */
    private void setButtons() {
        Container con = new Container();
        con.setLayout(new GridLayout(ROW, COL));

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                JButton btn = new JButton(guessIcon);
//                JButton btn = new JButton(data[i][j] + "");
//                System.out.println(btn);

                // 给所有格子添加背景色
                btn.setOpaque(true);
                btn.setBackground(new Color(244,183,113));

                btn.addActionListener(this);  // 格子展开
                con.add(btn);
                btns[i][j]=btn;
//                System.out.println(btns[i][j]);
            }
        }

        frame.add(con, BorderLayout.CENTER);

    }

    /**
     * 添加头部说明信息
     */
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


    /**
     * 格子展开
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource();
        for(int i=0; i<ROW; i++){
            for(int j=0; j<COL; j++){
                if(btn.equals(btns[i][j])){
                    if (data[i][j] == LEICODE){
                        lose();
                    }else{
                        openCell(i,j);
                        checkWin();
                    }
                    return;
                }
            }
        }
    }

    private void checkWin() {
        int count = 0;
        for(int i=0; i<ROW; i++){
            for(int j=0; j<COL; j++){
                if(btns[i][j].isEnabled())
                    count++;
            }
        }
        if(count==LEICOUNT){
            for(int i=0; i<ROW; i++){
                for(int j=0; j<COL; j++){
                    if(btns[i][j].isEnabled()){
                        btns[i][j].setIcon(winFlagIcon);
                    }
                }
            }
            bannerBtn.setIcon(winIcon);
            JOptionPane.showMessageDialog(frame,"你赢了！！\n点击banner重新开始","赢了",JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * 踩雷
     */
    private void lose() {
        bannerBtn.setIcon(failIcon);
        for(int i=0; i<ROW; i++){
            for(int j=0; j<COL; j++){
                if(btns[i][j].isEnabled()){
                    JButton btn = btns[i][j];
                    if(data[i][j] == LEICODE){
                        btn.setEnabled(false);
                        btn.setIcon(bombIcon);
                        btn.setDisabledIcon(bombIcon);
                    }else{
                        btn.setIcon(null);
                        btn.setEnabled(false);
                        btn.setOpaque(true);
                        btn.setText(data[i][j]+"");
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(frame,"可惜!你踩雷了!！\n可以点击上面的Banner重新开始。","踩雷啦",JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * 级联展开
     * @param i
     * @param j
     */
    public void openCell(int i, int j){
        JButton btn = btns[i][j];
        if(!btn.isEnabled())
            return;

        btn.setIcon(null);
        btn.setEnabled(false);
        btn.setOpaque(true); // 设置透明 才能显示自己设置的颜色
        btn.setBackground(Color.GREEN);
        btn.setText(data[i][j]+"");

        if(data[i][j] == 0){
            if (i>0 && j>0 && data[i-1][j-1] == 0) openCell(i-1, j-1);
            if (i>0 && data[i-1][j] == 0) openCell(i-1, j);
            if (i>0 && j<19 && data[i-1][j+1] == 0) openCell(i-1, j+1);
            if (j>0 && data[i][j-1] == 0) openCell(i, j-1);
            if (j<19 && data[i][j+1] == 0) openCell(i, j+1);
            if (i<19 && j>0 && data[i+1][j-1] == 0) openCell(i+1, j-1);
            if (i<19 && data[i+1][j] == 0) openCell(i+1, j);
            if (i<19 && j<19 && data[i+1][j+1] == 0) openCell(i+1, j+1);
        }
    }


}
