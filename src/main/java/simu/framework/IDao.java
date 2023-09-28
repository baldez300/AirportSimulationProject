package simu.framework;

import simu.entity.*;

import java.util.List;
import java.util.HashMap;

public interface IDao {
    void tallenna(Tulokset tulos, LSTulos pptulos, TTTulos tttulos, PTTulos pttulos, T1Tulos t1tulos, T2Tulos t2tulos);
    List<Tulokset> lataaKaikki();
    HashMap<Object, Object> lataaTarkemmatTiedot(int id);
}
