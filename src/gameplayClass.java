import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class gameplayClass extends JPanel implements KeyListener, ActionListener {

    /* Propriedades do Jogo */
    private boolean playState = false;
    private int score = 0;
    private int totalBricks = 21;
    private int delay = 8;
    private Timer time;

    /* Posição do Jogador */
    private int playerX = 310;

    /* Posição da Bola */
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballX_dir = -1;
    private int ballY_dir = -2;

    /* Instância do mapa */
    private mapGeneratorClass map;


    /* Construtor */
    public gameplayClass(){
        map = new mapGeneratorClass(3, 7);

        addKeyListener(this);

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        time = new Timer(delay, this);
        time.start();
    }

    /* Função para renderizar */
    public void paint(Graphics g){

        /* Background */
        g.setColor(Color.DARK_GRAY);
        g.fillRect(1, 1, 692, 592);

        /* Desenhar Mapa */
        map.draw((Graphics2D) g);

        /* Bordas */
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        /* Pontuação */
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Pontuação: "+score, 500, 30);

        /* Plataforma */
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550, 100, 8);

        /* Bola */
        g.setColor(Color.YELLOW);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        /* Checar se todos os blocos foram destruídos - Jogo Acabou */
        if(totalBricks <= 0){
            playState = false;
            ballX_dir = 0;
            ballY_dir = 0;

            g.setColor(Color.BLUE);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Você Ganhou", 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Pressione ENTER para recomeçar", 230, 350);
        }


        /* Checar se bola caiu para fora do mapa - Jogo acabou */
        if (ballPosY > 570) {
            playState = false;
            ballX_dir = 0;
            ballY_dir = 0;

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Fim de Jogo. Sua Pontuação: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Pressione ENTER para recomeçar", 230, 350);
        }
        g.dispose();
    }

    /* Função de Movimento do Jogo */
    public void moveRight(){
        playState = true;
        playerX += 20;
    }
    public void moveLeft(){
        playState = true;
        playerX -= 20;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time.start();

        /* Movimentação da Bola */
        if (playState == true){

            ballPosX += ballX_dir;
            ballPosY += ballY_dir;

            /* Checar colisão com blocos */
            A: for(int i = 0; i < map.map.length; i++){
                for (int j = 0; j < map.map[0].length; j++){
                    if(map.map[i][j] > 0){
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width){
                                ballX_dir = -ballX_dir;
                            }else {
                                ballY_dir = -ballY_dir;
                            }

                            break A;
                        }
                    }
                }
            }

            /* Checar choque com plataforma */
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
               ballY_dir = -ballY_dir;
            }

            /* Checar choque com bordas */
            if(ballPosX < 0){
                ballX_dir = -ballX_dir;
            }
            if(ballPosY < 0){
                ballY_dir = -ballY_dir;
            }
            if(ballPosX > 670){
                ballX_dir = -ballX_dir;
            }

        }


        repaint();
    }

    /* Atribuindo Controles ao Programa */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 600){
                playerX = 600;
            } else{
                moveRight();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX <= 10){
                playerX = 10;
            } else{
                moveLeft();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(playState == false){
                playState = true;
                ballPosX = 120;
                ballPosY = 350;
                ballX_dir = -1;
                ballY_dir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new mapGeneratorClass(3, 7);

                repaint();
            }
        }
    }








    /* ----NÃO UTILIZADOS---- */
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
