/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.7
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package knu;

public class ReceitaCNPJ {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected ReceitaCNPJ(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ReceitaCNPJ obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        knuJNI.delete_ReceitaCNPJ(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setCod_erro(int value) {
    knuJNI.ReceitaCNPJ_cod_erro_set(swigCPtr, this, value);
  }

  public int getCod_erro() {
    return knuJNI.ReceitaCNPJ_cod_erro_get(swigCPtr, this);
  }

  public void setTam_atividades_secundarias(int value) {
    knuJNI.ReceitaCNPJ_tam_atividades_secundarias_set(swigCPtr, this, value);
  }

  public int getTam_atividades_secundarias() {
    return knuJNI.ReceitaCNPJ_tam_atividades_secundarias_get(swigCPtr, this);
  }

  public void setAprovado_por(String value) {
    knuJNI.ReceitaCNPJ_aprovado_por_set(swigCPtr, this, value);
  }

  public String getAprovado_por() {
    return knuJNI.ReceitaCNPJ_aprovado_por_get(swigCPtr, this);
  }

  public void setAtividade_principal(String value) {
    knuJNI.ReceitaCNPJ_atividade_principal_set(swigCPtr, this, value);
  }

  public String getAtividade_principal() {
    return knuJNI.ReceitaCNPJ_atividade_principal_get(swigCPtr, this);
  }

  public void setBairro(String value) {
    knuJNI.ReceitaCNPJ_bairro_set(swigCPtr, this, value);
  }

  public String getBairro() {
    return knuJNI.ReceitaCNPJ_bairro_get(swigCPtr, this);
  }

  public void setCep(String value) {
    knuJNI.ReceitaCNPJ_cep_set(swigCPtr, this, value);
  }

  public String getCep() {
    return knuJNI.ReceitaCNPJ_cep_get(swigCPtr, this);
  }

  public void setComplemento(String value) {
    knuJNI.ReceitaCNPJ_complemento_set(swigCPtr, this, value);
  }

  public String getComplemento() {
    return knuJNI.ReceitaCNPJ_complemento_get(swigCPtr, this);
  }

  public void setData_abertura(String value) {
    knuJNI.ReceitaCNPJ_data_abertura_set(swigCPtr, this, value);
  }

  public String getData_abertura() {
    return knuJNI.ReceitaCNPJ_data_abertura_get(swigCPtr, this);
  }

  public void setData_emissao(String value) {
    knuJNI.ReceitaCNPJ_data_emissao_set(swigCPtr, this, value);
  }

  public String getData_emissao() {
    return knuJNI.ReceitaCNPJ_data_emissao_get(swigCPtr, this);
  }

  public void setData_situacao_cadastral(String value) {
    knuJNI.ReceitaCNPJ_data_situacao_cadastral_set(swigCPtr, this, value);
  }

  public String getData_situacao_cadastral() {
    return knuJNI.ReceitaCNPJ_data_situacao_cadastral_get(swigCPtr, this);
  }

  public void setData_situacao_especial(String value) {
    knuJNI.ReceitaCNPJ_data_situacao_especial_set(swigCPtr, this, value);
  }

  public String getData_situacao_especial() {
    return knuJNI.ReceitaCNPJ_data_situacao_especial_get(swigCPtr, this);
  }

  public void setDesc_erro(String value) {
    knuJNI.ReceitaCNPJ_desc_erro_set(swigCPtr, this, value);
  }

  public String getDesc_erro() {
    return knuJNI.ReceitaCNPJ_desc_erro_get(swigCPtr, this);
  }

  public void setLogradouro(String value) {
    knuJNI.ReceitaCNPJ_logradouro_set(swigCPtr, this, value);
  }

  public String getLogradouro() {
    return knuJNI.ReceitaCNPJ_logradouro_get(swigCPtr, this);
  }

  public void setMotivo_situacao_cadastral(String value) {
    knuJNI.ReceitaCNPJ_motivo_situacao_cadastral_set(swigCPtr, this, value);
  }

  public String getMotivo_situacao_cadastral() {
    return knuJNI.ReceitaCNPJ_motivo_situacao_cadastral_get(swigCPtr, this);
  }

  public void setMunicipio(String value) {
    knuJNI.ReceitaCNPJ_municipio_set(swigCPtr, this, value);
  }

  public String getMunicipio() {
    return knuJNI.ReceitaCNPJ_municipio_get(swigCPtr, this);
  }

  public void setNatureza_juridica(String value) {
    knuJNI.ReceitaCNPJ_natureza_juridica_set(swigCPtr, this, value);
  }

  public String getNatureza_juridica() {
    return knuJNI.ReceitaCNPJ_natureza_juridica_get(swigCPtr, this);
  }

  public void setNome_empresarial(String value) {
    knuJNI.ReceitaCNPJ_nome_empresarial_set(swigCPtr, this, value);
  }

  public String getNome_empresarial() {
    return knuJNI.ReceitaCNPJ_nome_empresarial_get(swigCPtr, this);
  }

  public void setNumero(String value) {
    knuJNI.ReceitaCNPJ_numero_set(swigCPtr, this, value);
  }

  public String getNumero() {
    return knuJNI.ReceitaCNPJ_numero_get(swigCPtr, this);
  }

  public void setNumero_inscricao(String value) {
    knuJNI.ReceitaCNPJ_numero_inscricao_set(swigCPtr, this, value);
  }

  public String getNumero_inscricao() {
    return knuJNI.ReceitaCNPJ_numero_inscricao_get(swigCPtr, this);
  }

  public void setSituacao_cadastral(String value) {
    knuJNI.ReceitaCNPJ_situacao_cadastral_set(swigCPtr, this, value);
  }

  public String getSituacao_cadastral() {
    return knuJNI.ReceitaCNPJ_situacao_cadastral_get(swigCPtr, this);
  }

  public void setSituacao_especial(String value) {
    knuJNI.ReceitaCNPJ_situacao_especial_set(swigCPtr, this, value);
  }

  public String getSituacao_especial() {
    return knuJNI.ReceitaCNPJ_situacao_especial_get(swigCPtr, this);
  }

  public void setTitulo_estabelecimento(String value) {
    knuJNI.ReceitaCNPJ_titulo_estabelecimento_set(swigCPtr, this, value);
  }

  public String getTitulo_estabelecimento() {
    return knuJNI.ReceitaCNPJ_titulo_estabelecimento_get(swigCPtr, this);
  }

  public void setUf(String value) {
    knuJNI.ReceitaCNPJ_uf_set(swigCPtr, this, value);
  }

  public String getUf() {
    return knuJNI.ReceitaCNPJ_uf_get(swigCPtr, this);
  }

  public void setHtml(String value) {
    knuJNI.ReceitaCNPJ_html_set(swigCPtr, this, value);
  }

  public String getHtml() {
    return knuJNI.ReceitaCNPJ_html_get(swigCPtr, this);
  }

  public String getAtividadeSecundaria(int index) {
    return knuJNI.ReceitaCNPJ_getAtividadeSecundaria(swigCPtr, this, index);
  }

  public ReceitaCNPJ() {
    this(knuJNI.new_ReceitaCNPJ(), true);
  }

}
