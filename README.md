<h1>Teste LuizaLabs Android</h1>

<p>
    Eis aqui minha poesia para computadores, uma mistura de aprendizado, desafios e alguns momentos de caos. 
    Desculpem a bagunça, mas cuidar de uma criança sozinho, procurar emprego e ainda encontrar tempo para estudar não é nada fácil! 
    Cada linha de código aqui representa um esforço sincero de quem está sempre tentando melhorar.
</p>

<h2>Tecnologias e Ferramentas Utilizadas</h2>
<ul>
    <li><strong>Retrofit</strong>: Para fazer chamadas de API de um jeito mais tranquilo.</li>
    <li><strong>Hilt</strong>: Aquele empurrãozinho para facilitar a injeção de dependências.</li>
    <li><strong>Compose</strong>: Porque a vida é melhor quando a interface é declarativa.</li>
    <li><strong>StateFlow</strong>: Para gerenciar estados de forma reativa e fluída.</li>
    <li><strong>RxJava</strong>: Fluxos de dados reativos para quando a gente quer que as coisas fluam naturalmente.</li>
    <li><strong>Mockk</strong>: Quando a gente precisa simular um comportamento e seguir em frente.</li>
    <li><strong>JUnit</strong>: Para garantir que os bugs fiquem bem longe do código (ou pelo menos tentar!).</li>
</ul>

<h2>Padrões e Arquitetura</h2>
<ul>
    <li><strong>#DesignPatterns</strong>: Aqueles truques clássicos para resolver problemas do jeito certo.</li>
    <li><strong>#MVVM</strong>: Separando as responsabilidades pra deixar o código mais limpinho.</li>
    <li><strong>#S.O.L.I.D</strong>: Porque um código sólido é sempre bem-vindo!</li>
</ul>

<h2>Integração Contínua</h2>
<p>
    Utilizamos o <strong>CircleCI</strong> para garantir que o nosso código esteja sempre em dia. Com pipelines configurados para rodar testes e gerar o build do APK automaticamente, podemos focar no que realmente importa: entregar uma experiência incrível para os usuários.
</p>

<h3>Pipeline no CircleCI</h3>

<a href="https://ibb.co/kJfP9sD"><img src="https://i.ibb.co/tzt1xT2/Captura-de-tela-2024-10-07-000836.png" alt="Captura-de-tela-2024-10-07-000836" border="0"></a><br /><a target='_blank' href='https://pt-br.imgbb.com/'>Image</a><br />


<p>O arquivo <code>.circleci/config.yml</code> contém a configuração do pipeline, com os seguintes passos:</p>
<pre><code>version: 2.1

orbs:
android: circleci/android@2.5.0

jobs:
build:
executor:
name: android/android-machine
resource-class: large
tag: 2021.10.1
steps:
- checkout
- run:
name: Install dependencies
command: |
sudo apt-get update && sudo apt-get install openjdk-17-jdk
sudo update-alternatives --set java /usr/lib/jvm/java-17-openjdk-amd64/bin/java
sudo update-alternatives --set javac /usr/lib/jvm/java-17-openjdk-amd64/bin/javac
echo 'export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64/' >> ~/.circlerc
- run:
name: Install permissions
command: |
chmod +x gradlew
- run:
name: Run unit test
command: |
./gradlew testReleaseUnitTest
- store_test_results:
path: app/build/test-results/testReleaseUnitTest
- run:
name: Build apk
command: |
./gradlew assembleDebug
- store_artifacts:
path: app/build/outputs/apk/debug
</code></pre>

<h3>Como configurar o CircleCI</h3>
<ol>
    <li>Crie uma conta no <a href="https://circleci.com/">CircleCI</a> e conecte seu repositório.</li>
    <li>Crie um diretório <code>.circleci</code> na raiz do seu projeto e adicione o arquivo <code>config.yml</code> com o conteúdo acima.</li>
    <li>Faça um commit do arquivo <code>.circleci/config.yml</code> e observe o pipeline rodar no CircleCI.</li>
</ol>

<h4>O que o pipeline faz:</h4>
<ul>
    <li><strong>Checkout</strong>: Faz a cópia do código do repositório.</li>
    <li><strong>Instalação de Dependências</strong>: Instala o JDK 17 e ajusta o ambiente para utilizar essa versão do Java.</li>
    <li><strong>Configuração de Permissões</strong>: Ajusta as permissões do <code>gradlew</code> para garantir que ele possa ser executado.</li>
    <li><strong>Execução de Testes</strong>: Roda os testes de unidade para garantir que o código esteja funcionando como esperado.</li>
    <li><strong>Armazenamento dos Resultados dos Testes</strong>: Armazena os resultados dos testes para visualização no CircleCI.</li>
    <li><strong>Build do APK</strong>: Gera o APK de debug.</li>
    <li><strong>Armazenamento do APK</strong>: Armazena o APK gerado como artefato, disponível para download no CircleCI.</li>
</ul>


