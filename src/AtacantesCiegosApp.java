
import javax.swing.*;

public class AtacantesCiegosApp {
	public static void main(String[] args) {
		
		
		int difficulty = 0;
		do {
			String menu = "Men? Atacantes Ciegos\n"
					+ "Seleccione nivel de dificultad\n"
					+ "1.- F?cil - 8x8 - 10 puntos por jugador\n"
					+ "2.- Medio - 10x10 - 15 puntos por jugador\n"
					+ "3.- Dif?cil - 12x12 - 20 puntos por jugador\n"
					+ "4.- Salir\n"
					+ "Ingrese su opci?n: ";
			try {
				
				difficulty = Integer.parseInt(JOptionPane.showInputDialog(menu));// Captamos la difultad 
				if (difficulty >4 || difficulty <1) {
					throw new Exception("N?mero fuera de rango");
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				continue;
			}
			if (difficulty == 4) 
				break;
			Battlefield board = new Battlefield(difficulty);
			board.getInput(1);
			board.getInput(2);
			board.showBoard();
			break;
		} while (true);
		
	}
}
