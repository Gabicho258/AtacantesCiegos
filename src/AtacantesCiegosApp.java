
import javax.swing.*;

public class AtacantesCiegosApp {
	public static void main(String[] args) {
		
		
		int difficulty = 0;
		int key = 0;
		do {
			String menu = "Menú Atacantes Ciegos\n"
					+ "Seleccione nivel de dificultad\n"
					+ "1.- Fácil - 8x8 - 10 puntos por jugador\n"
					+ "2.- Medio - 10x10 - 15 puntos por jugador\n"
					+ "3.- Difícil - 12x12 - 20 puntos por jugador\n"
					+ "Ingrese su opción: ";
			try {
				
				difficulty = Integer.parseInt(JOptionPane.showInputDialog(menu));// Captamos la difultad 
				if (difficulty >3 || difficulty <1) {
					throw new Exception("Número fuera de rango");
				}
				key = 1;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				continue;
			}
			Battlefield board = new Battlefield(difficulty);
			board.getInput(1);
			board.getInput(2);
			board.showBoard();
		} while (key != 1);
		
	}
}
