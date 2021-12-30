
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Battlefield extends JFrame {
	
	private int length;			
	private int difficulty;
	private int pointsPerPlayer;
	private Tanque[][] board;
	private ArrayList<Tanque> player1;
	private ArrayList<Tanque> player2;
	//private boolean turnPlayer1 = true;
	
	public Battlefield(int difficulty) {
		setDifficulty(difficulty);
		setLength(this.difficulty);
		board = new Tanque[length][length];
		
		setTitle("Atacantes ciegos");
        setSize(difficulty*100+480, difficulty*100+480);
        setLayout(new GridLayout(length, length, 5, 5));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(false);
        setLocationRelativeTo(null);
	}
	public int getPointsPerPlayer() {
		return pointsPerPlayer;
	}
	public ArrayList<Tanque> getPlayer1() {
		return player1;
	}
	public ArrayList<Tanque> getPlayer2() {
		return player2;
	}
	public void setPlayer1(ArrayList<Tanque> player1) {
		this.player1 = player1;
	}
	public void setPlayer2(ArrayList<Tanque> player2) {
		this.player2 = player2;
	}
	
	private void setDifficulty(int difficulty) {
		if (difficulty <= 1) {
			this.difficulty = 1;
			pointsPerPlayer = 10;
		} else if (difficulty == 2) {
			this.difficulty = difficulty;
			pointsPerPlayer = 15;
		} else if (difficulty >= 3) { 
			this.difficulty = 3;
			pointsPerPlayer = 20;
		}
	}
	public void showBoard() {
		JButton btn;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				//Pintar los lados de tablero
				btn = new JButton();
				btn.setSize(60, 60);
				if (j<length/2) 
		    		 btn.setBackground(new Color(143, 251, 255));
				else 
					btn.setBackground(new Color(127, 245, 166));

				if (board[i][j] != null && (board[i][j].isVisible() || board[i][j].wasAtacked())) {
					
					btn.setName("btn"+i+j);
					if (!this.board[i][j].isAlive()) {
						btn.setIcon(getIcon("empty.png", btn));
						add(btn);
						continue;
					}
					if (this.board[i][j].getName().endsWith("1") ) {
			    		btn.setBackground(new Color(143, 251, 255));
			    		btn.setIcon(getIcon("tanqueAzul.png", btn));
			    	 } else {
			    		btn.setBackground(new Color(127, 245, 166));
			    		btn.setIcon(getIcon("tanqueVerde.png", btn));
			    	 }
			    	 btn.addActionListener(new HandleClick());
			    	 add(btn);
			     } else {
			    	 btn.addActionListener(new HandleClick());
			    	 btn.setName("btn"+i+j);
			    	 add(btn);
			     }
			}
		}
		setVisible(true);
	}
	private class HandleClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton buttonClicked = (JButton) e.getSource();
			int i = Integer.parseInt(buttonClicked.getName().substring(3, 4));
			int j = Integer.parseInt(buttonClicked.getName().substring(4));
			if (board[i][j] != null) {
				board[i][j].dicreaseHealth();
			} else {
				board[i][j] = new Tanque();
			}
			//checkear winner
			if (!checkWinner()) {
				changeVisibility();
				getContentPane().removeAll();
				showBoard();
				
			}
		}
	}
	private boolean checkWinner() {
		boolean player1 = false;
		boolean player2 = false;

		int minCol;
		int maxCol;

		//Player 1 - Su mitad de tablero
		minCol = 0;
		maxCol = length/2 -1;
		for (int i = 0; i < board.length && !player1; i++) {
			for (int j = minCol; j < maxCol && !player1; j++) {
				if (board[i][j]!= null && board[i][j].isAlive()) 
					player1 = true;
			}
		}
		//Player 2 - Su mitad de tablero
		minCol = length/2;
		maxCol = length-1;
		for (int i = 0; i < board.length && !player2; i++) {
			for (int j = minCol; j < maxCol && !player2; j++) {
				if (board[i][j]!= null && board[i][j].isAlive()) 
					player2 = true;
			}
		}
		if ((player1 && !player2) || (!player1 && player2)) {
			if (player1) 
				JOptionPane.showMessageDialog(null, "¡Ha ganado el jugador 1!");
			else 
				JOptionPane.showMessageDialog(null, "¡Ha ganado el jugador 2!");
		}
		
		return ((player1 && !player2) || (!player1 && player2));
	}
	private Icon getIcon(String url, JButton button) {
		ImageIcon originalIcon = new ImageIcon(getClass().getResource(url));
		int width = button.getWidth();
		int height = button.getHeight();
		ImageIcon resizedIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		return resizedIcon;
	}
	private void changeVisibility() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] != null) {
					board[i][j].changeVisibility();						
				}
			}
		}
	}
	private void setLength(int difficulty) {
		if (difficulty == 1) 
			this.length = 8;
		else if (difficulty == 2) 
			this.length = 10;
		else  
			this.length = 12;
	}
	
	private void setTanquePosition(int player, ArrayList<Tanque> equipo) {
		int minCol;
		int maxCol;
		//Aquí se trabaja con indices de array 0 - ...
		if (player == 1) {
			minCol = 0;
			maxCol = length/2 -1;
		} else {
			minCol = length/2;
			maxCol = length-1;
		}
		for (int i = 0; i < equipo.size(); i++) {
			int columna = (int) (Math.random() * (maxCol + 1 - minCol)) + minCol;
			int fila = (int) (Math.random() * (this.board.length));
			if (checkPosition(this.board, fila, columna)) {
				this.board[fila][columna] = equipo.get(i);
			} else 
				i--;
		}
	}
	
	//Método que revisa si la posición está ocupada en el tablero o no
	private boolean checkPosition(Tanque[][]board, int fila, int columna) {
		return (board[fila][columna] == null);
	}
	
	public void getInput(int player) {
		int points = pointsPerPlayer;
		int key = 0;
		ArrayList<Tanque> equipo = new ArrayList<Tanque>();
		int num = 0;
		do {
			String menu = "Gestionar mi armamento - Jugador " + player
					+ "\nPuntos disponibles: " + points
					+ "\n1.- Añadir tanque"
					+ "\n2.- Eliminar tanque"
					+ "\n3.- Ver equipo"
					+ "\n4.- Terminar"
					+ "\nIngrese su opción: ";
			try {
				key = Integer.parseInt(JOptionPane.showInputDialog(menu));// Captamos la difultad para asignar los puntos 
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Opción ingresada no válido");
				continue;
			}
			int position;
			switch (key) {
			case 1:
				String typeTank= "Ingrese el tipo de tanque:\n"
						+ "1.- Tanque nivel 1 \n"
						+ "2.- Tanque nivel 2 \n"
						+ "3.- Tanque nivel 3 \n"
						+ "Ingrese su opción: \n";

				int level = 0;
				try {
					level = Integer.parseInt(JOptionPane.showInputDialog(typeTank));// Captamos el nivel
					if (level>3 || level<1) {
						throw new Exception();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Opción ingresada no válido");
					continue;
				}
				if (level >=1 && level <= 3) {
					if (level == 1 && points >= 2) {
						// Formato de nombre Tanque-Nivel-NúmeroDeCreacion-Equipo/jugador
						equipo.add(new Tanque(("TanqueNivel"+level+"x"+num+"x"+player), 2, 1	));
						num++;
						points -= 2;
					}else if (level == 2 && points >= 3) {
						equipo.add(new Tanque(("TanqueNivel"+level+"x"+num+"x"+player)	, 3, 2));
						num++;
						points -= 3;
					} else if (level == 3 && points >= 4) {
						equipo.add(new Tanque(("TanqueNivel"+level+"x"+num+"x"+player), 4, 3));
						num++;
						points -= 4;
					} else 
						JOptionPane.showMessageDialog(null, "No cuenta con puntos disponibles para este tipo de tanque");
				} else 
					JOptionPane.showMessageDialog(null, "Opción no valida");
				break;
			case 2:
				if (equipo.isEmpty()) {
					JOptionPane.showMessageDialog(null, "¡No hay tanques en su equipo!");
					break;
				}
				position = 1;
				String delete = "";
				for (Tanque tanque : equipo) { 
					delete += (position+".- "+tanque.getName() + "\n");
					position++;
				}
				delete += "¿A qué tanque desea eliminar?";
				try {
					position = Integer.parseInt(JOptionPane.showInputDialog(delete));
					if (equipo.size() < position || position < 1) {
						throw new Exception();
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Opción ingresada no válido");
					continue;
				}
				points+= equipo.remove((position-1)).getPoints();
				JOptionPane.showMessageDialog(null, "Tanque borrado correctamente");
				break;
			case 3:
				if (equipo.isEmpty()) {
					JOptionPane.showMessageDialog(null, "¡No hay tanques en su equipo!");
					break;
				}
				position = 1;
				String equipoStr = "";
				for (Tanque tanque : equipo) { 
					equipoStr += (position+".- "+tanque.getName() + "\n");
					position++;
				}
				JOptionPane.showMessageDialog(null, equipoStr);
				break;
			default:
				break;
			}
		} while (key != 4);
		
		if (player == 1) {
			for (Tanque tanque : equipo) 
				tanque.setVisible(true);
			this.setPlayer1(equipo);
			
		}else 
			this.setPlayer2(equipo);
		this.setTanquePosition(player, equipo);
	}
}











