# Lentokenttätoiminnan simulointi projekti

Tervetuloa lentokentän kolmivaihesimulaattorin dokumentaatioon. Tämä projekti on toteutettu osana Metropolian tieto- ja viestintätekniikan
tutkinnon toisen vuoden ohjelmistotuotannon projektia. Tämä README-tiedosto antaa sinulle yleiskuvan projektista ja ohjeet sen käyttöönottoon.
Tämä projekti on toteutettu käyttäen Java-ohjelmointikieltä ja JavaFX-kirjastoa. Ohjelma on toteutettu käyttäen MVC-arkkitehtuuria.

## Tarkoitus

Simulaattorilla voidaan simuloida lentokentän toimintaa ja saadaan tietoa palvelupisteiden kuormituksesta ja jonotusajoista. Tällä tavoin voidaan
optimoida palvelupisteiden määrää ja palveluaikoja. Simulaattorin avulla voidaan myös simuloida erilaisia skenaarioita asettamalla lentojen lähtöajat
kauemmas tai lähemmäs toisiaan. Tämä antaa tietoa siitä, miten lentokentän toiminta muuttuu, kun lentojen lähtöajat muuttuvat.

## Malli

Lentokentälle luodaan kaksi saapuvaa ryhmää matkustajia, kotimaan ja ulkomaan matkustajat. Matkustajat saapuvat lentokentälle normaalin jakauman mukaisesti.
Matkustajat voivat myöhästyä lennolta, jolloin heidät poistetaan järjestelmästä ja merkitään myöhästyneiksi. Kun molemmat lennot ovat lähteneet luodaan uudet matkustajat.
Simulointi jatkuu kunnes asetuksissa määritelty aika on kulunut.

## Käyttöohje

Simulaattorissa on viisi palvelupistettä: Lähtöselvitys, turvatarkastus, passintarkastus, lähtportti T1 ja T2. Lähtöselvitys ja turvatarkastus ovat yhteisiä molemmille lennoille.
Vain ulkomaan lennon asiakkaat käyvät passintarkustuksessa ennen lähtöportille T2 menoa.  
Simulaattorissa voidaan antaa käyttäjän syöttämät arvot palvelupisteiden määrille ja palveluajoille. Palveluajat generoidaan normaalin jakauman mukaan.  
Tarkemmat ohjeet löytyvät simulaattorin asetukset sivulta.  
Jos halutaan tallentaa simulaation tulokset, on ajettava src/main/resources kansiosta löytyvä database.sql, joka luo tietokannan ja taulut:  
```mysql -u root -p < database.sql```  
Simulaattori käynnistyy view-paketin Main-luokasta.

## Ohjelmoijat
- Mamadou Balde

- [Sami Paananen](
    https://users.metropolia.fi/~samipaan/CV/
)
- Vladimir Piniazhin

## Kiitokset
Simo Silander  
Vesa Ollikainen



