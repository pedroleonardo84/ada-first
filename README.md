#   📚  Projeto final - Ifome (Back-End)

---

### ✔️ Descrição do desafio


[ Link da descrição das Atividades](https://hugobrendow97.gitbook.io/f1rst-atividades/)

---

[![aula01.png](https://i.postimg.cc/D0WPb7ZK/aula01.png)](https://postimg.cc/T56DzMgk)


####  Atividade 1
✔️ Atividade:
- Criar cadastro de entregador com as seguintes regras:
Validação do Número do Documento:
Regra: O número do documento fornecido deve seguir o padrão estabelecido para cada tipo de documento.
Detalhes:
CPF: Deve conter 11 dígitos.
RG: Deve conter 7 dígitos.
CNH: Deve conter 11 dígitos.
- Justificativa: Garante a consistência e integridade dos dados dos documentos fornecidos.
Validação da Data de Vencimento da CNH:
- Regra: A data de vencimento da CNH não pode ser anterior à data atual.
- Justificativa: Evita que entregadores com CNH vencida sejam cadastrados, mantendo a conformidade com as regulamentações de trânsito.
Validação da Situação do Veículo:
- Regra: Antes de cadastrar um veículo, o sistema deve validar as seguintes condições:
A data do modelo do veículo não pode ser inferior a 2010.
A placa do veículo deve seguir o padrão estabelecido para o país.
O RENAVAM (Registro Nacional de Veículos Automotores) do veículo deve ser válido.
Detalhes:
Data do Modelo: Deve ser igual ou superior a 2010.
Placa do Veículo: Deve seguir o padrão específico do país (por exemplo, no Brasil: ABC1D23).
RENAVAM: Deve ser um número válido e único.
- Justificativa: Garante que apenas veículos em conformidade com as normas e regulamentações sejam registrados no sistema.
Validação de Conta Bancária:
- Regra: O sistema deve validar as informações da conta bancária fornecidas pelo entregador.
Detalhes:
Número da Agência: Deve conter dígitos válidos.
Número da Conta: Deve conter dígitos válidos.
Tipo de Conta: Deve ser uma opção válida (corrente, poupança, etc.).
Instituição Bancária: Deve ser uma instituição bancária válida.
- Justificativa: Garante que as informações bancárias fornecidas são precisas e correspondem a uma conta válida.

####  Atividade 2
✔️ Atividade:
Atividade 2
- 1 . Criar endpoints para Atividade 1 (MVC)
    - Salvar entregador
    - Remover entregador    
- 2. Criar testes de integração (Menu Testes de Integração) com base no conteúdo do LMS para os cenários identificados


# 1 - Como usar Cucumber

Para utilização do cucumber, precisamos realizar os seguintes passos:
Adicionar dependências necessárias para execução e escrita dos cenários:
```
<dependency>
	<groupId>io.cucumber</groupId>
	<artifactId>cucumber-java</artifactId>
	<version>6.8.1</version>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>io.cucumber</groupId>
	<artifactId>cucumber-junit-platform-engine</artifactId>
	<version>6.8.1</version>
</dependency>
<dependency>
	<groupId>io.cucumber</groupId>
	<artifactId>cucumber-spring</artifactId>
	<version>6.8.1</version>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>org.junit.platform</groupId>
	<artifactId>junit-platform-commons</artifactId>
	<version>1.7.1</version>
	<scope>test</scope>
</dependency>
```
Criar uma classe que será responsável pela subida dos testes quando utilizar o Cucumber
package br.com.ada.ifome;

​```
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
​
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class CucumberSpringContextConfiguration {
}
```
Crie um arquivo com o nome junit-platform.properties no diretório:

src/test/resources com o conteúdo abaixo:
```
cucumber.publish.enabled=true
cucumber.plugin=pretty,html:target/classes/static/features/index.html
Criar um arquivo do tipo .feature com o padrão Gherkin no src/test/resources exemplo: calculadora-soma.feature
#language: pt
```
Funcionalidade: Adição
​
  Cenário: soma entre 2 números
    Dado que o primeiro número é 10
    E o segundo número é 20
    Quando o usuário realiza a adição
    Então o resultado deve ser 30
Criar um arquivo para realizar a execução dos testes, no diretório src/test/java com o tipo de arquivo .java, Exemplo:

```
package br.com.ada.ifome;
​
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
​
public class AdicaoSteps {
​
    private int primeiroNumero;
    private int segundoNumero;
    private int resultado;
​
    @Dado("que o primeiro número é {int}")
    public void informarPrimeiroNumero(int numero) {
        this.primeiroNumero = numero;
    }
​
    @E("o segundo número é {int}")
    public void informarSegundoNumero(int numero) {
        this.segundoNumero = numero;
    }
​
    @Quando("o usuário realiza a adição")
    public void realizarAdicao() {
        this.resultado = primeiroNumero + segundoNumero;
    }
​
    @Então("o resultado deve ser {int}")
    public void verificarResultado(int esperado) {
        Assert.assertEquals(esperado, resultado);
    }
}
​```
Criar um executor no diretório src/test/java com o nome do arquivo RunCucumberTest.java para inicializar testes do Cucumber:
```
@Cucumber
public class RunCucumberTest {
}
```
Após criado, executar o teste:


# Cucumber & Jacoco

## Jacoco
Adicionar dependencia:
```
<dependency>
    <groupId>org.jacoco</groupId>
    <artifactId>org.jacoco.agent</artifactId>
    <version>0.8.7</version>
    <scope>runtime</scope>
</dependency>
Adicionar nos plugins:
<plugin>
	<groupId>org.jacoco</groupId>
	<artifactId>jacoco-maven-plugin</artifactId>
	<version>0.8.7</version>
	<executions>
		<execution>
			<id>prepare-agent</id>
			<goals>
				<goal>prepare-agent</goal>
			</goals>
		</execution>
		<execution>
			<id>report</id>
			<phase>verify</phase>
			<goals>
				<goal>report</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```
mvn clean install
mvn clean verify
Adicionar teste usando Cucumber + Spring Rest
​
Adicionar dependências no pom.xml
​```
<dependency>
	<groupId>io.cucumber</groupId>
	<artifactId>cucumber-java</artifactId>
	<version>6.10.4</version>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>io.cucumber</groupId>
	<artifactId>cucumber-junit</artifactId>
	<version>6.10.4</version>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>io.rest-assured</groupId>
	<artifactId>rest-assured</artifactId>
	<version>4.3.3</version>
	<scope>test</scope>
</dependency>
```
Criar dentro de resources/feature um arquivo chamado usuario.feature
Feature: Operações CRUD para Usuários
​
  Scenario: Criar um novo usuário
    Given o endpoint de usuários
    When eu envio uma requisição POST para criar um novo usuário
    Then eu recebo uma resposta com status code 201
Criar dentro do pacote test/java um arquivo chamado UsuarioSteps.java com o seguinte conteúdo:
​```
import br.com.ada.ifome.endereco.Endereco;
import br.com.ada.ifome.usuario.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
​
public class UsuarioSteps {
    private RequestSpecification request;
    private Response response;
​
    @Given("o endpoint de usuários")
    public void setEndpoint() {
        RestAssured.baseURI = "http://localhost:8080/api/v1/usuarios";
        request = RestAssured.given();
    }
​
    @When("eu envio uma requisição POST para criar um novo usuário")
    public void createNewUser() {
        Usuario usuario = new Usuario();
        usuario.setCpf("05566644411");
        usuario.setNome("Hugo");
        usuario.setEmail("hugo@mail.com");
        var endereco = new Endereco();
        endereco.setId(1L);
        endereco.setCep("72333111");
        endereco.setNumero(2L);
        endereco.setComplemento("apartamento");
        endereco.setLogradouro("Brasilia-DF");
        usuario.setEndereco(endereco);
​
        response = request.body(asJsonString(usuario)).contentType(ContentType.JSON).post();
    }
​
    @Then("eu recebo uma resposta com status code 201")
    public void verifyStatusCode() {
        response.then().statusCode(201);
    }
​
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
​```
Criar uma classe dentro do pacote test/java com o nome RunCucumberTest.java que irá realizar o start dos tests
```
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;
​
@RunWith(Cucumber.class)
public class RunCucumberTest {
}
```
Realizar start da aplicação no arquivo IfomeApplication.java
Realizar start dos testes chamando o arquivo RunCucumberTest.java
