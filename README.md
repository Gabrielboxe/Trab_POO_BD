# README.md

## Como Buildar e Executar o Projeto

### Pré-requisitos
- JDK 11 ou superior instalado
- Maven 3.6.3 ou superior instalado

### Passos para Buildar o Projeto

1. Navegue até o diretório raiz do projeto (onde está o arquivo `pom.xml`)

2. Execute o comando para compilar e empacotar o projeto:

```mvn clean package```

3. Após a conclusão bem-sucedida, o arquivo JAR será gerado na pasta `target/`

### Passos para Executar o Projeto

#### Método 1: Usando o Maven

```mvn exec:java -Dexec.mainClass="com.trabalhopratico.app.AppLauncher"```

#### Método 2: Executando o JAR diretamente
```java -cp target/trabalhopratico-1.0-SNAPSHOT.jar com.trabalhopratico.app.AppLauncher```

#### Método 3: Usando o JAR (JAR executável)
```java -jar target/trabalhopratico-1.0-SNAPSHOT.jar``` 
 