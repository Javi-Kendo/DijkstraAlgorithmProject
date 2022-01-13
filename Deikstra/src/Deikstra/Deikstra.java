package Deikstra;

import Deikstra.othersClass.DrawGraph;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Deikstra {
    public static int DLIN;
    public  static String[][] matrixForDrawRib;
    public  static String[] matrixForDrawVertex;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int stolb = 0;
        long pathLength = 0;

        System.out.print("Введите количество вершин графа: ");
        int n = sc.nextInt();

        System.out.println();

        if (n < 2) {
            if (n == 1) {
                System.out.println("К сожалению, кратчайшего пути нет! Вы останетесь дома :(\n");
            }
            if (n < 1) {
                System.out.println("Графа с таким количеством вершин не существует!\n" +
                        "Иди учи дисциплину \"Оптимизация на графах\"!");
            }
        } else {
            System.out.println("Введите матрицу весов, петли в графе игнорируются программой, " +
                    "\nпоскольку в Алгоритме Дейкстры они не имеют смысла," +
                    "\nдля отсутствия петли используйте \"-\", " +
                    "\nа вместо бесконечности(Infinity) используйте \"i\": ");

            //Перевод строчной матрицы, вводимой пользователем, в числовую,
            // меняем "-" на -1, а "i" на максимальное значение в типе int == 2,147,483,647
            int[][] A = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    String symbol = sc.next();
                    if (!symbol.equals("i") && !symbol.equals("-")) {
                        A[i][j] = Integer.parseInt(symbol);
                    } else if (symbol.equals("i")) {
                        A[i][j] = Integer.MAX_VALUE;
                    } else {
                        A[i][j] = -1;
                    }
                    if (i == j) {
                        A[i][j] = -1;
                    }
                }
            }

            long[][] B = new long[n][n]; // Создаём вторую матрицу той же размерности, что и первая типа long
            //Заполняем всю новую матрицу -1
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    B[i][j] = -1;
                }
            }

            B[0][0] = 0; // Присваиваем первому элементу значение 0
            //Идём по первой строке новой матрицы и заполняем остальные элементы как бы бесконечностью
            for (int i = 1; i < n; i++) {
                B[0][i] = Integer.MAX_VALUE;
            }

            //Начинаем выполнение алгоритма Дейкстры
            for (int i = 0; i < n; i++) {
                if (i != 0) {
                    //Ищем минимальное значение в текущей строчке
                    int min1 = Integer.MAX_VALUE;
                    for (int k = 0; k < n; k++) {
                        if (B[i][k] <= min1 && B[i][k] != -1) {
                            min1 = (int) B[i][k];
                            stolb = k;
                        }
                    }
                    //Если мы находимся в вершине t и длина пути в этой вершине является минимальной,
                    // то мы заканчиваем выполнение алгоритма
                    if (stolb == n - 1) {
                        break;
                    } else {
                        //Пересчитываем путь до всех оставшихся вершин
                        for (int j = 0; j < n; j++) {
                            if ((i != 1 && j != 0) || B[i - 1][j] != -1 && B[i - 1][j] != min1) {
                                //Если новый путь меньше, чем тот, который сейчас записан, то записываем новый
                                if ((B[i][stolb] + A[stolb][j] < B[i][j]) && A[stolb][j] >= 0) {
                                    B[i + 1][j] = B[i][stolb] + A[stolb][j];
                                } else if (A[stolb][j] >= 0) { //Если новый путь больше или равен,
                                    // то переписываем путь записанный ранее
                                    B[i + 1][j] = B[i][j];
                                }
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < n; j++) {
                        if (j != B[0][0]) {
                            B[1][j] = B[0][0] + A[0][j];
                        }
                    }
                }
            }

            System.out.println();

            //Переводим матрицу весов, которую вводил пользователь в строчную, заменяя
            //-1 на "-", а максимальное значение в типе int на знак бесконечности
            String[][] stringArrayOfMatrixA = new String[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (A[i][j] == -1) {
                        stringArrayOfMatrixA[i][j] = "-";
                    } else if (A[i][j] == Integer.MAX_VALUE) {
                        stringArrayOfMatrixA[i][j] = "\u221e";
                    } else {
                        stringArrayOfMatrixA[i][j] = Integer.toString(A[i][j]);
                    }
                }
            }

            //Cделаем красиво оформленную исходную матрицу весов публичной статической,
            // чтобы обратиться к ней из другого класса

            matrixForDrawRib = new String[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrixForDrawRib[i][j] = stringArrayOfMatrixA[i][j];
                }
            }

            System.out.println();

            //Выводим исходную матрицу, которую вводил пользователь
            System.out.println("Ваша исходная матрица:");
            System.out.printf("%5s", "");
            //Выводим вершины по горизонтали для вывода матрицы
            for (int i = 0; i < n; i++) {
                if(i != 0 && i != n - 1) {
                    System.out.printf("%5s", "x" + (i + 1));
                } else if (i == 0) {
                    System.out.printf("%5s", "s");
                } else {
                    System.out.printf("%5s", "t");
                }
            }

            System.out.println();

            //Выводим саму матрицу
            for (int i = 0; i < n; i++) {
                if(i != 0 && i != n - 1) {
                    System.out.printf("%5s", "x" + (i + 1));
                } else if (i == 0) {
                    System.out.printf("%5s", "s");
                } else {
                    System.out.printf("%5s", "t");
                }
                for (int j = 0; j < n; j++) {
                    System.out.printf("%5s", stringArrayOfMatrixA[i][j]);
                }
                System.out.println();
            }

            System.out.println();

            //Переводим алгоритм Дейкстры к красивому виду,
            // заменяя максимальное значение типа int на знак бесконечности,
            //а -1 на "-"
            String[][] stringArrayOfMatrixB = new String[n][n];
            int str = 0;
            int noWay = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (B[i][j] == -1) {
                        stringArrayOfMatrixB[i][j] = "-";
                    } else if (B[i][j] == Integer.MAX_VALUE) {
                        stringArrayOfMatrixB[i][j] = "\u221e";
                    } else {
                        stringArrayOfMatrixB[i][j] = Long.toString(B[i][j]);
                    }
                }
                //Присваиваем "*", т.е. постоянную метку для красоты вывода
                int min1 = Integer.MAX_VALUE;
                for (int k = 0; k < n; k++) {
                    if (B[i][k] <= min1 && B[i][k] != -1) {
                        min1 = (int) B[i][k];
                        str = i;
                        stolb = k;
                    }
                }
                if (B[str][stolb] != Integer.MAX_VALUE) {
                    stringArrayOfMatrixB[str][stolb] = B[str][stolb] + "*";
                    pathLength = B[str][stolb];
                } else {
                    noWay++;
                }
            }

            //Выводим алгоритм Дейкстра в приятном виде для пользователя
            System.out.println("Алгоритм Дейкстра:");
            System.out.printf("%2s", "");
            //Выводим вершины по горизонтали
            for (int i = 0; i < n; i++) {
                if(i != 0 && i != n - 1) {
                    System.out.printf("%5s", "x" + (i + 1));
                } else if (i == 0) {
                    System.out.printf("%5s", "s");
                } else {
                    System.out.printf("%5s", "t");
                }
            }
            System.out.println();
            //Выводим оставшуюся часть матрицы
            for (int i = 0; i < n; i++) {
                System.out.print(i + 1 + ".");
                for (int j = 0; j < n; j++) {
                    System.out.printf("%5s", stringArrayOfMatrixB[i][j]);
                }
                System.out.println();
            }

            System.out.println();

            //Проходим по алгоритму Дейкстры, чтобы вывести маршрут кратчайшего пути
            int k = 0, h = -1;
            String s;
            String[] road = new String[n];

            //Заполним массив строк пробелами,
            // чтобы легко его переделать в более компакнтый массив,
            // для более компактного вывода
            for (int i = 0; i < road.length; i++) {
                road[i] = " ";
            }

            //Ищем мартшрут по алгоритму Дейкстры
            for (int i = n - 1; i >= 0; i--) {

                for (int j = 0; j < n; j++) {
                    if (stringArrayOfMatrixB[i][j].contains("*")) {
                        road[k] = Integer.toString(j + 1);
                        s = stringArrayOfMatrixB[i][j].substring(0, stringArrayOfMatrixB[i][j].length() - 1);
                        i--;
                        if (i >= 0) {
                            while (s.equals(stringArrayOfMatrixB[i][j])) {
                                i--;
                            }
                            k++;
                        }
                        h++;
                        break;
                    }
                }
                if (h != -1) {
                    i++;
                }
            }

            if (noWay == 0) {
                //Выводим маршрут, который получился по алгоритму Дейкстры на экран
                String s1 = "";
                //Создаём строку пути
                for (int i = 0; i < n; i++) {
                    s1 += road[i] + " ";
                }
                //Убираем лишние пробелы из предыдущего массива
                String[] roadCopy = s1.split(" ");

                //Выводим маршрут
                System.out.println("Маршрут:");
                for (int i = roadCopy.length - 1; i >= 0; i--) {
                    if (i != 0 && i != roadCopy.length - 1) {
                        System.out.print("x" + road[i]);
                    } else if (i == roadCopy.length - 1) {
                        System.out.print("s");
                    } else {
                        System.out.print("t");
                    }
                    if (i != 0) {
                        System.out.print(" " + "->" + " ");
                    }
                }

                System.out.println();

                System.out.println("Длина кратчайшего пути: " + pathLength);
            } else {
                System.out.println("Пути нет");
            }
            //Заполняем массив для вывода вершин, при рисовании графа
            String[] matrixForGrap = new String[n];
            for (int i = 0; i < n; i++) {
                if (i != 0 && i != n - 1) {
                    matrixForGrap[i] = "x" + (i + 1);
                } else if (i == 0) {
                    matrixForGrap[i] = "s";
                } else {
                    matrixForGrap[i] = "t";
                }
            }

            //Делаем массив видимым в других классах
            matrixForDrawVertex = new String[n];
            for (int i = 0; i < n; i++) {
                matrixForDrawVertex[i] = matrixForGrap[i];
            }

            //Создаём форму и обращаемся к другому классу, для рисования графа
            JFrame frame = new JFrame("Weights Matrix Graph");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new DrawGraph());
            frame.setIconImage(ImageIO.read(new File("res/img/Icon1.png")));
            frame.pack();
            frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
            frame.setVisible(true);
            DLIN = n;
        }
    }
}