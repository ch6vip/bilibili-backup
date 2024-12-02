package top.ybgnb.bilibili.backup.ui.component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @ClassName BackupCountLabel
 * @Description 备份数文本
 * @Author hzhilong
 * @Time 2024/11/30
 * @Version 1.0
 */
public class BackupCountLabel extends JLabel {

    private String name;
    private int count;

    public BackupCountLabel(String name, int count, Color txtColor, Color bgColor) {
        super();
        this.name = name;
        this.count = count;
        setBorder(new EmptyBorder(5, 10, 5, 10));
        setForeground(txtColor);
        setBackground(bgColor);
        setOpaque(false);
        refreshText();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        super.paintComponent(g);
        g2.dispose();
    }

    public void setCount(int count) {
        this.count = count;
        refreshText();
    }

    public void refreshText() {
        setText(name + "  " + count);
    }

}
