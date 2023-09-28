package simu.framework;

import simu.entity.*;

import java.util.List;

public interface IDao {
    void tallenna(Tulokset tulos, LSTulos pptulos, TTTulos tttulos, PTTulos pttulos, T1Tulos t1tulos, T2Tulos t2tulos);
    void poista(Tulokset tulos);
    List<Tulokset> lataaKaikki();
    Tulokset lataaYksi();
}
