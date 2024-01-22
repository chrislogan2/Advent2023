## ! Let's start!

#input: https://adventofcode.com/2023/day/1/input


$inputfile="./day1puzzle1input.txt" # "./input_demo.txt"#
$inputdata=Get-Content $inputfile
# $numbers = 
<## first digit regex
$firstdigitregex="(?<![0-9])([0-9])"
# last digit regex
$lastdigitregex="(?![0-9])([0-9])"

$combinedregex="(?<![0-9])([0-9])([0-9])(?![0-9])"
$combinedregex="(?<!\d)(\d)\D*(\d)(?!\d)"
$goodregex="^\D*(\d)\D*$"
#>
function parse-wordthing {
    param($matchs)
    $matching= @{
        zero = 0
        one = 1
        two = 2
        three = 3
        four = 4
        five = 5 
        six = 6
        seven =7
        eight = 8
        nine =9
        "1" = 1
        "2" = 2
        "3" = 3
        "4" = 4
        "5" = 5
        "6" = 6
        "7" = 7
        "8" = 8
        "9" = 9
        "0" = 0
    }
    $number = $matching[$matchs]
    $number
}
# 
$truelycombined="(?:^.*?(?<!\d|one|two|three|four|five|six|seven|eight|nine)(?<FIRST>\d|one|two|three|four|five|six|seven|eight|nine).*(?<SECOND>\d|one|two|three|four|five|six|seven|eight|nine)(?!\d|one|two|three|four|five|six|seven|eight|nine)\D*$)|(?:^\D*(?<SINGLEDIGIT>\d|one|two|three|four|five|six|seven|eight|nine)\D*$)"
$sum = 0
$inputdataparsed=$inputdata | 
    foreach-object { 
    $x=$_ -match $truelycombined; 
    write-host $matches
    #write-output $_
    if ($Matches["FIRST"] -and $Matches["SECOND"]) {
        $data = 10*(parse-wordthing -matchs $Matches["FIRST"])+(parse-wordthing -matchs $Matches["SECOND"])
        #write-host "10 x $($Matches["FIRST"]) + $($Matches["SECOND"]) = $data `n"
        $sum+=$data
        $data
    } elseif ($Matches["SINGLEDIGIT"]) {
        $data = 11*(parse-wordthing -matchs $Matches["SINGLEDIGIT"])
        $sum+=$data
        $data
    } else {
        write-output "how did we get here?"
    }


}
write-host "The sum is: $sum"
#$inputdataparsed | ForEach-Object $