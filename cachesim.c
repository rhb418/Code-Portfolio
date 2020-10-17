#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

char memory[1<<24]; 

int log2(int n) {
    int r=0;
    while (n>>=1) r++;
    return r;
}

int hit(int address, int cache[], int obits, int sbits, int ways){
    int tag = address>>(sbits+obits);
    int set = address>>(obits) & ((1<<sbits)-1);
    int startindex = set*ways;

    for(int i =0; i<ways; i++){
        if(cache[startindex+i]==tag){
            return 1;
        }
    }
    return 0;     
}

void updateCache(int address, int cache[], int obits, int sbits, int ways, int hit){
    int tag = address>>(sbits+obits);
    int set = address>>(obits) & ((1<<sbits)-1);
    int startindex = set*ways;
    int temp;
    int valindex; 

    if(hit==0){
        for(int i =0; i<ways; i++){
            temp = cache[startindex+i];
            cache[startindex+i] = tag; 
            tag = temp; 
        }
    }
    else if(hit==1){
        for(int i=0; i<ways; i++){
            if(tag == cache[startindex+i]){
                valindex = i; 
            }
        }
        for(int i=0; i<valindex+1; i++){
            temp = cache[startindex+i];
            cache[startindex+i] = tag;
            tag = temp;
        }

    }
    return;
}

int main(int argc, char* argv[]){

    for(int k=0; k<(1<<24); k++){
        memory[k] = 0; 
    }

    int cachesizekb = atoi(argv[2]);
    int assoc = atoi(argv[3]);
    int blocksize = atoi(argv[4]);

    int nframes = cachesizekb*1024/blocksize;
    int nsets = nframes/assoc;

    int cache[nframes];

    for(int k =0; k<nframes; k++){
        cache[k] = -1; 
    }


    FILE *fp;
    fp = fopen(argv[1] , "r");
    char load[64] = "load";
    char store[64] = "store";
    char loadorstore[64];

    int offbits = log2(blocksize);
    int setbits = log2(nsets);


    char saddress [64];
    int address; 
    int size; 


    while(fscanf(fp, "%s", loadorstore) != EOF){
        if(strcmp(loadorstore,store) == 0){
            printf("store ");
            fscanf(fp,"%s",saddress);
            address = (int) strtol(saddress+2, NULL, 16);
            printf("0x%x ", address);

            fscanf(fp, "%d", &size); 

            int storehit = hit(address, cache, offbits, setbits, assoc);
            if(storehit==1){
                printf("hit\n");
            }
            else{
                printf("miss\n");
            }

            if(storehit == 1){
                updateCache(address, cache, offbits, setbits, assoc, storehit);
            }
            


            for(int i=0; i<size; i++){
                fscanf(fp, "%2hhx", &memory[address+i]);
            }

            
        }

        else if(strcmp(loadorstore,load) == 0){
            printf("load ");
            fscanf(fp,"%s",saddress);
            address = (int) strtol(saddress+2, NULL, 16);
            printf("0x%x ", address);

            fscanf(fp, "%d", &size); 

            int is_hit = hit(address, cache, offbits, setbits, assoc);
            if(is_hit==1){
                printf("hit ");
            }
            else{
                printf("miss ");
            }

            updateCache(address, cache, offbits, setbits, assoc, is_hit);

            for(int j =0; j<size; j++){
                printf("%02hhx", memory[address+j]);
            }
            printf("\n");
        }        

    }

    
    return 0; 

}