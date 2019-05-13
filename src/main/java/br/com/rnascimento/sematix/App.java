package br.com.rnascimento.sematix;

import java.math.BigDecimal;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;

import br.com.rnascimento.semantix.converter.RequisicaoModelConverter;
import br.com.rnascimento.sematix.model.RequisicaoModel;

/**
 * 
 * class App
 *
 */
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
	 * Método Inicial da App
	 * 
	 */
	private static void start() {
		SparkConf conf = new SparkConf().setMaster(LOCAL).setAppName(BR_COM_RNASCIMENTO_SEMATIX_APP);
		JavaSparkContext ctx = new JavaSparkContext(conf);

		JavaRDD<String> rddAug95 = ctx.textFile(ACCESS_LOG_AUG95);
		JavaRDD<RequisicaoModel> rddRequisicaoModelAug95 = rddAug95.map(linhaTxt -> linhaTxt.split(" "))
				.map(obj -> RequisicaoModelConverter.montarRequisicaoModel(obj));
		rddRequisicaoModelAug95 = rddRequisicaoModelAug95.cache();

		JavaRDD<String> rddJul95 = ctx.textFile(ACCESS_LOG_JUL95);
		JavaRDD<RequisicaoModel> rddRequisicaoModelJul95 = rddJul95.map(linhaTxt -> linhaTxt.split(" "))
				.map(obj -> RequisicaoModelConverter.montarRequisicaoModel(obj));
		rddRequisicaoModelJul95 = rddRequisicaoModelJul95.cache();

		obterNumeroHostUnico(rddRequisicaoModelAug95, ACCESS_LOG_AUG95);
		obterNumeroHostUnico(rddRequisicaoModelJul95, ACCESS_LOG_JUL95);

		JavaRDD<RequisicaoModel> rddRequisicaoModelAug404 = obterTotalErroCode404(rddRequisicaoModelAug95, ACCESS_LOG_AUG95);
		JavaRDD<RequisicaoModel> rddRequisicaoModelJul404 = obterTotalErroCode404(rddRequisicaoModelJul95, ACCESS_LOG_JUL95);
		
		obterTop5URLCode404(rddRequisicaoModelAug404, ACCESS_LOG_AUG95);
		obterTop5URLCode404(rddRequisicaoModelJul404, ACCESS_LOG_JUL95);
		
		obterQtdErroDia(rddRequisicaoModelAug404, ACCESS_LOG_AUG95);
		obterQtdErroDia(rddRequisicaoModelJul404, ACCESS_LOG_JUL95);
		
		obterTotalBytesRetornado(rddRequisicaoModelAug95, ACCESS_LOG_AUG95);
		obterTotalBytesRetornado(rddRequisicaoModelJul95, ACCESS_LOG_JUL95);
		
		ctx.close();
	}

	/**
	 * Obtém o Top 5 de URL's com o Codigo 404
	 * 
	 * @param rddRequisicaoModel
	 * @param nomeArquivo
	 */
	private static void obterTop5URLCode404(JavaRDD<RequisicaoModel> rddRequisicaoModel, String nomeArquivo) {
		
	}

	/**
	 * Obtém Quatidade de Erros por Dia
	 * 
	 * @param rddRequisicaoModel
	 * @param nomeArquivo
	 */
	private static void obterQtdErroDia(JavaRDD<RequisicaoModel> rddRequisicaoModel, String nomeArquivo) {
		
	}

	/**
	 * Obtém o Total de Bytes Retornado por Requisições
	 * 
	 * @param rddRequisicaoModel
	 * @param nomeArquivo
	 */
	private static void obterTotalBytesRetornado(JavaRDD<RequisicaoModel> rddRequisicaoModel, String nomeArquivo) {
		BigDecimal totalBytesRetornados = rddRequisicaoModel.map(m -> m.getTotalBytesRetornados()).reduce(new Function2<BigDecimal, BigDecimal, BigDecimal>() {
			/** * */
			private static final long serialVersionUID = -7194241335079993478L;

			@Override
			public BigDecimal call(BigDecimal v1, BigDecimal v2) throws Exception {
				
				if(v1 == null) {
					v1 = new BigDecimal(0);
				}
				
				if(v2 == null) {
					v2 = new BigDecimal(0);
				}
				
				return v1.add(v2);
			}
		});
		
		System.out.println("O TOTAL DE BYTES RETORNADOS PARA O ARQUIVO '" + nomeArquivo + "' É: " + totalBytesRetornados);
	}

	/**
	 * Obtém o Total de Erros com o Código 404
	 * 
	 * @param rddRequisicaoModel
	 * @return countCode404
	 */
	private static JavaRDD<RequisicaoModel> obterTotalErroCode404(JavaRDD<RequisicaoModel> rddRequisicaoModel, String nomeArquivo) {
		JavaRDD<RequisicaoModel> countCode404 = rddRequisicaoModel.filter(m -> verificarCodigo404(m)).cache();
		System.out.println("O TOTAL DE ERROS 404 PARA O ARQUIVO '" + nomeArquivo + "' " + countCode404.count());
		return countCode404;
	}

	/**
	 * Verifica se o Código é 404 
	 * 
	 * @param requisicaoModel
	 * @return boolean
	 */
	private static boolean verificarCodigo404(RequisicaoModel requisicaoModel) {
		if (NOT_FOUND_CODE.equals(requisicaoModel.getCodigoRetornoHttp()))
			return true;
		return false;
	}

	/**
	 * Obtém o Número de Host Únicos
	 * 
	 * @param rddRequisicaoModel
	 * @return nomeArquivo
	 */
	private static void obterNumeroHostUnico(JavaRDD<RequisicaoModel> rddRequisicaoModel, String nomeArquivo) {
		Long count = rddRequisicaoModel.map(linhaTxt -> linhaTxt.getHost()).distinct().count();
		System.out.println("NÚMERO DE HOSTS ÚNICOS PARA O ARQUIVO '" + nomeArquivo + "' " + count);
	}

}
