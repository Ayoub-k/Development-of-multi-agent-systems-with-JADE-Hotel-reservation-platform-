package agents;


import java.util.ArrayList;


//import dao.chambre;
//import dao.Hotel;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class Container {
	
	
	

	public static void main(String[] args) {
		
		
		ArrayList<chambre> listChambre1 = new ArrayList<chambre>();
		chambre ch1_1 = new chambre(103,700.0,"Single",1);
		chambre ch1_2 = new chambre(104,2000.0,"double",2);
		listChambre1.add(ch1_1);
		listChambre1.add(ch1_2);
		
		
    	ArrayList<chambre> listChambre2 = new ArrayList<chambre>();
		chambre ch2_1 = new chambre(10,500.0,"Single",1);
		chambre ch2_2 = new chambre(11,300.0,"Single",1);
		chambre ch2_3 = new chambre(12,800.0,"Single",2);
		listChambre2.add(ch2_1);
		listChambre2.add(ch2_2);
		listChambre2.add(ch2_3);
		
		Hotel H1=new Hotel("Maroc","CasaBlanca",listChambre1);
		Hotel H2=new Hotel("Maroc","Marrakech",listChambre2);
		
		
		try {
			Runtime rt = Runtime.instance();
			ProfileImpl pc = new ProfileImpl(false);
			pc.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			AgentContainer container = rt.createAgentContainer(pc);
			
			AgentController agentcontroller0 = container.createNewAgent("AgentInteface", AgentInterface.class.getName(), new Object[]{});
			
			AgentController agentcontroller = container.createNewAgent("AgentReservation", AgentReservation.class.getName(),new Object[]{});
			
			AgentController agentcontroller1 = container.createNewAgent("AgentHotel1", AgentHotel.class.getName(), 
					new Object[]{H1});
			
			
			AgentController agentcontroller2 = container.createNewAgent("AgentHotel2", AgentHotel.class.getName(), 
					new Object[]{H2});
			
			
			container.start();
			agentcontroller0.start();
			agentcontroller.start();
			agentcontroller1.start();
			agentcontroller2.start(); 
			
			System.out.println(" de container avec agent ");
		} catch (ControllerException e) {
			
			e.printStackTrace();
		}
		System.out.println("Demarrage de container avec agent ");
	} 
}
