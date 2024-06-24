PATH_PROJECT_JAR = target/PoC-1.0-SNAPSHOT.jar
PROJECT_GROUP    = br.fga.tcc1.poc
JADE_AGENTS      = mainAgent:$(PROJECT_GROUP).HelloWorldAgent;
JADE_FLAGS 		 = -gui -agents "$(JADE_AGENTS)" -name plataforma-de-testes

.PHONY:
	clean
	build-and-run

build-and-run:
	@echo "Gerando a build e executando o projeto"
	make build run

build:
	@echo "Gerando a build do projeto"
	./mvnw clean install

run:
	@echo "Executando o projeto com a última build criada"
	java -cp $(PATH_PROJECT_JAR) jade.Boot $(JADE_FLAGS)

clean:
	@echo "Removendo a build do projeto"
	./mvnw clean

build-and-run-win:
	@echo "Gerando a build e executando o projeto"
	make build-win run

build-win:
	@echo "Gerando a build do projeto"
	.\mvnw clean install
