package br.fga.tcc1.poc.agentes;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.io.Serial;

public class AgenteParticipativo extends Agent {

    @Serial
    private static final long serialVersionUID = 3528797197173321287L;

    @Override
    protected void setup() {
        Object[] args = getArguments();

        if (args != null && args.length > 0) {
            String argumento = (String) args[0];

            if (argumento.equalsIgnoreCase("fornece_dados")) {
                ServiceDescription serviceDescription = new ServiceDescription();
                serviceDescription.setType("fornece_dados");
                buscar(serviceDescription, "fornece_dados");
            }

            addBehaviour(new CyclicBehaviour(this) {
                @Serial
                private static final long serialVersionUID = -1821868423809748978L;

                @Override
                public void action() {
                    ACLMessage msg = receive();

                    if (msg != null) {
                        System.out.println(msg.getSender() + ": " + msg.getContent());
                    } else {
                        block();
                    }
                }
            });
        }
    }

    protected void buscar(final ServiceDescription sd, final String pedido) {
        addBehaviour(new TickerBehaviour(this, 60000) {
            @Serial
            private static final long serialVersionUID = -7864019234358042450L;

            @Override
            protected void onTick() {
                DFAgentDescription dfd = new DFAgentDescription();
                dfd.addServices(sd);

                try {
                    DFAgentDescription[] resultado = DFService.search(myAgent, dfd);

                    if (resultado.length > 0) {
                        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                        msg.addReceiver(resultado[0].getName());
                        msg.setContent(pedido);

                        myAgent.send(msg);

                        // Finaliza o comportamento
                        stop();
                    }
                } catch (FIPAException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        });
    }

}
