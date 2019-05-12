package br.com.rnascimento.sematix.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequisicaoModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4387486250847943964L;
	
	private String host;
	private String timestamp;
	private String requisicao;
	private String codigoRetornoHttp;
	private String totalBytesRetornados;	
	
}