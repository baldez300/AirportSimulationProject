package simu.framework;

import simu.entity.*;

import java.util.List;

public interface IDao {
    void tallenna(Tulokset tulokset);
    List<Tulokset> lataaKaikki();
}
