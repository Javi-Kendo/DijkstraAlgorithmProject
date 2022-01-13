package Deikstra.othersClass;

import Deikstra.Deikstra;

import java.awt.*;
import javax.swing.*;

import static Deikstra.Deikstra.DLIN;

public class DrawGraph extends JPanel {
    final int RADIUS = 25;

    //Создаём конструктор класса
    public DrawGraph() {
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.WHITE);
    }

    //Рисуем сам граф
    public void paintComponent(Graphics g) {

        double randomX1 = Math.random() * getWidth();
        double randomY1 = Math.random() * getHeight();

        int randomX = (int) randomX1;
        int randomY = (int) randomY1;

        super.paintComponent(g);

        g.setColor(Color.BLUE);

        int[] matrixVertexCenterX = new int[DLIN];
        int[] matrixVertexCenterY = new int[DLIN];
        //Рисуем вершины
        for (int i = 1; i <= DLIN; i++) {
            g.drawOval(randomX, randomY, RADIUS, RADIUS);
            if (i != 1 && i != DLIN) {
                g.drawString(Deikstra.matrixForDrawVertex[i - 1], randomX + 7, randomY + 17);
            } else if (i == 1) {
                g.drawString(Deikstra.matrixForDrawVertex[i - 1], randomX + 10, randomY + 17);
            } else {
                g.drawString(Deikstra.matrixForDrawVertex[i - 1], randomX + 12, randomY + 17);
            }

            matrixVertexCenterX[i - 1] = randomX + 25 / 2;
            matrixVertexCenterY[i - 1] = randomY + 25 / 2;

            randomX1 = Math.random() * (getWidth() - 100);
            randomY1 = Math.random() * (getHeight() - 100);

            randomX = (int) randomX1;
            randomY = (int) randomY1;
        }

        //Рисуем дуги
        for (int i = 1; i <= DLIN; i++) {
            int R = (int)(Math.random() * 256);
            int G = (int)(Math.random() * 256);
            int B = (int)(Math.random() * 256);
            Color color = new Color(R, G, B);
            for (int j = 1; j <= DLIN; j++) {
                if(!(Deikstra.matrixForDrawRib[i - 1][j - 1].equals("\u221e")) &&
                        !((Deikstra.matrixForDrawRib[i - 1][j - 1].equals("-"))))
                {
                    g.setColor(color);
                    drawArrowLine(g, matrixVertexCenterX[i - 1], matrixVertexCenterY[i - 1],
                            matrixVertexCenterX[j - 1], matrixVertexCenterY[j - 1],
                            15, 10, i - 1, j - 1);
                }
            }
        }

    }

    /**
     * Draw an arrow line between two points.
     * @parametr g the graphics component.
     * @parametr x1 x-position of first point.
     * @parametr y1 y-position of first point.
     * @parametr x2 x-position of second point.
     * @parametr y2 y-position of second point.
     * @parametr d  the width of the arrow.
     * @parametr h  the height of the arrow.
     */
    //Метод, который высчитывает наклон стрелочки на конце дуги и рисует её
    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h, int i, int j) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;


        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        if (x1 <= x2 && y1 <= y2) {
            g.drawLine(x1 + 9, y1 + 9, x2 - 9, y2 - 9);
            xPoints[0] = x2 - 9;
            xPoints[1] = (int) xm - 9;
            xPoints[2] = (int) xn - 9;
            yPoints[0] = y2 - 9;
            yPoints[1] = (int) ym - 9;
            yPoints[2] = (int) yn - 9;
            g.fillPolygon(xPoints, yPoints, 3);
            g.drawString(Deikstra.matrixForDrawRib[i][j], x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2);
        } else if (x1 <= x2 && y1 >= y2) {
            g.drawLine(x1 + 9, y1 - 9, x2 - 9, y2 + 9);
            xPoints[0] = x2 - 9;
            xPoints[1] = (int) xm - 9;
            xPoints[2] = (int) xn - 9;
            yPoints[0] = y2 + 9;
            yPoints[1] = (int) ym + 9;
            yPoints[2] = (int) yn + 9;
            g.fillPolygon(xPoints, yPoints, 3);
            g.drawString(Deikstra.matrixForDrawRib[i][j], x1 + (x2 - x1) / 2, y2 + (y1 - y2) / 2);
        } else if (x1 >= x2 && y1 >= y2) {
            g.drawLine(x1 - 9, y1 - 9, x2 + 9, y2 + 9);
            xPoints[0] = x2 + 9;
            xPoints[1] = (int) xm + 9;
            xPoints[2] = (int) xn + 9;
            yPoints[0] = y2 + 9;
            yPoints[1] = (int) ym + 9;
            yPoints[2] = (int) yn + 9;
            g.fillPolygon(xPoints, yPoints, 3);
            g.drawString(Deikstra.matrixForDrawRib[i][j], x2 + (x1 - x2) / 2, y2 + (y1 - y2) / 2);
        } else if (x1 >= x2 && y1 <= y2) {
            g.drawLine(x1 - 9, y1 + 9, x2 + 9, y2 - 9);
            xPoints[0] = x2 + 9;
            xPoints[1] = (int) xm + 9;
            xPoints[2] = (int) xn + 9;
            yPoints[0] = y2 - 9;
            yPoints[1] = (int) ym - 9;
            yPoints[2] = (int) yn - 9;
            g.fillPolygon(xPoints, yPoints, 3);
            g.drawString(Deikstra.matrixForDrawRib[i][j], x2 + (x1 - x2) / 2, y1 + (y2 - y1) / 2);
        }
    }
}