package br.com.rnascimento.semantix.converter;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import br.com.rnascimento.sematix.model.RequisicaoModel;

public class RequisicaoModelConverter {
	
	/**
	 * Monta o Objeto de Requisicao Model
	 * 
	 * @param object
	 * @return
	 */
	public static RequisicaoModel montarRequisicaoModel(String[] object) {
		if (object == null) {
			return new RequisicaoModel();
		}

		RequisicaoModel requisicaoModelRetorno = new RequisicaoModel();
		for (int i = 0; i < object.length; i++) {
			switch (i) {
			case 0:
				requisicaoModelRetorno.setHost(isNull(object, i));
				break;
			case 3:
				requisicaoModelRetorno.setTimestamp(isNull(object, i) + StringUtils.SPACE);
				break;
			case 4:
				requisicaoModelRetorno.setTimestamp(requisicaoModelRetorno.getTimestamp() + isNull(object, i));
				break;
			case 5:
				requisicaoModelRetorno.setRequisicao(isNull(object, i) + StringUtils.SPACE);
				break;
			case 6:
				requisicaoModelRetorno
						.setRequisicao(requisicaoModelRetorno.getRequisicao() + isNull(object, i) + StringUtils.SPACE);
				break;
			case 7:
				requisicaoModelRetorno.setRequisicao(requisicaoModelRetorno.getRequisicao() + isNull(object, i));
				break;
			case 8:
				requisicaoModelRetorno.setCodigoRetornoHttp(isNull(object, i));
				break;
			case 9:
				requisicaoModelRetorno.setTotalBytesRetornados(transformarValor(isNull(object, i)));
				break;
			default:
				break;
			}
		}
		return requisicaoModelRetorno;
	}
	
	/**
	 * 
	 * 
	 * @param object
	 * @param i
	 * @return
	 */
	private static String isNull(String[] object, int i) {
		return object[i] != null ? object[i] : null;
	}
	
	/**
	 * verifica se a string passada é um número
	 * 
	 * @param valor
	 * @return
	 */
	private static BigDecimal transformarValor(String valor) {
		if (NumberUtils.isCreatable(valor)) {
			return new BigDecimal(valor);
		}
		return BigDecimal.ZERO;
	}
}
