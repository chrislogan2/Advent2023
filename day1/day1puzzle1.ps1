## ! Let's start!

#input: https://adventofcode.com/2023/day/1/input


$inputfile="./day1puzzle1input.txt"
$inputdata=Get-Content $inputfile
$numbers = 

# first digit regex
$firstdigitregex="(?<![0-9])([0-9])"
# last digit regex
$lastdigitregex="(?![0-9])([0-9])"

$combinedregex="(?<![0-9])([0-9])([0-9])(?![0-9])"
$combinedregex="(?<!\d)(\d)\D*(\d)(?!\d)"
$goodregex="^\D*(\d)\D*$"


$truelycombined="(?:^\D*(?<!\d)(?<FIRST>\d).*(?<SECOND>\d)(?!\d)\D*$)|(?:^\D*(?<SINGLEDIGIT>\d)\D*$)"
$sum = 0
$inputdataparsed=$inputdata | 
    foreach-object { 
    $x=$_ -match $truelycombined; 
    if ($Matches["FIRST"] -and $Matches["SECOND"]) {
        $data = 10*($Matches["FIRST"])+$Matches["SECOND"]
        $sum+=$data
        $data
    } elseif ($Matches["SINGLEDIGIT"]) {
        $data = 11*($Matches["SINGLEDIGIT"])
        $sum+=$data
        $data
    } else {
        write-output "how did we get here?"
    }


}
$sum = 0
$inputdataparsed | ForEach-Object $