#set page(margin: (top: 1in, rest: 1.5in))

#set text(font: "EB Garamond", size: 9pt)

#set image(width: 10pt)

#set table(
    columns: (0.5in, 1.5in, auto, 1fr, 0.75in, auto),
    align: horizon,
    row-gutter: 5pt,
    rows: 25pt,
    stroke: (x, y) => if y == 0 { (bottom: 0.5pt + black) } else { none }
)

#let foil = box(text(fill: gradient.linear(..color.map.rainbow, angle: 45deg))[FOIL])

= Filip

#table(
    table.header(..([Copies], [Card], [Language], [Set], [Special], [Price (€)]).map(it => strong(it))),
    [4], [Past in Flames], [ENG], [Time Spiral Remastered: Extras], [Retro Frame], [1.00--2.00],
    [2], [Sensei's Divining Top], [ENG], [Champions of Kamigawa], [], [17.00--20.00],
    [4], [Empty the Warrens], [ENG], [Dominaria Remastered: Extras], [Retro Frame], [0.01--0.50],
)

= Kurczak

#table(
    table.header(..([Copies], [Card], [Language], [Set], [Special], [Price (€)]).map(it => strong(it))),
    [3], [Daze], [JPN], [Nemesis], [#foil], align(center)[...],
    [2], [Chain Lightning], [ENG], [Legends], [], [8.50--10.00],
    [1], [Volcanic Island], [ITA], [Foreign Black Bordered], [], align(center)[...],
    [4], [Unholy Heat], [JPN], [Modern Horizons 3: Retro Frame Cards], [Retro Frame #foil], [0.75--3.00],
    [3], [Price of Progress], [JPN], [Exodus], [], [6.50--9.00],
    [2], [Brazen Borrower \/\/ Petty Theft], [JPN], [Throne of Eldraine: Extras], [#foil], align(center)[...],
    [3], [Ball Lightning], [JPN], [Foreign Black Bordered], [], [20.00--30.00],
)
