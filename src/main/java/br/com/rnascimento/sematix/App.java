package br.com.rnascimento.sematix;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import br.com.rnascimento.sematix.model.RequisicaoModel;

public class App {
	
	public static void main(String[] args) {
		//Configuração Spark
		SparkConf conf = new SparkConf().setMaster("local").setAppName("br.com.rnascimento.sematix.App");
        JavaSparkContext ctx = new JavaSparkContext(conf);
        SQLContext sctx = new SQLContext(ctx);
        
        JavaRDD<String> nasaLogAug95 = ctx.textFile("access_log_Aug95");
//        JavaRDD<String> nasaLogJul95 = ctx.textFile("access_log_Jul95");
        
        JavaRDD<RequisicaoModel> requisicaoModelAug95 = nasaLogAug95
        		.map(linhaTxt -> linhaTxt.split(" "))
        		.map(obj -> montarRequisicaoModel(obj));
        
        // Cria o DataFrame
        Dataset<Row> requisicaoModelAug95DS = sctx.createDataFrame(requisicaoModelAug95, RequisicaoModel.class);

        requisicaoModelAug95DS.show();
        
	}

	private static RequisicaoModel montarRequisicaoModel(String[] Object) {
		try {
			if(Object == null) {
				return new RequisicaoModel();
			}
			
			return RequisicaoModel.builder()
					.host(Object[0])
					.timestamp(Object[3] + StringUtils.SPACE + Object[4])
					.requisicao(Object[5] + StringUtils.SPACE + Object[7])
					.codigoRetornoHttp(Object[8])
					.totalBytesRetornados(Object[9]).build();
			
		} catch (Exception e) {
			System.out.println("erro: " + Object);
			return null;
		}
	}
}
