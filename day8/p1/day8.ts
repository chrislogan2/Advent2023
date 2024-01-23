const fs = require('node:fs');

let message: string = 'Hello World';
console.log(message);
// just typin

type User = {
    name: string;
    age: number;
};
function isAdult(user: User): boolean {
    return user.age >=18;
}
const justine: User = {
    name: 'Justine',
    age: 23,
};
const isJustineAnAdult: boolean = isAdult(justine);

console.log(isJustineAnAdult);
//real code starts
type Row ={
    id_string: string;
    id: number;
    left: number;
    right: number;
    left_string: string;
    right_string: string;
};
type RowResult={
    rows: Row[];
    movement_pattern: string;
    start_id : number;
}
//https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions
const movementPatternRegex : RegExp = /^(?<MOV_PATTERN>[RL]*)$/gm;
const rowMatchingRegex : RegExp = /^(?<ID_STR>[A-Z]{3})\s=\s\((?<L_STR>[A-Z]{3}),\s(?<R_STR>[A-Z]{3})\)$/gm;

const inputFileName : string = "input.txt";
var inputFileData : string;

//async stuff
function doSomething() {
    return new Promise((resolve) => {
      setTimeout(() => {
        // Other things to do before completion of the promise
        console.log("Did something");
        // The fulfillment value of the promise
        resolve("https://example.com/");
      }, 200);
    });
  }
const parseRegexPromise = (text : string, regexp1 : RegExp, regexp2 : RegExp) =>
  new Promise((resolve, reject) => {
    let matchesArray1 : IterableIterator<RegExpMatchArray>  = text.matchAll(regexp1);
    let matchesArray2 : IterableIterator<RegExpMatchArray> = text.matchAll(regexp2);
    const results : IterableIterator<RegExpMatchArray>[] = [text.matchAll(regexp1), text.matchAll(regexp2)];
    if(results[0] === null || results[1] === null){
        reject("no matches found in one or both:(");
    }else{
        //console.log(matchesArray2.length);
        resolve(results);
    }
  });

  // async  2 -> promise based :)
  const readFilePromise = (path : string) =>
  new Promise((resolve, reject) => {
    fs.readFile(path,'utf8' , (error : string, result : string) => {
      if (error) {
        reject(error);
      } else {
        //console.log(result)
        resolve(result);
      }
    });
  });

function nextId(direction: string, row: Row): (number) {
    const Right : number = row.right;
    const Left : number = row.left;
    switch (direction) {
        case 'R':
            return Right;
            break;
        case 'L':
            return Left;
            break;
        default: 
            console.log(`That's not a valid direciton :( ${direction} )`);
            return null;
            break;
    }
    return 1;
}

const walkResults= (inputs : RowResult) =>
new Promise((resolve, reject) =>  {
    let steps : number = 0;
    let cur_id : number = inputs.start_id;
    let cur_move_pos : number = 0;
    let max_move_pos : number = inputs.movement_pattern.length;
    do {
        //console.log(inputs.rows[cur_id]);
        cur_id = nextId(inputs.movement_pattern[cur_move_pos], inputs.rows[cur_id]);
        if(cur_move_pos < max_move_pos-1) {
            cur_move_pos++;
        }else{
            cur_move_pos = 0;
        }
        steps++;
        //cur_id++;
    }while(inputs.rows[cur_id].id_string!=="ZZZ")
    //for(const row of inputs.rows) {
    //    console.log(row);
    //}
    console.log(steps);
    resolve(steps);

});
// just need to implement the loop detection algo from wikipedia probably ??
// https://en.wikipedia.org/wiki/Cycle_detection
const loopCounter= (movementPattern : string ,allRows : Row[], start_id: number) =>
new Promise((resolve, reject) =>  {
    let steps : number = 0;
    let cur_id : number = start_id;
    let cur_move_pos : number = 0;
    let max_move_pos : number = movementPattern.length;
    do {
        //console.log(inputs.rows[cur_id]);
        cur_id = nextId(movementPattern[cur_move_pos], allRows[cur_id]);
        if(cur_move_pos < max_move_pos-1) {
            cur_move_pos++;
        }else{
            cur_move_pos = 0;
        }
        steps++;
        //cur_id++;
        console.log(`${cur_id}, ${allRows[cur_id].id_string}, looking for ${start_id}`);
    }while(allRows[cur_id].id!==start_id)
    //for(const row of inputs.rows) {
    //    console.log(row);
    //}
    console.log(steps);
    resolve(steps);

});

async function nextId2(direction: string, rows: Row[]) {
    return new Promise(async (resolve, reject) => {
    let x : number[] =[];
    rows.forEach(row => {
        const nextid : number = nextId(direction,row);
        x.push(nextid);
    });
    resolve(x);
    });
}

const walkResults2= (inputs : RowResult) =>
new Promise((resolve, reject) =>  {
    const startNodes = inputs.rows.filter((row) => /..A/.test(row.id_string));
    console.log(startNodes);
    let steps : number = 0;
    let cur_id : number = inputs.start_id;
    let cur_ids: number[]= startNodes.map((row)=>{return (row.id)});
    let cur_move_pos : number = 0;
    let max_move_pos : number = inputs.movement_pattern.length;
   
        //console.log(inputs.rows[cur_id]);
        const loop_sizes=cur_ids.map((id)=>{
            return loopCounter(inputs.movement_pattern,inputs.rows, id);
        });
        //cur_id = nextId(inputs.movement_pattern[cur_move_pos], inputs.rows[cur_id]);
      
    //for(const row of inputs.rows) {
    //    console.log(row);
    //}
    console.log(loop_sizes);
    resolve(steps);

});

  //read file,
  //parse the regexes.
  async function processInputData(inputFile : string) {
    return new Promise(async (resolve, reject) => {
    let movementPattern : string;
    let startId : number = null;
    let rows : Row[] =[];
    const regexResults   = await readFilePromise(inputFile)
    .then((result : string) => parseRegexPromise(result,movementPatternRegex,rowMatchingRegex))
    .catch((error) => console.error("Failed to read data"));
    //console.log(regexResults[1]);
    for (const match of regexResults[0]) {
        movementPattern = match.groups['MOV_PATTERN']!=="" ? match.groups['MOV_PATTERN'] : movementPattern;
    }
    let id_num : number = 0;
    for (const match of regexResults[1]) {
        if(match.groups['ID_STR'] && match.groups['L_STR'] && match.groups['R_STR']){
            //console.log(`All 3 groups exist :) ${match.groups['ID_STR']}=id:${id_num}`);
            const row : Row =  {
                id_string: match.groups['ID_STR'],
                id:  id_num,
                right: null,
                left: null,
                right_string: match.groups['R_STR'],
                left_string: match.groups['L_STR'],
            };
            rows.push(row);
            id_num++;
        }
        else
            console.log("matches fewer than 3!");


    }
    //console.log(movementPattern);
    if(rows.length >0){
        rows.forEach((row)=>{
            if(row.id_string==="AAA")
                startId=row.id;
            rows.forEach((innerRow)=>{
                if(innerRow.left_string === row.id_string)
                    innerRow.left = row.id;
                if(innerRow.right_string === row.id_string)
                    innerRow.right = row.id;
            });
        });
        //console.log(rows);
        const rowResult : RowResult = {
            rows: rows,
            movement_pattern: movementPattern,
            start_id: startId,
        };
        resolve(rowResult);
    }
    else
        reject("eror :(");
    });
   // console.log(x[0]);
  }
  let x : number[] = []; // have to make it an emptt arra yor else push fails :(
  //https://www.freecodecamp.org/news/fix-typeerror-cannot-read-property-push-of-undefined-in-javascript/
x.push(12);
console.log(x);
 processInputData(inputFileName).then((results : RowResult)=>walkResults2(results)).catch((error)=>{
    console.error(error);
});

//console.log(nextId('R',zztop));
//promise way ^^
//callback way vv
/*
fs.readFile(inputFileName, 'utf8', (err : string, data : string) => {
    if(err) {
        console.error(err);
        return;
    }
    return data;
});
*/