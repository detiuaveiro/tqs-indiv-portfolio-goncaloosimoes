## 2.3 Alínea (f)

Foi relativamente simples trocar entre o mock e a implementação real do cliente HTTP nos testes, pois o ProductFinderService foi projetado para receber a dependência através do construtor. Assim, nos testes unitários utilizámos um mock (comportamento controlado), enquanto nos testes de integração bastou injetar a implementação real (TqsBasicHttpClient). Este design, baseado em interfaces e injeção de dependências, aumentou a flexibilidade do código e tornou possível alternar facilmente entre diferentes implementações sem alterar a lógica do serviço.

Se o ProductFinderService instanciasse diretamente o cliente HTTP no seu construtor, isso criaria um forte acoplamento com uma implementação específica. Como consequência, não seria possível utilizar mocks nos testes unitários, tornando os testes mais lentos, frágeis e dependentes de chamadas reais à rede. Além disso, dificultaria a evolução do código, caso fosse necessário trocar a biblioteca HTTP no futuro.

## 2.3 Alínea (g)

### mvn test
Executa apenas os testes unitários, normalmente com o Surefire Plugin. Estes testes são rápidos e independentes de serviços externos. Os testes de integração não são incluídos aqui.

### mvn package
Compila o código, executa os testes unitários (Surefire) e, se tudo correr bem, empacota a aplicação (por exemplo, cria um .jar ou .war). Os testes de integração também não são executados por padrão.

### mvn package -DskipTests=true
Faz o mesmo que o comando anterior, mas ignora todos os testes (não executa nem unitários nem integração). Apenas compila e gera o pacote. Isso pode ser útil para builds rápidos, mas arriscado porque não garante que o código está correto.

### mvn failsafe:integration-test
Executa especificamente os testes de integração (aqueles que seguem a convenção *IT.java ou *IntegrationTest.java). Normalmente é usado em conjunto com mvn verify, que garante a execução correta da sequência build + testes unitários + testes de integração.

### mvn install
Compila o código, roda os testes unitários, empacota e instala o artefacto no repositório local (~/.m2/repository), de forma que possa ser usado como dependência em outros projetos. Os testes de integração só rodam se estiver configurado para isso (ex.: via Failsafe em verify).