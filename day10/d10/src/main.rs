use std::fs::File;
use std::io::prelude::*;
use std::io::{self, BufRead};
use std::path::Path;
use std::fs::read_to_string;
/*
notes:
    | is a vertical pipe connecting north and south.
    - is a horizontal pipe connecting east and west.
    L is a 90-degree bend connecting north and east.
    J is a 90-degree bend connecting north and west.
    7 is a 90-degree bend connecting south and west.
    F is a 90-degree bend connecting south and east.
    . is ground; there is no pipe in this tile.
    S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has
*/
fn read_lines_vec(filename: &str) -> Vec<String> {
    read_to_string(filename) 
        .unwrap()  // panic on possible file-reading errors
        .lines()  // split the string into an iterator of string slices
        .map(String::from)  // make each slice into a string
        .collect()  // gather them together into a vector
}
fn read_lines<P>(filename: P) -> io::Result<io::Lines<io::BufReader<File>>> 
where P: AsRef<Path>, {
    let file = File::open(filename)?;
    Ok(io::BufReader::new(file).lines())
}
fn get_adjacent_valid(row: usize, col: usize, data: &Vec<String>) {
    
    if row >0 {
        println!(" {} ",data[row-1].chars().nth(col).unwrap());
    }
    if col >0 {
        print!("{}S",data[row].chars().nth(col-1).unwrap());
    }else{
        print!(" S");
    }

    if col < data[row].chars().count()-1 {
        println!("{}",data[row].chars().nth(col+1).unwrap());
    } else {
        println!("");
    }
    if row < data.len()-1 {
        println!(" {}",data[row+1].chars().nth(col).unwrap());
    }

}
fn main() {
    let path = Path::new("demo1.txt");
    let display = path.display();

    // open path in read only mode, returns io::Result<File>
    let mut file = match File::open(&path) {
        Err(why) => panic!("couldn't open {}: {}", display, why),
        Ok(file) => file,
    };

    if let Ok(lines) = read_lines("./demo1.txt") {
        for line in lines.flatten() {
            println!("{}", line);
        }
    }
    let mut zz = read_lines_vec("./demo1.txt");
    println!("{}",zz[1].chars().nth(2).unwrap());
    let rows = zz.len();
    let cols = zz[0].chars().count();
    println!("{}:{} wow!", rows, cols);
//ROW, COL
let mut cur_row = 0;
let mut cur_col = 0;
   'start_finder: for x in zz.iter() {
        cur_col=0;
        for z in x.chars(){
           // println!("What char? {}", z);
            if z == 'S' {
                println!("START POINT!");
                break 'start_finder;
               // z= 'T';

            }
            cur_col=cur_col+1;
        }
        cur_row=cur_row+1;
    }
    /*String::new();
    match file.read_to_string(&mut s) {
        Err(why) => panic!("couldn't read {}: {}", display, why),
        Ok(_) => print!("{} contains:\n{}", display, s),
    };*/
    println!("{}::::{}\n{}", cur_row, cur_col, zz[cur_row].chars().nth(cur_col).unwrap());
    get_adjacent_valid(cur_row,cur_col, &zz);

}
