package br.com.rnascimento.sematix;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import br.com.rnascimento.semantix.converter.RequisicaoModelConverter;
import br.com.rnascimento.sematix.model.RequisicaoModel;

public class App {

	private static final String ACCESS_LOG_JUL95 = "access_log_Jul95";
	private static final String ACCESS_LOG_AUG95 = "access_log_Aug95";
	private static final String BR_COM_RNASCIMENTO_SEMATIX_APP = "br.com.rnascimento.sematix.App";
	private static final String LOCAL = "local";
	private static final String NOT_FOUND_CODE = "404";

	public static void main(String[] args) {
		start();
	}

	/**
	 * Método inicial
	 * 
	 */
	private static void start() {
		// Configuração Spark
		SparkConf conf = new SparkConf().setMaster(LOCAL).setAppName(BR_COM_RNASCIMENTO_SEMATIX_APP);
		JavaSparkContext ctx = new JavaSparkContext(conf);

		JavaRDD<String> nasaLogAug95 = ctx.textFile(ACCESS_LOG_AUG95);
		nasaLogAug95 = nasaLogAug95.cache();
		JavaRDD<RequisicaoModel> requisicaoModelAug95 = nasaLogAug95.map(linhaTxt -> linhaTxt.split(" "))
				.map(obj -> RequisicaoModelConverter.montarRequisicaoModel(obj));
		requisicaoModelAug95 = requisicaoModelAug95.cache();

		JavaRDD<String> nasaLogAJul95 = ctx.textFile(ACCESS_LOG_JUL95);
		nasaLogAJul95 = nasaLogAJul95.cache();
		JavaRDD<RequisicaoModel> requisicaoModelJul95 = nasaLogAJul95.map(linhaTxt -> linhaTxt.split(" "))
				.map(obj -> RequisicaoModelConverter.montarRequisicaoModel(obj));
		requisicaoModelJul95 = requisicaoModelJul95.cache();

		obterNumeroHostUnicos(requisicaoModelAug95, requisicaoModelJul95);

		obterTotalCode404Aug(requisicaoModelAug95);	
		obterTotalCode404Jul(requisicaoModelJul95);

	}

	/**
	 * obtém o Total de Erros com o Código 404 para o arquivo access_log_Aug95
	 * 
	 * @param requisicaoModelAug95
	 * @return
	 */
	private static JavaRDD<RequisicaoModel> obterTotalCode404Aug(JavaRDD<RequisicaoModel> requisicaoModelAug95) {
		JavaRDD<RequisicaoModel> countAugCode404 = requisicaoModelAug95.filter(m -> verificarCodigo404(m)).cache();
		System.out.println("O TOTAL DE ERROS 404 PARA O ARQUIVO 'access_log_Aug95': " + countAugCode404.count());
		return countAugCode404;
	}

	/**
	 * obtém o Total de Erros com o Código 404 para o arquivo access_log_Jul95
	 * 
	 * @param requisicaoModelJul95
	 * @return
	 */
	private static JavaRDD<RequisicaoModel> obterTotalCode404Jul(JavaRDD<RequisicaoModel> requisicaoModelJul95) {
		JavaRDD<RequisicaoModel> countJulCode404 = requisicaoModelJul95.filter(m -> verificarCodigo404(m)).cache();
		System.out.println("O TOTAL DE ERROS 404 PARA O ARQUIVO 'access_log_Jul95': " + countJulCode404.count());
		return countJulCode404;
	}

	/**
	 * 
	 * @param requisicaoModel
	 * @return
	 */
	private static boolean verificarCodigo404(RequisicaoModel requisicaoModel) {
		if (NOT_FOUND_CODE.equals(requisicaoModel.getCodigoRetornoHttp()))
			return true;
		return false;
	}

	/**
	 * obtém o Numero de Host Únicos
	 * 
	 * @param requisicaoModelAug95
	 * @param requisicaoModelJul95
	 */
	private static void obterNumeroHostUnicos(JavaRDD<RequisicaoModel> requisicaoModelAug95,
			JavaRDD<RequisicaoModel> requisicaoModelJul95) {
		Long aug95 = requisicaoModelAug95.map(linhaTxt -> linhaTxt.getHost()).distinct().count();
		Long jul95 = requisicaoModelJul95.map(linhaTxt -> linhaTxt.getHost()).distinct().count();

		System.out.println("NÚMERO DE HOSTS ÚNICOS PARA O ARQUIVO 'access_log_Aug95': " + aug95);
		System.out.println("NÚMERO DE HOSTS ÚNICOS PARA O ARQUIVO 'access_log_Jul95': " + jul95);
	}

}
