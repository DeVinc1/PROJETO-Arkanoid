import javax.swing.*;

public class mainClass {

    public static void main(String[] args) {

        /* Instanciando Mecânicas de Jogo */
        gameplayClass gameMechanics = new gameplayClass();

        /* Propriedades do JFrame */
        JFrame frame = new JFrame();

        frame.add(gameMechanics);

        frame.setVisible(true);
        frame.setResizable(false);
        frame.setBounds(10, 10, 710, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setTitle("Arkanoid");





    }
}
