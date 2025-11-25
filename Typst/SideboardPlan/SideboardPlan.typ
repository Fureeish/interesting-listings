#set page(margin: 0.75in)

#set text(font: "EB Garamond", size: 10pt)

#let zip-longest(..args, fill: none) = {
  let arrays = args.pos()
  let max-len = calc.max(..arrays.map(array.len))
  array.zip(..arrays.map(arr => arr + (fill, ) * (max-len - arr.len())), exact: true)
}

#show figure.where(
    kind: table
): set figure.caption(position: top)

#show figure.caption: it => [
    #smallcaps(strong(it.body))
]

#set par(leading: 0.55em, spacing: 0.55em)

#let col-size = 2.75cm

#let plan-for(matchup, side-in, side-out, comments: []) = {
    side-in = side-in.map(it => [+#it])
    side-out = side-out.map(it => [#sym.minus#it])
    figure(
        caption: matchup,
        table(
            stroke: none,
            inset: 3pt,
            columns: (col-size, col-size),
            align: left,
            table.vline(x: 1, start: 0, stroke: 0.5pt),
            table.hline(stroke: 0.5pt),
            ..zip-longest(side-in, side-out).flatten()
        )
    )
    emph(text(size: 8pt, fill: luma(180), comments))
}

#show "+": set text(baseline: -0.5pt)
#show sym.minus: set text(baseline: -0.5pt)

#let play(body) = text(fill: rgb(120, 170, 60), body)
#let draw(body) = text(fill: rgb(210, 70, 40), body)

#grid(
    columns: (1fr, 1fr, 1fr),
    gutter: 5pt,
    row-gutter: 20pt,
    plan-for([8-cast / Blue Painter], ([],), ([],)),
    plan-for([ANT], ([],), ([],)),
    plan-for([BUG Beanstalk], ([],), ([],)),
    plan-for([Cephalid Breakfast], ([],), ([],)),
    plan-for([Ub Cloudpost], ([],), ([],)),
)
