package compilador;

public enum TokenType {
    T_PROGRAMA,
    T_INICIO,
    T_FIM,
    T_SE,
    T_ENTAO,
    T_SENAO,
    T_FIMSE,
    T_ENQUANTO,
    T_COMENTARIO,
    T_QUEBRA_LINHA,
    T_FIMENQUANTO,
    T_LEIA,
    T_ESCREVA,
    T_MAIS,
    T_MENOS,
    T_VEZES,
    T_DIV,
    T_MAIOR,
    T_MENOR,
    T_IGUAL,
    T_E,
    T_OU,
    T_NAO,
    T_ATRIB,
    T_ABRE,
    T_FECHA,
    T_INTEIRO,
    T_LOGICO,
    T_V,
    T_F,
    T_FACA,
    T_IDENTIF,
    T_NUMERO,
    T_LITERAL,
    T_FIMPROGRAMA,
    T_INVALIDO;

    public String getType() {
        if (this == T_INVALIDO) {
            return "<Invalido>";
        }
        return name().substring(2);
    }
}
