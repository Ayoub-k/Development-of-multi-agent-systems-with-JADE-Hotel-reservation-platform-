package agents;

import java.io.IOException;
import java.util.ArrayList;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


public class AgentInterface extends GuiAgent {
	
	
	

	private static final long serialVersionUID = 1L;
	public ArrayList<String> info = new ArrayList<String>();
	private AgentGui gui;

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
		
		gui = new AgentGui();
		gui.setAgentGui(this);

		addBehaviour(new CyclicBehaviour(this) {

			private static final long serialVersionUID = 1L;

			public void action() {
				
				ACLMessage bienReserver = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));

				if (info.size() > 0) {
					System.out.println("Bien realise");

					try {
						ACLMessage chreche = new ACLMessage(ACLMessage.CFP);
						chreche.addReceiver(new AID("AgentReservation", AID.ISLOCALNAME));
						Object[] objInfo = {info};
						chreche.setContentObject(objInfo);
						myAgent.send(chreche);
						gui.InfoChambre.setText("Votre demande est encore traiter ... :\n ");
						info.clear();

					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				
				
				
				else if(bienReserver != null ) {
					try {
						Object[] obj = (Object[]) bienReserver.getContentObject();
						
						gui.InfoChambre.append("  Votre chambre bien reserver :\n");
						gui.InfoChambre.append("  Hotel : "+(String)obj[0]+"\n");
						gui.InfoChambre.append("  Pays : "+(String)obj[1]+"\n");
						gui.InfoChambre.append("  Ville : "+(String)obj[2]+"\n");
						gui.InfoChambre.append("  Numero chambre : "+String.valueOf(obj[3])+"\n");
						gui.InfoChambre.append("  Prix chambre : "+String.valueOf(obj[4])+"\n");
						gui.InfoChambre.append("-------------------------------------------------------------- \n");
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}

			}
			
		});

	}

	protected void onGuiEvent(GuiEvent event) {

		if (event.getType() == 1) {

			@SuppressWarnings("unchecked")
			ArrayList<String> param = (ArrayList<String>) event.getParameter(0);
			this.setInfo(param);
		}

	}

	public ArrayList<String> getInfo() {
		return info;
	}

	public void setInfo(ArrayList<String> info) {
		this.info = info;
	}

}
