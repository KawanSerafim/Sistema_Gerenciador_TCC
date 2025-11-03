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
 * 1. CodigoErro (Contrato):
 * - É o código enviado à camada de API/Front-end (ex: "VD_001").
 * - É estável, seguro e usado para internacionalização (i18n).
 *
 * 2. Mensagem de Log (Depuração):
 * - É a mensagem gravada no log do servidor (via ex.getMessage()).
 * - DEVE conter contexto (IDs, valores) para facilitar a depuração.
 * - NUNCA deve ser enviada ao front-end.
 *
 * Legenda de Prefixos de CodigoErro:
 * - VD: Validação (Formato, obrigatoriedade, tipo de dados)
 * - RN: Regra de Negócio (Lógica de domínio, estados, duplicidade)
 * - AU: Autorização (Permissões de usuário)
 * - GN: Genérico (Erros comuns, ex: não encontrado)
 *
 * -----------------------------------------------------------------------------
 *
 * 1. Campo obrigatório
 * -------------------
 * CodigoErro: VD_001_CAMPO_OBRIGATORIO
 * Mensagem (Log): [Entidade] (ID: [ID_Entidade]): Validação falhou. O campo
 * obrigatório '[nome_campo]' estava nulo ou vazio.
 * Exemplo (Log):  Cliente (ID: 123): Validação falhou. O campo
 * obrigatório 'Nome' estava nulo ou vazio.
 *
 * -----------------------------------------------------------------------------
 *
 * 2. Formato inválido
 * -------------------
 * CodigoErro: VD_002_FORMATO_INVALIDO
 * Mensagem (Log): [Entidade] (ID: [ID_Entidade]): O campo '[nome_campo]'
 * possui formato inválido. (ValorRecebido: '[valor_invalido]')
 * Exemplo (Log):  Cliente (ID: 123): O campo 'Email' possui formato
 * inválido. (ValorRecebido: 'usuario-sem-arroba.com')
 *
 * -----------------------------------------------------------------------------
 *
 * 3. Relação obrigatória (Associação Ausente)
 * -------------------
 * CodigoErro: VD_003_ASSOCIACAO_OBRIGATORIA
 * Mensagem (Log): [Entidade A] (ID: [ID_Entidade_A]): Associação obrigatória
 * ausente. Deve estar associado a um(a) '[Entidade B]'.
 * Exemplo (Log):  Pedido (ID: 456): Associação obrigatória ausente.
 * Deve estar associado a um 'Cliente'.
 *
 * -----------------------------------------------------------------------------
 *
 * 4. Não encontrado
 * -------------------
 * CodigoErro: GN_001_REGISTRO_NAO_ENCONTRADO
 * Mensagem (Log): [Entidade]: Não encontrado. Nenhuma entidade localizada
 * com o critério: [campo_busca] = '[valor_busca]'.
 * Exemplo (Log): Produto: Não encontrado. Nenhuma entidade localizada
 * com o critério: SKU = 'ABC-999-XYZ'.
 *
 * -----------------------------------------------------------------------------
 *
 * 5. Sem permissão (Autorização)
 * -------------------
 * CodigoErro: AU_001_PERMISSAO_NEGADA
 * Mensagem (Log): Autorização: Permissão negada para Usuário (ID: [ID_Usuario]).
 * (Ação: '[Ação_Tentada]', Recurso: '[Entidade_Alvo](ID:[ID])')
 * Exemplo (Log): Autorização: Permissão negada para Usuário (ID: 789).
 * (Ação: 'ExcluirPedido', Recurso: 'Pedido(ID:456)')
 *
 * -----------------------------------------------------------------------------
 *
 * 6. Estado inválido
 * -------------------
 * CodigoErro: RN_001_ESTADO_INVALIDO_PARA_ACAO
 * Mensagem (Log): [Entidade] (ID: [ID]): Ação '[Ação]' falhou devido a estado
 * inválido. (EstadoAtual: '[Atual]', Esperado: '[Esperado]')
 * Exemplo (Log): Pedido (ID: 456): Ação 'Cancelar' falhou devido a estado
 * inválido. (EstadoAtual: 'ENVIADO', Esperado: 'PENDENTE')
 *
 * -----------------------------------------------------------------------------
 *
 * 7. Validação de Data/Tempo (ex: Data no passado)
 * -------------------
 * CodigoErro: VD_004_DATA_INVALIDA
 * Mensagem (Log): [Entidade] (ID: [ID]): Validação de data falhou p/ o campo
 * '[Nome_Campo_Data]'. (Valor: '[Valor]', Condição: '[Motivo]')
 * Exemplo (Log):  Agendamento (ID: 777): Validação de data falhou p/ o campo
 * 'DataInicio'. (Valor: '2025-10-20', Condição: 'no passado')
 *
 * -----------------------------------------------------------------------------
 *
 * 8. Diferente do padrão (Validação de Formato Específico)
 * -------------------
 * CodigoErro: VD_005_PADRAO_INVALIDO
 * Mensagem (Log): [Entidade] (ID: [ID]): O campo '[nome_campo]' não segue o
 * padrão esperado. (Valor: '[valor]', Padrão: '[padrão]')
 * Exemplo (Log): Produto (ID: 123): O campo 'SKU' não segue o padrão
 * esperado. (Valor: 'SKU-ERRADO', Padrão: 'XXX-NNN')
 *
 * -----------------------------------------------------------------------------
 *
 * 9. Duplicidade (Violação de Unicidade / Já cadastrado)
 * -------------------
 * CodigoErro: RN_002_REGISTRO_DUPLICADO
 * Mensagem (Log): [Entidade]: Falha de unicidade. Já existe um registro
 * com [nome_campo_unico] = '[valor_duplicado]'.
 * Exemplo (Log): Cliente: Falha de unicidade. Já existe um registro
 * com CPF = '111.222.333-44'.
 *
 * -----------------------------------------------------------------------------
 *
 * 10. Ação impossível com campo nulo
 * -------------------
 * CodigoErro: RN_003_CONDICAO_ACAO_NAO_ATENDIDA
 * Mensagem (Log): [Entidade] (ID: [ID]): Ação '[Ação]' não pode ser executada,
 * pois o campo obrigatório '[nome_campo_nulo]' está nulo.
 * Exemplo (Log): Pedido (ID: 456): Ação 'Enviar' não pode ser executada,
 * pois o campo obrigatório 'EnderecoEntrega' está nulo.
 *
 * -----------------------------------------------------------------------------
 *
 * 11. Validação de Coleção (Pelo menos um item)
 * -------------------
 * CodigoErro: VD_006_COLECAO_OBRIGATORIA_VAZIA
 * Mensagem (Log): [Entidade] (ID: [ID]): Validação de coleção falhou. A
 * entidade deve ter pelo menos um(a) '[Nome_Item]' associado.
 * Exemplo (Log):  Pedido (ID: 456): Validação de coleção falhou. A
 * entidade deve ter pelo menos um 'ItemPedido' associado.
 *
 * ------------------------------------------------------------------------------
 *
 * 12. Campo não suportado
 * -------------------
 * ErrorCode: VD_007_CAMPO_NAO_SUPORTADO
 * Mensagem (Log): [Entidade] (Contexto: [ID/Ação]): O campo
 * '[nome_campo_invalido]' não é suportado [motivo/contexto].
 * Exemplo (Log):  Pedido (Contexto: Ordenação): O campo 'nomeCliente'
 * não é suportado.
 *
 * =============================================================================
 */