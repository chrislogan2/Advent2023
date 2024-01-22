#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#define TRUE 1
#define FALSE 0
typedef struct part_num_candidate {
    int line_pos; //index of 
    int part_number; //parsed number
    int part_size; // length in digits
    int adjacent; //really true/false
    struct part_num_candidate * next;
}part_num;
const part_num part_num_def = {
    .line_pos=0,
    .part_number=0,
    .part_size=0,
    .adjacent=FALSE,
    .next = NULL
};

typedef struct part_symbol {
    int sym_pos;
    int gear;
    int ratios[10];
    struct part_symbol * next;
}part_symbol;
//typedef struct part_num_candidate part_num;
//typedef struct part_symbol part_symbol;
const part_symbol part_symbol_def = {
    .sym_pos=0,
    .gear=FALSE,
    .ratios={0},
    .next = NULL
};
int add_ratio(int ratio, part_symbol * symbol);
void parse_line(char * current_line, part_num * parts, part_symbol * symbols);
int check_adjacent(part_num * parts, part_symbol * symbols);
int check_adjacent2(part_num * parts, part_symbol * symbols);
int main() {
    printf("Hellow there\n");
    static char  file_name[] = "input.txt";
    static char  symbollist[] = "!@#$%^&*()";
    int parts_sum=0;
    part_num * parts_cur_head=(part_num *)(malloc(sizeof(part_num))), *parts_prev1=NULL, *parts_prev2=NULL;

    //our rolling list of parts :)

    part_symbol * partsym_cur_head=(part_symbol *)(malloc(sizeof(part_symbol))), *partsym_prev1=NULL, *partsym_prev2=NULL;// =(part_symbol *)(malloc(sizeof(part_symbol)));

    char current_line[1024]; // Lines shouldn't be nearly long enough
    char prev_line1[1024];
    char prev_line2[1024];
    FILE *fp = fopen(file_name, "r");

    if( fp == NULL) {
        return 1;
    }
    while (fgets(current_line, 1024, fp)){
        int part_flag=FALSE;
        int cur_part=0;
        int part_digit_count=0;
        part_num * current_part =(part_num *)(malloc(sizeof(part_num)));
        current_part->next=NULL;
        part_symbol * current_sym = (part_symbol *)(malloc(sizeof(part_symbol))); 
        current_sym->next = NULL;

        parts_cur_head = current_part;
        parts_cur_head->next=NULL;
        partsym_cur_head = current_sym;
        partsym_cur_head->next = NULL;
        parse_line(current_line, parts_cur_head, partsym_cur_head);

        //now checking adjacency.
        current_part=parts_cur_head;
            parts_sum+=check_adjacent2(parts_cur_head, partsym_cur_head);
                if(partsym_prev1 == NULL){

                }else{
            parts_sum+=        check_adjacent2(parts_cur_head,partsym_prev1);
                }
                if(parts_prev1==NULL){

                } else{
             parts_sum+=        check_adjacent2(parts_prev1,partsym_cur_head);
                }


        
        parts_prev2=parts_prev1;
        parts_prev1=parts_cur_head;

        partsym_prev2=partsym_prev1;
        partsym_prev1=partsym_cur_head;
       // printf("\n");
        
       // printf("%s\n",current_line);
    }
    printf("%d is sum", parts_sum);
    return 0;
}
int add_ratio(int ratio, part_symbol * symbol){
    int i = 0;
    while(symbol->ratios[i]!=0){
        i++;
    }
    symbol->ratios[i]= ratio;
        printf("%d, is the radio %d of symbol %d\n", ratio, i, symbol->gear);

    if ((i>0) && (symbol->gear)){
        //printf("%d x %d = %d",ratio, symbol->ratios[0])
        return ratio * symbol->ratios[0];
    }
    return 0;
}
void parse_line(char * current_line ,part_num * parts, part_symbol * symbols) {
    int part_flag=FALSE;
    int cur_part=0;
    int part_digit_count=0;
    
    part_num * current_part;// =(part_num *)(malloc(sizeof(part_num)));
    //current_part->next=NULL;
    part_symbol * current_sym; //= (part_symbol *)(malloc(sizeof(part_symbol))); 
    //current_sym->next = NULL;
    current_part = parts;
    current_sym = symbols;

    current_part->next=NULL;
    current_sym->next=NULL;
    //parts_cur_head = current_part;
    //parts_cur_head->next=NULL;
    // partsym_cur_head = current_sym;
    //partsym_cur_head->next = NULL;

    for (int i = 0; i < strlen(current_line); i++)
    {
            
        // printf(" %d ",i);
        char cur_char = current_line[i];
        if(cur_char >='0' && cur_char <='9' && part_flag==FALSE){
            part_flag=1;
            cur_part=(cur_char-48);
            part_digit_count=1;
        }else if(cur_char >='0' && cur_char <='9' && part_flag==TRUE){
            cur_part = cur_part*10+(cur_char-48);
            part_digit_count++;
        }else if(part_flag==TRUE){
            current_part->line_pos=i-1;
            current_part->part_number=cur_part;
            current_part->part_size=part_digit_count;
            current_part->adjacent=FALSE;

           // printf("\n%d is the current number.\n%d is the size, %d is the position.\n", current_part->part_number,current_part->part_size,current_part->line_pos);
            if ( current_part == parts) {
               // printf("%d,\n\n",current_part->part_number);
                current_part = (part_num *)(malloc(sizeof(part_num)));
                current_part->next = NULL;
            }else if(parts->next == NULL) {
                parts->next = current_part;
                current_part->next=(part_num *)(malloc(sizeof(part_num)));
                current_part = current_part->next;
                current_part->next = NULL;
                
            } else{
                current_part->next=(part_num *)(malloc(sizeof(part_num)));
                current_part=current_part->next;
                current_part->next = NULL;
            }

            cur_part=0;
            part_digit_count=0;
            part_flag=FALSE;
        }
        if(((cur_char !='.') && (32<cur_char <127) && !isspace(cur_char)) && part_flag == FALSE){ //shut up I know it's stupid
            //printf("SYMBOL!!! [%c]\n", cur_char);
            if(cur_char=='*'){
                current_sym->gear=TRUE;
                }else{
                current_sym->gear=FALSE;
            }
            // https://stackoverflow.com/questions/23509348/how-to-set-all-elements-of-an-array-to-zero-or-any-same-value
            memset(current_sym->ratios, 0x00, 10);
            current_sym->sym_pos=i;
            if( current_sym == symbols) {
                current_sym = (part_symbol *)(malloc(sizeof(part_symbol)));
                current_sym->next=NULL;
            } else if (symbols->next==NULL) {
                symbols->next = current_sym;
                current_sym->next=(part_symbol *)(malloc(sizeof(part_symbol)));
                current_sym=current_sym->next;
                current_sym->next=NULL;
            }else {
                current_sym->next = (part_symbol *)(malloc(sizeof(part_symbol)));
                current_sym=current_sym->next;
                current_sym->next = NULL;
            }
        }
    }
}

int check_adjacent(part_num * parts, part_symbol * symbols){
            part_num * current_part;
            part_symbol * current_sym;
            int parts_sum = 0;
          current_part=parts;
           while(current_part != NULL && current_part->part_number >=0) {
                
                int cur_size=current_part->part_size;
                int cur_part_loc=current_part->line_pos;
               // printf("%d << checking this part.\n",current_part->part_number);
                current_sym = symbols;
                if(current_part->adjacent != TRUE){
                while(current_sym !=NULL && current_sym->sym_pos >=0){
                    int current_sym_pos = current_sym->sym_pos;
                    if((cur_part_loc-cur_size)<=current_sym_pos && current_sym_pos<=(cur_part_loc+1)) {
                        current_part->adjacent=TRUE;
                        parts_sum+=current_part->part_number;
                      //  printf("%d, size:%d, pos:%d is adjacent\n to symbol pos:%d\n",current_part->part_number, current_part->part_size,current_part->line_pos,current_sym->sym_pos);
                        break;
                    }else{
                       // printf("%d, size:%d, pos:%d is NOT adjacent\n to symbol pos:%d\n",current_part->part_number,current_part->part_size,current_part->line_pos,current_sym->sym_pos);

                    }
                    current_sym =current_sym->next;
                }
                }
                

                current_part=current_part->next;
           }
    return parts_sum;
}


int check_adjacent2(part_num * parts, part_symbol * symbols){
            part_num * current_part;
            part_symbol * current_sym;
            int parts_sum = 0;
          current_part=parts;
           while(current_part != NULL && current_part->part_number >=0) {
                
                int cur_size=current_part->part_size;
                int cur_part_loc=current_part->line_pos;
               // printf("%d << checking this part.\n",current_part->part_number);
                current_sym = symbols;
                
                while(current_sym !=NULL && current_sym->sym_pos >=0){
                    int current_sym_pos = current_sym->sym_pos;
                    if((cur_part_loc-cur_size)<=current_sym_pos && current_sym_pos<=(cur_part_loc+1)) {
                        current_part->adjacent=TRUE;
                        
                        parts_sum+=add_ratio(current_part->part_number, current_sym);
                      //  printf("%d, size:%d, pos:%d is adjacent\n to symbol pos:%d\n",current_part->part_number, current_part->part_size,current_part->line_pos,current_sym->sym_pos);
                        break;
                    }else{
                       // printf("%d, size:%d, pos:%d is NOT adjacent\n to symbol pos:%d\n",current_part->part_number,current_part->part_size,current_part->line_pos,current_sym->sym_pos);

                    }
                    current_sym =current_sym->next;
                }
                
                

                current_part=current_part->next;
           }
    return parts_sum;
}
