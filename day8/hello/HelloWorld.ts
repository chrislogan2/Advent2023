let message: string = 'Hello World';
console.log(message);
// just typin

type User = {
    name: string;
    age: number;
};
type Row ={
    id_string: string;
    id: number;
    left: number;
    right: number;
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