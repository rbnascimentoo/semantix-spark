[![Build Status](https://travis-ci.com/rbnascimentoo/semantix-spark.svg?branch=master)](https://travis-ci.com/rbnascimentoo/semantix-spark)

# semantix-spark
Projeto Java Spark Engenheiro de Dados

Para rodar o projeto é necessário a instalação do lombok no eclipse, também necessário ter na pasta raiz do projeto os logs da NASA Kennedy encontrados nos links abaixo:

ftp://ita.ee.lbl.gov/traces/NASA_access_log_Jul95.gz - -
ftp://ita.ee.lbl.gov/traces/NASA_access_log_Aug95.gz

Algoritimo para responder algumas questões, tais como:

1. Número de hosts únicos.
2. O total de erros 404.
3. Os 5 URLs que mais causaram erro 404.
4. Quantidade de erros 404 por dia.
5. O total de bytes retornados.

R -
1 -
	NÚMERO DE HOSTS ÚNICOS PARA O ARQUIVO 'access_log_Aug95': 75060
	NÚMERO DE HOSTS ÚNICOS PARA O ARQUIVO 'access_log_Jul95': 81983
2 -
	O TOTAL DE ERROS 404 PARA O ARQUIVO 'access_log_Aug95': 9973
	O TOTAL DE ERROS 404 PARA O ARQUIVO 'access_log_Jul95': 10713
3 -
	
4 -
	
5 -
	O TOTAL DE BYTES RETORNADOS PARA O ARQUIVO 'access_log_Aug95' É: 26677328516
	O TOTAL DE BYTES RETORNADOS PARA O ARQUIVO 'access_log_Jul95' É: 38466412587	
	
	SEMANTIX

1 - Qual o objetivo do comando cache em Spark?

O comando cache faz com que um RDD mantenha-se na memória disponível para os clusters não sendo necessário reprocessar o mesmo tornando o processamento do nó mais rápido, sendo eficiente em casos em que se manipula o mesmo RDD para mais de uma operação.

2 - O mesmo código implementado em Spark é normalmente mais rápido que a implementação equivalente em MapReduce. Por quê?

O código implementado em Spark pode ser mais rápido pois o mesmo estende as funções do próprio MapReduce, seu desempenho é mais rápido pois armazena os dados em memória, caso a memoria ram não seja suficiente aí então se é utilizado o armazenamento em disco e seu processamento é quase em tempo real ao contrário do MapReduce que precisaria rodar vários jobs para execução de tarefas mais complexas e seu armazenamento é em disco.

3 - Qual é a função do SparkContext?

É um objeto central do Spark que auxilia as aplicações Spark em um cluster (nós), com ele podemos ter acesso ao gerenciador de clusters, podendo fazer com que todos os nós tenham acesso ao determinado arquivo por exemplo.

4 - Explique com suas palavras o que é Resilient Distributed Datasets (RDD).

São os objetos de manipulação do Spark, são objetos de apenas leitura, não podendo ser modificados, é possível apenas criar novos RDD's, os RDD's são tolerantes a falhas, seu processamento é todo em memória podendo ser operado em paralelo entre nós.

5 -GroupByKey é menos eficiente que reduceByKey em grandes dataset. Por quê?

Basicamento o GroupByKey realiza a leitura de todo o RDD embaralhando-os na rede e formando uma lista de chave e valor, isso pode ser prejudicial pois pode acabar alocando muita memória e acaba comprometendo o desempenho, enquanto o reduceByKey​ faz sua redução primeiro alcando menos memória e somente o resultado é embaralhado e reduzindo significafivamente o trafego de rede.

Explique o que o código Scala abaixo faz:

val textFile = sc.textFile("hdfs://...")
val counts = textFile.flatMap(line => line.split(" "))
.map(word => (word, 1))
.reduceByKey(_ + _)
counts.saveAsTextFile("hdfs://...")

Neste código, é lido um arquivo de texto a partir de um diretório especifico e atribuido a variável 'textFile' (linha 1). 
É feita a leitura do arquivo de texto e então é realizado a quebra de linha e/ou palavras por espaço (linha 2).
É gerado um map (objeto chave e valor) sendo chave a "word" e valor o "1" criando um tipo de RDD (linha 3).
E então é realizada a mescla dos valores criando um novo RDD (linha 4).
E por último o RDD é salvo como um arquivo tipo texto em um diretório específico (linha 5).
