package br.com.rnascimento.sematix.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * class RequisicaoModel
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RequisicaoModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -814043822690120710L;
	
	private String host;
	private String timestamp;
	private String requisicao;
	private String codigoRetornoHttp;
	private BigDecimal totalBytesRetornados;
	private String nomeFile;
	
}