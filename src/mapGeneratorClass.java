import java.awt.*;

public class mapGeneratorClass {

    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public mapGeneratorClass(int row, int col){

        map = new int[row][col];
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                map[i][j] = 1;
            }
        }
        brickWidth = 540/col;
        brickHeight = 150/row;
    }

    public  void draw(Graphics2D g){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++) {
                if(map[i][j] > 0){
                    /* Desenhar os blocos no mapa */
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    /* Criando a borda */
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);


                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col){
        map[row][col] = value;
    }


}
