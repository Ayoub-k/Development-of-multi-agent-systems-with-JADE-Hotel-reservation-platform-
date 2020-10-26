package agents;
import java.io.IOException;
import java.util.ArrayList;

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
import jade.lang.acl.UnreadableException;


public class AgentReservation extends Agent {

	private static final long serialVersionUID = 1L;
	
	public ArrayList<String> demande = new ArrayList<String>();
	ArrayList<String> hotels = new ArrayList<String>();
	
	ArrayList<chambre> chambres_free = new ArrayList<chambre>();
	
	
	protected void setup() {
		
//		 AMSAgentDescription[] agents = null;
//		  try {
//		   SearchConstraints c = new SearchConstraints();
//		   c.setMaxResults(new Long(-1));
//		   agents = AMSService.search(this, new AMSAgentDescription(), c);
//		  } catch (Exception e) {
//		   System.out.println("Erreur " + e);
//		   e.printStackTrace();
//		  }
		
		System.out.println("D�marrage de l'agent :" + this.getAID().getName());
		try {

			DFAgentDescription Afd = new DFAgentDescription();
			Afd.setName(getAID());

			ServiceDescription sd = new ServiceDescription();
			sd.setType("Reservation Hotel");
			sd.setName("JADE-Reservation");
			Afd.addServices(sd);

			DFService.register(this, Afd);

		} catch (FIPAException e) {
			e.printStackTrace();
		}

		addBehaviour(new CyclicBehaviour(this) {

			private static final long serialVersionUID = 1L;

			public void action() {

				// cherche vendeur par envoyer un message de type inform;
				ACLMessage cherche = receive(MessageTemplate.MatchPerformative(ACLMessage.CFP));
				ACLMessage propose = receive(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
				ACLMessage confirm = receive(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
				

				if (cherche != null) {
                    
                    
					System.out.println(getLocalName() + " je vecherche des hotel");
					DFAgentDescription participants = new DFAgentDescription();
					ServiceDescription sde = new ServiceDescription();
					sde.setType("Hotel");
					sde.setName("JADE-Hotel");
					participants.addServices(sde);
					DFAgentDescription[] result = null;
					try {
						result = DFService.search(myAgent, participants);
					} catch (FIPAException e) {
						e.printStackTrace();
					}
					
					
					for (int i = 0; i < result.length; i++) {
						hotels.add(result[i].getName().getLocalName());
					}
					
					System.out.println(" Hotel trouver "+result.length);
					
					try {
						Object[] obj = (Object[]) cherche.getContentObject();
						demande =  (ArrayList<String>) obj[0];
					} catch (UnreadableException e1) {	
						e1.printStackTrace();
					}
					
					if(!hotels.isEmpty()) {
						   try {
							ACLMessage msg1 = new ACLMessage(ACLMessage.CFP);
							Object[] chambre = {demande.get(0),demande.get(1),demande.get(2),demande.get(3),demande.get(4),demande.get(5)};
							msg1.setContentObject(chambre);
							for (int i=0;i<hotels.size();i++) {
								msg1.addReceiver(new AID((String) hotels.get(i), AID.ISLOCALNAME) );
								myAgent.send(msg1);
							System.out.println("CPF envoyer, demende");
							}
						   }catch (Exception e) {
								
								e.printStackTrace();
							}
						   hotels.clear();
						   
						}
					
				}
				
				
				
			
				else if(propose != null) {
					try {
						Object[] obj;
						 obj =(Object[])  propose.getContentObject();
						 
						// System.out.println(obj[0]);
						
						//@SuppressWarnings("unchecked")
						//ArrayList<chambre> chambres_free = (ArrayList<chambre>) obj[0];
						Object[] ch =  (Object[]) obj[0];
						 
//						Double min_prix=chambres_free.get(0).getPrix();
//						int N_c_f = chambres_free.get(0).getN_chambre();
						
						Double min_prix=(Double) ch[1];
						int N_c_f = (int) ch[0];
						
						System.out.println(N_c_f+" Numero att");
						
						for(int i=1;i<obj.length;i++) {
							
							ch =  (Object[]) obj[i];
							
							
							if ((Double) ch[1]<min_prix) {
								min_prix = (Double) ch[1];
								N_c_f = (int) ch[0];
							}
						}
						
						
						ACLMessage acc_pro = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
						acc_pro.addReceiver(new AID(propose.getSender().getLocalName(), AID.ISLOCALNAME));
						Object[] obj2 = {N_c_f};
						acc_pro.setContentObject(obj2);
						myAgent.send(acc_pro);
						
					} catch (UnreadableException | IOException e) {
						e.printStackTrace();
					}
					
				}
				
				
				
				
				else if(confirm != null) {
					try {
						Object[] obj_cf = (Object[]) confirm.getContentObject();
						ACLMessage bienReserver = new ACLMessage(ACLMessage.INFORM);
						bienReserver.addReceiver(new AID("AgentInteface", AID.ISLOCALNAME));
						bienReserver.setContentObject(obj_cf);
						myAgent.send(bienReserver);
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				

			}
		});

	}

}
