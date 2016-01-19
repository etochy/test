import java.io.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

class Calcul extends JPanel{
	private String _cheminInit;
	private List<Double> _tabDonnee= new ArrayList<Double>();
	private double _poids = 0; // nombre de mesure de vitesses
	private double _pasTemps = 0; // frequence de la prise de la mesure vitesse
	
	public Calcul(String cheminInit){
		_cheminInit = cheminInit;
	}
	public void lire(){
		int x = 0;
		try {
			File f = new File(_cheminInit);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			try{
				// initialisation
				String line = br.readLine();
				_poids = Double.parseDouble(line)*9.81;
				line = br.readLine();
				_pasTemps = Double.parseDouble(line);
				
				line = br.readLine();
				while (line != null){
					_tabDonnee.add(Double.parseDouble(line));
					++x;
					line = br.readLine();
				}
				br.close();
				fr.close();
			}catch(IOException exception){System.out.println("Erreur : "+exception.getMessage());}
		}catch(FileNotFoundException exception){System.out.println("Erreur : Fichier non trouvé");}
	}
	
	public void afficher(){
		double y;
		for(int x=0; x<_tabDonnee.size(); ++x){
			y = _poids*(_tabDonnee.get(x)*_tabDonnee.get(x))*_pasTemps;
			System.out.println(x+" : "+y); //Formule peu etre inexacte
		}
		this.repaint();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);

		double y;
		int x2;
		int plus = 780/_tabDonnee.size();
		int y2;
		double puissance;
		for(int x=0; x<_tabDonnee.size(); ++x){
			puissance = _poids*(_tabDonnee.get(x)*_tabDonnee.get(x))*_pasTemps;
			y = _tabDonnee.get(x);
			x2 = plus*x;
			y2 = 500 - (int)y;
			g.fillOval(x2,y2,10,10);
			g.drawString("T:"+x*_pasTemps,x2,y2);
			g.drawString("V:"+y,x2,y2-10);
			g.drawString("P:"+puissance,x2,y2+20);
		}
	}
}

class Fenetre extends JFrame{
	public Fenetre(String init){
		this.setTitle("Test");
		this.setSize (800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.validate();
		this.setVisible(true);
		Calcul pan = new Calcul(init);
		this.setContentPane(pan); 
		this.validate();
		this.setVisible(true);
		pan.lire();
		pan.afficher();
	}
}

class MainActivity{
	public static void main(String [] args){
		Fenetre f = new Fenetre("init.txt");
	}
}
