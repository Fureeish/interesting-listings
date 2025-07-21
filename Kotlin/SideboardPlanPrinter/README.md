# Sideboard Plan Printer

Generates LaTeX sequence that produces a grid of sideboarding guides for matchups. Takes a CSV (separated by semicolons due to commas being present in some of the card names) with rows representing cards, columns representing matchups and cells representing which cards to side in or out.

## Example

Given a file with the following structure:

|                                                         |    | Red Prison | UB Tempo | UR Aggro (Cori) |
|--------------------------------------------------------:|:--:|:----------:|:--------:|:---------------:|
|                                       Bloodstained Mire | 1  |            |          |                 |
|                                          Flooded Strand | 1  |            |          |                 |
|                                                  Island | 1  |            |          |                 |
|                                             Marsh Flats | 1  |            |          |                 |
|                                        Misty Rainforest | 1  |            |          |                 |
|                                          Polluted Delta | 4  |            |          |                 |
|                                                   Swamp | 1  |            |          |                 |
|                                        Undercity Sewers | 2  |            |          |                 |
|                                         Underground Sea | 3  |            |          |                 |
|                                               Wasteland | 4  |            |          |                 |
|                                       Archon of Cruelty | 1  |     1      |    1     |                 |
|                                   Atraxa, Grand Unifier | 1  |     1      |    1     |                 |
|                          Brazen Borrower // Petty Theft | 2  |            |          |                 |
|                                         Murktide Regent | 2  |     2      |          |                 |
|                                       Orcish Bowmasters | 2  |            |          |                 |
| Tamiyo, Inquisitive Student // Tamiyo, Seasoned Scholar | 4  |            |          |                 |
|                                            Animate Dead | 1  |     1      |          |        1        |
|                                              Brainstorm | 4  |            |          |                 |
|                                                  Ponder | 4  |            |          |                 |
|                                                  Entomb | 4  |     4      |    4     |                 |
|                                               Reanimate | 4  |     4      |          |                 |
|                                            Thoughtseize | 4  |     4      |          |                 |
|                                                    Daze | 4  |     1      |          |                 |
|                                           Force of Will | 4  |     3      |          |        4        |
|                                            **Maindeck** | 60 |     14     |    9     |        9        |
|                                           **Sideboard** | 15 |     14     |    9     |        9        |
|                                              Barrowgoyf | 2  |     2      |    2     |        2        |
|                          Brazen Borrower // Petty Theft | 1  |     1      |    1     |        1        |
|                                       Consign to Memory | 2  |     2      |          |                 |
|                                       Dauthi Voidwalker | 3  |     2      |    3     |        3        |
|                                              Fatal Push | 2  |     2      |    2     |        2        |
|                                       Force of Negation | 2  |     2      |          |                 |
|                                                Null Rod | 2  |     2      |          |                 |
|                                       Sheoldred's Edict | 1  |     1      |    1     |        1        |

The code produces the following row:

<img width="822" height="247" alt="image" src="https://github.com/user-attachments/assets/ee70682b-5e38-49d0-ac1d-dd77c4829f93" />
