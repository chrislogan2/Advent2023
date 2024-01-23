var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
const fs = require('node:fs');
let message = 'Hello World';
console.log(message);
function isAdult(user) {
    return user.age >= 18;
}
const justine = {
    name: 'Justine',
    age: 23,
};
const isJustineAnAdult = isAdult(justine);
console.log(isJustineAnAdult);
//https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_expressions
const movementPatternRegex = /^(?<MOV_PATTERN>[RL]*)$/gm;
const rowMatchingRegex = /^(?<ID_STR>[A-Z]{3})\s=\s\((?<L_STR>[A-Z]{3}),\s(?<R_STR>[A-Z]{3})\)$/gm;
const inputFileName = "input.txt";
var inputFileData;
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
const parseRegexPromise = (text, regexp1, regexp2) => new Promise((resolve, reject) => {
    let matchesArray1 = text.matchAll(regexp1);
    let matchesArray2 = text.matchAll(regexp2);
    const results = [text.matchAll(regexp1), text.matchAll(regexp2)];
    if (results[0] === null || results[1] === null) {
        reject("no matches found in one or both:(");
    }
    else {
        //console.log(matchesArray2.length);
        resolve(results);
    }
});
// async  2 -> promise based :)
const readFilePromise = (path) => new Promise((resolve, reject) => {
    fs.readFile(path, 'utf8', (error, result) => {
        if (error) {
            reject(error);
        }
        else {
            //console.log(result)
            resolve(result);
        }
    });
});
function nextId(direction, row) {
    const Right = row.right;
    const Left = row.left;
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
const walkResults = (inputs) => new Promise((resolve, reject) => {
    let steps = 0;
    let cur_id = inputs.start_id;
    let cur_move_pos = 0;
    let max_move_pos = inputs.movement_pattern.length;
    do {
        //console.log(inputs.rows[cur_id]);
        cur_id = nextId(inputs.movement_pattern[cur_move_pos], inputs.rows[cur_id]);
        if (cur_move_pos < max_move_pos - 1) {
            cur_move_pos++;
        }
        else {
            cur_move_pos = 0;
        }
        steps++;
        //cur_id++;
    } while (inputs.rows[cur_id].id_string !== "ZZZ");
    //for(const row of inputs.rows) {
    //    console.log(row);
    //}
    console.log(steps);
    resolve(steps);
});
const loopCounter = (movementPattern, allRows, start_id) => new Promise((resolve, reject) => {
    let steps = 0;
    let cur_id = start_id;
    let cur_move_pos = 0;
    let max_move_pos = movementPattern.length;
    do {
        //console.log(inputs.rows[cur_id]);
        cur_id = nextId(movementPattern[cur_move_pos], allRows[cur_id]);
        if (cur_move_pos < max_move_pos - 1) {
            cur_move_pos++;
        }
        else {
            cur_move_pos = 0;
        }
        steps++;
        //cur_id++;
        console.log(`${cur_id}, ${allRows[cur_id].id_string}, looking for ${start_id}`);
    } while (allRows[cur_id].id !== start_id);
    //for(const row of inputs.rows) {
    //    console.log(row);
    //}
    console.log(steps);
    resolve(steps);
});
function nextId2(direction, rows) {
    return __awaiter(this, void 0, void 0, function* () {
        return new Promise((resolve, reject) => __awaiter(this, void 0, void 0, function* () {
            let x = [];
            rows.forEach(row => {
                const nextid = nextId(direction, row);
                x.push(nextid);
            });
            resolve(x);
        }));
    });
}
const walkResults2 = (inputs) => new Promise((resolve, reject) => {
    const startNodes = inputs.rows.filter((row) => /..A/.test(row.id_string));
    console.log(startNodes);
    let steps = 0;
    let cur_id = inputs.start_id;
    let cur_ids = startNodes.map((row) => { return (row.id); });
    let cur_move_pos = 0;
    let max_move_pos = inputs.movement_pattern.length;
    //console.log(inputs.rows[cur_id]);
    const loop_sizes = cur_ids.map((id) => {
        return loopCounter(inputs.movement_pattern, inputs.rows, id);
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
function processInputData(inputFile) {
    return __awaiter(this, void 0, void 0, function* () {
        return new Promise((resolve, reject) => __awaiter(this, void 0, void 0, function* () {
            let movementPattern;
            let startId = null;
            let rows = [];
            const regexResults = yield readFilePromise(inputFile)
                .then((result) => parseRegexPromise(result, movementPatternRegex, rowMatchingRegex))
                .catch((error) => console.error("Failed to read data"));
            //console.log(regexResults[1]);
            for (const match of regexResults[0]) {
                movementPattern = match.groups['MOV_PATTERN'] !== "" ? match.groups['MOV_PATTERN'] : movementPattern;
            }
            let id_num = 0;
            for (const match of regexResults[1]) {
                if (match.groups['ID_STR'] && match.groups['L_STR'] && match.groups['R_STR']) {
                    //console.log(`All 3 groups exist :) ${match.groups['ID_STR']}=id:${id_num}`);
                    const row = {
                        id_string: match.groups['ID_STR'],
                        id: id_num,
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
            if (rows.length > 0) {
                rows.forEach((row) => {
                    if (row.id_string === "AAA")
                        startId = row.id;
                    rows.forEach((innerRow) => {
                        if (innerRow.left_string === row.id_string)
                            innerRow.left = row.id;
                        if (innerRow.right_string === row.id_string)
                            innerRow.right = row.id;
                    });
                });
                //console.log(rows);
                const rowResult = {
                    rows: rows,
                    movement_pattern: movementPattern,
                    start_id: startId,
                };
                resolve(rowResult);
            }
            else
                reject("eror :(");
        }));
        // console.log(x[0]);
    });
}
let x = []; // have to make it an emptt arra yor else push fails :(
//https://www.freecodecamp.org/news/fix-typeerror-cannot-read-property-push-of-undefined-in-javascript/
x.push(12);
console.log(x);
processInputData(inputFileName).then((results) => walkResults2(results)).catch((error) => {
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
//# sourceMappingURL=day8.js.map