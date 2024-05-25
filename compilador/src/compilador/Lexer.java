import compilador.Token;
import compilador.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private static final String DELIMITADORES = "\\s+|(?=[{}():;])|(?<=[{}():;])";
    private static final String OPERADORES_RELACIONAIS = ">|<|==?";
    private static final String OPERADORES_ARITMETICOS = "[+\\-*/]";
    private static final String PALAVRAS_RESERVADAS = "programa|inicio|fimprograma|se|entao|senao|fimse|enquanto|fimenquanto|leia|escreva|int|logico|v|f|nao|faca";
    private static final String IDENTIFICADOR = "[a-zA-Z_][a-zA-Z0-9_]*";
    private static final String NUMERO = "[0-9]+";
    private static final String LITERAL = "\"[^\"]*\"";

    public List<Token> lexicalAnalysis(String sourceCode) {
        List<Token> tokens = new ArrayList<>();

        // Remove os comentários de uma linha
        String codeWithoutLineComments = removeLineComments(sourceCode);

        // Remove os comentários de bloco
        String codeWithoutComments = removeBlockComments(codeWithoutLineComments);

        String[] lexemes = codeWithoutComments.split(DELIMITADORES);

        for (String lexeme : lexemes) {
            if (lexeme.isEmpty()) {
                continue;
            }

            TokenType tokenType = getTokenType(lexeme);
            Token token = new Token(tokenType, lexeme);
            tokens.add(token);
        }

        return tokens;
    }

    private String removeLineComments(String code) {
        Pattern pattern = Pattern.compile("//.*");
        Matcher matcher = pattern.matcher(code);
        return matcher.replaceAll("");
    }

    private String removeBlockComments(String code) {
        Pattern pattern = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(code);
        return matcher.replaceAll("");
    }

    private TokenType getTokenType(String lexeme) {
        if (lexeme.matches(PALAVRAS_RESERVADAS)) {
            return TokenType.valueOf("T_" + lexeme.toUpperCase());
        } else if (lexeme.matches(OPERADORES_RELACIONAIS)) {
            if (lexeme.equals("<")) {
                return TokenType.T_MENOR;
            } else if (lexeme.equals(">")) {
                return TokenType.T_MAIOR;
            } else if (lexeme.equals("==")) {
                return TokenType.T_IGUAL;
            }
            return TokenType.valueOf("T_" + lexeme.toUpperCase());
        } else if (lexeme.matches(OPERADORES_ARITMETICOS)) {
            if (lexeme.equals("+")) {
                return TokenType.T_MAIS;
            } else if (lexeme.equals("-")) {
                return TokenType.T_MENOS;
            } else if (lexeme.equals("*")) {
                return TokenType.T_VEZES;
            } else if (lexeme.equals("/")) {
                return TokenType.T_DIV;
            }
            return TokenType.valueOf("T_" + lexeme.toUpperCase());
        } else if (lexeme.matches(IDENTIFICADOR)) {
            return TokenType.T_IDENTIF;
        } else if (lexeme.matches(NUMERO)) {
            return TokenType.T_NUMERO;
        } else if (lexeme.matches(LITERAL)) {
            return TokenType.T_LITERAL;
        } else {
            return TokenType.T_INVALIDO;
        }
    }
}
