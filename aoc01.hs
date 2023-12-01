import Data.Char
import Data.List (isPrefixOf, isSuffixOf)

spelledDigits :: [(String, Int)]
spelledDigits = [("one", 1), ("two", 2), ("three", 3), ("four", 4), ("five", 5), ("six", 6), ("seven", 7), ("eight", 8), ("nine", 9)]

firstDigit :: String -> Int -> Int
firstDigit s i | isDigit (head s) = digitToInt (head s)
               | otherwise = if fst (spelledDigits!!i) `isPrefixOf` s then snd (spelledDigits!!i)
                             else if i < 8 then firstDigit s (i+1)
                             else firstDigit (tail s) 0

lastDigit :: String -> Int -> Int
lastDigit s i | isDigit (last s) = digitToInt (last s)
              | otherwise = if fst (spelledDigits!!i) `isSuffixOf` s then snd (spelledDigits!!i )
                            else if i < 8 then lastDigit s (i+1)
                            else lastDigit (init s) 0

main :: IO ()
main = do
   s <- readFile "aoc01.txt"

   -- first star
   let numbers = [filter isDigit x | x <- lines s]
   let firstResult = sum [digitToInt (head x) * 10 + digitToInt (last x) | x <- numbers]

   -- second star
   let result = sum [firstDigit x 0 * 10 + lastDigit x 0 | x <- lines s]

   print result