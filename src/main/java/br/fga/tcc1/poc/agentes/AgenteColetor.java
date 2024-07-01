package br.fga.tcc1.poc.agentes;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.io.Serial;

public class AgenteColetor extends Agent {
    @Serial
    private static final long serialVersionUID = 7084732692625983764L;

    @Override
    protected void setup() {
        ServiceDescription sd = new ServiceDescription();
        sd.setType("fornece_dados");
        sd.setName(this.getLocalName());

        registrarServico(sd);
        receberMensagens("fornece_dados", "Dados solicitados");
    }

    protected void registrarServico(ServiceDescription sd) {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
    }

    protected void receberMensagens(final String mensagem, final String resposta) {
        addBehaviour(new CyclicBehaviour() {
            @Serial
            private static final long serialVersionUID = -6798726141889684953L;

            @Override
            public void action() {
                ACLMessage msg = receive();

                if (msg != null) {
                    if (msg.getContent().equalsIgnoreCase(mensagem)) {
                        ACLMessage reply = msg.createReply();
                        reply.setContent(resposta);
                        myAgent.send(reply);
                    }
                } else {
                    block();
                }
            }
        });
    }
}
