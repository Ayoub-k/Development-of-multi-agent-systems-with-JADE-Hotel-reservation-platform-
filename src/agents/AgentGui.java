package agents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jade.gui.GuiEvent;


public class AgentGui extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	
	   
	
	String [] type= {"","double","Single"};
	JLabel      Pays, ville, nbrpersonne,DateD,DateF,typechambre;
	JTextField  textPays,textville,textnbrpersonne,textDateD,textDateF;
	JButton     envoyerE, exitE;
	JPanel      Pane,Pane1,Pane2,Pane3;
	JTextArea   InfoChambre;
	JComboBox<Object>   Typechambre =new JComboBox<Object>(type);
	private AgentInterface agentGui ;
	 
	
	public AgentInterface getAgentGui() {
		return agentGui;
	}


	public void setAgentGui(AgentInterface agentGui) {
		this.agentGui = agentGui;
	}


	public AgentGui() {
		Pays            = new JLabel("Pays");
		ville           = new JLabel("Ville");
		nbrpersonne     = new JLabel("NbrPersonne");
		DateD           = new JLabel("DateDebut");
		DateF           = new JLabel("DateFin");
		typechambre     = new JLabel("type de chambre");
 		
		textPays        = new JTextField(6);
		textville       = new JTextField(6);
		textnbrpersonne = new JTextField(6);
		textDateD       = new JTextField(6);
		textDateF       = new JTextField(6);
		
		envoyerE        = new JButton("Envoyer");
		exitE           = new JButton("Quitter");
		
		
		InfoChambre     = new JTextArea(3,30);
		
		Pane1            = new JPanel(new FlowLayout());
		Pane2            = new JPanel(new FlowLayout());
		Pane             = new JPanel(new BorderLayout());
		Pane3            = new JPanel();
		
		Pane1.add(Pays);
		Pane1.add(textPays);
		Pane1.add(ville);
		Pane1.add(textville);
		Pane1.add(nbrpersonne);
		Pane1.add(textnbrpersonne);
		Pane1.add(typechambre);
		Pane1.add(Typechambre);
		Pane1.add(DateD);
		Pane1.add(textDateD);
		Pane1.add(DateF);
		Pane1.add(textDateF);
		
		
		
		Pane2.add(exitE);
		Pane2.add(envoyerE);
		Pane1.setBackground(Color.YELLOW);
		Pane2.setBackground(Color.YELLOW);
		this.add(Pane1,BorderLayout.NORTH);
		
		this.add(new JScrollPane(InfoChambre),BorderLayout.CENTER);
		this.add(Pane2,BorderLayout.SOUTH);
			
		exitE.addActionListener((e)->{
			System.exit(0);
		});	
		
        envoyerE.addActionListener((e)->{
        	
        	ArrayList<String> info =new ArrayList<String>();
    		//Pays, ville, chambre, nbrpersonne,DateD,DateF,typechambre;
    		info.add(textPays.getText());
    		info.add(textville.getText());
    		info.add((String) Typechambre.getSelectedItem());
    		info.add(textnbrpersonne.getText());
    		info.add(textDateD.getText());
    		info.add(textDateF.getText());
    		
        	
    		GuiEvent gevent = new GuiEvent(this,1);
    		gevent.addParameter(info);
    		agentGui.onGuiEvent(gevent);
        	
        });
		
		this.setTitle("My_Project");
		pack();
		this.setSize(1100, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


	
//	public void actionPerformed(ActionEvent e) {
//		if(e.getSource().equals(envoyerE)) {
//			InfoChambre.append("bien ");
//		}
//	}
	
	public static void main(String[] args) {
		new AgentGui();
		
	}

	
	
	

}
