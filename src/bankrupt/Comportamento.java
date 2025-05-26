package bankrupt;

import java.util.Random;

public enum Comportamento {
    IMPULSIVO {
        @Override
        public boolean deveComprar(Jogador jogador, Propriedade propriedade) {
            return propriedade.getPreco() <= jogador.getSaldo();
        }
    },
    EXIGENTE {
        @Override
        public boolean deveComprar(Jogador jogador, Propriedade propriedade) {
            return propriedade.getAluguel() > 50 && propriedade.getPreco() <= jogador.getSaldo();
        }
    },
    CAUTELOSO {
        @Override
        public boolean deveComprar(Jogador jogador, Propriedade propriedade) {
            return (jogador.getSaldo() - propriedade.getPreco()) >= 80;
        }
    },
    ALEATORIO {
        @Override
        public boolean deveComprar(Jogador jogador, Propriedade propriedade) {
            return new Random().nextBoolean() && propriedade.getPreco() <= jogador.getSaldo();
        }
    };

    public abstract boolean deveComprar(Jogador jogador, Propriedade propriedade);
}
