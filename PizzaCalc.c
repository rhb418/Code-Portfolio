#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#define PI 3.14159265358979323846

struct pizza{
    char name [65];
    float val;
    struct pizza* next; 
};
struct pizza* glob;

struct pizza* giveMax(struct pizza* list){
    struct pizza* tprevious; 
    struct pizza* previous;
    struct pizza* maximum = list; 
    struct pizza* begin = list;
    while(list->next !=NULL){
        tprevious = list;
        list = list->next;
        if(list->val > maximum->val){
            maximum = list;
            previous = tprevious;
        }
        if(list->val == maximum->val && strcmp(list->name,maximum->name)<0){
            maximum = list;
            previous = tprevious;
        }        

    }
    if(begin == maximum){
        glob = begin->next;
        begin->next =NULL;
        return begin;
    }
    else{
        previous->next = maximum->next; 
        maximum->next = begin;
        begin = maximum;
        glob = begin->next;
        begin->next =NULL;
        return begin; 
    }

}
int main(int argc, char* argv[]){
    struct pizza* first;
    struct pizza* last; 
    struct pizza* np; 
    struct pizza* temp; 
    struct pizza* ret;
    struct pizza* final; 

    int counter = 0; 
    int c2 =2; 
    FILE *fp;
    fp = fopen(argv[1] , "r");
    if(fp==NULL){
        printf("%s", "Error Opening File");
        return(-1);
    }
    
    while(1){
        char str1 [65];
        
        if(fscanf(fp, "%s\n", &str1)==EOF){
            printf("%s\n", "PIZZA FILE IS EMPTY");
            return(0);
            break;
        } 
        

        if(strcmp(str1,"DONE")==0){
            break; 
        }        
        float diameter;
        fscanf(fp,"%f\n",&diameter);

        float price;
        fscanf(fp, "%f\n",&price); 

        float ppd; 
        if(diameter == 0.0 || price ==0.0) ppd =0.0;
        else ppd = PI/4*diameter*diameter/price;

        if(counter ==0){
            first = (struct pizza*) malloc(sizeof(struct pizza));
            strcpy(first->name,str1);
            first->val = ppd;
            first->next = NULL;
            last = first; 
        }
        if(counter == 1){
            np = (struct pizza*) malloc(sizeof(struct pizza));
            strcpy(np->name,str1);
            np->val = ppd;
            np->next = NULL;
            last->next = np;
            last = last->next;             
        }
        counter =1; 
    }
    
    glob = first; 

    final = giveMax(glob);
    ret= final;
    while(glob!=NULL){
        final->next = giveMax(glob);
        final = final->next;        
    }

    
    while(ret!=NULL){
        printf("%s ", ret->name);
        printf("%f\n",ret->val);
        temp = ret;
        ret = ret->next;
        free(temp);
    }
    fclose(fp);
    
    return EXIT_SUCCESS; 
}
