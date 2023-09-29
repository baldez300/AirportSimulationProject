package simu.framework;

import simu.entity.*;

import java.util.List;
import java.util.HashMap;

public interface IDao {
    void tallenna(HashMap<Object, Object> tuloksetMap);
    List<Tulokset> lataaKaikki();
    HashMap<Object, Object> lataaTarkemmatTiedot(int id);
}
