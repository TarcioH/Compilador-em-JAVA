import compilador.Token;
import compilador.TokenType;

import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int currentTokenIndex;
    private Token currentToken;
    private int variableCount;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
        this.currentToken = tokens.get(0);
        this.variableCount = 0;
    }

public void parse() {
    programa();
    consume(TokenType.T_FIMPROGRAMA);
}



private void programa() {
    consume(TokenType.T_PROGRAMA);
    consume(TokenType.T_IDENTIF);
    declaraVariaveis();
    consume(TokenType.T_INICIO);
    corpoPrograma();
}


   private void declaraVariaveis() {
    while (currentToken.getType() == TokenType.T_INTEIRO || currentToken.getType() == TokenType.T_LOGICO) {
        tipoVariavel();
        listaVariaveis();

        if (variableCount > 10) {
            throw new RuntimeException("Error: Exceeded maximum variable count");
        }
    }
}

private void tipoVariavel() {
    if (currentToken.getType() == TokenType.T_INTEIRO) {
        consume(TokenType.T_INTEIRO);
    } else if (currentToken.getType() == TokenType.T_LOGICO) {
        consume(TokenType.T_LOGICO);
    } else {
        throw new RuntimeException("Error: Expected variable type (inteiro or logico), found: " + currentToken.getType());
    }
}

private void listaVariaveis() {
    while (currentToken.getType() == TokenType.T_IDENTIF) {
        consume(TokenType.T_IDENTIF);
        variableCount++;
    }
}


private void corpoPrograma() {
    consume(TokenType.T_INICIO);
    while (currentToken.getType() != TokenType.T_FIMPROGRAMA) {
        if (currentToken.getType() == TokenType.T_LEIA) {
            comandoLeitura();
        } else if (currentToken.getType() == TokenType.T_ESCREVA) {
            comandoEscrita();
        } else if (currentToken.getType() == TokenType.T_IDENTIF) {
            comandoAtribuicao();
        } else if (currentToken.getType() == TokenType.T_SE) {
            comandoCondicional();
        } else if (currentToken.getType() == TokenType.T_ENQUANTO) {
            comandoLaco();
        } else {
            throw new RuntimeException("Error: Invalid statement");
        }
    }
    consume(TokenType.T_FIMPROGRAMA); // Avançar para o próximo token após o loop
}




    private void comandoLeitura() {
        consume(TokenType.T_LEIA);
        consume(TokenType.T_ABRE);
        consume(TokenType.T_IDENTIF);
        consume(TokenType.T_FECHA);
    }

    private void comandoEscrita() {
        consume(TokenType.T_ESCREVA);
        consume(TokenType.T_ABRE);
        expressao();
        consume(TokenType.T_FECHA);
    }

    private void comandoAtribuicao() {
    consume(TokenType.T_IDENTIF);
    consume(TokenType.T_ATRIB);
    expressao();
}


    private void comandoCondicional() {
        consume(TokenType.T_SE);
        expressao();
        consume(TokenType.T_ENTAO);
        corpoPrograma();
        if (currentToken.getType() == TokenType.T_SENAO) {
            consume(TokenType.T_SENAO);
            corpoPrograma();
        }
        consume(TokenType.T_FIMSE);
    }

    private void comandoLaco() {
    consume(TokenType.T_ENQUANTO);
    expressao();
    consume(TokenType.T_FACA);
    corpoPrograma();
    consume(TokenType.T_FIMSE); // Consumir o token T_FIMSE após o corpo do laço
}


    private void expressao() {
        expressaoSimples();
        if (isRelationalOperator()) {
            consume(currentToken.getType());
            expressaoSimples();
        }
    }

    private void expressaoSimples() {
        termo();
        while (isAdditiveOperator()) {
            consume(currentToken.getType());
            termo();
        }
    }

    private void termo() {
        fator();
        while (isMultiplicativeOperator()) {
            consume(currentToken.getType());
            fator();
        }
    }

    private void fator() {
        if (currentToken.getType() == TokenType.T_IDENTIF) {
            consume(TokenType.T_IDENTIF);
        } else if (currentToken.getType() == TokenType.T_NUMERO) {
            consume(TokenType.T_NUMERO);
        } else if (currentToken.getType() == TokenType.T_ABRE) {
            consume(TokenType.T_ABRE);
            expressao();
            consume(TokenType.T_FECHA);
        } else {
            throw new RuntimeException("Error: Invalid factor");
        }
    }

    private boolean isRelationalOperator() {
        TokenType type = currentToken.getType();
        return type == TokenType.T_MAIOR || type == TokenType.T_MENOR || type == TokenType.T_IGUAL;
    }

    private boolean isAdditiveOperator() {
        TokenType type = currentToken.getType();
        return type == TokenType.T_MAIS || type == TokenType.T_MENOS;
    }

    private boolean isMultiplicativeOperator() {
        TokenType type = currentToken.getType();
        return type == TokenType.T_VEZES || type == TokenType.T_DIV;
    }

    private void consume(TokenType expectedType) {
        if (currentToken.getType() == expectedType) {
            currentTokenIndex++;
            if (currentTokenIndex < tokens.size()) {
                currentToken = tokens.get(currentTokenIndex);
            } else {
                // Se chegamos ao final dos tokens, definimos um token EOF (fim de arquivo)
                currentToken = new Token(TokenType.T_FIMPROGRAMA, "");
            }
        } else {
            throw new RuntimeException("Error: Unexpected token. Expected: " + expectedType + ", Found: " + currentToken.getType());
        }
    }
}
