# Spring training
* Slides
* Examples

##
Slides can be viewed using the kc-cli. Simply follow the steps below to view the slides in a browser or create a PDF.

When printing the PDF beware that the width is smaller then in a browser. So it's possible that text (mainly code examples) fall of the page. So make sure the code examples are not to width and verify wether or not the PDF looks good.

### Install
```
npm i -g @infosupport/kc-cli
```

### Serve
option               | description
---------------------|--------------
`kc serve`           | serve presentation from current directory on default port
`kc serve -o`        | open presentation in default browser
`kc serve -p`        | pick random free port
`kc serve -p <port>` | use specified `<port>`

### Print

option                  | description
------------------------|--------------
`kc print`              | print presentation and labs to pdf 
`kc print -o`           | open output file(s) after printing
`kc print --no-labs`    | skip printing labs
`kc print --no-slides`  | skip printing slides

