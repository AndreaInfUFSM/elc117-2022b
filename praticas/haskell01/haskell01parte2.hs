
-- 1. Crie uma função `itemize :: [String] -> [String]` que receba uma lista de nomes e aplique a função `htmlItem` em cada nome.
htmlItem :: String -> String
htmlItem str = "<li>" ++ str ++ "</li>"

itemize :: [String] -> [String]
itemize strs = map htmlItem strs 

-- 2. Crie uma função `onlyVowels :: String -> String` que receba uma string e retorne outra contendo somente suas vogais. Por exemplo: `onlyVowels "abracadabra"` vai retornar `"aaaaa"`.
isVowel2 :: Char -> Bool
isVowel2 c = elem c "aeiouAEIOU"
   
onlyVowels :: String -> String
onlyVowels str = filter isVowel2 str

-- 3. Escreva uma função `onlyElderly :: [Int] -> [Int]` que, dada uma lista de idades, selecione somente as que forem maiores que 65 anos.
isElderly :: Int -> Bool
isElderly age = age > 65

onlyElderly :: [Int] -> [Int]
onlyElderly ages = filter isElderly ages

-- 5. Crie uma função `onlyLongWords :: [String] -> [String]` que receba uma lista de strings e retorne somente as strings longas (use a função `isLongWord` definida no código de exemplo no início da prática).
isLongWord :: String -> Bool
isLongWord str = length str > 10

onlyLongWords :: [String] -> [String]
onlyLongWords strs = filter isLongWord strs

-- 6. Escreva uma função `onlyEven` que receba uma lista de números inteiros e retorne somente aqueles que forem pares. Agora é com você a definição da tipagem da função!
isEven :: Int -> Bool
isEven x = mod x 2 == 0

onlyEven :: [Int] -> [Int]
onlyEven xs = filter isEven xs

-- 7. Escreva uma função `onlyBetween60and80` que receba uma lista de números e retorne somente os que estiverem entre 60 e 80, inclusive. Você deverá criar uma função auxiliar `between60and80` e usar `&&` para expressar o operador "E" lógico em Haskell.
between60and80 :: Int -> Bool
between60and80 x = (x > 60) && (x < 80)

onlyBetween60and80 :: [Int] -> [Int]
onlyBetween60and80 xs = filter between60and80 xs

-- 8. Crie uma função `countSpaces` que receba uma string e retorne o número de espaços nela contidos. Dica 1: você vai precisar de uma função que identifica espaços. Dica 2: aplique funções consecutivamente, isto é, use o resultado de uma função como argumento para outra. 
space :: Char -> Bool
space c = c == ' '

countSpaces :: String -> Int
countSpaces cs = length (filter space cs)

-- 9. Escreva uma função `calcAreas` que, dada uma lista de valores de raios de círculos, retorne uma lista com a área correspondente a cada raio.
circleArea :: Float -> Float
circleArea r = pi * r^2

calcAreas :: [Float] -> [Float]
calcAreas rs = map circleArea rs
