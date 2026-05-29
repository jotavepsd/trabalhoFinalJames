# Projeto FATEC - Catálogo de Produtos

Esse é o meu projeto de catálogo de produtos feito para o trabalho final da matéria. Ele foi desenvolvido usando Java com Spring Boot no backend, banco de dados PostgreSQL e as telas foram feitas com HTML, Thymeleaf e Bootstrap para não ficar feio.

O sistema também já tem a parte de login e segurança (Spring Security), dividindo quem é Usuário comum e quem é Admin.

(Desafios)

* **Campo de Quantidade (Estoque):** Criei o campo de quantidade de produtos no banco e na tela. Coloquei uma validação no `ProdutoService` que não deixa salvar se o número for negativo (dá um erro na tela em vermelho se tentar colocar tipo -5).
* **Tela do Admin (Auditoria):** Fiz uma página nova só para o Admin (`/produtos/admin/consulta`). Ela mostra os produtos que foram mexidos por último primeiro (ordenado por data mais recente). Além disso, se o estoque estiver menor que 5, a linha da tabela fica vermelha com um aviso de "Estoque Baixo".
* **Alertas de Sucesso com Horário:** Agora, sempre que você cadastra, edita ou exclui um produto, o sistema te joga de volta para a lista e mostra aquele alerta verde lá em cima falando que deu certo e o horário exato que aconteceu (ex: *Produto cadastrado com sucesso às 20:15:30*).
* **Trava nas URLs:** Arrumei o arquivo de segurança para o usuário comum não conseguir acessar as páginas do admin. Se ele tentar digitar `/produtos/admin/consulta` ou `/produtos/excluir/` direto no navegador, o Spring joga aquela tela de erro 403 (Acesso Negado).

---

## 🛠️ Tecnologias que usei

* **Java 17** e **Spring Boot 3**
* **Spring Data JPA** (para mexer com o banco sem sofrer com SQL)
* **Spring Security** (para a parte de login e permissões)
* **Thymeleaf** (para ligar o HTML com o Java)
* **Bootstrap 5** (para os estilos e tabelas)
* **PostgreSQL** (banco de dados)
* **DBeaver** (para olhar o banco)

---



## 🔧 Como rodar o projeto na sua máquina

### 1. Criar o banco de dados
Abra o seu PgAdmin e crie o banco com esse comando:
```sql
CREATE DATABASE catalogofinal;
```

Configurar o application.properties
Dica: Vá em src/main/resources/application.properties e veja se o seu usuário e senha do Postgres estão certos:

Properties
spring.datasource.url=jdbc:postgresql://localhost:5432/catalogofinal
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update

Abra o projeto no IntelliJ, espere o Maven baixar as coisas se for a primeira vez, abra a classe principal (CatalogoApplication) e clique no Play verde.
Depois é só abrir o navegador em: http://localhost:8080/produtos
## Usuários
* **Admin** : usuario: admin / senha: admin123
* **User**  : usuario: joao / senha: joao123
