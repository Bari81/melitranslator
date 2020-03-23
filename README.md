# melitranslator
Morse translator

1.- 2text (convierte morse a texto. Usar 2 espacios entre palabras. )
POST
url:  https://mltranslator.herokuapp.com/melitranslator/2text
Headers: Content-Type: application/json
Body: {"text": "-- .  --. ..- ... - .- "}

2.- 2morse (convierte texto a morse. Cada espacio entre palabras se traducira como 2 espacios en morse)

url:  https://mltranslator.herokuapp.com/melitranslator/2morse
Headers: Content-Type: application/json
Body: {"text": "hola a todos"}

3.- binary2morse (covierte String de binario a morse. 1 a 3 ceros para espacio entre signos, 4 a 7 ceros para salto entre letras, mas de 8 para salto entre palabras)

url:  https://mltranslator.herokuapp.com/melitranslator/bits2morse
Headers: Content-Type: application/json
Body: {"text": "110110110110000111110111110111110000110111110110110000110111111"}

