import numpy as np
#
#   0 3 6 9 12 15
#   1 3 6 10 15 21
#   10 13 16 21 30 45
prodData = np.loadtxt("/Users/chrislogan/projects/Advent2023/day9/input.txt")
demoData = np.array([[0, 3, 6, 9, 12, 15],[1, 3, 6, 10, 15, 21],[10, 13, 16, 21, 30, 45]])
sum=0
#allArrays.append(demoData)
for currentRow in prodData:
    n=1
    allArrays=[]
    allArrays.append( currentRow)
    while True:
        nderiv=np.diff(currentRow,n)
        allArrays.append(nderiv)
        #print(nderiv)
        n=n+1
        if nderiv.any():
            print("nth deriv is noo all zero "+ str(n))
        else:
            break
    #print(allArrays)
    resultArray=[]
    n=0
    for i in reversed(range(len(allArrays))):
        if i>0:
            nextVal=0
            rowToModify = allArrays[i-1]
            if (n == 0):
                nextVal= allArrays[i][-1]+allArrays[i-1][-1]
            else: 
                nextVal = resultArray[n-1][-1]+rowToModify[-1]
            x=np.append(rowToModify,nextVal)
            #sum=sum+nextVal
            #print(x)
            print("nextval is "+str(nextVal)+" and sum so far is: "+str(sum))
            
            resultArray.append(x)
           # print(resultArray)
            n=n+1
            #print(allArrays[i-1][j])
        else:
            print(resultArray[-1][-1])
            
            sum = sum + resultArray[-1][-1]
            # print("new value for: " + str(i-1) +" " + str(allArrays[i][j][-1]+allArrays[i-1][j][-2]))
            
            #allArrays[i-1][j].append(allArrays[i][j][-1]+allArrays[i][j][-2])
print(sum)
# print(allArrays[i])
#print(allArrays.count(allArrays))


sum=0
#### part 2 #####
for currentRow in prodData:
    n=1
    allArrays=[]
    flipRow= np.flip(currentRow)
    allArrays.append( flipRow)
    while True:
        nderiv=np.diff(flipRow,n)
        allArrays.append(nderiv)
        #print(nderiv)
        n=n+1
        if nderiv.any():
            print("nth deriv is noo all zero "+ str(n))
        else:
            break
    #print(allArrays)
    resultArray=[]
    n=0
    for i in reversed(range(len(allArrays))):
        if i>0:
            nextVal=0
            rowToModify = allArrays[i-1]
            if (n == 0):
                nextVal= allArrays[i][-1]+allArrays[i-1][-1]
            else: 
                nextVal = resultArray[n-1][-1]+rowToModify[-1]
            x=np.append(rowToModify,nextVal)
            #sum=sum+nextVal
            #print(x)
            print("nextval is "+str(nextVal)+" and sum so far is: "+str(sum))
            
            resultArray.append(x)
           # print(resultArray)
            n=n+1
            #print(allArrays[i-1][j])
        else:
            print(resultArray[-1][-1])
            
            sum = sum + resultArray[-1][-1]
            # print("new value for: " + str(i-1) +" " + str(allArrays[i][j][-1]+allArrays[i-1][j][-2]))
            
            #allArrays[i-1][j].append(allArrays[i][j][-1]+allArrays[i][j][-2])
print(sum)