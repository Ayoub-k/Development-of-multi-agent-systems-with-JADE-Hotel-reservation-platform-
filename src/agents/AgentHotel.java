package agents;




import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import dao.Hotel;
//import dao.chambre;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class AgentHotel extends Agent {

	private static final long serialVersionUID = 1L;
	ArrayList<chambre> chambres = new ArrayList<chambre>();
	ArrayList<chambre> chambres_free = new ArrayList<chambre>();

	ArrayList<Integer> index_chambre_free = new ArrayList<Integer>();
	int nb_chambre_free = 0;
	
	Date date_debut;
	Date date_fin;
	
	

	protected void setup() {
		
		 AMSAgentDescription[] agents = null;
		  try {
		   SearchConstraints c = new SearchConstraints();
		   c.setMaxResults(new Long(-1));
		   agents = AMSService.search(this, new AMSAgentDescription(), c);
		  } catch (Exception e) {
		   System.out.println("Erreur " + e);
		   e.printStackTrace();
		  }
		  
		  System.out.println("----- Agents AMS Listes ----- ");
			for (int i = 0; i < agents.length; i++) {
				AID agentID = agents[i].getName();
				System.out.println("    " + i + ": " + agentID.getName());
			}
			System.out.println("---------------------------- ");

		Object[] ch = getArguments();
		Hotel hotel = (Hotel) ch[0];
		hotel.setNomHotel(this.getAID().getName());
		chambres = hotel.getChambres();

		System.out.println("D�marrage de l'agent :" + this.getAID().getName());
		System.out.println(hotel.getChambres().get(0).getPrix());

		try {

			DFAgentDescription Afd = new DFAgentDescription();
			Afd.setName(getAID());

			ServiceDescription sd = new ServiceDescription();
			sd.setType("Hotel");
			sd.setName("JADE-Hotel");
			Afd.addServices(sd);

			DFService.register(this, Afd);

		} catch (FIPAException e) {
			e.printStackTrace();
		}

		addBehaviour(new CyclicBehaviour(this) {

			private static final long serialVersionUID = 1L;
			

			public void action() {

				// recevoir demande, recherce les chambre libre similaire a la demande et
				// l'envoier � l'AgentReservation
				ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.CFP));
				ACLMessage acc_Pro = receive(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));
				if (msg != null) {
					try {

						Object[] cmd = (Object[]) msg.getContentObject();
						
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
						date_debut = simpleDateFormat.parse((String) cmd[4]);
						date_fin = simpleDateFormat.parse((String) cmd[5]);
						
						
						if (hotel.getPays().equals(cmd[0]) && hotel.getVille().equals(cmd[1])) {
							for (int i = 0; i < chambres.size(); i++) {

								int nb = Integer.parseInt((String) cmd[3]);
								
								if (chambres.get(i).getType().equals(cmd[2]) && chambres.get(i).getNb_Per() == nb
										&& chambres.get(i).getDate_fin().before(date_debut)) {

									index_chambre_free.add(i);
									chambres_free.add(chambres.get(i));
									nb_chambre_free++;
								}

							}
							if (nb_chambre_free > 0) {
								 
								Object[] objC = new Object[nb_chambre_free];
								ACLMessage rooms = new ACLMessage(ACLMessage.PROPOSE);
								
								for(int i=0;i<nb_chambre_free;i++) {
									
									
									
									Object[] c = {chambres_free.get(i).getN_chambre(),chambres_free.get(i).getPrix()};
									objC[i]= c;
									System.out.println(chambres_free.get(i).getN_chambre());
								}
								 
								nb_chambre_free=0;
								System.out.println("Insertion "+objC);
								rooms.setContentObject(objC);
								
								
								
								rooms.addReceiver(new AID(msg.getSender().getLocalName(), AID.ISLOCALNAME));
							
								System.out.println("chambres_free envoyer a reservation");
								myAgent.send(rooms);

							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}//fin de recherche chambres free similaire
				
			
				else if (acc_Pro != null) {
					
				
					try {
						Object[] o =(Object[]) acc_Pro.getContentObject();
						
						int N_chambre_res = (int) o[0];
						
						System.out.println(N_chambre_res+" Numero att rese");
						
						ACLMessage confirm = new ACLMessage(ACLMessage.CONFIRM);
						confirm.addReceiver(new AID(acc_Pro.getSender().getLocalName(), AID.ISLOCALNAME));
						
						
						
						for (int i = 0; i < chambres.size(); i++) {
							if(chambres.get(i).getN_chambre()==N_chambre_res) {
								
								chambres.get(i).setDate_debut(date_debut);
								chambres.get(i).setDate_fin(date_fin);
								Object[] obj = {hotel.getNomHotel(),hotel.getPays(),hotel.getVille(),N_chambre_res,chambres.get(i).getPrix()};
								confirm.setContentObject(obj);
								myAgent.send(confirm);
								break;
							}
						}
						
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}
				
				else {
					block();
				}
			}
		});
	}
}
