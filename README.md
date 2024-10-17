# INF01120 Técnicas de Construção de Programas
### Semestre: 2020/2
#### Professor: Marcelo Soares Pimenta

## Descrição
O objetivo do trabalho é a definição, implementação, teste e depuração de um gerador de música a partir de texto.\
O software recebe um texto como entrada e gera um conjunto de notas correspondentes ao texto segundo alguns parâmetros (timbre, ritmo – na forma de Beats por Minuto ou BPM, etc). Os parâmetros são definidos através de um mapeamento de texto para informações musicais. Parte do mapeamento já está definida abaixo. O restante deve ser definido pelo grupo e documentado para conhecimento do professor.

Texto                | Nota
-------------------- | ------
Letra A              | Nota Lá
Letra B              | Nota Si
Letra C              | Nota Do
Letra D              | Nota Ré
Letra E              | Nota Mi
Letra F              | Nota Fá
Letra G              | Nota Sol
Letras a,b,c,d,e,f,g | Se o caractere anterior era NOTA (A a G), repete nota; caso contrário, silêncio ou pausa
Caractere espaço     | Aumenta volume para o DOBRO do volume; se não puder aumentar, volta ao volume default (de início)

Texto                                                                            | Ação
-------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------
Caractere !                                                                      | Trocar instrumento para o instrumento General MIDI #114 (Agogo)
Qualquer outra letra vogal (O ou o, I ou i, U ou u)                              | Trocar instrumento para o instrumento General MIDI #7 (Harpsichord)
Qualquer outra outra letra consoante ((todas consoantes exceto as que são notas) | Se o caractere anterior era NOTA (A a G), repete nota; caso contrário, silêncio ou pausa
Dígito                                                                           | Trocar instrumento para o instrumento General MIDI cujo numero é igual ao valor do instrumento ATUAL + valor do dígito
Caractere ? OU caractere .                                                       | Aumenta UMA oitava; se não puder, aumentar, volta à oitava default (de início)
Caractere NL                                                                     | Trocar instrumento para o instrumento General MIDI #15 (Tubular Bells) 
Caractere ;                                                                      | Trocar instrumento para o instrumento General MIDI #76 (Pan Flute)
Caractere ,                                                                      | Trocar instrumento para o instrumento General MIDI #20 (Church Organ)
ELSE                                                                             | Se o caractere anterior era NOTA (A a G), repete nota; caso contrário, silêncio ou pausa
