/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclo;

import Core.Share;
import static Core.Share.FFTNo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class TraceFrame extends JFrame implements Share {

    public Queue<FVec> queue = new LinkedList<FVec>();
    TracePanel tracePanel;
    Boolean start = true;
    JButton showStop;

    public TraceFrame() {
        Container container = this.getContentPane();
        tracePanel = new TracePanel();
        container.setLayout(new BorderLayout());
        container.add(tracePanel, BorderLayout.CENTER);
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(4, 1));
        showStop = new JButton("Stop");
        rightPanel.add(showStop);
        container.add(rightPanel, BorderLayout.WEST);
        showStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (start) {
                    start = false;
                    showStop.setText("Start");
                } else {
                    start = true;
                    showStop.setText("Stop");
                }
            }
        });
    }

    public void pInsert(int[] intVec) {
        if (!start) return;
        FVec newVec = new FVec(intVec);
        synchronized (queue) {
            if (queue.size() < 400) {
                queue.offer(newVec);
            } else {
                queue.poll();
                queue.offer(newVec);
            }
            tracePanel.repaint();
        }
    }

    class TracePanel extends JPanel {

        public void paintComponent(Graphics g) {
            int x, y = 20;
            int width = this.getWidth();
            int height = this.getHeight();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 1000  ,    1000);
            g.setColor(Color.BLUE);
            // g.drawString("Hello", 100, 100);
            synchronized (queue) {
                for (FVec fv : queue) {
                    x = 20;
                    for (int i = 0; i < FFTNo; i++) {
                        if (fv.spec[i] == 1) {
                            g.drawOval(x + i * 5, y, 2, 2);
                            x++;
                        }
                    }
                    y++;
                }
            }
        }
    }

}

class FVec implements Share {

    public int[] spec = new int[FFTNo];

    public FVec(int[] in_) {
        spec = in_.clone();
    }
}
