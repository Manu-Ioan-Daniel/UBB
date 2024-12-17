#bingo sort
def my_bingo_sort(list,key=None,reverse=False):
    if key is None:
         key=lambda x:x
    if len(list)<=1:
        return list
    index=0
    while(index<len(list)):
        if reverse:
            min_sau_max=max(list[index:],key=key)
        else:
            min_sau_max=min(list[index:],key=key)

        for i in range(index,len(list)):
            if key(list[i])==key(min_sau_max):
                list[index],list[i]=list[i],list[index]
                index+=1
    return list
