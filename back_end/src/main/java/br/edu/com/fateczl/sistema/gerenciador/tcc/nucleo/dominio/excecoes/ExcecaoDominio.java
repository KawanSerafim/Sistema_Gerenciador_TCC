package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes;

public class ExcecaoDominio extends RuntimeException {
    private final CodigoErro codigoErro;

    public ExcecaoDominio(CodigoErro codigoErro, String mensagemLog) {
        this(codigoErro, mensagemLog, null);
    }

    public ExcecaoDominio(
            CodigoErro codigoErro,
            String mensagemLog,
            Throwable causa
    ) {
        super(mensagemLog, causa);
        this.codigoErro = codigoErro;
    }

    public CodigoErro pegarCodigoErro() {
        return codigoErro;
    }
}

/*
 * =============================================================================
 * GUIA DE EXCEÇÕES DE DOMÍNIO (CodigoErro + MensagemLog)
 * =============================================================================
 *
 * Este guia define o "Contrato" e a "Mensagem de Log" para cada
 * exceção de negócio.
 *
 * 1. CodigoErro:
 * - É o código enviado à camada de API/Front-end (ex: "VD_001").
 * - É estável, seguro e usado para internacionalização (i18n).
 *
 * 2. Mensagem de Log:
 * - É a mensagem gravada no log do servidor (via ex.getMessage()).
 * - DEVE conter contexto (IDs, valores) para facilitar a depuração.
 * - NUNCA deve ser enviada ao front-end.
 *
 * Legenda de Prefixos de CodigoErro:
 * - VD: Validação (Formato, obrigatoriedade, tipo de dados).
 * - RN: Regra de Negócio (Lógica de domínio, estados, duplicidade).
 * - AU: Autorização (Permissões de usuário).
 * - GN: Genérico (Erros comuns, ex: não encontrado).
 *
 * =============================================================================
 * VALIDAÇÃO - VD
 * =============================================================================
 *
 * 001 - Campo obrigatório.
 * -------------------
 * CodigoErro: VD_001_CAMPO_OBRIGATORIO
 * Mensagem: [Entidade] (ID: [ID_Entidade]): Validação falhou. O campo
 * obrigatório '[nome_campo]' estava nulo ou vazio.
 *
 * -----------------------------------------------------------------------------
 *
 * 002 - Formato inválido.
 * -------------------
 * CodigoErro: VD_002_FORMATO_INVALIDO
 * Mensagem: [Entidade] (ID: [ID_Entidade]): O campo '[nome_campo]'
 * possui formato inválido. (ValorRecebido: '[valor_invalido]')
 *
 * -----------------------------------------------------------------------------
 *
 * 003 - Relação obrigatória.
 * -------------------
 * CodigoErro: VD_003_ASSOCIACAO_OBRIGATORIA
 * Mensagem: [Entidade A] (ID: [ID_Entidade_A]): Associação obrigatória
 * ausente. Deve estar associado a um(a) '[Entidade B]'.
 *
 * -----------------------------------------------------------------------------
 *
 * 004 - Validação de Data.
 * -------------------
 * CodigoErro: VD_004_DATA_INVALIDA
 * Mensagem: [Entidade] (ID: [ID]): Validação de data falhou p/ o campo
 * '[Nome_Campo_Data]'. (Valor: '[Valor]', Condição: '[Motivo]')
 *
 * -----------------------------------------------------------------------------
 *
 * 005 - Diferente do padrão.
 * -------------------
 * CodigoErro: VD_005_PADRAO_INVALIDO
 * Mensagem: [Entidade] (ID: [ID]): O campo '[nome_campo]' não segue o
 * padrão esperado. (Valor: '[valor]', Padrão: '[padrão]')
 *
 * -----------------------------------------------------------------------------
 *
 * 006 - Validação de Coleção.
 * -------------------
 * CodigoErro: VD_006_COLECAO_OBRIGATORIA_VAZIA
 * Mensagem: [Entidade] (ID: [ID]): Validação de coleção falhou. A
 * entidade deve ter pelo menos um(a) '[Nome_Item]' associado.
 *
 * -----------------------------------------------------------------------------
 *
 * 007 - Campo não suportado.
 * -------------------
 * CodigoErro: VD_007_CAMPO_NAO_SUPORTADO
 * Mensagem: [Entidade] (Contexto: [ID/Ação]): O campo
 * '[nome_campo_invalido]' não é suportado [motivo/contexto].
 *
 * -----------------------------------------------------------------------------
 *
 * 008 - Arquivo inválido.
 * -------------------
 * CodigoErro: VD_008_ARQUIVO_INVALIDO
 * Mensagem: Leitor de Arquivo: Falha ao processar o arquivo
 * ['Tipo_Arquivo']: [Mensagem_Exceção]
 *
 *
 * =============================================================================
 * REGRAS DE NEGÓCIO - RN
 * =============================================================================
 *
 * 001 - Estado inválido.
 * -------------------
 * CodigoErro: RN_001_ESTADO_INVALIDO_PARA_ACAO
 * Mensagem: [Entidade] (ID: [ID]): Ação '[Ação]' falhou devido a estado
 * inválido. (EstadoAtual: '[Atual]', Esperado: '[Esperado]')
 *
 * -----------------------------------------------------------------------------
 *
 * 002 - Duplicidade.
 * -------------------
 * CodigoErro: RN_002_REGISTRO_DUPLICADO
 * Mensagem: [Entidade]: Falha de unicidade. Já existe um registro
 * com [nome_campo_unico] = '[valor_duplicado]'.
 *
 * -----------------------------------------------------------------------------
 *
 * 003 - Ação impossível com campo nulo.
 * -------------------
 * CodigoErro: RN_003_CONDICAO_ACAO_NAO_ATENDIDA
 * Mensagem: [Entidade] (ID: [ID]): Ação '[Ação]' não pode ser executada,
 * pois o campo obrigatório '[nome_campo_nulo]' está nulo.
 *
 *
 * =============================================================================
 * AUTORIZAÇÃO - AU
 * =============================================================================
 *
 * 001 - Sem permissão.
 * -------------------
 * CodigoErro: AU_001_PERMISSAO_NEGADA
 * Mensagem: Autorização: Permissão negada para Usuário (ID: [ID_Usuario]).
 * (Ação: '[Ação_Tentada]', Recurso: '[Entidade_Alvo](ID:[ID])')
 *
 * -----------------------------------------------------------------------------
 *
 * 002 - Credenciais inválidas.
 * -------------------
 * CodigoErro: AU_002_CREDENCIAS_INVALIDAS
 * Mensagem: Autenticação: Falha na tentativa de login para o usuário
 * [email_tentado]. (Motivo: Credenciais inválidas, IP: [IP_Address])
 *
 * -----------------------------------------------------------------------------
 *
 * 003 - Conta inválida.
 * -------------------
 * CodigoErro: AU_003_CONTA_INVALIDA
 * Mensagem: Autenticação: Falha na tentativa de login para o usuário
 * [email_tentado]. (Motivo: Conta de estádo inválido, IP: [IP_Address])
 *
 * =============================================================================
 * GENÉRICO - GN
 * =============================================================================
 *
 * 001 - Não encontrado
 * -------------------
 * CodigoErro: GN_001_REGISTRO_NAO_ENCONTRADO
 * Mensagem: [Entidade]: Não encontrado. Nenhuma entidade localizada
 * com o critério: [campo_busca] = '[valor_busca]'.
 *
 * =============================================================================
 */